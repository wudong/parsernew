package uk.ac.ebi.uniprot.parser.impl.rl;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Book;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Citation;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.JournalArticle;
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
		}
		else
			throw new RuntimeException("Unable to parse RL line");


	}
	private JournalArticle convert(RlLineObject.JournalArticle ja){
		 JournalArticle citation = factory.buildJournalArticle();
		 citation.setJournalName(factory.buildJournalName(ja.journal));
		 citation.setFirstPage(factory.buildPage(""+ja.first_page));
		 citation.setLastPage(factory.buildPage(""+ja.last_page));
		 citation.setPublicationDate(factory.buildPublicationDate(""+ja.year));
		 citation.setVolume(factory.buildVolume("" + ja.volume));
		return citation;
		
	}
	private Book convert (RlLineObject.Book b){
		Book citation = factory.buildBook();
		
		 citation.setFirstPage(factory.buildPage(""+b.page_start));
		 citation.setLastPage(factory.buildPage(""+b.page_end));
		 citation.setPublicationDate(factory.buildPublicationDate(""+b.year));
		 citation.setVolume(factory.buildVolume("" + b.volume));
		
		return citation;
	}
}
