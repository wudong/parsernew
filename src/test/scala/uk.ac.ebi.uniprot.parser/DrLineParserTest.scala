package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.dt.DtObjectParser
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.dr.{DrLineObject, DrObjectParser}
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class DrLineParserTest extends FunSuite  {

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


  test("A valid DR Line blocks") {
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

    expectResult( ("ProteinModelPortal", "Q6GZX4", 1)){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(2);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.size())
    }

    expectResult( ("GeneID", "2947773", 1)){
      val dr1: DrLineObject.DrObject = obj.drObjects.get(3);
      (dr1.DbName, dr1.attributes.get(0), dr1.attributes.size())
    }

    expectResult( ("ProtClustDB", "CLSP2511514", 1)){
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

}
