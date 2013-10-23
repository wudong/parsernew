package uk.ac.ebi.uniprot.parser.impl;

import org.antlr.v4.runtime.*;
import uk.ac.ebi.uniprot.parser.DefaultErrorListener;
import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.antlr.*;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineErrorListener;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryErrorListener;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineErrorListener;

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
        lexer.removeErrorListeners();

        if (lexer instanceof FtLineLexer){
            lexer.addErrorListener(new FtLineErrorListener(true));
        }else if (lexer instanceof CcLineLexer){
            lexer.addErrorListener(new CcLineErrorListener(true));
        }else if (lexer instanceof UniprotLexer){
            lexer.addErrorListener(new EntryErrorListener(true));
        }else {
            lexer.addErrorListener(new DefaultErrorListener(true));
        }

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		P parser = factory.createParser(tokens);
        parser.removeErrorListeners();

        if (parser instanceof FtLineParser){
            parser.addErrorListener(new FtLineErrorListener(false));
        }else if (parser instanceof CcLineParser){
            parser.addErrorListener(new CcLineErrorListener(false));
        }else if (parser instanceof UniprotParser){
            parser.addErrorListener(new EntryErrorListener(false));
        }else {
            parser.addErrorListener(new DefaultErrorListener(false));
        }

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

        //remove thie reflection to improve error handling and parsing speed.
        if (parser instanceof AcLineParser){
            ((AcLineParser) parser).ac_ac();
        }else if (parser instanceof CcLineParser){
            ((CcLineParser) parser).cc_cc();
        }else if (parser instanceof DeLineParser){
            ((DeLineParser) parser).de_de();
        }else if (parser instanceof DrLineParser){
            ((DrLineParser) parser).dr_dr();
        }else if (parser instanceof DtLineParser){
            ((DtLineParser) parser).dt_dt();
        }else if (parser instanceof FtLineParser){
            ((FtLineParser) parser).ft_ft();
        }else if (parser instanceof GnLineParser){
            ((GnLineParser) parser).gn_gn();
        }else if (parser instanceof IdLineParser){
            ((IdLineParser) parser).id_id();
        }else if (parser instanceof KwLineParser){
            ((KwLineParser) parser).kw_kw();
        }else if (parser instanceof OcLineParser){
            ((OcLineParser) parser).oc_oc();
        }else if (parser instanceof OgLineParser){
            ((OgLineParser) parser).og_og();
        }else if (parser instanceof OhLineParser){
            ((OhLineParser) parser).oh_oh();
        }else if (parser instanceof OsLineParser){
            ((OsLineParser) parser).os_os();
        }else if (parser instanceof OxLineParser){
            ((OxLineParser) parser).ox_ox();
        }else if (parser instanceof PeLineParser){
            ((PeLineParser) parser).pe_pe();
        }else if (parser instanceof RaLineParser){
            ((RaLineParser) parser).ra_ra();
        }else if (parser instanceof RcLineParser){
            ((RcLineParser) parser).rc_rc();
        }else if (parser instanceof RgLineParser){
            ((RgLineParser) parser).rg_rg();
        }else if (parser instanceof RlLineParser){
            ((RlLineParser) parser).rl_rl();
        }else if (parser instanceof RnLineParser){
            ((RnLineParser) parser).rn_rn();
        }else if (parser instanceof RpLineParser){
            ((RpLineParser) parser).rp_rp();
        }else if (parser instanceof RtLineParser){
            ((RtLineParser) parser).rt_rt();
        }else if (parser instanceof RxLineParser){
            ((RxLineParser) parser).rx_rx();
        }else if (parser instanceof SqLineParser){
            ((SqLineParser) parser).sq_sq();
        }else if (parser instanceof SsLineParser){
            ((SsLineParser) parser).ss_ss();
        }else if (parser instanceof UniprotParser){
            ((UniprotParser) parser).entry();
        }else{
            throw new RuntimeException("Parser's type : "+parser.getClass() +" is not recognized.");
        }

		return listener.getObject();
	}

}
