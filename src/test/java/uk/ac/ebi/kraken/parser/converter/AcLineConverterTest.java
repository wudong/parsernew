package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.SecondaryUniProtAccession;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.parser.impl.ac.UniProtAcLineObject;


public class AcLineConverterTest {
	private AcLineConverter converter = new AcLineConverter();
	@Test
	public void testConverter() throws Exception{
		//val ac_one_line_moreacc = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
		AcLineObject acObj = new AcLineObject();
		acObj.primaryAcc ="Q6GZX4";
		acObj.secondaryAcc.add("Q6GZX5");
		acObj.secondaryAcc.add("Q6GZX6");
		
		UniProtAcLineObject uniObj = converter.convert(acObj);
		TestCase.assertEquals("Q6GZX4", uniObj.primaryAccession.getValue());
		TestCase.assertEquals(2, uniObj.secondAccessions.size());
		testSecondAccIn("Q6GZX5", uniObj);
		testSecondAccIn("Q6GZX6", uniObj);
		
	}
	@Test
	public void testConverter2() throws Exception{
		//val ac_one_line_moreacc = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
		AcLineObject acObj = new AcLineObject();
		acObj.primaryAcc ="Q6GZX4";
		
		UniProtAcLineObject uniObj = converter.convert(acObj);
		TestCase.assertEquals("Q6GZX4", uniObj.primaryAccession.getValue());
		TestCase.assertEquals(0, uniObj.secondAccessions.size());
	}
	private void testSecondAccIn(String val, UniProtAcLineObject uniObj){
		boolean found =false;
		for(SecondaryUniProtAccession scObj: uniObj.secondAccessions){
			if(val.equals(scObj.getValue())){
				found =true;
				break;
			}
		}
		TestCase.assertTrue(found);
	}
}
