package uk.ac.ebi.uniprot.parser.impl.ss;

import org.antlr.v4.runtime.misc.NotNull;
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

    private SsLineObject object = new SsLineObject();
	private DateTimeFormatter formatter
			= DateTimeFormat.forPattern("dd-MMM-yyyy");


	@Override
    public void exitSs_line_source(@NotNull SsLineParser.Ss_line_sourceContext ctx) {
        String text = ctx.SOURCE_TEXT().getText();
        object.ssSourceLines.add(text);
    }

    @Override
    public void exitSs_line_ia(@NotNull SsLineParser.Ss_line_iaContext ctx) {
        SsLineObject.SsLine ssLine = new SsLineObject.SsLine();
        ssLine.topic = ctx.TOPIC().getText();
        ssLine.text = ctx.IA_TEXT().getText();
        object.ssIALines.add(ssLine);
    }

	@Override
	public void exitSs_line_ev(@NotNull SsLineParser.Ss_line_evContext ctx) {
		SsLineObject.EvLine evLine = new SsLineObject.EvLine();
		evLine.id = ctx.ev_id().getText();
		evLine.db = ctx.ev_db().getText();
		evLine.attr_1 = ctx.ev_attr_1().getText();
		evLine.attr_2 = ctx.ev_attr_2().getText();
		evLine.date = DateTime.parse(ctx.EV_DATE().getText(), formatter);
		object.ssEVLines.add(evLine);
	}

	public SsLineObject getObject() {
        return object;
    }
}
