package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * User: wudong, Date: 03/09/13, Time: 16:35
 */
public class CcLineObject {

	public List<CC> ccs = new ArrayList<CC>();

	public static class CC {
		public CCTopicEnum topic;
		public Object object;
	}

	public static class AlternativeProducts {
		public List<String> events = new ArrayList<String>();
		public String namedIsoforms;
		public String comment;

		public List<AlternativeProductName> names = new ArrayList<AlternativeProductName>();
	}

	public static class AlternativeProductName {
		public String name;
		public List<String> synNames = new ArrayList<String>();
		public List<String> isoId = new ArrayList<String>();
		public List<String> sequence_FTId = new ArrayList<String>();
		public AlternativeNameSequenceEnum sequence_enum = null;
		public String note;
	}

	public static class BiophysicochemicalProperties {
		public int bsorption_abs;
		public String bsorption_note;
		public List<String> kms = new ArrayList<String>();
		public List<String> vmaxs = new ArrayList<String>();
		public String kp_note;
		public String ph_dependence;
		public String rdox_potential;
		public String temperature_dependence;
	}

	public static enum AlternativeNameSequenceEnum {
		Displayed, External, Not_described, Unsure
	}


	public static class WebResource {
		public String name;
		public String url;
		public String note;
	}


	public static enum CCTopicEnum {
		ALLERGEN, BIOTECHNOLOGY, CATALYTIC_ACTIVITY, CAUTION, COFACTOR,
		DEVELOPMENTAL_STAGE, DISEASE, DISRUPTION_PHENOTYPE, DOMAIN,
		ENZYME_REGULATION, FUNCTION, INDUCTION, MISCELLANEOUS,
		PATHWAY, PHARMACEUTICAL, POLYMORPHISM, PTM, SIMILARITY,
		SUBUNIT, TISSUE_SPECIFICITY, TOXIC_DOSE, ALTERNATIVE_PRODUCTS,
		BIOPHYSICOCHEMICAL_PROPERTIES, WEB_RESOURCE, INTERACTION,
		SUBCELLULAR_LOCATION, SEQUENCE_CAUTION, MASS_SPECTROMETRY, RNA_EDITING;

		public static CCTopicEnum fromSting(String s) {
			String replace = s.replace(' ', '_');
			return CCTopicEnum.valueOf(replace);
		}
	}

	public static class Interaction {
		public List<InteractionObject> interactions = new ArrayList<InteractionObject>();
	}

	public static class InteractionObject {
		public boolean isSelf;
		public String spAc;
		public String gene;
		public boolean xeno;
		public int nbexp;
		public String firstId;
		public String secondId;
	}

	public static class SubcullarLocation {
		 public String molecule;
		 public List<LocationObject> locations = new ArrayList<LocationObject>();
		 public List<SubcullarLocationNote> notes = new ArrayList<SubcullarLocationNote>();
	}

	public static class SubcullarLocationNote{
		public String note;
		public LocationFlagEnum noteFlag;
	}

	public static class LocationObject {
		public String subcellular_location;
		public LocationFlagEnum subcellular_location_flag;
		public String topology;
		public LocationFlagEnum topology_flag;
		public String orientation;
		public LocationFlagEnum orientation_flag;
	}

	public static enum LocationFlagEnum {
		By_similarity, Probable, Potential;

		public static LocationFlagEnum fromSting(String s) {
			String replace = s.replace(' ', '_');
			return LocationFlagEnum.valueOf(replace);
		}
	}

	public static class RnaEditing {
		public RnaEditingLocationEnum locationEnum;
		public List<Integer> locations = new ArrayList<Integer>();
		public String note;
	}

	public static enum RnaEditingLocationEnum {
		Undetermined , Not_applicable
	}

	public static class SequenceCaution {
		public List<SequenceCautionObject> sequenceCautionObjects = new ArrayList<SequenceCautionObject>();
	}

	public static enum SequenceCautionType {
		Frameshift,
		Erroneous_initiation,
		Erroneous_termination,
		Erroneous_gene_model_prediction,
		Erroneous_translation,
		Miscellaneous_discrepancy;

		public static SequenceCautionType fromSting(String s) {
			String replace = s.replace(' ', '_');
			return SequenceCautionType.valueOf(replace);
		}
	}

	public static class SequenceCautionObject {
		public String sequence;
		public SequenceCautionType type;
		public List<Integer> positions = new ArrayList<Integer>();
		public String positionValue;
		public String note;
	}


	public static class MassSpectrometry {
		public float mass;
		public float mass_error;
		public String method;
		public List<MassSpectrometryRange> ranges = new ArrayList<MassSpectrometryRange>();
		public String range_isoform;
		public String note;
		public String source;
	}

	public static class MassSpectrometryRange {
		public int start;
		public boolean start_unknown;
		public int end;
		public boolean end_unknown;
	}

	public static class Disease {
		public String name;
		public String abbr;
		public String mim;
		public List<DiseaseText> descriptions = new ArrayList<DiseaseText>();
		public List<DiseaseText> notes = new ArrayList<DiseaseText>();
	}

	public static class DiseaseText {
		public String text;
		public List<String> pubmedid = new ArrayList<String>();
	}
}
