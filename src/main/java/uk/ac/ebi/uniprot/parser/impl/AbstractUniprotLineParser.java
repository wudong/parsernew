package uk.ac.ebi.uniprot.parser.impl;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.ParseException;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.antlr.IdLineParser;
import uk.ac.ebi.uniprot.parser.antlr.OsLineParser;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class AbstractUniprotLineParser<T, L extends Lexer, P extends Parser>
        implements UniprotLineParser<T> {

    final private GrammarFactory<L, P> factory;
	private ParseTreeObjectExtractor<T> listener;

	protected AbstractUniprotLineParser(GrammarFactory<L, P> factory, ParseTreeObjectExtractor<T> listener){
        this.factory=factory;
		this.listener = listener;
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

    protected  T processWithParser(P parser){
	    parser.addParseListener(listener);

	    //call the parser with the top rule name.
	    Method method = null;
	    try {
		    method = parser.getClass().getMethod(factory.getTopRuleName());
		    method.invoke(parser);
	    } catch (NoSuchMethodException e) {
		    throw new ParseException(e.getMessage());
	    } catch (InvocationTargetException e) {
		    throw new ParseException(e.getMessage());
	    } catch (IllegalAccessException e) {
		    throw new ParseException(e.getMessage());
	    }

	    return listener.getObject();
    }

}
