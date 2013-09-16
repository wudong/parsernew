package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.OrganismHost;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineObject;

public class OhLineConverterTest {
	@Test
	public void test(){
		//"OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).
		OhLineObject ohO = new OhLineObject();
		ohO.tax_id = 9598;
		ohO.hostname ="Pan troglodytes (Chimpanzee)";
		OhLineConverter converter = new OhLineConverter();
		OrganismHost oh=converter.convert(ohO);
		
		TestCase.assertEquals("9598", oh.getNcbiTaxonomyId().getValue());
		
		TestCase.assertEquals("Pan troglodytes (Chimpanzee)", oh.getOrganism().getScientificName().getValue());
	}
}
