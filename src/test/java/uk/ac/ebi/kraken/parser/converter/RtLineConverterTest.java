package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Title;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineObject;

public class RtLineConverterTest {
	@Test
	public void test(){
		RtLineObject rt =new RtLineObject();
		rt.title ="A novel adapter protein employs a phosphotyrosine binding domain";
		RtLineConverter converter = new RtLineConverter();
		Title title = converter.convert(rt);
		TestCase.assertEquals("A novel adapter protein employs a phosphotyrosine binding domain", title.getValue());
		
	}
}
