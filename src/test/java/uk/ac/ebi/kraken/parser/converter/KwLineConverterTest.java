package uk.ac.ebi.kraken.parser.converter;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.Keyword;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineConverter;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineObject;

public class KwLineConverterTest {
	private final KwLineConverter converter =new  KwLineConverter();
	@Test
	public void test(){
		//KW   Activator; Complete proteome; Reference proteome; Transcription
		KwLineObject obj = new KwLineObject();
		obj.keywords.add("Activator");
		obj.keywords.add("Complete proteome");
		obj.keywords.add("Reference proteome");
		obj.keywords.add("Transcription");
		List<String> keywords = new ArrayList<>();
		keywords.add("Activator");
		keywords.add("Complete proteome");
		keywords.add("Reference proteome");
		keywords.add("Transcription");
		List<Keyword> kws =converter.convert(obj);
		TestCase.assertEquals(4,  kws.size());
		for(Keyword kw:kws){
			keywords.contains(kw.getValue());
		}
		
	}
	@Test
	public void testEvidences(){
		//KW   Activator; Complete proteome; Reference proteome; Transcription
		KwLineObject obj = new KwLineObject();
		obj.keywords.add("Activator");
		obj.keywords.add("Complete proteome");
		obj.keywords.add("Reference proteome");
		obj.keywords.add("Transcription");
		List<String> keywords = new ArrayList<>();
		keywords.add("Activator");
		keywords.add("Complete proteome");
		keywords.add("Reference proteome");
		keywords.add("Transcription");
		List<Keyword> kws =converter.convert(obj);
		TestCase.assertEquals(4,  kws.size());
		for(Keyword kw:kws){
			keywords.contains(kw.getValue());
		}
		
	}
}
