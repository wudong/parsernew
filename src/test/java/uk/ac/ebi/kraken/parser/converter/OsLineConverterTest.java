package uk.ac.ebi.kraken.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.Organism;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineConverter;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;

public class OsLineConverterTest {
	@Test
	public void test(){
		//"OS   Solanum melongena (Eggplant) (Aubergine).
		OsLineObject osO = new OsLineObject();
		osO.organism_species ="Solanum melongena (Eggplant) (Aubergine)";
		OsLineConverter converter = new OsLineConverter();
		Organism org =converter.convert(osO);
		TestCase.assertEquals("Solanum melongena", org.getScientificName().getValue());
		TestCase.assertEquals("Eggplant", org.getCommonName().getValue());
		TestCase.assertEquals("Aubergine", org.getSynonym().getValue());
		
	}
	
	
	@Test
	public void test2(){
		//OS   Homo sapiens (Human).
		OsLineObject osO = new OsLineObject();
		osO.organism_species ="Homo sapiens (Human)";
		OsLineConverter converter = new OsLineConverter();
		Organism org =converter.convert(osO);
		TestCase.assertEquals("Homo sapiens", org.getScientificName().getValue());
		TestCase.assertEquals("Human", org.getCommonName().getValue());
		TestCase.assertEquals("", org.getSynonym().getValue());
		
	}
	
}
