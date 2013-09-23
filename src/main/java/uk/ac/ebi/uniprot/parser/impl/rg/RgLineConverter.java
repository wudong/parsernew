package uk.ac.ebi.uniprot.parser.impl.rg;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.AuthoringGroup;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;

public class RgLineConverter implements Converter<RgLineObject, List<AuthoringGroup> > {

	@Override
	public List<AuthoringGroup> convert(RgLineObject f) {
		List<AuthoringGroup> ags = new ArrayList<>();
		if((f.reference_group != null) && (!f.reference_group.isEmpty())){
			ags.add(DefaultCitationNewFactory.getInstance().buildAuthoringGroup(f.reference_group));
		}
		return ags;
	}

}
