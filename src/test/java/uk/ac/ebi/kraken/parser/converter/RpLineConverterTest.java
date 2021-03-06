package uk.ac.ebi.kraken.parser.converter;

import java.util.List;

import org.junit.Test;
import static junit.framework.TestCase.*;

import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.CitationSummary;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rp.RpLineObject;

public class RpLineConverterTest {
	private final RpLineConverter converter = new RpLineConverter();
	@Test
	public void test1(){
		// "RP   NUCLEOTIDE SEQUENCE [MRNA].\n";
		RpLineObject rp =new RpLineObject();
		rp.scopes.add("NUCLEOTIDE SEQUENCE [MRNA]");
		 List<CitationSummary> css =converter.convert(rp);
		 assertEquals(1, css.size());
		 CitationSummary cs = css.get(0);
		 assertEquals("NUCLEOTIDE SEQUENCE [MRNA]", cs.getValue());
	}
	
	@Test
	public void test2(){
		// """RP   NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C), FUNCTION, INTERACTION
        //RP   WITH PKC-3, SUBCELLULAR LOCATION, TISSUE SPECIFICITY, DEVELOPMENTAL
        //RP   STAGE, AND MUTAGENESIS OF PHE-175 AND PHE-221.
		RpLineObject rp =new RpLineObject();
		rp.scopes.add("NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C)");
		rp.scopes.add("FUNCTION");
		rp.scopes.add("INTERACTION WITH PKC-3");
		rp.scopes.add("SUBCELLULAR LOCATION");
		rp.scopes.add("TISSUE SPECIFICITY");
		rp.scopes.add("DEVELOPMENTAL STAGE");
		rp.scopes.add("MUTAGENESIS OF PHE-175 AND PHE-221");

		 List<CitationSummary> css =converter.convert(rp);
		 assertEquals(7, css.size());

		 assertEquals("NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C)", css.get(0).getValue());
		 assertEquals("FUNCTION", css.get(1).getValue());
		 assertEquals("INTERACTION WITH PKC-3", css.get(2).getValue());
		 assertEquals("SUBCELLULAR LOCATION", css.get(3).getValue());
		 assertEquals("TISSUE SPECIFICITY", css.get(4).getValue());
		 assertEquals("DEVELOPMENTAL STAGE", css.get(5).getValue());
		 assertEquals("MUTAGENESIS OF PHE-175 AND PHE-221", css.get(6).getValue());
	}
}
