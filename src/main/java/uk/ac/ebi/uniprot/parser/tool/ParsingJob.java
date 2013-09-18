package uk.ac.ebi.uniprot.parser.tool;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

import java.util.concurrent.Callable;

/**
 * An job that parsing the string and return an EntryObject.
 * User: wudong, Date: 18/09/13, Time: 22:12
 */
public class ParsingJob implements Callable<EntryObject> {

	private final String rawString;
	private final UniprotLineParser<EntryObject> parser;

	public ParsingJob(String rawString, UniprotLineParser<EntryObject> parser ){
		this.rawString = rawString;
		this.parser = parser;
	}

	@Override
	public EntryObject call() throws Exception {
		EntryObject parse = parser.parse(rawString);
		return parse;
	}
}
