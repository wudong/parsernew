package uk.ac.ebi.uniprot.parser.impl.ss;

import org.antlr.v4.runtime.misc.NotNull;
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

    public SsLineObject getObject() {
        return object;
    }
}
