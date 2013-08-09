package uk.ac.ebi.uniprot.parser.impl.id;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
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
public class IDLineParser implements UniprotLineParser<IdLineObject> {

    public IdLineObject parse(String input) {
        ANTLRInputStream in = new ANTLRInputStream(input);
        return getIdLineObject(in);
    }

    private IdLineObject getIdLineObject(ANTLRInputStream in) {
        IdLineLexer lexer = new IdLineLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        IdLineParser parser = new IdLineParser(tokens);

        IdLineModelListener idLineModelListener = new IdLineModelListener();
        parser.addParseListener(idLineModelListener);

        IdLineParser.Id_lineContext id_lineContext = parser.id_line();
        if (id_lineContext.isEmpty()){
            throw new ParseException();
        }

        return idLineModelListener.getObject();
    }
}
