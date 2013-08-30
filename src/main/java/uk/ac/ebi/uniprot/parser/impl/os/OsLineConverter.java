package uk.ac.ebi.uniprot.parser.impl.os;


import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.Organism;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class OsLineConverter implements Converter<OsLineObject, Organism> {

	//migrating old parser
	@Override
	public Organism convert(OsLineObject f) {
		String value = f.organism_species;
		 List<String> names = new ArrayList<String>();
	        int level = 0;
	        boolean uppercase = false;
	        StringBuffer sb = new StringBuffer();
	        for (int iii = value.length() - 2; iii > -1; iii--) {
	            char c = value.charAt(iii);
	            if (c == '(') {
	                level --;
	                sb.insert(0, "(");
	                if (level == 0) {
	                    /**
	                     * If the '(' is at index 0 then it is part of the scientific name and not the common name.
	                     * So if this case is verified, then do nothing with it.
	                     */
	                    if (uppercase && iii > 0) {
	                        String name = sb.toString().trim();
	                        names.add(0, name.substring(1, name.length() - 1));
	                        sb = new StringBuffer();
	                    }
	                }

	            } else if (c == ')') {
	                level++;
	                sb.insert(0, ")");
	            } else {
	                if (c == Character.toUpperCase(c)) {
	                    uppercase = true;
	                } else {
	                    uppercase = false;
	                }
	                sb.insert(0, c);
	            }
	        }

	        names.add(0, sb.toString().trim());
	        Organism organism = DefaultUniProtFactory.getInstance().buildOrganism();
	        organism.setScientificName(DefaultUniProtFactory.getInstance().buildOrganismScientificName(names.get(0)));
	        if (names.size() == 2) {
	            organism.setCommonName(DefaultUniProtFactory.getInstance().buildOrganismCommonName(names.get(1)));
	        } else if (names.size() == 3) {
	            organism.setCommonName(DefaultUniProtFactory.getInstance().buildOrganismCommonName(names.get(1)));
	            organism.setSynonym(DefaultUniProtFactory.getInstance().buildOrganismSynonym(names.get(2)));
	        }
	        return organism;
	}

}
