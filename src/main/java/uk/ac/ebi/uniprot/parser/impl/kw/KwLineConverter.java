package uk.ac.ebi.uniprot.parser.impl.kw;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.Keyword;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class KwLineConverter implements Converter<KwLineObject, List<Keyword> > {

	@Override
	public List<Keyword> convert(KwLineObject f) {
		List<Keyword> keywords =new ArrayList<>();
		for(String kw: f.keywords){
			Keyword keyword = DefaultUniProtFactory.getInstance().buildKeyword(kw);
		//	keyword.setEvidenceIds(evidences)
			keywords.add(keyword);
		}	
		return keywords;
	}

}
