package uk.ac.ebi.kraken.parser.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static junit.framework.TestCase.*;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.DBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.FourFieldDBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.ThreeFieldDBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.TwoFieldDBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.XRefDBType;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;

public class DrLineConverterTest {
	final private DrLineConverter converter = new DrLineConverter();
	@Test
	public void test(){
		/**
		 *  val drLine = """DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
                 |DR   RefSeq; YP_031579.1; NC_005946.1.
                 |DR   ProteinModelPortal; Q6GZX4; -.
                 |DR   GeneID; 2947773; -.
                 |DR   ProtClustDB; CLSP2511514; -.
                 |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
                 |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
                 |DR   InterPro; IPR007031; Poxvirus_VLTF3.
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null ));	
		obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null ));
		obj.drObjects.add(creatDrObject("ProtClustDB", "CLSP2511514", "-", null, null ));	
		obj.drObjects.add(creatDrObject("GO", "GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null ));
		obj.drObjects.add(creatDrObject("GO", "GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null ));
		obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null ));
		List<DBCrossReference> xrefs = converter.convert(obj);
		assertEquals(7, xrefs.size());
		validate( xrefs.get(0), XRefDBType.EMBL,
				"AY548484", "AAT09660.1", "-", "Genomic_DNA");

		validate( xrefs.get(1), XRefDBType.REFSEQ,
				"YP_031579.1", "NC_005946.1", null, null);
		validate( xrefs.get(2), XRefDBType.GENEID,
				"2947773", "-", null, null);
		validate( xrefs.get(3), XRefDBType.PROTCLUSTDB,
				"CLSP2511514", "-", null, null);
		validate( xrefs.get(4), XRefDBType.GO,
				"GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null);
		validate( xrefs.get(5), XRefDBType.GO,
				"GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null );
		validate( xrefs.get(6), XRefDBType.INTERPRO,
				"IPR007031", "Poxvirus_VLTF3", null, null );
	}
	@Test
	public void testEvidence(){
		//"DR   EMBL; CP001509; ACT41999.1; -; Genomic_DNA.{EI3}
        //DR   EMBL; AM946981; CAQ30614.1; -; Genomic_DNA.{EI4}
		//DR   GeneID; 2947773; -.
		
		DrLineObject obj = new DrLineObject();
		DrLineObject.DrObject obj1 =creatDrObject("EMBL", "CP001509", "ACT41999.1", "-", "Genomic_DNA"  );
		DrLineObject.DrObject obj2 =creatDrObject("EMBL", "AM946981", "CAQ30614.1", "-", "Genomic_DNA"  );
		DrLineObject.DrObject obj3 =creatDrObject("GeneID", "2947773", "-", null, null );
		obj.drObjects.add(obj1);
		obj.drObjects.add(obj2);
		obj.drObjects.add(obj3);
		List<String> evIds = new ArrayList<String>();
		evIds.add("EI3");
		obj.evidenceInfo.evidences.put(obj1, evIds);
		evIds = new ArrayList<String>();
		evIds.add("EI4");
		obj.evidenceInfo.evidences.put(obj2, evIds);
		List<DBCrossReference> xrefs = converter.convert(obj);
		assertEquals(3, xrefs.size());
		DBCrossReference xref1 =xrefs.get(0);
		DBCrossReference xref2=xrefs.get(1);
		DBCrossReference xref3=xrefs.get(2);
		validate(xref1 , XRefDBType.EMBL,
				"CP001509", "ACT41999.1", "-", "Genomic_DNA");
		validate(xref2 , XRefDBType.EMBL,
				"AM946981", "CAQ30614.1", "-", "Genomic_DNA");
		validate( xref3, XRefDBType.GENEID,
				"2947773", "-", null, null);
		List<EvidenceId> eviIds1 = xref1.getEvidenceIds();
		List<EvidenceId> eviIds2 = xref2.getEvidenceIds();
		List<EvidenceId> eviIds3 = xref3.getEvidenceIds();
		assertEquals(1, eviIds1.size());
		assertEquals(1, eviIds2.size());
		assertEquals(0, eviIds3.size());
		assertEquals("EI3", eviIds1.get(0).getValue());
		assertEquals("EI4", eviIds2.get(0).getValue());
		
	}
	private void validate(DBCrossReference xref, XRefDBType type,
			String first, String second,
			String third, String fourth){
		if(fourth ==null){
			if(third ==null){
				assertTrue(xref instanceof TwoFieldDBCrossReference);
				TwoFieldDBCrossReference xref2 = (TwoFieldDBCrossReference) xref;
				assertEquals(first, xref2.getFirst().getValue());
				assertEquals(second, xref2.getSecond().getValue());
			}else{
				assertTrue(xref instanceof ThreeFieldDBCrossReference);
				ThreeFieldDBCrossReference xref3 = (ThreeFieldDBCrossReference) xref;
				assertEquals(first, xref3.getFirst().getValue());
				assertEquals(second, xref3.getSecond().getValue());
				assertEquals(third, xref3.getThird().getValue());
			}
		}else{
			assertTrue(xref instanceof FourFieldDBCrossReference);
			FourFieldDBCrossReference xref4 = (FourFieldDBCrossReference) xref;
			assertEquals(first, xref4.getFirst().getValue());
			assertEquals(second, xref4.getSecond().getValue());
			assertEquals(third, xref4.getThird().getValue());
			assertEquals(fourth, xref4.getFourth().getValue());
		}
	}
	private DrLineObject.DrObject creatDrObject(String dbname, String first, String second,
			String third, String fourth){
		DrLineObject.DrObject dr1 =new DrLineObject.DrObject();
		dr1.DbName =dbname;
		dr1.attributes.add(first);
		dr1.attributes.add(second);
		if(third !=null)
		dr1.attributes.add(third);
		if(fourth !=null)
		dr1.attributes.add(fourth);
		return dr1;
	}
}
