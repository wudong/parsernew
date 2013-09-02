package uk.ac.ebi.uniprot.parser.impl.rt;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Title;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class RtLineConverter implements Converter<RtLineObject, Title> {

	@Override
	public Title convert(RtLineObject f) {
		Title title = DefaultCitationNewFactory.getInstance().buildTitle();
		title.setValue(f.title);
		return title;
	}

}
