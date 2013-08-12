package uk.ac.ebi.uniprot.parser.impl.ac;

import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.antlr.AcLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.AcLineParser;
import uk.ac.ebi.uniprot.parser.antlr.IdLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.IdLineParser;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class AcObjectParser extends AbstractUniprotLineParser<AcLineObject, AcLineLexer, AcLineParser> {

    public AcObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Ac.getFactory());
    }

    @Override
    protected AcLineObject processWithParser(AcLineParser parser) {
        AcLineModelListener idLineModelListener = new AcLineModelListener();
        parser.addParseListener(idLineModelListener);
        parser.ac_blocks();
        return idLineModelListener.getObject();
    }


}
