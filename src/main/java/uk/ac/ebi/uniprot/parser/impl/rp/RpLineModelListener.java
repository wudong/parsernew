package uk.ac.ebi.uniprot.parser.impl.rp;

import org.antlr.v4.runtime.WritableToken;
import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RpLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.RpLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.RpLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RpLineModelListener extends RpLineBaseListener implements ParseTreeObjectExtractor<RpLineObject> {

    private RpLineObject object = new RpLineObject();

    public RpLineObject getObject() {
        return object;
    }



    @Override
    public void exitSeparator(@NotNull RpLineParser.SeparatorContext ctx) {
        if (ctx.CHANGE_OF_LINE() != null) {
            WritableToken symbol = (WritableToken) ctx.CHANGE_OF_LINE().getSymbol();
            symbol.setText(" ");
            symbol.setType(RpLineLexer.SPACE);
        }
    }

    @Override
    public void exitMulti_word(@NotNull RpLineParser.Multi_wordContext ctx) {
        object.position = ctx.getText();
    }
}
