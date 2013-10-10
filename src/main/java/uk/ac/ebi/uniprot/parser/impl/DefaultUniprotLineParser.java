package uk.ac.ebi.uniprot.parser.impl;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Nullable;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.ParseException;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class DefaultUniprotLineParser<T, L extends Lexer, P extends Parser>
		implements UniprotLineParser<T> {

	final private GrammarFactory<L, P> factory;
	private ParseTreeObjectExtractor<T> listener;
	private static final org.slf4j.Logger
			logger = org.slf4j.LoggerFactory.getLogger(DefaultUniprotLineParser.class);

	protected DefaultUniprotLineParser(GrammarFactory<L, P> factory, ParseTreeObjectExtractor<T> listener) {
		this.factory = factory;
		this.listener = listener;
	}

	public P createParserFromInput(CharStream in,
	                               GrammarFactory<L, P> factory) {
        L lexer = factory.createLexer(in);
        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, @Nullable Object o, int line, int i2, String s, @Nullable RecognitionException e) {
                throw new ParseException("Syntax Error while lexing the input String in Line: " + line + ", Position: " + i2,
                        "", e);
            }
        });

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		P parser = factory.createParser(tokens);
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
			                        int line, int charPositionInLine, String msg, RecognitionException e) {
				throw new ParseException("Syntax Error while parsing the input String in Line: " + line + ", Position: " + charPositionInLine,
						"", e);
			}
		});
		return parser;
	}

	@Override
	public T parse(String s) {
		ANTLRInputStream in = new ANTLRInputStream(s);
		P parserFromInput = createParserFromInput(in, factory);
		return processWithParser(parserFromInput, s);
	}

	protected T processWithParser(P parser, String originString) {
		parser.addParseListener(listener);

		//call the parser with the top rule name.
		Method method = null;
		try {
			method = parser.getClass().getMethod(factory.getTopRuleName());
			method.invoke(parser);
		} catch (NoSuchMethodException e) {
			logger.error("Parser's method doesn't exist.");
			logger.info("Offending String \n{}", originString);
			throw new ParseException("Parser's method doesn't exist", originString, e);
		} catch (InvocationTargetException e) {
			logger.error("Exception while calling the parser on rule {}, with exception message: {}", factory.getTopRuleName(), e.getMessage());
			logger.info("Offending String \n{}", originString);
			throw new ParseException("Exception while calling the parser", originString, e);
		} catch (IllegalAccessException e) {
			logger.error("No privilege to call the parser's method.");
			logger.info("Offending String \n{}", originString);
			throw new ParseException("No privilege to call the parser's method", originString, e);
		}

		return listener.getObject();
	}

}
