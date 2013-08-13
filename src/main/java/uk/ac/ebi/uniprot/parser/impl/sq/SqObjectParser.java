package uk.ac.ebi.uniprot.parser.impl.sq;

import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.antlr.SqLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.SqLineParser;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class SqObjectParser extends AbstractUniprotLineParser<SqLineObject, SqLineLexer, SqLineParser> {

    public SqObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Sq.getFactory());
    }

    @Override
    protected SqLineObject processWithParser(SqLineParser parser) {
        SqLineModelListener sqLineModelListener = new SqLineModelListener();
        parser.addParseListener(sqLineModelListener);
        SqLineParser.Sq_blocksContext sq_blocksContext = parser.sq_blocks();
        return sqLineModelListener.getObject();
    }


}
