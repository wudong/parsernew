package uk.ac.ebi.uniprot.parser.tool;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.antlr.UniprotLexer;
import uk.ac.ebi.uniprot.parser.antlr.UniprotParser;
import uk.ac.ebi.uniprot.parser.antlr.UniprotParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryModelListener;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

import java.io.*;

/**
 * A naive parser for test.
 *
 * User: wudong, Date: 18/09/13, Time: 22:22
 */
public class FFParser {

	public static void main (String[] args) throws IOException {
		File file= new File(args[0]);
		FFParser ffParser = new FFParser();
		ffParser.parse(new FileInputStream(file));
	}

	public void parse(InputStream in) throws IOException {
		ANTLRInputStream antlrInputStream = new ANTLRInputStream(in);
		UniprotLexer uniprotLexer = new UniprotLexer(antlrInputStream);
		CommonTokenStream tokens = new CommonTokenStream(uniprotLexer);
		UniprotParser uniprotParser = new UniprotParser(tokens);
		uniprotParser.addParseListener(new EntryModelListener(){
			private int count=0;

			@Override
			public void exitEntry(@NotNull UniprotParser.EntryContext ctx) {
				count++;
				EntryObject object = super.getObject();
				System.out.println(count + ": " +object.ac.primaryAcc);
			}
		});
		uniprotParser.ff();
	}
}
