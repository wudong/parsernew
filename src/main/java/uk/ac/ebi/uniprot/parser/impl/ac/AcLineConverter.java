package uk.ac.ebi.uniprot.parser.impl.ac;

import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class AcLineConverter implements Converter<AcLineObject, UniProtAcLineObject> {

	@Override
	public UniProtAcLineObject convert(AcLineObject f) {
		UniProtAcLineObject uniObj = new UniProtAcLineObject();
		uniObj.primaryAccession =DefaultUniProtFactory.getInstance().buildPrimaryUniProtAccession(f.primaryAcc);
		for(String s: f.secondaryAcc){
			uniObj.secondAccessions.add(DefaultUniProtFactory.getInstance().buildSecondaryUniProtAccession(s));
		}		
		return uniObj;
	}

}
