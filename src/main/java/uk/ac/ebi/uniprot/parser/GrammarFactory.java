package uk.ac.ebi.uniprot.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import uk.ac.ebi.uniprot.parser.antlr.*;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public interface GrammarFactory<L extends Lexer, P extends Parser> {

	//public static final String packageName = "uk.ac.ebi.uniprot.parser.antlr";

	public static enum GrammarFactoryEnum {
		Uniprot, Ac, Id, Dt, Kw, Dr, Sq, Gn, Pe, Os, Og, Rn, Rt, Rp, Ra, Rg, Rc, Rx, De, Rl, Ft, Oc, Ox, Oh, Cc, Ss;

		private GrammarFactory factory;

		private GrammarFactory createFactory() {
			final String name = this.name();

			//final String lexerName = packageName + "." + name + (name.equals("Uniprot")?"":"Line") + "Lexer";
			//final String parserName = packageName + "." + name + (name.equals("Uniprot")?"":"Line") +"Parser";

			return new GrammarFactory() {
				@Override
				public Lexer createLexer(CharStream in) {
                    switch (GrammarFactoryEnum.this) {
                        case Uniprot:return new UniprotLexer(in);
                        case Ac:return new AcLineLexer(in);
                        case Id:return new IdLineLexer(in);
                        case Dt:return new DtLineLexer(in);
                        case Kw:return new KwLineLexer(in);
                        case Dr:return new DrLineLexer(in);
                        case Sq:return new SqLineLexer(in);
                        case Gn:return new GnLineLexer(in);
                        case Pe:return new PeLineLexer(in);
                        case Os:return new OsLineLexer(in);
                        case Og:return new OgLineLexer(in);
                        case Rn:return new RnLineLexer(in);
                        case Rt:return new RtLineLexer(in);
                        case Rp:return new RpLineLexer(in);
                        case Ra:return new RaLineLexer(in);
                        case Rg:return new RgLineLexer(in);
                        case Rc:return new RcLineLexer(in);
                        case Rx:return new RxLineLexer(in);
                        case De:return new DeLineLexer(in);
                        case Rl:return new RlLineLexer(in);
                        case Ft:return new FtLineLexer(in);
                        case Oc:return new OcLineLexer(in);
                        case Ox:return new OxLineLexer(in);
                        case Oh:return new OhLineLexer(in);
                        case Cc:return new CcLineLexer(in);
                        case Ss:return new SsLineLexer(in);

                        default: throw new RuntimeException ("Lexer is not defined for: "+ GrammarFactoryEnum.this);
                    }

				}

				@Override
				public Parser createParser(CommonTokenStream tokens) {
                    switch (GrammarFactoryEnum.this) {
                        case Uniprot:return new UniprotParser(tokens);
                        case Ac:return new AcLineParser(tokens);
                        case Id:return new IdLineParser(tokens);
                        case Dt:return new DtLineParser(tokens);
                        case Kw:return new KwLineParser(tokens);
                        case Dr:return new DrLineParser(tokens);
                        case Sq:return new SqLineParser(tokens);
                        case Gn:return new GnLineParser(tokens);
                        case Pe:return new PeLineParser(tokens);
                        case Os:return new OsLineParser(tokens);
                        case Og:return new OgLineParser(tokens);
                        case Rn:return new RnLineParser(tokens);
                        case Rt:return new RtLineParser(tokens);
                        case Rp:return new RpLineParser(tokens);
                        case Ra:return new RaLineParser(tokens);
                        case Rg:return new RgLineParser(tokens);
                        case Rc:return new RcLineParser(tokens);
                        case Rx:return new RxLineParser(tokens);
                        case De:return new DeLineParser(tokens);
                        case Rl:return new RlLineParser(tokens);
                        case Ft:return new FtLineParser(tokens);
                        case Oc:return new OcLineParser(tokens);
                        case Ox:return new OxLineParser(tokens);
                        case Oh:return new OhLineParser(tokens);
                        case Cc:return new CcLineParser(tokens);
                        case Ss:return new SsLineParser(tokens);

                        default: throw new RuntimeException ("Parser is not defined for: "+ GrammarFactoryEnum.this);
                    }
				}

//				@Override
//				public String getTopRuleName() {
//					if (name.equals("Uniprot")) return "entry";
//					return (name + "_" + name).toLowerCase();
//				}
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

	//public String getTopRuleName();


}
