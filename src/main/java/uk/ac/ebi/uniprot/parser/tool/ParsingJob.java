package uk.ac.ebi.uniprot.parser.tool;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

import java.util.concurrent.Callable;

/**
 * An job that parsing the string and return an EntryObject.
 * User: wudong, Date: 18/09/13, Time: 22:12
 */
public class ParsingJob implements Callable<EntryObject> {

	private final String rawString;

    private static UniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();
    private static ThreadLocal<UniprotLineParser<EntryObject>> parsers
            = new ThreadLocal<UniprotLineParser<EntryObject>>();

	public ParsingJob(String rawString ){
		this.rawString = rawString;

	}

	@Override
	public EntryObject call() throws Exception {
        UniprotLineParser<EntryObject> parser = parsers.get();
        if (parser ==null){
            parser = parserFactory.createEntryParser();
            parsers.set(parser);
        }
        EntryObject parsedObject = parser.parse(rawString);
		return parsedObject;
	}
}
