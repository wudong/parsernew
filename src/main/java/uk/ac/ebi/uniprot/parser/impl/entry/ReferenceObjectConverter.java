package uk.ac.ebi.uniprot.parser.impl.entry;

import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Author;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Citation;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject.ReferenceObject;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rg.RgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineConverter;

public class ReferenceObjectConverter implements Converter<EntryObject.ReferenceObject, Citation> {
	private final static RaLineConverter raLineConverter = new RaLineConverter();
	private final static RcLineConverter rcLineConverter = new RcLineConverter();
	private final static RgLineConverter rgLineConverter = new RgLineConverter();
	private final static RlLineConverter rlLineConverter = new RlLineConverter();
	private final static RnLineConverter rnLineConverter = new RnLineConverter();
	private final static RpLineConverter rpLineConverter = new RpLineConverter();
	private final static RtLineConverter rtLineConverter = new RtLineConverter();
	private final static RxLineConverter rxLineConverter = new RxLineConverter();
	
	@Override
	public Citation convert(ReferenceObject f) {
		Citation citation = rlLineConverter.convert(f.rl);
		if(f.ra !=null){
			List<Author> authors =raLineConverter.convert(f.ra);
			citation.setAuthors(authors);
		}
		citation.setEvidenceIds(rnLineConverter.convert(f.rn));
		if(f.rc !=null){
			citation.setSampleSources(rcLineConverter.convert(f.rc));
		}
		if(f.rg !=null){
			citation.setAuthoringGroup(rgLineConverter.convert(f.rg));
		}
		if(f.rp !=null){
			citation.setCitationSummary( rpLineConverter.convert(f.rp));
		}
		if(f.rt !=null){
			citation.setTitle(rtLineConverter.convert(f.rt));
		}
		if(f.rx !=null){
			citation.setCitationsXrefs( rxLineConverter.convert(f.rx));
		}
		return citation;
	}

}
