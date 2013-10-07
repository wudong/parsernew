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
		if (args.length==2){
			ffParser.setStartParingString(args[1]);
		}
		ffParser.parse(args[0]);
	}

	private String startParingString;

	public String getStartParingString() {
		return startParingString;
	}

	public void setStartParingString(String startParingString) {
		this.startParingString = startParingString;
	}

	public void parse(String in) throws IOException {
		boolean found=true;
		if (this.startParingString!=null)found=false;

		EntryBufferedReader reader = new EntryBufferedReader(in);
		String entry = reader.next();

		if (this.startParingString!=null){
			found = entry.startsWith(this.startParingString);
		}

        boolean foundNext=false;
		int count = 0;
		long time = 0;
		try {
			while (entry != null) {
				if (found && !foundNext){
                    foundNext=true;
					//doing nothing. just read the next one.
				} else if (found && foundNext){
                    count++;
                    long start = System.currentTimeMillis();
                    EntryObject entryObject = parseEntry(entry);
                    System.out.println(entryObject.id.entryName);
                    time += System.currentTimeMillis() - start;
                }

				entry = reader.next();

				if (!found&&this.startParingString!=null){
					found = entry.startsWith(this.startParingString);
				}

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
