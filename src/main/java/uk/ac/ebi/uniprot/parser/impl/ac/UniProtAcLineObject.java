package uk.ac.ebi.uniprot.parser.impl.ac;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.PrimaryUniProtAccession;
import uk.ac.ebi.kraken.interfaces.uniprot.SecondaryUniProtAccession;

public class UniProtAcLineObject {
	public PrimaryUniProtAccession primaryAccession;
	public List<SecondaryUniProtAccession> secondAccessions = new ArrayList<>();
}
