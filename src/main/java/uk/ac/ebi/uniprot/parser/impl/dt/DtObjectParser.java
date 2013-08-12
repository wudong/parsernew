package uk.ac.ebi.uniprot.parser.impl.dt;

import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.antlr.DtLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.DtLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class DtObjectParser extends AbstractUniprotLineParser<DtLineObject, DtLineLexer, DtLineParser> {

    public DtObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Dt.getFactory());
    }

    @Override
    protected DtLineObject processWithParser(DtLineParser parser) {
        DtLineModelListener listener = new DtLineModelListener();
        parser.addParseListener(listener);
        parser.dt_blocks();
        return listener.getObject();
    }


}
