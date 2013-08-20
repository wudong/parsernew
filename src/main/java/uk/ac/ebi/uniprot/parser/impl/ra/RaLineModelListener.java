package uk.ac.ebi.uniprot.parser.impl.ra;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RaLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.RaLineParser;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RaLineModelListener extends RaLineBaseListener implements ParseTreeObjectExtractor<RaLineObject> {

    private RaLineObject object = new RaLineObject();

    @Override
    public void exitName(@NotNull RaLineParser.NameContext ctx) {
        String text = ctx.getText();
        object.authors.add(text);
    }

    public RaLineObject getObject() {
        return object;
    }
}
