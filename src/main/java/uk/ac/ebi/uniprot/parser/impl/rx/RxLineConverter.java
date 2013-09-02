package uk.ac.ebi.uniprot.parser.impl.rx;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.CitationXrefs;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class RxLineConverter implements Converter<RxLineObject, CitationXrefs> {
	DefaultCitationNewFactory factory = DefaultCitationNewFactory.getInstance();
	@Override
	public CitationXrefs convert(RxLineObject f) {
	
		CitationXrefs citationXrefs = factory.buildCitationXrefs();
		if((f==null)||(f.rxs ==null) ||(f.rxs.isEmpty()))
			return citationXrefs;
		for (RxLineObject.RX rx: f.rxs){
			if(rx.type == RxLineObject.DB.PubMed){
				citationXrefs.setPubMedId(rx.value);
			}else if(rx.type == RxLineObject.DB.DOI){
				citationXrefs.setDOI(rx.value);
			}else if(rx.type == RxLineObject.DB.AGRICOLA){
				citationXrefs.setAgricolaId(rx.value);
			}
		}
		return citationXrefs;
	}

}
