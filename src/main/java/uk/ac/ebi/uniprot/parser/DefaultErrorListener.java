package uk.ac.ebi.uniprot.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 23/10/2013
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class DefaultErrorListener extends BaseErrorListener {

    private final boolean inLex;

    public DefaultErrorListener(boolean inLax){
        this.inLex = inLax;
    }

    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg, RecognitionException e) {
        String prefix = inLex ? "Lex": "Parser";
        String errorMessage =
                String.format("%s Syntax Error while parsing the input String in Line: %d, Position: %d. The original" +
                        "parsing error message is: \n %s", prefix, line, charPositionInLine, msg);

        throw new ParseException(errorMessage, "",e);
    }

}
