package uk.ac.ebi.kraken.parser.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static junit.framework.TestCase.*;
import uk.ac.ebi.kraken.interfaces.common.Value;
import uk.ac.ebi.kraken.interfaces.uniprot.Gene;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.GeneName;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.GeneNameSynonym;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.ORFName;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject;

public class GnLineConverterTest {
	private final GnLineConverter converter = new GnLineConverter();
	@Test
	public void test1(){
		//GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
		GnLineObject gnLineObj= new GnLineObject();
		GnLineObject.GnName gnName = new GnLineObject.GnName();
		gnName.type =GnLineObject.GnNameType.GENAME;
		gnName.names.add("Jon99Cii");
		GnLineObject.GnName gnSName = new GnLineObject.GnName();
		gnSName.type = GnLineObject.GnNameType.SYNNAME;
		gnSName.names.add("SER1");
		gnSName.names.add("SER5");
		gnSName.names.add("Ser99Da");
		
		GnLineObject.GnName gnOrfName = new GnLineObject.GnName();
		gnOrfName.type =GnLineObject.GnNameType.ORFNAME;
		gnOrfName.names.add("CG7877");
		GnLineObject.GnObject gnObj =new GnLineObject.GnObject();
		gnObj.names.add(gnName);
		gnObj.names.add(gnSName);
		gnObj.names.add(gnOrfName);
		gnLineObj.gnObjects.add(gnObj);
		List<Gene> genes = converter.convert( gnLineObj) ;
		assertEquals(1, genes.size());
		Gene gene = genes.get(0);
		assertTrue(gene.hasGeneName());
		GeneName gname = gene.getGeneName();
		assertEquals("Jon99Cii", gname.getValue());
		assertEquals(3, gene.getGeneNameSynonyms().size());
		List<String> syn = new ArrayList<String>();
		syn.add("SER1");
		syn.add("SER5");
		syn.add("Ser99Da");
		for(Value val: gene.getGeneNameSynonyms()){
			validate(val, syn);
		}
		assertEquals(0, gene.getOrderedLocusNames().size());
		
		assertEquals(1, gene.getORFNames().size());
		List<String> orf = new ArrayList<String>();
		orf.add("CG7877");
		validate(gene.getORFNames().get(0), orf);
	}
	@Test
	public void test2(){
		//GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
      //  |GN   and
      //  |GN   Name=Jon99Cii2;
		
		GnLineObject gnLineObj= new GnLineObject();
		GnLineObject.GnName gnName = new GnLineObject.GnName();
		gnName.type =GnLineObject.GnNameType.GENAME;
		gnName.names.add("Jon99Cii");
		GnLineObject.GnName gnSName = new GnLineObject.GnName();
		gnSName.type = GnLineObject.GnNameType.SYNNAME;
		gnSName.names.add("SER1");
		gnSName.names.add("SER5");
		gnSName.names.add("Ser99Da");
		
		GnLineObject.GnName gnOrfName = new GnLineObject.GnName();
		gnOrfName.type =GnLineObject.GnNameType.ORFNAME;
		gnOrfName.names.add("CG7877");
		GnLineObject.GnObject gnObj =new GnLineObject.GnObject();
		gnObj.names.add(gnName);
		gnObj.names.add(gnSName);
		gnObj.names.add(gnOrfName);
		gnLineObj.gnObjects.add(gnObj);
		GnLineObject.GnObject gnObj2 =new GnLineObject.GnObject();
		GnLineObject.GnName gnName2 = new GnLineObject.GnName();
		gnName2.type =GnLineObject.GnNameType.GENAME;
		gnName2.names.add("Jon99Cii2");
		gnObj2.names.add(gnName2);
		gnLineObj.gnObjects.add(gnObj2);
		
		List<Gene> genes = converter.convert( gnLineObj) ;
		assertEquals(2, genes.size());
		Gene gene = genes.get(0);
		Gene gene2 = genes.get(1);
		assertTrue(gene.hasGeneName());
		GeneName gname = gene.getGeneName();
		assertEquals("Jon99Cii", gname.getValue());
		assertEquals(3, gene.getGeneNameSynonyms().size());
		List<String> syn = new ArrayList<String>();
		syn.add("SER1");
		syn.add("SER5");
		syn.add("Ser99Da");
		for(Value val: gene.getGeneNameSynonyms()){
			validate(val, syn);
		}
		assertEquals(0, gene.getOrderedLocusNames().size());
		
		assertEquals(1, gene.getORFNames().size());
		List<String> orf = new ArrayList<String>();
		orf.add("CG7877");
		validate(gene.getORFNames().get(0), orf);
		
		assertTrue(gene2.hasGeneName());
		assertEquals("Jon99Cii2",  gene2.getGeneName().getValue());
		assertEquals(0, gene2.getGeneNameSynonyms().size());
		assertEquals(0, gene2.getOrderedLocusNames().size());
		assertEquals(0, gene2.getORFNames().size());
	
		
	}
	
	@Test
	public void testEvidenceTag(){
		/*
		 * GN   Name=blaCTX-M-14{EI4}; Synonyms=beta-lactamase CTX-M-14{EI7},
          GN   bla-CTX-M-14a{EI8, EI9}, ORFNames=ETN48_p0088{EI5}
		 */
		GnLineObject gnLineObj= new GnLineObject();
		GnLineObject.GnName gnName = new GnLineObject.GnName();
		gnName.type =GnLineObject.GnNameType.GENAME;
		gnName.names.add("blaCTX-M-14");
		List<String> evs = new ArrayList<>();
		evs.add("EI4");
		gnName.getEvidenceInfo().evidences.put("blaCTX-M-14", evs );
		GnLineObject.GnName gnSName = new GnLineObject.GnName();
		gnSName.type = GnLineObject.GnNameType.SYNNAME;
		gnSName.names.add("beta-lactamase CTX-M-14");
		gnSName.names.add("bla-CTX-M-14a");
		evs = new ArrayList<>();
		evs.add("EI7");
		gnSName.getEvidenceInfo().evidences.put("beta-lactamase CTX-M-14", evs );
		evs = new ArrayList<>();
		evs.add("EI8");
		evs.add("EI9");
		gnSName.getEvidenceInfo().evidences.put("bla-CTX-M-14a", evs );
		
		GnLineObject.GnName gnOrfName = new GnLineObject.GnName();
		gnOrfName.type =GnLineObject.GnNameType.ORFNAME;
		gnOrfName.names.add("ETN48_p0088");
		evs = new ArrayList<>();
		evs.add("EI5");
		gnOrfName.getEvidenceInfo().evidences.put("ETN48_p0088", evs );
		GnLineObject.GnObject gnObj =new GnLineObject.GnObject();
		gnObj.names.add(gnName);
		gnObj.names.add(gnSName);
		gnObj.names.add(gnOrfName);
		gnLineObj.gnObjects.add(gnObj);
		List<Gene> genes = converter.convert( gnLineObj) ;
		assertEquals(1, genes.size());
		Gene gene = genes.get(0);
		assertTrue(gene.hasGeneName());
		GeneName gname = gene.getGeneName();
	
		assertEquals("blaCTX-M-14", gname.getValue());
		List<EvidenceId> evIDs =gname.getEvidenceIds();
		assertEquals(1, evIDs.size());
		EvidenceId evId = evIDs.get(0);
	
		assertEquals("EI4", evId.getValue());
		assertEquals(2, gene.getGeneNameSynonyms().size());
		List<String> syn = new ArrayList<String>();
		syn.add("beta-lactamase CTX-M-14");
		syn.add("bla-CTX-M-14a");
		GeneNameSynonym synName1 = gene.getGeneNameSynonyms().get(0);
		GeneNameSynonym synName2 = gene.getGeneNameSynonyms().get(1);
		for(GeneNameSynonym val: gene.getGeneNameSynonyms()){
			validate(val, syn);
			if(val.getValue().equals("bla-CTX-M-14a")){
				synName2 =val;
			}
			if(val.getValue().equals("beta-lactamase CTX-M-14")){
				synName1 =val;
			}
		}
		evIDs =synName1.getEvidenceIds();
		assertEquals(1, evIDs.size());
		 evId = evIDs.get(0);
	
		assertEquals("EI7", evId.getValue());
		
		evIDs =synName2.getEvidenceIds();
		assertEquals(2, evIDs.size());
		 evId = evIDs.get(0);
		 EvidenceId evId2 = evIDs.get(1);
		assertEquals("EI8", evId.getValue());
		assertEquals("EI9", evId2.getValue());
		
		assertEquals(0, gene.getOrderedLocusNames().size());
		
		assertEquals(1, gene.getORFNames().size());
		List<String> orf = new ArrayList<String>();
		orf.add("ETN48_p0088");
		validate(gene.getORFNames().get(0), orf);
		ORFName orfName = gene.getORFNames().get(0);
		evIDs =orfName.getEvidenceIds();
		assertEquals(1, evIDs.size());
		 evId = evIDs.get(0);
	
		assertEquals("EI5", evId.getValue());
	}
	private void validate(Value val, List<String> vals){
		assertTrue(vals.contains(val.getValue()));
	}
}
