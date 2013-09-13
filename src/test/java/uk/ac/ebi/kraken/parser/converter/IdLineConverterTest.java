package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineConverter;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;

public class IdLineConverterTest {
	private IdLineConverter converter = new IdLineConverter();
	@Test
	public void testConverter() throws Exception{
		//ID   001R_FRG3G              Reviewed;         256 AA
		IdLineObject idObj = new IdLineObject();
		idObj.reviewed =true;
		idObj.entryName  ="001R_FRG3G";
		idObj.sequenceLength =256;
		
		UniProtEntry uniObj = converter.convert(idObj);	
		TestCase.assertEquals("001R_FRG3G", uniObj.getUniProtId().getValue());
		TestCase.assertEquals(UniProtEntryType.SWISSPROT, uniObj.getType());		
	}
	@Test
	public void testConverter2() throws Exception{
		//ID   001R_FRG3G              Reviewed;         256 AA
		IdLineObject idObj = new IdLineObject();
		idObj.reviewed =false;
		idObj.entryName  ="001R_FRG3G";
		idObj.sequenceLength =256;
		
		UniProtEntry uniObj = converter.convert(idObj);	
		TestCase.assertEquals("001R_FRG3G", uniObj.getUniProtId().getValue());
		TestCase.assertEquals(UniProtEntryType.TREMBL, uniObj.getType());		
	}
}
