package uk.ac.ebi.uniprot.parser.impl.entry;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.antlr.UniprotParser;
import uk.ac.ebi.uniprot.parser.antlr.UniprotParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineObject;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineObject;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineObject;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineObject;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineObject;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineObject;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineObject;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineObject;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineObject;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineObject;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineObject;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineObject;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineObject;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class EntryModelListener extends UniprotParserBaseListener implements ParseTreeObjectExtractor<EntryObject> {

	private EntryObject object ;
	private EntryObject.ReferenceObject ref;
	private DefaultUniprotLineParserFactory parserFactory = new DefaultUniprotLineParserFactory();

	@Override
	public void enterEntry(@NotNull UniprotParser.EntryContext ctx) {
		object = new EntryObject();
	}

	@Override
	public void exitKw(@NotNull UniprotParser.KwContext ctx) {
		UniprotLineParser<KwLineObject> kwLineParser = parserFactory.createKwLineParser();
		KwLineObject parse = kwLineParser.parse(ctx.getText());
		object.kw = parse;
	}

	@Override
	public void exitDt(@NotNull UniprotParser.DtContext ctx) {
		UniprotLineParser<DtLineObject> kwLineParser = parserFactory.createDtLineParser();
		DtLineObject parse = kwLineParser.parse(ctx.getText());
		object.dt = parse;
	}

	@Override
	public void exitRp(@NotNull UniprotParser.RpContext ctx) {
		UniprotLineParser<RpLineObject> kwLineParser = parserFactory.createRpLineParser();
		RpLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rp = parse;
	}

	@Override
	public void exitOs(@NotNull UniprotParser.OsContext ctx) {
		UniprotLineParser<OsLineObject> kwLineParser = parserFactory.createOsLineParser();
		OsLineObject parse = kwLineParser.parse(ctx.getText());
		object.os = parse;
	}

	@Override
	public void exitRn(@NotNull UniprotParser.RnContext ctx) {
		UniprotLineParser<RnLineObject> kwLineParser = parserFactory.createRnLineParser();
		RnLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rn = parse;
	}

	@Override
	public void exitRl(@NotNull UniprotParser.RlContext ctx) {
		UniprotLineParser<RlLineObject> kwLineParser = parserFactory.createRlLineParser();
		RlLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rl = parse;
	}

	@Override
	public void exitSq(@NotNull UniprotParser.SqContext ctx) {
		UniprotLineParser<SqLineObject> kwLineParser = parserFactory.createSqLineParser();
		SqLineObject parse = kwLineParser.parse(ctx.getText());
		object.sq = parse;
	}

	@Override
	public void exitRg(@NotNull UniprotParser.RgContext ctx) {
		UniprotLineParser<RgLineObject> kwLineParser = parserFactory.createRgLineParser();
		RgLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rg = parse;
	}

	@Override
	public void exitOx(@NotNull UniprotParser.OxContext ctx) {
		UniprotLineParser<OxLineObject> kwLineParser = parserFactory.createOxLineParser();
		OxLineObject parse = kwLineParser.parse(ctx.getText());
		object.ox = parse;
	}

	@Override
	public void exitRc(@NotNull UniprotParser.RcContext ctx) {
		UniprotLineParser<RcLineObject> kwLineParser = parserFactory.createRcLineParser();
		RcLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rc = parse;
	}

	@Override
	public void enterReference(@NotNull UniprotParser.ReferenceContext ctx) {
		this.ref = new EntryObject.ReferenceObject();
	}

	@Override
	public void exitReference(@NotNull UniprotParser.ReferenceContext ctx) {
		object.ref.add(this.ref);
		this.ref = null;
	}

	@Override
	public void exitId(@NotNull UniprotParser.IdContext ctx) {
		UniprotLineParser<IdLineObject> kwLineParser = parserFactory.createIdLineParser();
		IdLineObject parse = kwLineParser.parse(ctx.getText());
		object.id = parse;
	}

	@Override
	public void exitDe(@NotNull UniprotParser.DeContext ctx) {
		UniprotLineParser<DeLineObject> kwLineParser = parserFactory.createDeLineParser();
		DeLineObject parse = kwLineParser.parse(ctx.getText());
		object.de = parse;
	}

	@Override
	public void exitGn(@NotNull UniprotParser.GnContext ctx) {
		UniprotLineParser<GnLineObject> kwLineParser = parserFactory.createGnLineParser();
		GnLineObject parse = kwLineParser.parse(ctx.getText());
		object.gn = parse;
	}

	@Override
	public void exitRa(@NotNull UniprotParser.RaContext ctx) {
		UniprotLineParser<RaLineObject> kwLineParser = parserFactory.createRaLineParser();
		RaLineObject parse = kwLineParser.parse(ctx.getText());
		ref.ra = parse;
	}

	@Override
	public void exitPe(@NotNull UniprotParser.PeContext ctx) {
		UniprotLineParser<PeLineObject> kwLineParser = parserFactory.createPeLineParser();
		PeLineObject parse = kwLineParser.parse(ctx.getText());
		object.pe = parse;
	}

	@Override
	public void exitOc(@NotNull UniprotParser.OcContext ctx) {
		UniprotLineParser<OcLineObject> kwLineParser = parserFactory.createOcLineParser();
		OcLineObject parse = kwLineParser.parse(ctx.getText());
		object.oc = parse;
	}

	@Override
	public void exitAc(@NotNull UniprotParser.AcContext ctx) {
		UniprotLineParser<AcLineObject> kwLineParser = parserFactory.createAcLineParser();
		AcLineObject parse = kwLineParser.parse(ctx.getText());
		object.ac = parse;
	}

	@Override
	public void exitRt(@NotNull UniprotParser.RtContext ctx) {
		UniprotLineParser<RtLineObject> kwLineParser = parserFactory.createRtLineParser();
		RtLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rt = parse;
	}

	@Override
	public void exitFt(@NotNull UniprotParser.FtContext ctx) {
		UniprotLineParser<FtLineObject> kwLineParser = parserFactory.createFtLineParser();
		FtLineObject parse = kwLineParser.parse(ctx.getText());
		object.ft = parse;
	}

	@Override
	public void exitRx(@NotNull UniprotParser.RxContext ctx) {
		UniprotLineParser<RxLineObject> kwLineParser = parserFactory.createRxLineParser();
		RxLineObject parse = kwLineParser.parse(ctx.getText());
		ref.rx = parse;
	}

	@Override
	public void exitCc(@NotNull UniprotParser.CcContext ctx) {
		UniprotLineParser<CcLineObject> kwLineParser = parserFactory.createCcLineParser();
		CcLineObject parse = kwLineParser.parse(ctx.getText());
		object.cc = parse;
	}

	@Override
	public void exitOg(@NotNull UniprotParser.OgContext ctx) {
		UniprotLineParser<OgLineObject> kwLineParser = parserFactory.createOgLineParser();
		OgLineObject parse = kwLineParser.parse(ctx.getText());
		object.og = parse;
	}

	@Override
	public void exitDr(@NotNull UniprotParser.DrContext ctx) {
		UniprotLineParser<DrLineObject> kwLineParser = parserFactory.createDrLineParser();
		DrLineObject parse = kwLineParser.parse(ctx.getText());
		object.dr = parse;
	}

	@Override
	public void exitOh(@NotNull UniprotParser.OhContext ctx) {
		UniprotLineParser<OhLineObject> kwLineParser = parserFactory.createOhLineParser();
		OhLineObject parse = kwLineParser.parse(ctx.getText());
		object.oh = parse;
	}

	@Override
	public EntryObject getObject() {
        return this.object;
    }
}
