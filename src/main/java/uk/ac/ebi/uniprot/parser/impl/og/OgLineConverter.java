package uk.ac.ebi.uniprot.parser.impl.og;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.Organelle;
import uk.ac.ebi.uniprot.parser.Converter;

public class OgLineConverter implements Converter<OgLineObject, List<Organelle> > {
	//to do, waiting for OgLineObject to be complete
	@Override
	public List<Organelle> convert(OgLineObject f) {
		List<Organelle> organelles = new ArrayList<Organelle>();
		for(String val: f.plasmidNames){
			
		}
		return organelles;
	}

}
