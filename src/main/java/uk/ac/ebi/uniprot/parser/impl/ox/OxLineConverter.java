package uk.ac.ebi.uniprot.parser.impl.ox;

import uk.ac.ebi.kraken.interfaces.uniprot.NcbiTaxonomyId;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class OxLineConverter implements Converter<OxLineObject, NcbiTaxonomyId> {

	@Override
	public NcbiTaxonomyId convert(OxLineObject f) {
		NcbiTaxonomyId taxid = DefaultUniProtFactory.getInstance().buildNcbiTaxonomyId();
		taxid.setValue("" +f.taxonomy_id);
		return taxid;
	}

}
