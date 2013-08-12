package uk.ac.ebi.uniprot.parser.impl.id;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import uk.ac.ebi.uniprot.parser.ParseException;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.antlr.IdLineLexer;
import uk.ac.ebi.uniprot.parser.antlr.IdLineParser;

import java.util.BitSet;

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

        //for test purpose.
        //parser.setBuildParseTree(true);

        IdLineModelListener idLineModelListener = new IdLineModelListener();
        parser.addParseListener(idLineModelListener);
        IdLineParser.Id_lineContext id_lineContext = parser.id_line();

        //id_lineContext.inspect(parser);

        //how to force detect error?

        return idLineModelListener.getObject();
    }
}
