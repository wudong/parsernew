package uk.ac.ebi.uniprot.parser.impl.dr;

import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.antlr.DrLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.DrLineParser;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class DrObjectParser extends AbstractUniprotLineParser<DrLineObject, DrLineLexer, DrLineParser> {

    public DrObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Dr.getFactory());
    }

    @Override
    protected DrLineObject processWithParser(DrLineParser parser) {
        DrLineModelListener listener = new DrLineModelListener();
        parser.addParseListener(listener);
        parser.dr_blocks();
        return listener.getObject();
    }


}
