package uk.ac.ebi.uniprot.parser.impl.ss;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.SsLineParser;
import uk.ac.ebi.uniprot.parser.antlr.SsLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class SsLineModelListener extends SsLineParserBaseListener implements ParseTreeObjectExtractor<SsLineObject> {

	private DateTimeFormatter formatter
			= DateTimeFormat.forPattern("dd-MMM-yyyy");
    private SsLineObject object = new SsLineObject();

	@Override
	public void enterEv_lines(@NotNull SsLineParser.Ev_linesContext ctx) {
		SsLineObject.Ev ev = new SsLineObject.Ev();
		object.evs.add(ev);

		TerminalNode terminalNode = ctx.EV_TAG();
		ev.evtag = terminalNode.getText();

		String text = ctx.ev_db_name().VALUE().getText();
		ev.xref = text;

		String text1 = ctx.value_1().getText();
		ev.value_1 = text1;

		String text2 = ctx.value_2().getText();
		ev.value_2 = text2;

		String text3 = ctx.DATE().getText();
		DateTime dateTime = formatter.parseDateTime(text3);
		ev.date = dateTime;
	}

	public SsLineObject getObject() {
        return object;
    }
}
