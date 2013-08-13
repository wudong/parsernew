package uk.ac.ebi.uniprot.parser.impl.gn;

import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.antlr.GnLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.GnLineParser;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class GnObjectParser extends AbstractUniprotLineParser<GnLineObject, GnLineLexer, GnLineParser> {

    public GnObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Gn.getFactory());
    }

    @Override
    protected GnLineObject processWithParser(GnLineParser parser) {
        GnLineModelListener gnLineModelListener = new GnLineModelListener();
        parser.addParseListener(gnLineModelListener);
        GnLineParser.Gn_line_blocksContext gn_line_blocksContext = parser.gn_line_blocks();
        return gnLineModelListener.getObject();
    }


}
