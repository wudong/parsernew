package uk.ac.ebi.uniprot.parser.impl.os;

import org.antlr.v4.runtime.WritableToken;
import org.antlr.v4.runtime.misc.NotNull;

import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OsLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.OsLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.OsLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OsLineModelListener extends OsLineBaseListener implements ParseTreeObjectExtractor<OsLineObject> {

	private OsLineObject object = new OsLineObject();
	private StringBuilder sb = new StringBuilder();

	@Override
	public void exitWords(@NotNull OsLineParser.WordsContext ctx) {
		if (sb.length() == 0) {
			sb.append(ctx.getText());
		} else {
			sb.append(" ");
			sb.append(ctx.getText());
		}
	}


	/*
	 * Replace the CHANGE_OF_LINE to a SIMPLE SPACE.
	 */
	@Override
	public void exitSeparator(@NotNull OsLineParser.SeparatorContext ctx) {
		if (ctx.CHANGE_OF_LINE() != null) {
			WritableToken symbol = (WritableToken) ctx.CHANGE_OF_LINE().getSymbol();
			symbol.setText(" ");
			symbol.setType(OsLineLexer.SPACE);
		}
	}

	@Override
	public void exitOs_os(@NotNull OsLineParser.Os_osContext ctx) {
		object.organism_species = sb.toString();
	}

	public OsLineObject getObject() {
		return object;
	}
}
