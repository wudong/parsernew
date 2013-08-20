package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.dr.{DrLineObject, DrObjectParser}
import uk.ac.ebi.uniprot.parser.impl.gn.GnObjectParser
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject.GnNameType
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class GnLineParserTest extends FunSuite  {

  val gnLine1 =
   """GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
      |""".stripMargin.replace("\r", "");

  test("A valid GN Line block one liner.") {
    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine1)

    obj should not be null
    obj.gnObjects should have size (1)
    val gn = obj.gnObjects.get(0)

    gn.names should have size (3)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii")){
      val g1 = gn.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }

    expectResult((GnNameType.SYNNAME, 3, "SER1", "SER5", "Ser99Da")){
      val g1 = gn.names.get(1)
      (g1.`type`, g1.names.size(), g1.names.get(0),g1.names.get(1), g1.names.get(2))
    }

    expectResult((GnNameType.ORFNAME, 1, "CG7877")){
      val g1 = gn.names.get(2)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }

  val gnLine2 =
    """GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da;
      |GN   ORFNames=CG7877;
      |""".stripMargin.replace("\r", "");

  test("A valid GN Line block two liner.") {
    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine2)

    obj should not be null
    obj.gnObjects should have size (1)
    val gn = obj.gnObjects.get(0)

    gn.names should have size (3)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii")){
      val g1 = gn.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }

    expectResult((GnNameType.SYNNAME, 3, "SER1", "SER5", "Ser99Da")){
      val g1 = gn.names.get(1)
      (g1.`type`, g1.names.size(), g1.names.get(0),g1.names.get(1), g1.names.get(2))
    }

    expectResult((GnNameType.ORFNAME, 1, "CG7877")){
      val g1 = gn.names.get(2)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }

  val gnLine3 =
    """GN   Name=Jon99Cii; Synonyms=SER1, SER5,
      |GN   Ser99Da; ORFNames=CG7877;
      |""".stripMargin.replace("\r", "");

  test("A valid GN Line block two liner with line-seperating in between name.") {
    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine3)

    obj should not be null
    obj.gnObjects should have size (1)
    val gn = obj.gnObjects.get(0)

    gn.names should have size (3)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii")){
      val g1 = gn.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }

    expectResult((GnNameType.SYNNAME, 3, "SER1", "SER5", "Ser99Da")){
      val g1 = gn.names.get(1)
      (g1.`type`, g1.names.size(), g1.names.get(0),g1.names.get(1), g1.names.get(2))
    }

    expectResult((GnNameType.ORFNAME, 1, "CG7877")){
      val g1 = gn.names.get(2)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }


  val gn2Line2 =
    """GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
      |GN   and
      |GN   Name=Jon99Cii2;
      |""".stripMargin.replace("\r", "");

  test("A valid GN Line blocks.") {
    val parser = (new DefaultUniprotLineParserFactory).createGnLineParser();
    val obj = parser.parse(gnLine3)

    obj should not be null
    obj.gnObjects should have size (2)

    val gn1 = obj.gnObjects.get(1)
    gn1.names should have size (1)
    expectResult((GnNameType.GENAME, 1, "Jon99Cii2")){
      val g1 = gn1.names.get(0)
      (g1.`type`, g1.names.size(), g1.names.get(0))
    }
  }
}
