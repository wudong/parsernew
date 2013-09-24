package uk.ac.ebi.kraken.parser.converter;

import org.junit.Test;

import static junit.framework.TestCase.*;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Book;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.CitationTypeEnum;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.ElectronicArticle;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Patent;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.SubmissionDatabase;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Citation;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.JournalArticle;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Submission;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Thesis;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.UnpublishedObservations;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineObject;

public class RlLineConverterTest {
	private final RlLineConverter converter = new RlLineConverter();
	@Test
	public void testJournalArticle(){
		// "RL   J. Mol. Biol. 168:321-331(1983).
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.JournalArticle ja = new RlLineObject.JournalArticle();
		ja.first_page ="321";
		ja.last_page ="331";
		ja.volume ="168";
		ja.year =1983;
		ja.journal = "J. Mol. Biol.";
		rlObject.reference = ja;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof JournalArticle) ;
		assertEquals(CitationTypeEnum.JOURNAL_ARTICLE, citation.getCitationType());
		JournalArticle journal = (JournalArticle) citation;
		assertEquals("321", journal.getFirstPage().getValue());
		assertEquals("331", journal.getLastPage().getValue());
		assertEquals("168", journal.getVolume().getValue());
		assertEquals("1983", journal.getPublicationDate().getValue());
		assertEquals("J. Mol. Biol.", journal.getJournalName().getValue());
		
	}
	@Test
	public void testSubmission(){
		// "RL   Submitted (OCT-1995) to the EMBL/GenBank/DDBJ databases.
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Submission subm = new RlLineObject.Submission();
		subm.month ="OCT";
		subm.year =1995;
		subm.db =RlLineObject.SubmissionDB.EMBL;
		rlObject.reference = subm;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof Submission) ;
		assertEquals(CitationTypeEnum.SUBMISSION, citation.getCitationType());
		Submission submission = (Submission) citation;
		assertEquals("OCT-1995", submission.getPublicationDate().getValue());
		assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, submission.getSubmittedToDatabase());
	}
	@Test
	public void testThesis(){
		// "RL   Thesis (1977), University of Geneva, Switzerland.\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Thesis th = new RlLineObject.Thesis();
		th.country ="Switzerland";
		th.institute ="University of Geneva";
		th.year =1977;
		rlObject.reference = th;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof Thesis) ;
		assertEquals(CitationTypeEnum.THESIS, citation.getCitationType());
		Thesis thesis = (Thesis) citation;
		assertEquals("Switzerland", thesis.getCountry().getValue());
		assertEquals("1977", thesis.getPublicationDate().getValue());
		assertEquals("University of Geneva", thesis.getInstitute().getValue());
	}
	
	@Test
	public void testPatent(){
		//"RL   Patent number WO9010703, 20-SEP-1990.\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Patent th = new RlLineObject.Patent();
		th.day =20;
		th.month ="SEP";
		th.year =1990;
		th.patentNumber ="WO9010703";
		rlObject.reference = th;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof Patent) ;
		assertEquals(CitationTypeEnum.PATENT, citation.getCitationType());
		Patent patent = (Patent) citation;
		assertEquals("WO9010703", patent.getPatentNumber().getValue());
		assertEquals("20-SEP-1990", patent.getPublicationDate().getValue());
		
	}
	@Test
	public void testBook(){
		//RL   (In) Boyer P.D. (eds.);
        //RL   The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York (1975).
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Book th = new RlLineObject.Book();
		th.editors.add("Boyer P.D.");
		th.title = "The enzymes (3rd ed.)";
		th.page_start ="397";
		th.page_end ="547";
		th.place ="New York";
		th.press = "Academic Press";
		th.volume ="11";
		th.year =1975;
		rlObject.reference = th;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof Book) ;
		assertEquals(CitationTypeEnum.BOOK, citation.getCitationType());
		Book book = (Book) citation;
		assertEquals(1, book.getEditors().size());
		assertEquals("Boyer P.D.", book.getEditors().get(0).getValue());
		assertEquals("The enzymes (3rd ed.)", book.getBookName().getValue());
		assertEquals("397", book.getFirstPage().getValue());
		assertEquals("547", book.getLastPage().getValue());
		assertEquals("1975", book.getPublicationDate().getValue());
		assertEquals("11", book.getVolume().getValue());
	}
	@Test
	public void testElectronicArticle(){
		// "RL   (er) Plant Gene Register PGR98-023.\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.EPub th = new RlLineObject.EPub();
		th.title ="Plant Gene Register PGR98-023";
		
		rlObject.reference = th;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof ElectronicArticle) ;
		assertEquals(CitationTypeEnum.ELECTRONIC_ARTICLE, citation.getCitationType());
		ElectronicArticle ea = (ElectronicArticle) citation;
		assertEquals("Plant Gene Register", ea.getJournalName().getValue());
		assertEquals("PGR98-023", ea.getLocator().getValue());
		
	}
	@Test
	public void testUnpublished(){
		// "RL   Unpublished observations (OCT-1978).\n";
		RlLineObject rlObject = new RlLineObject();
		RlLineObject.Unpublished th = new RlLineObject.Unpublished();
		th.month ="OCT";
		th.year =1978;
		
		rlObject.reference = th;
		Citation citation = converter.convert(rlObject);
		assertTrue(citation instanceof UnpublishedObservations) ;
		assertEquals(CitationTypeEnum.UNPUBLISHED_OBSERVATIONS, citation.getCitationType());
		UnpublishedObservations ea = (UnpublishedObservations) citation;
	
		assertEquals("OCT-1978", ea.getPublicationDate().getValue());
	}
}
