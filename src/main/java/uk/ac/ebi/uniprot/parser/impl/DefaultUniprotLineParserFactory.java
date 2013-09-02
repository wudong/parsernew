package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.parser.GrammarFactory;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.antlr.*;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineObject;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineObject;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineObject;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineObject;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineObject;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineObject;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineObject;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineObject;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineObject;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineObject;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineObject;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineObject;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineModelListener;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineObject;

/**
 * <p/>
 * User: wudong, Date: 19/08/13, Time: 15:55
 */
public class DefaultUniprotLineParserFactory implements UniprotLineParserFactory {
	@Override
	public UniprotLineParser<AcLineObject> createAcLineParser() {
		return new AbstractUniprotLineParser<AcLineObject, AcLineLexer, AcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ac.getFactory(),
				new AcLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<DrLineObject> createDrLineParser() {
		return new AbstractUniprotLineParser<DrLineObject, DrLineLexer, DrLineParser>(
				GrammarFactory.GrammarFactoryEnum.Dr.getFactory(),
				new DrLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<DeLineObject> createDeLineParser() {
		return new AbstractUniprotLineParser<DeLineObject, DeLineLexer, DeLineParser>(
				GrammarFactory.GrammarFactoryEnum.De.getFactory(),
				new DeLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<DtLineObject> createDtLineParser() {
		return new AbstractUniprotLineParser<DtLineObject, DtLineLexer, DtLineParser>(
				GrammarFactory.GrammarFactoryEnum.Dt.getFactory(),
				new DtLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<GnLineObject> createGnLineParser() {
		return new AbstractUniprotLineParser<GnLineObject, GnLineLexer, GnLineParser>(
				GrammarFactory.GrammarFactoryEnum.Gn.getFactory(),
				new GnLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<IdLineObject> createIdLineParser() {
		return new AbstractUniprotLineParser<IdLineObject, IdLineLexer, IdLineParser>(
				GrammarFactory.GrammarFactoryEnum.Id.getFactory(),
				new IdLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<KwLineObject> createKwLineParser() {
		return new AbstractUniprotLineParser<KwLineObject, KwLineLexer, KwLineParser>(
				GrammarFactory.GrammarFactoryEnum.Kw.getFactory(),
				new KwLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OgLineObject> createOgLineParser() {
		return new AbstractUniprotLineParser<OgLineObject, OgLineLexer, OgLineParser>(
				GrammarFactory.GrammarFactoryEnum.Og.getFactory(),
				new OgLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<OsLineObject> createOsLineParser() {
		return new AbstractUniprotLineParser<OsLineObject, OsLineLexer, OsLineParser>(
				GrammarFactory.GrammarFactoryEnum.Os.getFactory(),
				new OsLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<PeLineObject> createPeLineParser() {
		return new AbstractUniprotLineParser<PeLineObject, PeLineLexer, PeLineParser>(
				GrammarFactory.GrammarFactoryEnum.Pe.getFactory(),
				new PeLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<SqLineObject> createSqLineParser() {
		return new AbstractUniprotLineParser<SqLineObject, SqLineLexer, SqLineParser>(
				GrammarFactory.GrammarFactoryEnum.Sq.getFactory(),
				new SqLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RnLineObject> createRnLineParser() {
		return new AbstractUniprotLineParser<RnLineObject, RnLineLexer, RnLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rn.getFactory(),
				new RnLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RtLineObject> createRtLineParser() {
		return new AbstractUniprotLineParser<RtLineObject, RtLineLexer, RtLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rt.getFactory(),
				new RtLineModelListener()
		);
	}


	@Override
	public UniprotLineParser<RpLineObject> createRpLineParser() {
		return new AbstractUniprotLineParser<RpLineObject, RpLineLexer, RpLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rp.getFactory(),
				new RpLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RaLineObject> createRaLineParser() {
		return new AbstractUniprotLineParser<RaLineObject, RaLineLexer, RaLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ra.getFactory(),
				new RaLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RgLineObject> createRgLineParser() {
		return new AbstractUniprotLineParser<RgLineObject, RgLineLexer, RgLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rg.getFactory(),
				new RgLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RcLineObject> createRcLineParser() {
		return new AbstractUniprotLineParser<RcLineObject, RcLineLexer, RcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rc.getFactory(),
				new RcLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RxLineObject> createRxLineParser() {
		return new AbstractUniprotLineParser<RxLineObject, RxLineLexer, RxLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rx.getFactory(),
				new RxLineModelListener()
		);
	}

	@Override
	public UniprotLineParser<RlLineObject> createRlLineParser() {
		return new AbstractUniprotLineParser<RlLineObject, RlLineLexer, RlLineParser>(
				GrammarFactory.GrammarFactoryEnum.Rl.getFactory(),
				new RlLineModelListener()
		);
	}
	
	@Override
	public UniprotLineParser<FtLineObject> createFtLineParser() {
		return new AbstractUniprotLineParser<FtLineObject, FtLineLexer, FtLineParser>(
				GrammarFactory.GrammarFactoryEnum.Ft.getFactory(),
				new FtLineModelListener()
		);
	}
	
	@Override
	public UniprotLineParser<OcLineObject> createOcLineParser() {
		return new AbstractUniprotLineParser<OcLineObject, OcLineLexer, OcLineParser>(
				GrammarFactory.GrammarFactoryEnum.Oc.getFactory(),
				new OcLineModelListener()
		);
	}


}
