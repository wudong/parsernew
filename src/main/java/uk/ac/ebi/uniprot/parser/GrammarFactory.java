package uk.ac.ebi.uniprot.parser;

import com.google.common.base.Throwables;
import org.antlr.v4.runtime.*;
import uk.ac.ebi.uniprot.parser.impl.AbstractUniprotLineParser;

import java.lang.reflect.Constructor;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public interface GrammarFactory<L extends Lexer, P extends Parser> {

	public static final String packageName = "uk.ac.ebi.uniprot.parser.antlr";

	public static enum GrammarFactoryEnum {
		Ac, Id, Dt, Kw, Dr, Sq, Gn, Pe, Os, Og, Rn, Rt, Rp, Ra;

		private GrammarFactory factory;
		private AbstractUniprotLineParser parser;

		private GrammarFactory createFactory() {
			final String name = this.name();
			final String lexerName = packageName + "." + name + "LineLexer";
			final String parserName = packageName + "." + name + "LineParser";

			return new GrammarFactory() {
				@Override
				public Lexer createLexer(CharStream in) {
					try {
						Class<? extends Lexer> aClass = (Class<? extends Lexer>) Class.forName(lexerName);
						Constructor<? extends Lexer> constructor = aClass.getConstructor(CharStream.class);
						Lexer lexer = constructor.newInstance(in);
						return lexer;
					} catch (Exception e) {
						throw Throwables.propagate(e);
					}
				}

				@Override
				public Parser createParser(CommonTokenStream tokens) {
					try {
						Class<? extends Parser> aClass = (Class<? extends Parser>) Class.forName(parserName);
						Constructor<? extends Parser> constructor = aClass.getConstructor(TokenStream.class);
						Parser lexer = constructor.newInstance(tokens);
						return lexer;
					} catch (Exception e) {
						throw Throwables.propagate(e);
					}
				}

				@Override
				public String getTopRuleName() {
					return (name + "_" + name).toLowerCase();
				}
			};
		}

		public synchronized GrammarFactory getFactory() {
			if (factory == null) {
				factory = createFactory();
			}
			return factory;
		}
	}

	public L createLexer(CharStream in);

	public P createParser(CommonTokenStream tokens);

	public String getTopRuleName();


}
