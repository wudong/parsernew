package uk.ac.ebi.uniprot.parser.impl.ra;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RnLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.RnLineParser;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RaLineModelListener extends RnLineBaseListener implements ParseTreeObjectExtractor<RnLineObject> {

    private RnLineObject object = new RnLineObject();

    @Override
    public void exitRn_number(@NotNull RnLineParser.Rn_numberContext ctx) {
        String text = ctx.getText();
        object.number = Integer.parseInt(text);
    }

    public RnLineObject getObject() {
        return object;
    }
}
