package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import uk.ac.ebi.kraken.interfaces.factories.FeatureFactory;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureLocationModifier;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureSequence;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HasAlternativeSequence;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HasFeatureDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HasFeatureId;
import uk.ac.ebi.kraken.interfaces.uniprot.features.MutagenFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.VarSeqFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.VariantFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.VariantReport;
import uk.ac.ebi.kraken.model.factories.DefaultFeatureFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class FtLineConverter implements Converter<FtLineObject, List<Feature>> {
	private final FeatureFactory factory =DefaultFeatureFactory.getInstance();
	@Override
	public List<Feature> convert(FtLineObject f) {
		List<Feature> features = new ArrayList<Feature>();
		for(FtLineObject.FT ft: f.fts){
			Feature feature = factory.buildFeature(convert(ft.type));
			updateFeatureLocation(feature, ft.location_start, ft.location_end);
			String text = consumeFeatureStatus(feature, ft.ft_text);
			if(feature instanceof HasFeatureDescription ){
				((HasFeatureDescription) feature).setFeatureDescription(factory.buildFeatureDescription(text));
			}
			if(feature instanceof HasFeatureId){
				((HasFeatureId) feature).setFeatureId(factory.buildFeatureId(ft.ftId));
			}
			if(feature instanceof MutagenFeature){
				text = consumeMutagenReport((MutagenFeature) feature, text);
			}
			if(feature instanceof VariantFeature){
				text = consumeVariantReport((VariantFeature) feature, text);
			}
			if(feature instanceof VarSeqFeature){
				text = consumeVarSplicFeature((VarSeqFeature) feature, text);
			}
			if(feature instanceof HasAlternativeSequence){
				consumeAlternativeSequence((HasAlternativeSequence) feature, text);
			}
			features.add(feature);
		}
		return features;
	}
	
	 private  void consumeAlternativeSequence(HasAlternativeSequence feature, String value) {
	        if (value.equalsIgnoreCase("Missing") || value.startsWith("Missing")) {
	            return;
	        }

	        int index = value.indexOf("->");
	        String alternativeSequence = value.substring(index + 2).replaceAll("\n", "").replaceAll(" ", "");
	        StringTokenizer st = new StringTokenizer(alternativeSequence, ",:or");


	        while (st.hasMoreTokens()) {
	            FeatureSequence alt = DefaultFeatureFactory.getInstance().buildFeatureSequence(st.nextToken().trim());
	            feature.getAlternativeSequences().add(alt);
	        }

	        FeatureSequence original = DefaultFeatureFactory.getInstance().buildFeatureSequence(value.substring(0, index).trim().replaceAll("\\s*", ""));
	        feature.setOriginalSequence(original);

	    }

	 private  String consumeVarSplicFeature(VarSeqFeature feature, String value) {

	        int index = value.indexOf("(");
	        if (index == -1) {
	            return value.substring(0, value.length());
	        }

	        value = value.replaceAll("\\(in", "(");
	        String reportString = value.substring(index + 1, value.length() - 1);

	        reportString = reportString.replaceAll("\n", "");
	        reportString = reportString.replaceAll(" and ", ",");
//			reportString = reportString.replaceAll("in ", "");
	        reportString = reportString.replaceAll("isoform ", "");

	        StringTokenizer st = new StringTokenizer(reportString, ",");

	        while (st.hasMoreTokens()) {
	            feature.getVarsplicIsoforms().add(DefaultFeatureFactory.getInstance().buildVarsplicIsoform(st.nextToken().trim()));
	        }
	        return value.substring(0, index).trim();
	    }
	
	  private String consumeVariantReport(VariantFeature feature, String value) {
	        int index = value.indexOf("(");
	        if (index == -1) {
	            return value.substring(0, value.length());
	        }

	        String reportString = value.substring(index + 1, value.length() - 1);

	        reportString = reportString.replaceAll("\n", " ");
	        VariantReport aVariantReport = DefaultFeatureFactory.getInstance().buildVariantReport();
	      
	        aVariantReport.setValue(reportString);
	        feature.setVariantReport(aVariantReport);
	        value = value.substring(0, index);
	        return value;
	    }

	 private  String consumeMutagenReport(MutagenFeature feature, String value) {
	        int index = value.indexOf(": ");
	        if (index == -1) {
	            return value.substring(0, value.length() - 1);
	        }

	        String reportString = value.substring(index + 2, value.length());
	        feature.setMutagenReport(DefaultFeatureFactory.getInstance().buildMutagenReport(reportString));
	        return value.substring(0, index).trim();
	    }

	
	  private  String consumeFeatureStatus(Feature feature, String annotation) {
		  if(annotation ==null)
			  return null;

	        annotation = annotation.trim();
	        if (annotation.equals("By similarity")) {
	            feature.setFeatureStatus(FeatureStatus.BY_SIMILARITY);
	            return "";
	        } else if (annotation.equals("Potential")) {
	            feature.setFeatureStatus(FeatureStatus.POTENTIAL);
	            return "";
	        } else if (annotation.equals("Probable")) {
	            feature.setFeatureStatus(FeatureStatus.PROBABLE);
	            return "";
	        } else if (annotation.endsWith(("(By similarity)"))) {
	            feature.setFeatureStatus(FeatureStatus.BY_SIMILARITY);
	            return annotation.substring(0, annotation.length() - 15).trim();
	        } else if (annotation.endsWith(("(Potential)"))) {
	            feature.setFeatureStatus(FeatureStatus.POTENTIAL);
	            return annotation.substring(0, annotation.length() - 12).trim();
	        } else if (annotation.endsWith(("(Probable)"))) {
	            feature.setFeatureStatus(FeatureStatus.PROBABLE);
	            return annotation.substring(0, annotation.length() - 10).trim();
	        }

	        return annotation.trim();
	    }
	private  void  updateFeatureLocation(Feature feature, String locationStart, String locationEnd) {
		if(locationStart ==null){
			 feature.getFeatureLocation().setStartModifier(FeatureLocationModifier.UNKOWN);
		}else if (locationStart.trim().isEmpty()){
			 feature.getFeatureLocation().setStartModifier(FeatureLocationModifier.UNKOWN);
		}else {
			locationStart = locationStart.trim();
			char c =locationStart.charAt(0);
			if(c =='?'){
				feature.getFeatureLocation().setStartModifier(FeatureLocationModifier.UNSURE);
				if(locationStart.length()>1){
					String val = locationStart.substring(1);
					feature.getFeatureLocation().setStart(Integer.parseInt(val.trim()));
				}
			}else if (c=='<'){
				feature.getFeatureLocation().setStartModifier(FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE);
				if(locationStart.length()>1){
					String val = locationStart.substring(1);
					feature.getFeatureLocation().setStart(Integer.parseInt(val.trim()));
				}
			}
			else {
				feature.getFeatureLocation().setStartModifier(FeatureLocationModifier.EXACT);
				feature.getFeatureLocation().setStart(Integer.parseInt(locationStart));
			}
		}
		if(locationEnd ==null){
			 feature.getFeatureLocation().setEndModifier(FeatureLocationModifier.UNKOWN);
		}else if (locationEnd.trim().isEmpty()){
			 feature.getFeatureLocation().setEndModifier(FeatureLocationModifier.UNKOWN);
		}else {
			locationEnd = locationEnd.trim();
			char c =locationEnd.charAt(0);
			if(c =='?'){
				feature.getFeatureLocation().setEndModifier(FeatureLocationModifier.UNSURE);
				if(locationEnd.length()>1){
					String val = locationEnd.substring(1);
					feature.getFeatureLocation().setEnd(Integer.parseInt(val.trim()));
				}
			}else if (c=='>'){
				feature.getFeatureLocation().setEndModifier(FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE);
				if(locationEnd.length()>1){
					String val = locationEnd.substring(1);
					feature.getFeatureLocation().setEnd(Integer.parseInt(val.trim()));
				}
			}
			else {
				feature.getFeatureLocation().setEndModifier(FeatureLocationModifier.EXACT);
				feature.getFeatureLocation().setEnd(Integer.parseInt(locationEnd));
			}
		}
	}
	
	private FeatureType convert(FtLineObject.FTType type ){
		FeatureType ftype =FeatureType.ACT_SITE;
		switch(type) {
		case INIT_MET:
			ftype = FeatureType.INIT_MET;
			break;
		case SIGNAL:
			ftype =FeatureType.SIGNAL;
			break;
		case PROPEP:
			ftype =FeatureType.PROPEP;
			break;
		case TRANSIT:
			ftype =FeatureType.TRANSIT;
			break;
		case CHAIN:
			ftype =FeatureType.CHAIN;
			break;
		case PEPTIDE:
			ftype =FeatureType.PEPTIDE;
			break;
		case TOPO_DOM:
			ftype =FeatureType.TOPO_DOM;
			break;
		case TRANSMEM:
			ftype =FeatureType.TRANSMEM;
			break;
		case INTRAMEM: 
			ftype =FeatureType.INTRAMEM;
			break;
		case DOMAIN:
			ftype =FeatureType.DOMAIN;
			break;
		case REPEAT:
			ftype =FeatureType.REPEAT;
			break;
		case CA_BIND:
			ftype =FeatureType.CA_BIND;
			break;
		case ZN_FING: 
			ftype =FeatureType.ZN_FING;
			break;
		case DNA_BIND:
			ftype =FeatureType.DNA_BIND;
			break; 
		case NP_BIND:
			ftype =FeatureType.NP_BIND;
			break;
		case REGION: 
			ftype =FeatureType.REGION;
			break;
		case COILED:
			ftype =FeatureType.COILED;
			break;
		case MOTIF:
			ftype =FeatureType.MOTIF;
			break;
		case COMPBIAS:
			ftype =FeatureType.COMPBIAS;
			break;
		case ACT_SITE:
			ftype =FeatureType.ACT_SITE;
			break;
		case METAL:
			ftype =FeatureType.METAL;
			break;
		case BINDING: 
			ftype =FeatureType.BINDING;
			break;
		case SITE:
			ftype =FeatureType.SITE;
			break;
		case NON_STD:
			ftype =FeatureType.NON_STD;
			break;
		case MOD_RES:
			ftype =FeatureType.MOD_RES;
			break;
		case LIPID:
			ftype =FeatureType.LIPID;
			break;
		case CARBOHYD:
			ftype =FeatureType.CARBOHYD;
			break;
		case DISULFID:
			ftype =FeatureType.DISULFID;
			break;
		case CROSSLNK:
			ftype =FeatureType.CROSSLNK;
			break;
		case VAR_SEQ: 
			ftype =FeatureType.VAR_SEQ;
			break;
		case VARIANT:
			ftype =FeatureType.VARIANT;
			break;
		case MUTAGEN: 
			ftype =FeatureType.MUTAGEN;
			break;
		case UNSURE:
			ftype =FeatureType.UNSURE;
			break;
		case CONFLICT:
			ftype =FeatureType.CONFLICT;
			break;
		case NON_CONS:
			ftype =FeatureType.NON_CONS;
			break;
		case NON_TER: 
			ftype =FeatureType.NON_TER;
			break;
		case HELIX:
			ftype =FeatureType.HELIX;
			break;
		case STRAND:
			ftype =FeatureType.STRAND;
			break;
		case TURN:
			ftype =FeatureType.TURN;
			break;
		}
		return ftype;
	}
}
