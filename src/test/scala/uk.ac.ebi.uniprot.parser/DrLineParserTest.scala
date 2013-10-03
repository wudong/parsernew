package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject.DrObject

import scala.collection.JavaConverters._
import java.util
;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class DrLineParserTest extends FunSuite  {
  test("A valid DR Line blocks") {

    val drLine = """DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
                   |DR   RefSeq; YP_031579.1; NC_005946.1.
                   |DR   ProteinModelPortal; Q6GZX4; -.
                   |DR   GeneID; 2947773; -.
                   |DR   ProtClustDB; CLSP2511514; -.
                   |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
                   |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
                   |DR   GO; GO:0006351; P:transcription, DNA-dependent; IEA:UniProtKB-KW.
                   |DR   InterPro; IPR007031; Poxvirus_VLTF3.
                   |DR   Pfam; PF04947; Pox_VLTF3; 1.
                   |""".stripMargin.replace("\r","");

    val parser = (new DefaultUniprotLineParserFactory).createDrLineParser();
    val obj = parser.parse(drLine)

    obj should not be null;
    obj.drObjects should not be null;
    obj.drObjects should have size (10);

    expectResult( ("EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(0);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.get(1), dr1.attributes.get(2), dr1.attributes.get(3))
    }

    expectResult( ("RefSeq", "YP_031579.1", "NC_005946.1")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(1);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.get(1))
    }

    expectResult( ("ProteinModelPortal", "Q6GZX4", 2)){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(2);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.size())
    }

    expectResult( ("GeneID", "2947773", 2)){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(3);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.size())
    }

    expectResult( ("ProtClustDB", "CLSP2511514", 2)){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(4);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.size())
    }

    expectResult( ("GO", "GO:0006355",  "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(5);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.get(1), dr1.attributes.get(2))
    }

    expectResult( ("GO", "GO:0046782",  "P:regulation of viral transcription", "IEA:InterPro")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(6);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.get(1), dr1.attributes.get(2))
    }


    expectResult( ("GO", "GO:0006351",  "P:transcription, DNA-dependent", "IEA:UniProtKB-KW")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(7);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.get(1), dr1.attributes.get(2))
    }

    expectResult( ("InterPro", "IPR007031", "Poxvirus_VLTF3")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(8);
      (dr1.DbName, dr1.attributes.get(0),dr1.attributes.get(1))
    }

    expectResult( ("Pfam", "PF04947", "Pox_VLTF3", "1")){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(9);
      (dr1.DbName, dr1.attributes.get(0),  dr1.attributes.get(1), dr1.attributes.get(2))
    }

  }

  test("a Go line"){
    val drLine = """DR   GO; GO:0005524; F:ATP binding; IEA:UniProtKB-KW.
                    |DR   GO; GO:0004674; F:protein serine/threonine kinase activity; IEA:UniProtKB-KW.
                    |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createDrLineParser();
    val obj = parser.parse(drLine)

    obj should not be null;
    obj.drObjects should not be null;
    obj.drObjects should have size (2);
    val drObject: DrObject = obj.drObjects.get(0)
    drObject.DbName should be ("GO")
    drObject.attributes.asScala should be (List("GO:0005524", "F:ATP binding", "IEA:UniProtKB-KW"))
        
    val dro2: DrObject = obj.drObjects.get(1)
    dro2.DbName should be ("GO")
    dro2.attributes.asScala should be (List("GO:0004674", "F:protein serine/threonine kinase activity", "IEA:UniProtKB-KW"))
  }

  test("Dr Line with Evidence"){
    val drLine =
        """DR   EMBL; CP001509; ACT41999.1; -; Genomic_DNA.{EI3}
          |DR   EMBL; AM946981; CAQ30614.1; -; Genomic_DNA.{EI4}
          |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createDrLineParser();
    val obj = parser.parse(drLine)

    obj should not be null;
    obj.drObjects should not be null;
    obj.drObjects should have size (2);

    val drObject: DrObject = obj.drObjects.get(0)
    val list: util.List[String] = obj.getEvidenceInfo.evidences.get(drObject)
    list should not be (null)
    list should contain ("EI3")

    val drObject2: DrObject = obj.drObjects.get(1)
    val list2: util.List[String] = obj.getEvidenceInfo.evidences.get(drObject2)
    list2 should not be (null)
    list2 should contain ("EI4")
  }

  test("Dr Line with dot value in middle"){
    val drLine = """DR   EMBL; AY548484; AAT09696.1; -; Genomic_DNA.
                   |DR   Gene3D; 3.40.50.1000; -; 2.
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createDrLineParser();
    val obj = parser.parse(drLine)

    obj should not be null;
    obj.drObjects should not be null;
    obj.drObjects should have size (2);
    val drObject: DrObject = obj.drObjects.get(1)
    drObject.DbName should be ("Gene3D")
    drObject.attributes.asScala should be (List("3.40.50.1000", "-", "2"))
  }

  test("Dr Line with ';' in middle"){
    val drLine = """DR   Orphanet; 102724; Acute myeloid leukemia with t(8;21)(q22;q22) translocation.
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createDrLineParser();
    val obj = parser.parse(drLine)

    obj should not be null;
    obj.drObjects should not be null;
    obj.drObjects should have size (1);
    val drObject: DrObject = obj.drObjects.get(0)
    drObject.DbName should be ("Orphanet")
    drObject.attributes.asScala should be (List("102724", "Acute myeloid leukemia with t(8;21)(q22;q22) translocation"))
  }

  test("Dr Line with '. ' in middle"){
    val drLine = """DR   UCSC; T23F11.3a.1; c. elegans.
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createDrLineParser();
    val obj = parser.parse(drLine)

    obj should not be null;
    obj.drObjects should not be null;
    obj.drObjects should have size (1);
    val drObject: DrObject = obj.drObjects.get(0)
    drObject.DbName should be ("UCSC")
    drObject.attributes.asScala should be (List("T23F11.3a.1", "c. elegans"))
  }


}
