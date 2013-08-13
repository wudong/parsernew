package uk.ac.ebi.uniprot.parser.impl.id;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import uk.ac.ebi.uniprot.parser.AbstractUniprotLineParser;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.ParseException;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.antlr.IdLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.IdLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class IdObjectParser extends AbstractUniprotLineParser<IdLineObject, IdLineLexer, IdLineParser> {

    public IdObjectParser() {
        super(GrammarFactory.GrammarFactoryEnum.Id.getFactory());
    }

    @Override
    protected IdLineObject processWithParser(IdLineParser parser) {
        IdLineModelListener idLineModelListener = new IdLineModelListener();
        parser.addParseListener(idLineModelListener);
        IdLineParser.Id_lineContext id_lineContext = parser.id_line();
        return idLineModelListener.getObject();
    }


}
