package uk.ac.ebi.uniprot.parser.impl.rg;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.AuthoringGroup;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

import java.util.ArrayList;
import java.util.List;

public class RgLineConverter implements Converter<RgLineObject, List<AuthoringGroup> > {

	@Override
	public List<AuthoringGroup> convert(RgLineObject f) {
		List<AuthoringGroup> ags = new ArrayList<>();

		List<String> reference_groups = f.reference_groups;
		for (String reference_group : reference_groups) {
			ags.add(DefaultCitationNewFactory.getInstance().buildAuthoringGroup(reference_group));
		}

		return ags;
	}

}
