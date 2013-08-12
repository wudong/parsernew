package uk.ac.ebi.uniprot.parser.impl.kw;

import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.antlr.KwLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.KwLineParser;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class KwObjectParser extends AbstractUniprotLineParser<KwLineObject, KwLineLexer, KwLineParser> {

    public KwObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Kw.getFactory());
    }

    @Override
    protected KwLineObject processWithParser(KwLineParser parser) {
        KwLineModelListener kwLineModelListener = new KwLineModelListener();
        parser.addParseListener(kwLineModelListener);
        KwLineParser.Kw_blocksContext kw_blocksContext = parser.kw_blocks();
        return kwLineModelListener.getObject();
    }


}
