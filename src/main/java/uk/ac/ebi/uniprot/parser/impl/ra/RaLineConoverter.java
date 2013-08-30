package uk.ac.ebi.uniprot.parser.impl.ra;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Author;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class RaLineConoverter implements Converter<RaLineObject, List<Author> > {

	@Override
	public List<Author> convert(RaLineObject f) {
		List<Author> authors =new ArrayList<>();
		for(String val:f.authors){
			authors.add(DefaultCitationNewFactory.getInstance().buildAuthor(val));
		}
		return authors;
	}

}
