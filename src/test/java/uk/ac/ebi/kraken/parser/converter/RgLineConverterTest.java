package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;
import org.junit.Test;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.AuthoringGroup;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineObject;

import java.util.List;

public class RgLineConverterTest {
	@Test
	public void test(){
		//"RG   The mouse genome sequencing consortium;\n";
		RgLineObject rgline = new RgLineObject();
		rgline.reference_groups.add("The mouse genome sequencing consortium");
		RgLineConverter converter = new RgLineConverter();
		List<AuthoringGroup> ags = converter.convert(rgline);
		TestCase.assertEquals(1, ags.size());
		TestCase.assertEquals("The mouse genome sequencing consortium", 
				ags.get(0).getValue());
		
	}
}
