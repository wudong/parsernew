package uk.ac.ebi.uniprot.parser.impl.rp;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.CitationSummary;
import uk.ac.ebi.kraken.model.factories.DefaultCitationNewFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class RpLineConverter implements Converter<RpLineObject, List<CitationSummary>> {

	@Override
	public List<CitationSummary> convert(RpLineObject f) {
		List<CitationSummary> citationSummaries =new ArrayList<>();
		String line = f.position;
		if((line ==null)|| line.isEmpty())
			return citationSummaries;
		
		 int index = line.indexOf(", AND ");
	        if (index == -1) {
	            citationSummaries.add(0, DefaultCitationNewFactory.getInstance().buildCitationSummary(line));
	        } else {
	            citationSummaries.add(0, DefaultCitationNewFactory.getInstance().buildCitationSummary(line.substring(index + 6)));
	            line = line.substring(0, index);
	            while ((index = line.lastIndexOf(", ")) != -1) {
	                citationSummaries.add(0, DefaultCitationNewFactory.getInstance().buildCitationSummary(line.substring(index + 2)));
	                line = line.substring(0, index);
	            }
	            citationSummaries.add(0, DefaultCitationNewFactory.getInstance().buildCitationSummary(line));
	        }
		return citationSummaries;
	}
	
}
