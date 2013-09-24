package uk.ac.ebi.uniprot.parser.impl.de;

import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.parser.impl.HasEvidence;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * User: wudong, Date: 29/08/13, Time: 11:48
 */
public class DeLineObject implements HasEvidence{
	public Name recName;
	public List<Name> altName = new ArrayList<Name>();
	public List<Name> subName = new ArrayList<Name>();
	public String alt_Allergen;
	public String alt_Biotech;
	public List<String> alt_CD_antigen = new ArrayList<String>();
	public List<String> alt_INN = new ArrayList<String>();

	public List<NameBlock> containedNames = new ArrayList<NameBlock>();
	public List<NameBlock> includedNames = new ArrayList<NameBlock>();

	public List<FlagType> flags = new ArrayList<FlagType>();

	public EvidenceInfo evidenceInfo = new EvidenceInfo();

	@Override
	public EvidenceInfo getEvidenceInfo() {
		return evidenceInfo;
	}

	public static class NameBlock {
		public Name recName;
		public List<Name> altName = new ArrayList<Name>();
		public List<Name> subName = new ArrayList<Name>();
		public String alt_Allergen;
		public String alt_Biotech;
		public List<String> alt_CD_antigen = new ArrayList<String>();
		public List<String> alt_INN = new ArrayList<String>();
	}

	public static class Name {
		public String fullName;
		public List<String> shortNames = new ArrayList<String>();
		public List<String> ecs = new ArrayList<String>();
	}

	public static enum FlagType {
		Precursor, Fragment, Precursor_Fragment, Fragments
	}
}
