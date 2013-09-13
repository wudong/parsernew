package uk.ac.ebi.uniprot.parser.impl.oh;

import uk.ac.ebi.kraken.interfaces.uniprot.Organism;
import uk.ac.ebi.kraken.interfaces.uniprot.OrganismHost;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class OhLineConverter implements Converter<OhLineObject, OrganismHost> {
	private DefaultUniProtFactory factory =DefaultUniProtFactory.getInstance();
	@Override
	public OrganismHost convert(OhLineObject f) {
		 Organism org = factory.buildOrganism();
		 org.setScientificName(factory.buildOrganismScientificName(f.hostname));

		OrganismHost organismHost = DefaultUniProtFactory.getInstance().buildOrganismHost();
		organismHost.setOrganism(org);
		organismHost.setNcbiTaxonomyId(DefaultUniProtFactory.getInstance().buildNcbiTaxonomyId("" +f.tax_id));

		return organismHost;
	}

}
