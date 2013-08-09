package uk.ac.ebi.uniprot.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class GrammerFactory<L extends Lexer, P extends Parser> {
    public L createLexer(CharStream in) {
        return null;
    }

    public P createParser(CommonTokenStream tokens) {
        return null;

    }
}
