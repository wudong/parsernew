package uk.ac.ebi.kraken.parser.converter;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.NcbiTaxonomyId;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
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
	@Test
	public void testEvidence(){
	//	OX   NCBI_TaxID=9606{EI1,EI2};
		OxLineObject osO = new OxLineObject();
		osO.taxonomy_id =9606;
		List<String> evIds = new ArrayList<String>();
		evIds.add("EI1");
		evIds.add("EI2");
		osO.evidenceInfo.evidences.put(osO.taxonomy_id, evIds);
		
		
		OxLineConverter converter = new OxLineConverter();
		NcbiTaxonomyId taxId =converter.convert(osO);
		TestCase.assertEquals("9606", taxId.getValue());	
		List<EvidenceId> eviIds = taxId.getEvidenceIds();
		assertEquals(2, eviIds.size());
		EvidenceId eviId = eviIds.get(0);
		EvidenceId eviId2 = eviIds.get(1);
		assertEquals("EI1", eviId.getValue());
		assertEquals("EI2", eviId2.getValue());
	}
}
