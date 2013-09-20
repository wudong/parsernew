package uk.ac.ebi.kraken.parser.converter;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.Keyword;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
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
		assertEquals(4,  kws.size());
		for(Keyword kw:kws){
			keywords.contains(kw.getValue());
		}
		
	}
	@Test
	public void testEvidences(){
		//KW   Activator{EI1}; Complete proteome{EI2};
        //KW   Reference proteome; Transcription{EI2,EI3};
		
		KwLineObject obj = new KwLineObject();
		obj.keywords.add("Activator");
		obj.keywords.add("Complete proteome");
		obj.keywords.add("Reference proteome");
		obj.keywords.add("Transcription");
		List<String> evIds = new ArrayList<String>();
		evIds.add("EI1");
		obj.evidenceInfo.evidences.put("Activator", evIds);
		evIds = new ArrayList<String>();
		evIds.add("EI2");
		obj.evidenceInfo.evidences.put("Complete proteome", evIds);
		evIds = new ArrayList<String>();
		evIds.add("EI2");
		evIds.add("EI3");
		obj.evidenceInfo.evidences.put("Transcription", evIds);
		
		List<Keyword> kws =converter.convert(obj);
		assertEquals(4,  kws.size());
		Keyword kw1 =kws.get(0);
		Keyword kw2 =kws.get(1);
		Keyword kw3 =kws.get(2);
		Keyword kw4 =kws.get(3);
		for(Keyword kw:kws){
			if(kw.getValue().equals("Activator")){
				kw1 =kw;
			}else if(kw.getValue().equals("Complete proteome")){
				kw2 =kw;
			}else if(kw.getValue().equals("Reference proteome")){
				kw3 =kw;
			}else if(kw.getValue().equals("Transcription")){
				kw4 =kw;
			}
		}		
		assertNotNull(kw1);
		assertNotNull(kw2);
		assertNotNull(kw3);
		assertNotNull(kw4);
		List<EvidenceId> eviIds = kw1.getEvidenceIds();
		assertEquals(1, eviIds.size());
		EvidenceId eviId = eviIds.get(0);
		assertEquals("EI1", eviId.getValue());
		eviIds = kw2.getEvidenceIds();
		assertEquals(1, eviIds.size());
		eviId = eviIds.get(0);
		assertEquals("EI2", eviId.getValue());
		eviIds = kw3.getEvidenceIds();
		assertEquals(0, eviIds.size());
		eviIds = kw4.getEvidenceIds();
		assertEquals(2, eviIds.size());
		eviId = eviIds.get(0);
		EvidenceId eviId2 = eviIds.get(1);
		assertEquals("EI2", eviId.getValue());
		assertEquals("EI3", eviId2.getValue());
	}
}
