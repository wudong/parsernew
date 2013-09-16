package uk.ac.ebi.uniprot.parser.impl.dt;

import uk.ac.ebi.kraken.interfaces.uniprot.EntryAudit;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class DtLineConverter implements Converter<DtLineObject, EntryAudit> {

	@Override
	public EntryAudit convert(DtLineObject f) {
		EntryAudit enAudit = DefaultUniProtFactory.getInstance().buildEntryAudit();
		enAudit.setFirstPublicDate(f.integration_date.toDate());
		enAudit.setLastAnnotationUpdateDate(f.entry_date.toDate());
		enAudit.setEntryVersion(f.entry_version);
		enAudit.setLastSequenceUpdateDate(f.seq_date.toDate());
		enAudit.setSequenceVersion(f.seq_version);
		//isSwissprot cannot be used, this is in entry object
		return enAudit;
	}
}
