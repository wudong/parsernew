package uk.ac.ebi.uniprot.parser.impl.rn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class RnLineConverter implements Converter<RnLineObject, List<EvidenceId>> {

	@Override
	public List<EvidenceId> convert(RnLineObject f) {
		Map<Object, List<EvidenceId> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		List<EvidenceId> evIds = evidences.get(f.number);
		if(evIds ==null)
			evIds = new ArrayList<>();
		return evIds;
	}

}
