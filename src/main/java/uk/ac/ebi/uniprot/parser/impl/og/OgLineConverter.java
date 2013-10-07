package uk.ac.ebi.uniprot.parser.impl.og;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.GeneEncodingType;
import uk.ac.ebi.kraken.interfaces.uniprot.Organelle;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class OgLineConverter implements Converter<OgLineObject, List<Organelle> > {
	private final DefaultUniProtFactory factory = DefaultUniProtFactory.getInstance();
	@Override
	public List<Organelle> convert(OgLineObject f) {
		List<Organelle> organelles = new ArrayList<Organelle>();
 	 Map<Object, List<EvidenceId>> evidenceMap =EvidenceHelper.convert(f.getEvidenceInfo());
		for(OgLineObject.OgEnum ogEnum: f.ogs){
			GeneEncodingType type = GeneEncodingType.UNKOWN;
			switch (ogEnum){
			case HYDROGENOSOME:
				type = GeneEncodingType.HYDROGENOSOME;
				break;
			case MITOCHONDRION:
				type = GeneEncodingType.MITOCHONDRION;
				break;
			case NUCLEOMORPH:
				type = GeneEncodingType.NUCLEOMORPH;
				break;
			case PLASTID:
				type = GeneEncodingType.PLASTID;
				break;
			case PLASTID_APICOPLAST:
				type = GeneEncodingType.APICOPLAST_PLASTID;
				break;
			case PLASTID_CHLOROPLAST:
				type = GeneEncodingType.CHLOROPLAST_PLASTID;
				break;
			case PLASTID_ORGANELLAR_CHROMATOPHORE:
				type = GeneEncodingType.CHROMATOPHORE_PLASTID;
				break;
			case PLASTID_CYANELLE:
				type = GeneEncodingType.CYANELLE_PLASTID;
				break;
			case PLASTID_NON_PHOTOSYNTHETIC:
				type = GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID;
				break;
			default:
				type = GeneEncodingType.UNKOWN;
				break;
			
			}
			 Organelle org = factory.buildOrganelle(type);
			 EvidenceHelper.setEvidences( org, evidenceMap, ogEnum);
			 organelles.add(org);
		}
	
		for(String val: f.plasmidNames){
			 Organelle org = factory.buildOrganelle(GeneEncodingType.PLASMID);
			 org.setValue(val);
			 EvidenceHelper.setEvidences( org, evidenceMap, val);
			 organelles.add(org);
		}
		return organelles;
	}
	
}
