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

	public static class BiophysicochemicalProperties{
		public String bsorption_note;
		public int bsorption_abs;
		public List<String> kms= new ArrayList<String>();
		public List<String> vmaxs= new ArrayList<String>();
		public String kp_note;
		public String ph_dependence;
		public String rdox_potential;
		public String temperature_dependence;
	}

	public static enum AlternativeNameSequenceEnum {
		Displayed, External, Not_described
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
		SUBUNIT, TISSUE_SPECIFICITY, TOXIC_DOSE, ALTERNATIVE_PRODUCTS, BIOPHYSICOCHEMICAL_PROPERTIES, WEB_RESOURCE, INTERACTION;

		public static CCTopicEnum fromSting(String s) {
			String replace = s.replace(' ', '_');
			return CCTopicEnum.valueOf(replace);
		}
	}

	public static class Interaction {
		public List<InteractionObject> interactions = new ArrayList<InteractionObject>();
	}

	public static class InteractionObject {}
}
