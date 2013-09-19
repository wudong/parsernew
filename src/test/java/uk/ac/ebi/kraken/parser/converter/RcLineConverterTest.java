package uk.ac.ebi.kraken.parser.converter;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.SampleSource;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.SampleSourceType;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineObject;

public class RcLineConverterTest {
	@Test
	public void test(){
		// "RC   STRAIN=Holstein; TISSUE=Lymph node, and Mammary gland;\n";
		RcLineObject rcline =new RcLineObject();
		RcLineObject.RC rc1 = new RcLineObject.RC();
		rc1.tokenType =RcLineObject.RcTokenEnum.STRAIN;
		rc1.values.add("Holstein");
		rcline.rcs.add(rc1);
		RcLineObject.RC rc2 = new RcLineObject.RC();
		rc2.tokenType =RcLineObject.RcTokenEnum.TISSUE;
		rc2.values.add("Lymph node");
		rc2.values.add("Mammary gland");
		rcline.rcs.add(rc2);
		RcLineConverter converter = new RcLineConverter();
		List<SampleSource> sss =converter.convert(rcline);
		TestCase.assertEquals(3,  sss.size());
		SampleSource strain = sss.get(0);
		SampleSource tissue1 = sss.get(1);
		SampleSource tissue2 = sss.get(2);
		TestCase.assertEquals(SampleSourceType.STRAIN, strain.getType());
		TestCase.assertEquals("Holstein", strain.getValue());
		TestCase.assertEquals(SampleSourceType.TISSUE, tissue1.getType());
		TestCase.assertEquals("Lymph node", tissue1.getValue());
		TestCase.assertEquals(SampleSourceType.TISSUE, tissue2.getType());
		TestCase.assertEquals("Mammary gland", tissue2.getValue());
		
	}
}
