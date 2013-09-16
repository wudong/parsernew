package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.NcbiTaxonomyId;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineObject;

public class OxLineConverterTest {
	@Test
	public void test(){
	//	OX   NCBI_TaxID=9606;
		OxLineObject osO = new OxLineObject();
		osO.taxonomy_id =9606;
		OxLineConverter converter = new OxLineConverter();
		NcbiTaxonomyId taxId =converter.convert(osO);
		TestCase.assertEquals("9606", taxId.getValue());
		
		
	}
	
}
