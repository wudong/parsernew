package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject.{GnObject, GnNameType}
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class GnLineParserTest extends FunSuite {

  test("A valid GN Line block one liner.") {

    val gnLine1 =
      """GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine1)

    obj should not be null
    obj.gnObjects should have size (1)
    val gn = obj.gnObjects.get(0)

    gn.names should have size (3)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii")) {
      val g1 = gn.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }

    expectResult((GnNameType.SYNNAME, 3, "SER1", "SER5", "Ser99Da")) {
      val g1 = gn.names.get(1)
      (g1.`type`, g1.names.size(), g1.names.get(0), g1.names.get(1), g1.names.get(2))
    }

    expectResult((GnNameType.ORFNAME, 1, "CG7877")) {
      val g1 = gn.names.get(2)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }



  test("A valid GN Line block two liner.") {

    val gnLine2 =
      """GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da;
        |GN   ORFNames=CG7877;
        |""".stripMargin.replace("\r", "");
    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine2)

    obj should not be null
    obj.gnObjects should have size (1)
    val gn = obj.gnObjects.get(0)

    gn.names should have size (3)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii")) {
      val g1 = gn.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }

    expectResult((GnNameType.SYNNAME, 3, "SER1", "SER5", "Ser99Da")) {
      val g1 = gn.names.get(1)
      (g1.`type`, g1.names.size(), g1.names.get(0), g1.names.get(1), g1.names.get(2))
    }

    expectResult((GnNameType.ORFNAME, 1, "CG7877")) {
      val g1 = gn.names.get(2)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }



  test("A valid GN Line block two liner with line-seperating in between name.") {

    val gnLine3 =
      """GN   Name=Jon99Cii; Synonyms=SER1, SER5,
        |GN   Ser99Da; ORFNames=CG7877;
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine3)

    obj should not be null
    obj.gnObjects should have size (1)
    val gn = obj.gnObjects.get(0)

    gn.names should have size (3)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii")) {
      val g1 = gn.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }

    expectResult((GnNameType.SYNNAME, 3, "SER1", "SER5", "Ser99Da")) {
      val g1 = gn.names.get(1)
      (g1.`type`, g1.names.size(), g1.names.get(0), g1.names.get(1), g1.names.get(2))
    }

    expectResult((GnNameType.ORFNAME, 1, "CG7877")) {
      val g1 = gn.names.get(2)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }

  test("A valid GN Line blocks.") {
    val gn2Line2 =
      """GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
        |GN   and
        |GN   Name=Jon99Cii2;
        |""".stripMargin.replace("\r", "");
    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gn2Line2)

    obj should not be null
    obj.gnObjects should have size (2)

    val gn1 = obj.gnObjects.get(1)
    gn1.names should have size (1)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii2")) {
      val g1 = gn1.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }

  test("Gn name with evidence"){
      val gnwithEv =
        """GN   Name=blaCTX-M-14{EI4}; Synonyms=beta-lactamase CTX-M-14{EI7},
          |GN   bla-CTX-M-14a{EI8}, blaCTX-M-14a{EI9}, blaCTX-M-14b{EI10},
          |GN   blatoho-3{EI12}, blaUOE-2{EI11}, CTX-M-14{EI6};
          |GN   ORFNames=ETN48_p0088{EI5}, pCT_085{EI13}, pHK01_011{EI14,EI15};
          |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnwithEv)

    obj.gnObjects should have size (1)
    val gnObject: GnObject = obj.gnObjects.get(0)
    gnObject.names should have size (3)

    gnObject.names.get(0).names should have size (1)
    gnObject.names.get(0).names.get(0) should equal("blaCTX-M-14")
    gnObject.names.get(0).getEvidenceInfo.evidences should have size (1)
    gnObject.names.get(0).getEvidenceInfo.evidences.get("blaCTX-M-14") should have size (1)
    gnObject.names.get(0).getEvidenceInfo.evidences.get("blaCTX-M-14") should contain ("EI4")

    //the ORF name.
    gnObject.names.get(2).names should have size (3)
    gnObject.names.get(2).names.get(2) should equal("pHK01_011")
    gnObject.names.get(2).getEvidenceInfo.evidences should have size (3)
    gnObject.names.get(2).getEvidenceInfo.evidences.get("pHK01_011") should have size (2)
    gnObject.names.get(2).getEvidenceInfo.evidences.get("pHK01_011") should contain ("EI14")
    gnObject.names.get(2).getEvidenceInfo.evidences.get("pHK01_011") should contain ("EI15")
  }

  test ("Gn Name canot be parsed"){
    val gnwithEv =
      """GN   Name=ACO2; Synonyms=EI305; OrderedLocusNames=At1g62380;
        |GN   ORFNames=F24O1.10;
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnwithEv)

    obj.gnObjects should have size (1)
    val gnObject: GnObject = obj.gnObjects.get(0)
    gnObject.names should have size (4)
    gnObject.names.get(0).names should contain ("ACO2")
    gnObject.names.get(3).names should contain ("F24O1.10")
  }

  test ("Gn Name has ',' inside"){
    val gnwithEv =
      """GN   Name=ARG5,6;
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnwithEv)

    obj.gnObjects should have size (1)
    val gnObject: GnObject = obj.gnObjects.get(0)
    gnObject.names should have size (1)
    gnObject.names.get(0).names should contain ("ARG5,6")
  }

}
