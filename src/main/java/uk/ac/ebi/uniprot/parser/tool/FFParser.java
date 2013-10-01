package uk.ac.ebi.uniprot.parser.tool;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

import java.io.IOException;

/**
 * A naive parser for test.
 * <p/>
 * User: wudong, Date: 18/09/13, Time: 22:22
 */
public class FFParser {

	public static void main(String[] args) throws IOException {
		FFParser ffParser = new FFParser();
		ffParser.parse(args[0]);
	}

	public void parse(String in) throws IOException {
		EntryBufferedReader reader = new EntryBufferedReader(in);
		String entry = reader.next();

		int count = 0;
		long time = 0;
		try {
			while (entry != null) {
				count++;
				long start = System.currentTimeMillis();
				EntryObject entryObject = parseEntry(entry);
				System.out.println(entryObject.id.entryName);
				entry = reader.next();
				time += System.currentTimeMillis() - start;
			}
		} finally {
			System.out.println(String.format("parsing %s entries total time used %s", count, time));
		}

	}

	private EntryObject parseEntry(String entry) {
		DefaultUniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();
		UniprotLineParser<EntryObject> entryParser = parserFactory.createEntryParser();
		EntryObject parse = entryParser.parse(entry);
		return parse;
	}
}
