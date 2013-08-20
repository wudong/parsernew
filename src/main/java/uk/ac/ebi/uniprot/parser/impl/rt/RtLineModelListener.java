package uk.ac.ebi.uniprot.parser.impl.rt;

import org.antlr.v4.runtime.WritableToken;
import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.*;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RtLineModelListener extends RtLineBaseListener implements ParseTreeObjectExtractor<RtLineObject> {

    private RtLineObject object = new RtLineObject();

    @Override
    public void exitSeparator(@NotNull RtLineParser.SeparatorContext ctx) {
        if (ctx.CHANGE_OF_LINE() != null) {
            WritableToken symbol = (WritableToken) ctx.CHANGE_OF_LINE().getSymbol();
            symbol.setText(" ");
            symbol.setType(RtLineLexer.SPACE);
        }
    }

    @Override
    public void exitMulti_word(@NotNull RtLineParser.Multi_wordContext ctx) {
        String text = ctx.getText();
        object.title = text;
    }

    public RtLineObject getObject() {
        return object;
    }
}
