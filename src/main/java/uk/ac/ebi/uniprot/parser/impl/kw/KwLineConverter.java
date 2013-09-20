package uk.ac.ebi.uniprot.parser.impl.kw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.Keyword;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class KwLineConverter implements Converter<KwLineObject, List<Keyword> > {
	@Override
	public List<Keyword> convert(KwLineObject f) {
		Map<Object, List<EvidenceId> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		List<Keyword> keywords =new ArrayList<>();
		for(String kw: f.keywords){
			Keyword keyword = DefaultUniProtFactory.getInstance().buildKeyword(kw);
			EvidenceHelper.setEvidences(keyword, evidences, kw);
			keywords.add(keyword);
		}	
		return keywords;
	}

}
