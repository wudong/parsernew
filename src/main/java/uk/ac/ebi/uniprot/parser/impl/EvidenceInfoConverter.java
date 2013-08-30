package uk.ac.ebi.uniprot.parser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.factories.DefaultEvidenceFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class EvidenceInfoConverter implements Converter<EvidenceInfo, Map<Object, List<EvidenceId>> > {

	@Override
	public Map<Object, List<EvidenceId>> convert(EvidenceInfo f) {
		 Map<Object, List<EvidenceId>> evidences =new HashMap<>();
		 for(Map.Entry<Object, List<String> > entry:f.evidences.entrySet() ){
			 evidences.put(entry.getKey(), convert(entry.getValue()));
		 }
		 return evidences;
	}

	private List<EvidenceId> convert(List<String> evStrs){
		List<EvidenceId> evIds = new ArrayList<>();
		for(String evStr:evStrs){
			evIds.add(DefaultEvidenceFactory.getInstance().buildEvidenceId(evStr));
		}
		return evIds;
	}
}
