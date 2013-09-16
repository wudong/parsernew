package uk.ac.ebi.uniprot.parser.impl.oc;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.NcbiTaxon;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class OcLineConverter implements Converter<OcLineObject,  List<NcbiTaxon>> {
	private final DefaultUniProtFactory factory = DefaultUniProtFactory.getInstance();
	@Override
	public List<NcbiTaxon> convert(OcLineObject f) {
		List<NcbiTaxon> taxons =new ArrayList<>();
		for(String name: f.nodes){
			NcbiTaxon taxon = factory.buildNcbiTaxon(name );
			taxons.add(taxon);
		}
		return taxons;
	}

}
