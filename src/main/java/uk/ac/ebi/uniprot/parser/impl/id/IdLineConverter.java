package uk.ac.ebi.uniprot.parser.impl.id;

import uk.ac.ebi.kraken.interfaces.factories.UniProtFactory;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntryType;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class IdLineConverter implements Converter<IdLineObject, UniProtEntry> {
	private UniProtFactory factory = DefaultUniProtFactory.getInstance();
	@Override
	public UniProtEntry convert(IdLineObject f) {
		UniProtEntry entry = factory.buildEntry();
		entry.setUniProtId(f.entryName);
		if(f.reviewed){
			entry.setType(UniProtEntryType.SWISSPROT);
		}else{
			entry.setType(UniProtEntryType.TREMBL);
		}
		return entry;
	}

}
