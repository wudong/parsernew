package uk.ac.ebi.uniprot.parser.impl.kw;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.antlr.IdLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.IdLineParser;
import uk.ac.ebi.uniprot.parser.antlr.KwLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.KwLineParser;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class KwLineModelListener extends KwLineBaseListener {

    private KwLineObject object = new KwLineObject();

    @Override
    public void exitKeyword(@NotNull KwLineParser.KeywordContext ctx) {
        String text = ctx.MULTI_WORD().getText();
        object.keywords.add(text);
    }

    public KwLineObject getObject() {
        return object;
    }
}
