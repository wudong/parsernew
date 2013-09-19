package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.CommentStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Absorption;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Comment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DiseaseCommentStructured;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Interaction;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.InteractionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.InteractionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSequenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSynonym;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.KineticParameters;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryCommentSource;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryMethod;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MaximumVelocityUnit;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstantUnit;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.PHDependence;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Position;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RedoxPotential;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingLocationType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingNote;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionPosition;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocation;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocationComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularLocationValue;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SubcellularMolecule;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.TemperatureDependence;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.TextOnlyComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.WebResourceComment;
import uk.ac.ebi.kraken.model.factories.DefaultCommentFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class CcLineConverter implements Converter<CcLineObject, List<Comment> > {
	private final DefaultCommentFactory factory = DefaultCommentFactory.getInstance();
	@Override
	public List<Comment> convert(CcLineObject f) {
		List<Comment> comments = new ArrayList<>();
		for(CcLineObject.CC cc: f.ccs){
			if(cc.topic ==CcLineObject.CCTopicEnum.SEQUENCE_CAUTION){
				comments.addAll(convertSequenceCaution(cc));
			}else
				comments.add(convert(cc));
		}
		return comments;
	}
	
	private List<SequenceCautionComment> convertSequenceCaution(CcLineObject.CC cc){
		List<SequenceCautionComment> comments = new ArrayList<>();
		if(cc.topic !=CcLineObject.CCTopicEnum.SEQUENCE_CAUTION){
			return comments;
		}
		CcLineObject.SequenceCaution seqC = (CcLineObject.SequenceCaution)cc.object;
		for(CcLineObject.SequenceCautionObject cObj:seqC.sequenceCautionObjects){
			SequenceCautionComment comment = factory.buildComment(CommentType.SEQUENCE_CAUTION);
			if((cObj.note !=null) &&(!cObj.note.isEmpty())){
				comment.setNote(factory.buildSequenceCautionCommentNote(cObj.note));
			}
			//position could be multiple
			List<SequenceCautionPosition> positions = new ArrayList<>();
			for(Integer pos: cObj.positions){
				positions.add(factory.buildSequenceCautionPosition(pos.toString()));
			}
			comment.setPositions(positions);
			if(cObj.sequence !=null){
				comment.setSequence(cObj.sequence);
			}
			switch(cObj.type) {
			case Frameshift:
				comment.setType(SequenceCautionType.FRAMESHIFT);
				break;
			case Erroneous_initiation:
				comment.setType(SequenceCautionType.ERRONEOUS_INITIATION);
				break;
			case Erroneous_termination:
				comment.setType(SequenceCautionType.ERRONEOUS_TERMIINATION);
				break;
			case Erroneous_gene_model_prediction:
				comment.setType(SequenceCautionType.ERRONEOUS_PREDICTION);
				break;
			case Erroneous_translation:
				comment.setType(SequenceCautionType.ERRONEOUS_TRANSLATION);
				break;
			case Miscellaneous_discrepancy:
				comment.setType(SequenceCautionType.MISCELLANEOUS_DISCREPANCY);
				break;
			}
			comments.add(comment);
		
		}
		return comments;
	}
	private  <T extends Comment> T convert(CcLineObject.CC cc){
		CcLineObject.CCTopicEnum topic = cc.topic;
		
		CommentType type = convert(topic);
		T comment =factory.buildComment(type);
		switch(topic){
		case ALTERNATIVE_PRODUCTS:
			updateAlternativeProduct((AlternativeProductsComment)comment, 
					(CcLineObject.AlternativeProducts)cc.object);
			break;
		case BIOPHYSICOCHEMICAL_PROPERTIES:
			updateBiophyChem((BioPhysicoChemicalPropertiesComment)comment, 
					(CcLineObject.BiophysicochemicalProperties)cc.object);
			break;
		case WEB_RESOURCE:
			updateWebResource((WebResourceComment)comment, 
					(CcLineObject.WebResource)cc.object);
			break;
		case INTERACTION:
			updateInteraction((InteractionComment)comment, 
					(CcLineObject.Interaction)cc.object);
			break;
		case DISEASE:
			updateDisease((DiseaseCommentStructured)comment, 
					(CcLineObject.Interaction)cc.object);
			break;
		case MASS_SPECTROMETRY:
			updateMassSpectrometry((MassSpectrometryComment)comment, 
					(CcLineObject.MassSpectrometry)cc.object);
			break;
		case SUBCELLULAR_LOCATION:
			updateSubcellularLocation((SubcellularLocationComment) comment,
					(CcLineObject.SubcullarLocation)cc.object);
			break;
		case RNA_EDITING:
			updateRNAEditing((RnaEditingComment) comment, (CcLineObject.RnaEditing) cc.object);
			break;
		default:
				updateTextOnly((TextOnlyComment) comment, (String )cc.object);
		}
		return comment;
	
	}
	private void updateAlternativeProduct(AlternativeProductsComment comment, CcLineObject.AlternativeProducts cObj){
		for(String event: cObj.events){
			comment.getEvents().add(factory.buildAlternativeProductsEvent(event));
		}

		if((cObj.comment !=null) &&(!cObj.comment.isEmpty())){
			comment.setComment(factory.buildAlternativeProductsCommentComment(cObj.comment));
		}
		for(CcLineObject.AlternativeProductName name: cObj.names){
			AlternativeProductsIsoform aPIsoform =factory.buildAlternativeProductsIsoform();
			aPIsoform.setName(factory.buildIsoformName(name.name));
			if((name.note !=null) &&(!name.note.isEmpty()))
				aPIsoform.setNote(factory.buildIsoformNote(name.note));
			List<IsoformId> isoIds = new ArrayList<>();
			for(String isoId: name.isoId){
				isoIds.add(factory.buildIsoformId(isoId));
			}
			aPIsoform.setIds(isoIds);
			List<IsoformSynonym> isoSyns = new ArrayList<>();
			for(String syn: name.synNames){
				isoSyns.add(factory.buildIsoformSynonym(syn));
			}
			aPIsoform.setSynonyms(isoSyns);
			List<IsoformSequenceId> isoSeqIds = new ArrayList<>();
			for(String featId: name.sequence_FTId){
				isoSeqIds.add(factory.buildIsoformSequenceId(featId));
			}
			aPIsoform.setSequenceIds(isoSeqIds);
			if(name.sequence_enum !=null) {
			switch(name.sequence_enum){
			case Displayed:
				aPIsoform.setIsoformSequenceStatus(IsoformSequenceStatus.DISPLAYED);
				break;
			case External: 
				aPIsoform.setIsoformSequenceStatus(IsoformSequenceStatus.EXTERNAL);
				break;
			case Not_described:
				aPIsoform.setIsoformSequenceStatus(IsoformSequenceStatus.NOT_DESCRIBED);
				break;
			}
			}
			comment.getIsoforms().add(aPIsoform);
		}
	
	}
	private void updateBiophyChem(BioPhysicoChemicalPropertiesComment comment, CcLineObject.BiophysicochemicalProperties cObj){
	
		//has Kinetic parameter
		//  CC       Kinetic parameters:
        //CC         KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
		//CC         Celsius and at pH 7.6);
        //CC         Vmax=1.9 mmol/min/mg enzyme;
		
		if((cObj.kms.size()>0) || (cObj.vmaxs.size()>0)){
			KineticParameters kp = factory.buildKineticParameters();
			for (String kmStr: cObj.kms){
				MichaelisConstant km = factory.buildMichaelisConstant();
				int index = kmStr.indexOf(' ');
				String val = kmStr.substring(0, index).trim();
				kmStr = kmStr.substring(index+1).trim();
				index = kmStr.indexOf(' ');
				String unit = kmStr.substring(0, index).trim();
				kmStr = kmStr.substring(index+5).trim();
				double value = Double.parseDouble(val);
				km.setConstant((float)value);
				km.setUnit(MichaelisConstantUnit.convert(unit));
				km.setSubstrate(factory.buildSubstrate(kmStr));
				kp.getMichaelisConstants().add(km);
			}
			for(String vmaxStr:cObj.vmaxs){
				MaximumVelocity mv = factory.buildMaximumVelocity();
				int index = vmaxStr.indexOf(' ');
				String val = vmaxStr.substring(0, index).trim();
			
				vmaxStr = vmaxStr.substring(index+1).trim();
				index = vmaxStr.indexOf(' ');
				String unit = vmaxStr.substring(0, index).trim();
				vmaxStr = vmaxStr.substring(index+1).trim();
				double value = Double.parseDouble(val);
				mv.setUnit(MaximumVelocityUnit.convert(unit));
				mv.setVelocity((float) value);
				mv.setEnzyme(factory.buildEnzyme(vmaxStr));
				kp.getMaximumVelocities().add(mv);
			}
			if((cObj.kp_note !=null) &&(!cObj.kp_note.isEmpty())){
				kp.setNote(factory.buildKineticParameterNote(cObj.kp_note));
			}
			comment.setKineticParameters(kp);
		}
		// CC       pH dependence:
        //CC         Optimum pH is 7.75;
		if((cObj.ph_dependence !=null) &&(!cObj.ph_dependence.isEmpty())){
			PHDependence phDependence = factory.buildPHDependence(cObj.ph_dependence);
			comment.setPHDepencence(phDependence);
		}
		if((cObj.rdox_potential !=null) &&(!cObj.rdox_potential.isEmpty())){
			RedoxPotential redoxPotential = factory.buildRedoxPotential(cObj.rdox_potential);
			comment.setRedoxPotential(redoxPotential);
		}
		if((cObj.temperature_dependence !=null) &&(!cObj.temperature_dependence.isEmpty())){
			 TemperatureDependence temperatureDependence = factory.buildTemperatureDependence(cObj.temperature_dependence);
			 comment.setTemperatureDependence(temperatureDependence);
		}
		if(cObj.bsorption_abs>0){
			  Absorption absorption = factory.buildAbsorption();
			  absorption.setMax(cObj.bsorption_abs);
			  if((cObj.bsorption_note !=null) &&(!cObj.bsorption_note.isEmpty())){
				  absorption.setNote(factory.buildAbsorptionNote(cObj.bsorption_note));
			  }
			  comment.setAbsorption(absorption);
		}
		
	}
	
	private void updateWebResource(WebResourceComment comment, CcLineObject.WebResource cObj){
		comment.setDatabaseName(factory.buildDatabaseName(cObj.name));
		if((cObj.note !=null) &&(!cObj.note.isEmpty())){
			comment.setDatabaseNote(factory.buildDatabaseNote(cObj.note));
		}
		if(cObj.url.startsWith("ftp")){
			comment.setDatabaseFTP(factory.buildDatabaseFTP(cObj.url));
		}else
			comment.setDatabaseURL(factory.buildDatabaseURL(cObj.url));
	}
	private void updateInteraction(InteractionComment comment, CcLineObject.Interaction cObj){
		List<Interaction> interactions = new ArrayList<>();
		for( CcLineObject.InteractionObject io: cObj.interactions){
			Interaction interaction =factory.buildInteraction();
			
			if(!io.isSelf)
				interaction.setInteractorUniProtAccession(factory.buildInteractorUniProtAccession(io.spAc));
			if((io.gene !=null) &&(!io.gene.isEmpty())){
				interaction.setInteractionGeneName(factory.buildInteractionGeneName(io.gene));
			}
			if(io.xeno)
				interaction.setInteractionType(InteractionType.XENO);
			else if(io.isSelf){
				interaction.setInteractionType(InteractionType.SELF);
			}else
				interaction.setInteractionType(InteractionType.BINARY);
			interaction.setFirstInteractor(factory.buildIntActAccession(io.firstId));
			if((io.secondId !=null) &&(!io.secondId.isEmpty())){
				interaction.setSecondInteractor(factory.buildIntActAccession(io.secondId));
			}
			interaction.setNumberOfExperiments(io.nbexp);
			interactions.add(interaction);
		}
		comment.setInteractions(interactions);
	}
	private void updateDisease(DiseaseCommentStructured comment, CcLineObject.Interaction cObj){
		//to be implemented
	}
	
	private void updateSubcellularLocation(SubcellularLocationComment comment, CcLineObject.SubcullarLocation cObj){
		// to be implemented
		 SubcellularMolecule molecule = factory.buildSubcellularMolecule();
		 molecule.setValue(cObj.molecule);
		 comment.setSubcellularMolecule(molecule);
		 SubcellularLocationValue note = factory.buildSubcellularLocationNote();
		 note.setValue(cObj.note);
		 if(cObj.noteFlag !=null){
			 switch(cObj.noteFlag){
			 case By_similarity:
				 note.setCommentStatus(CommentStatus.BY_SIMILARITY);
				 break;
			 case Probable:
				 note.setCommentStatus(CommentStatus.PROBABLE);
				 break;
			 case Potential:
				 note.setCommentStatus(CommentStatus.PROBABLE);
				 break;
			 }
		 }
		 List<SubcellularLocation> locations = new ArrayList<SubcellularLocation>();
		 for(CcLineObject.LocationObject lo: cObj.locations){
			 SubcellularLocation location = factory.buildSubcellularLocation();
			 locations.add(location);
			 if((lo.subcellular_location !=null) &&(!lo.subcellular_location.isEmpty())){
				 SubcellularLocationValue locationVal= factory.buildSubcellularLocationValue();
				 locationVal.setValue(lo.subcellular_location);
				 if(lo.subcellular_location_flag !=null){
					 switch(lo.subcellular_location_flag){
					 case By_similarity:
						 locationVal.setCommentStatus(CommentStatus.BY_SIMILARITY);
						 break;
					 case Probable:
						 locationVal.setCommentStatus(CommentStatus.PROBABLE);
						 break;
					 case Potential:
						 locationVal.setCommentStatus(CommentStatus.PROBABLE);
						 break;
					 }
				 }
				 location.setLocation(locationVal);
			 }
			 if((lo.orientation !=null) &&(!lo.orientation.isEmpty())){
				 SubcellularLocationValue orientationVal= factory.buildSubcellularLocationValue();
				 orientationVal.setValue(lo.orientation);
				 if(lo.orientation_flag !=null){
					 switch(lo.orientation_flag){
					 case By_similarity:
						 orientationVal.setCommentStatus(CommentStatus.BY_SIMILARITY);
						 break;
					 case Probable:
						 orientationVal.setCommentStatus(CommentStatus.PROBABLE);
						 break;
					 case Potential:
						 orientationVal.setCommentStatus(CommentStatus.PROBABLE);
						 break;
					 }
				 }
				 location.setOrientation(orientationVal);
			 }
			 if((lo.topology !=null) &&(!lo.topology.isEmpty())){
				 SubcellularLocationValue topologyVal= factory.buildSubcellularLocationValue();
				 topologyVal.setValue(lo.topology);
				 if(lo.topology_flag !=null){
					 switch(lo.topology_flag){
					 case By_similarity:
						 topologyVal.setCommentStatus(CommentStatus.BY_SIMILARITY);
						 break;
					 case Probable:
						 topologyVal.setCommentStatus(CommentStatus.PROBABLE);
						 break;
					 case Potential:
						 topologyVal.setCommentStatus(CommentStatus.PROBABLE);
						 break;
					 }
				 }
				 location.setTopology(topologyVal);
			 }
			 
		 }
		 comment.setSubcellularLocations(locations);
		 
	}

	private void updateMassSpectrometry(MassSpectrometryComment comment, CcLineObject.MassSpectrometry cObj){
		comment.setMethod( MassSpectrometryMethod.toType(cObj.method));
		comment.setMolWeight(cObj.mass);
		comment.setMolWeightError(cObj.mass_error);
		if((cObj.range_note !=null) &&(!cObj.range_note.isEmpty())){
			comment.setNote(factory.buildMassSpectrometryCommentNote(cObj.range_note));
		}
		if((cObj.source !=null) &&(!cObj.source.isEmpty())){
			List<MassSpectrometryCommentSource> sources = new ArrayList<>();		
			sources.add(factory.buildMassSpectrometryCommentSource(cObj.source));
			comment.setSources(sources);
		}
		List<MassSpectrometryRange> ranges= new ArrayList<>();
		MassSpectrometryRange range = factory.buildMassSpectrometryRange();
		range.setStart(cObj.range_start);
		range.setEnd(cObj.range_end);
		if((cObj.range_isoform !=null) &&(! cObj.range_isoform.isEmpty()))
			range.setIsoformId(factory.buildMassSpectrometryIsoformId(cObj.range_isoform));
		ranges.add(range);
		comment.setRanges(ranges);
	}
	private void updateRNAEditing(RnaEditingComment comment, CcLineObject.RnaEditing cObj){
		List<Position> positions = new ArrayList<>();
		comment.setLocationType(RnaEditingLocationType.Known);
		for(int pos:cObj.locations){
			Position position = factory.buildRnaEditingPosition();
			position.setPosition(""+pos);
			positions.add(position);
		}
		comment.setPositions(positions);
		if((cObj.note !=null) &&(!cObj.note.isEmpty())){
			RnaEditingNote note = factory.buildRnaEditingNote();
			note.setValue(cObj.note);
			comment.setRnaEditingNote(note);
		}
	}
	private void updateTextOnly(TextOnlyComment comment, String cObj){
		if(cObj.endsWith("."))
			cObj = cObj.substring(0, cObj.length() -1);
		String value =setCommentStatus(cObj, comment );
		comment.setValue(value);
	}
	
	private  String setCommentStatus(String annotation, Comment comment) {
		if (annotation.toUpperCase().endsWith(" (POTENTIAL)")) {
			comment.setCommentStatus(CommentStatus.POTENTIAL);
			annotation = annotation.substring(0, annotation.length() - 11);
		} else if (annotation.toUpperCase().endsWith(" (PROBABLE)")) {
			comment.setCommentStatus(CommentStatus.PROBABLE);
			annotation = annotation.substring(0, annotation.length() - 11);
		} else if (annotation.toUpperCase().endsWith(" (BY SIMILARITY)")) {
			comment.setCommentStatus(CommentStatus.BY_SIMILARITY);
			annotation = annotation.substring(0, annotation.length() - 16);
		} else {
			comment.setCommentStatus(CommentStatus.EXPERIMENTAL);
		}
		return annotation;
	}
	private CommentType convert(CcLineObject.CCTopicEnum topic){
		CommentType type = CommentType.UNKNOWN;
		switch(topic){
		case ALLERGEN:
			type = CommentType.ALLERGEN;
			break;
		case BIOTECHNOLOGY:
			type = CommentType.BIOTECHNOLOGY;
			break;
		case CATALYTIC_ACTIVITY:
			type = CommentType.CATALYTIC_ACTIVITY;
			break;
		case CAUTION:
			type = CommentType.CAUTION;
			break;
		case COFACTOR:
			type = CommentType.COFACTOR;
			break;
		case DEVELOPMENTAL_STAGE:
			type = CommentType.DEVELOPMENTAL_STAGE;
			break;
		case DISEASE:
			type = CommentType.DISEASE;
			break;
		case DISRUPTION_PHENOTYPE:
			type = CommentType.DISRUPTION_PHENOTYPE;
			break;
		case DOMAIN:
			type = CommentType.DOMAIN;
			break;
		case ENZYME_REGULATION: 
			type = CommentType.ENZYME_REGULATION;
			break;
		case FUNCTION:
			type = CommentType.FUNCTION;
			break;
		case INDUCTION:
			type = CommentType.INDUCTION;
			break;
		case MISCELLANEOUS:
			type = CommentType.MISCELLANEOUS;
			break;
		case PATHWAY:
			type = CommentType.PATHWAY;
			break;
		case PHARMACEUTICAL:
			type = CommentType.PHARMACEUTICAL;
			break;
		case POLYMORPHISM:
			type = CommentType.POLYMORPHISM;
			break;
		case PTM:
			type = CommentType.PTM;
			break;
		case SIMILARITY:
			type = CommentType.SIMILARITY;
			break;		
		case SUBUNIT: 
			type = CommentType.SUBUNIT;
			break;
		case TISSUE_SPECIFICITY:
			type = CommentType.TISSUE_SPECIFICITY;
			break;
		case TOXIC_DOSE:
			type = CommentType.TOXIC_DOSE;
			break;
		case ALTERNATIVE_PRODUCTS:
			type = CommentType.ALTERNATIVE_PRODUCTS;
			break;
		case BIOPHYSICOCHEMICAL_PROPERTIES:
			type = CommentType.BIOPHYSICOCHEMICAL_PROPERTIES;
			break;
		case WEB_RESOURCE:
			type = CommentType.WEBRESOURCE;
			break;
		case INTERACTION:
			type = CommentType.INTERACTION;
			break;
		case MASS_SPECTROMETRY:
			type = CommentType.MASS_SPECTROMETRY;
			break;
		case RNA_EDITING:
			type = CommentType.RNA_EDITING;
			break;
		default:
			type = CommentType.UNKNOWN;
		}
		return type;
	}
}
