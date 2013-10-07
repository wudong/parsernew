package uk.ac.ebi.uniprot.parser.impl.entry;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineConverter;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineConverter;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineConverter;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineConverter;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineConverter;

public class EntryObjectConverter implements Converter<EntryObject, UniProtEntry> {
	private final static AcLineConverter acLineConverter = new AcLineConverter();
	private final static CcLineConverter ccLineConverter = new CcLineConverter();
	private final static DeLineConverter deLineConverter = new DeLineConverter();
	private final static DrLineConverter drLineConverter = new DrLineConverter();
	private final static GnLineConverter gnLineConverter = new GnLineConverter();
	private final static IdLineConverter idLineConverter = new IdLineConverter();
	private final static KwLineConverter kwLineConverter = new KwLineConverter();
	private final static OcLineConverter ocLineConverter = new OcLineConverter();
	private final static OgLineConverter OgLineConverter = new OgLineConverter();
	
	private final static OhLineConverter OhLineConverter = new OhLineConverter();
	private final static OsLineConverter OsLineConverter = new OsLineConverter();
	private final static OxLineConverter OxLineConverter = new OxLineConverter();
	private final static PeLineConverter peLineConverter = new PeLineConverter();
	private final static RaLineConverter raLineConverter = new RaLineConverter();
	
	private final static RcLineConverter rcLineConverter = new RcLineConverter();
	private final static RgLineConverter rgLineConverter = new RgLineConverter();
	private final static RlLineConverter rlLineConverter = new RlLineConverter();
	private final static RnLineConverter rnLineConverter = new RnLineConverter();
	private final static RpLineConverter rpLineConverter = new RpLineConverter();
	private final static RtLineConverter rtLineConverter = new RtLineConverter();
	private final static RxLineConverter rxLineConverter = new RxLineConverter();
	private final static SqLineConverter sqLineConverter = new SqLineConverter();
	private final static SsLineConverter ssLineConverter = new SsLineConverter();
	
	
	@Override
	public UniProtEntry convert(EntryObject f) {
		// TODO Auto-generated method stub
		return null;
	}

}
