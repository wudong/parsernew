package uk.ac.ebi.kraken.parser.converter;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineObject;

public class RnLineConverterTest {
	@Test
	public void test(){
	//	 "RN   [11]\n";
		RnLineObject osO = new RnLineObject();
		osO.number=11;
		RnLineConverter converter = new RnLineConverter();
		List<EvidenceId>  evIds =converter.convert(osO);
		assertEquals(0, evIds.size());
		
	}
	@Test
	public void testEvidence(){
//		 "RN   [11]{[EI1][EI2]}\n";
		RnLineObject osO = new RnLineObject();
		osO.number=11;
		List<String> evIds = new ArrayList<String>();
		evIds.add("EI1");
		evIds.add("EI2");
		osO.evidenceInfo.evidences.put(osO.number, evIds);
		
		RnLineConverter converter = new RnLineConverter();
		List<EvidenceId>  eviIds =converter.convert(osO);
		
		assertEquals(2, eviIds.size());
		EvidenceId eviId = eviIds.get(0);
		EvidenceId eviId2 = eviIds.get(1);
		assertEquals("EI1", eviId.getValue());
		assertEquals("EI2", eviId2.getValue());
	}
}
