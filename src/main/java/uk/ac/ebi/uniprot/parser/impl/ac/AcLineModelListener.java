package uk.ac.ebi.uniprot.parser.impl.ac;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.AcLineParser;
import uk.ac.ebi.uniprot.parser.antlr.AcLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class AcLineModelListener extends AcLineParserBaseListener implements ParseTreeObjectExtractor<AcLineObject> {

    private AcLineObject object = new AcLineObject();

    @Override
    public void exitAccession(@NotNull AcLineParser.AccessionContext ctx) {
        String text = ctx.ACCESSION().getText();
        if (object.primaryAcc == null) {
            object.primaryAcc = text;
        } else {
            object.secondaryAcc.add(text);
        }
    }

    public AcLineObject getObject() {
        return object;
    }
}
