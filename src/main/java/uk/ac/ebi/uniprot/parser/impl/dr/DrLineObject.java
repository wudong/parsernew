package uk.ac.ebi.uniprot.parser.impl.dr;

import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;
import uk.ac.ebi.uniprot.parser.impl.HasEvidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 13/08/13
 * Time: 09:20
 * To change this template use File | Settings | File Templates.
 */
public class DrLineObject implements HasEvidence{

    public List<DrObject> drObjects = new ArrayList<DrObject>();
	public EvidenceInfo evidenceInfo = new EvidenceInfo();

	@Override
	public EvidenceInfo getEvidenceInfo() {
		return evidenceInfo;
	}

	public static class DrObject {
        public String DbName;
        public List<String> attributes = new ArrayList<String>();
    }
}
