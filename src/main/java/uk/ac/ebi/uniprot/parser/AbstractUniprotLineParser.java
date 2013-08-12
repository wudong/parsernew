package uk.ac.ebi.uniprot.parser;

import org.antlr.v4.runtime.*;
import uk.ac.ebi.uniprot.parser.antlr.IdLineParser;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractUniprotLineParser<T, L extends Lexer, P extends Parser>
        implements UniprotLineParser<T> {

    final private GrammarFactory<L, P> factory;

    protected AbstractUniprotLineParser(GrammarFactory<L, P> factory){
        this.factory=factory;
    }

    public P createParserFromInput(CharStream in,
        GrammarFactory<L, P> factory) {
        L lexer = factory.createLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        P parser = factory.createParser(tokens);
        parser.addErrorListener(new BaseErrorListener(){
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new ParseException(msg);
            }
        });
        return  parser;
    }

    @Override
    public T parse(String s) {
        ANTLRInputStream in = new ANTLRInputStream(s);
        P parserFromInput = createParserFromInput(in, factory);
        return processWithParser(parserFromInput);
    }

    protected abstract T processWithParser(P parser);

}
