package uk.ac.ebi.uniprot.parser.impl.rc;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.SampleSource;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.SampleSourceType;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class RcLineConverter implements Converter<RcLineObject, List<SampleSource>> {

	@Override
	public List<SampleSource> convert(RcLineObject f) {
		List<SampleSource> sss = new ArrayList<> ();
		for (RcLineObject.RC rc:f.rcs){
			SampleSourceType type =convert(rc.tokenType);
			for(String val: rc.values){
				SampleSource sampleSource = DefaultCitationNewFactory.getInstance().buildSampleSource(type);
				sampleSource.setValue(val);
				//add evidence id
				sss.add(sampleSource);
			}
			
		}
		return sss;
	}

	private SampleSourceType convert(RcLineObject.RcTokenEnum type){
		switch(type){
		case STRAIN:
			return SampleSourceType.STRAIN;
		case PLASMID:
			return SampleSourceType.PLASMID;
		case TRANSPOSON:
			return SampleSourceType.TRANSPOSON;
		case TISSUE:
			return SampleSourceType.TISSUE;
		default:
			return SampleSourceType.STRAIN;
		}
	
	}
}
