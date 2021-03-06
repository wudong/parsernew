package uk.ac.ebi.uniprot.parser.impl;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.HasEvidences;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;

public class EvidenceHelper {
	private static  EvidenceInfoConverter evConverter = new EvidenceInfoConverter();
	public static void setEvidences(HasEvidences he, Map<Object, List<EvidenceId> > evidences, Object obj){
		List<EvidenceId> evIds = evidences.get(obj);
		if(evIds !=null)
			he.setEvidenceIds(evIds);
	}
	public static Map<Object, List<EvidenceId>> convert(EvidenceInfo f) {
		return evConverter.convert(f);
	}
}
