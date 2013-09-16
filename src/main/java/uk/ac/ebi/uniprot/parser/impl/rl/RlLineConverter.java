package uk.ac.ebi.uniprot.parser.impl.rl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Book;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Citation;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Editor;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.ElectronicArticle;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.JournalArticle;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Patent;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Submission;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.SubmissionDatabase;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Thesis;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.UnpublishedObservations;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class RlLineConverter implements Converter<RlLineObject, Citation> {
	private DefaultCitationNewFactory factory =DefaultCitationNewFactory.getInstance();
	@Override
	public Citation convert(RlLineObject f) {
		if(f.reference instanceof RlLineObject.JournalArticle){
			return convert((RlLineObject.JournalArticle) f.reference);
		}else if(f.reference instanceof RlLineObject.Book){
			return convert((RlLineObject.Book) f.reference);
		}else if(f.reference instanceof RlLineObject.EPub){
			return convert((RlLineObject.EPub) f.reference);
		}else if(f.reference instanceof RlLineObject.Patent){
			return convert((RlLineObject.Patent) f.reference);
		}else if(f.reference instanceof RlLineObject.Submission){
			return convert((RlLineObject.Submission) f.reference);
		}else if(f.reference instanceof RlLineObject.Thesis){
			return convert((RlLineObject.Thesis) f.reference);
		}else if(f.reference instanceof RlLineObject.Unpublished){
			return convert((RlLineObject.Unpublished) f.reference);
		}
		
		else
			throw new RuntimeException("Unable to parse RL line");


	}
	private Citation convert(RlLineObject.JournalArticle ja){
		 JournalArticle citation = factory.buildJournalArticle();
		 citation.setJournalName(factory.buildJournalName(ja.journal));
		 citation.setFirstPage(factory.buildPage(""+ja.first_page));
		 citation.setLastPage(factory.buildPage(""+ja.last_page));
		 citation.setPublicationDate(factory.buildPublicationDate(""+ja.year));
		 citation.setVolume(factory.buildVolume("" + ja.volume));
		return citation;
		
	}
	private Citation convert (RlLineObject.Book b){
		Book citation = factory.buildBook();
		
		 citation.setFirstPage(factory.buildPage(""+b.page_start));
		 citation.setLastPage(factory.buildPage(""+b.page_end));
		 citation.setPublicationDate(factory.buildPublicationDate(""+b.year));
		 citation.setVolume(factory.buildVolume("" + b.volume));
		 citation.setBookName(factory.buildBookName(b.title));
		 List<Editor> editors = new ArrayList<>();
		 for(String val:b.editors){
			 editors.add(factory.buildEditor(val));
		 }
		 citation.setEditors(editors);
		 if(b.press !=null &&( !b.press.isEmpty())){
			 citation.setPublisher(factory.buildPublisher(b.press));
		 }
		 if((b.place !=null) &&(!b.place.isEmpty())){
			 citation.setCity(factory.buildCity(b.place));
		 }
		return citation;
	}
	private Citation convert(RlLineObject.EPub ep){
		String line = ep.title;
		ElectronicArticle citation = factory.buildElectronicArticle();
		if (line.startsWith("(er) Plant Gene Register ")) {
			citation.setJournalName(factory.buildJournalName("Plant Gene Register"));
			citation.setLocator(factory.buildLocator(line.substring(line.lastIndexOf(" ") + 1, line.length() - 1)));
		} else if (line.startsWith("(er) Worm Breeder's Gazette")) {
			citation.setJournalName(factory.buildJournalName("Worm Breeder's Gazette"));
			citation.setLocator(factory.buildLocator(line.substring(line.lastIndexOf(" ") + 1, line.length() - 1)));
		} else {
			citation.setJournalName(factory.buildJournalName(line.substring(4, line.length() - 1)));
		}
		return citation;
	}
	private Citation convert(RlLineObject.Patent patent){
		Patent citation = factory.buildPatent();
		citation.setPatentNumber(factory.buildPatentNumber(patent.patentNumber));
		String day = ""+patent.day;
		if(patent.day<10){
			day ="0"+patent.day;
		}
		String date = day +"-" + patent.month +"-" +patent.year;
		citation.setPublicationDate(factory.buildPublicationDate(date));
		return citation;
	}
	
	private Citation convert(RlLineObject.Submission submission){
		Submission citation =factory.buildSubmission();
		
		switch (submission.db){
		case EMBL:
			 citation.setSubmittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
			break;
		case UNIPROTKB:
			 citation.setSubmittedToDatabase(SubmissionDatabase.UNIPROTKB);
			break;
		case PDB:
			 citation.setSubmittedToDatabase(SubmissionDatabase.PDB);
			break;
		case PIR:
			citation.setSubmittedToDatabase(SubmissionDatabase.PIR);
			break;
		default:
			throw new RuntimeException("submission db is not supported");
		}
		String date = submission.month +"-"+submission.year;
		citation.setPublicationDate(factory.buildPublicationDate(date));
		return citation;
	}
	private Citation convert(RlLineObject.Thesis thesis){
		 Thesis citation = factory.buildThesis();
		 citation.setPublicationDate(factory.buildPublicationDate(""+thesis.year));
		 citation.setInstitute(factory.buildInstitute(thesis.institute));
		 citation.setCountry(factory.buildCountry(thesis.country));
		// citation.setCity(city)
		return citation;
	}
	private Citation convert(RlLineObject.Unpublished unpub){
		UnpublishedObservations citation = factory.buildUnpublishedObservations();
		citation.setPublicationDate(factory.buildPublicationDate(unpub.month +"-"+unpub.year));
		return citation;
	}
}
