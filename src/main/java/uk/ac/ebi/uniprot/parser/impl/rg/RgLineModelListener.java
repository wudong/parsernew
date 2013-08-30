package uk.ac.ebi.uniprot.parser.impl.rg;

import org.antlr.v4.runtime.misc.NotNull;

import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RgLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.RgLineParser;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RgLineModelListener extends RgLineBaseListener implements ParseTreeObjectExtractor<RgLineObject> {

    private RgLineObject object = new RgLineObject();

    @Override
    public void exitWords(@NotNull RgLineParser.WordsContext ctx) {
        object.reference_group =ctx.getText();
    }

    public RgLineObject getObject() {
        return object;
    }
}
