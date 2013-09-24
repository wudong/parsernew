package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._

import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RpLineParserTest extends FunSuite {

  test("A valid RP Line") {

    val rpLine = "RP   NUCLEOTIDE SEQUENCE [MRNA].\n";

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;
    obj.scopes should have size (1)
    obj.scopes should contain("NUCLEOTIDE SEQUENCE [MRNA]");

  }


  test("A valid RP Line 2") {

    val rpLine = "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n";

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;

    obj.scopes should have size (1)
    obj.scopes should contain("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");

  }

  test("A invalid RP Line with lower case letter") {

    val rpLine = "RP   NUCLEOTIDE sequence [MRNA].\n";

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();

    intercept[ParseException] {
      parser.parse(rpLine)
    }
  }

  test("A invalid RP Line without DOT Ending") {

    val rpLine = "RP   NUCLEOTIDE SEQUENCE [MRNA]\n";

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();
    intercept[ParseException] {
      val obj = parser.parse(rpLine)
    }
  }

  test("A valid RP 2 Lines") {

    val rpLine = """RP   NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C), FUNCTION, INTERACTION
                   |RP   WITH PKC-3, SUBCELLULAR LOCATION, TISSUE SPECIFICITY, DEVELOPMENTAL
                   |RP   STAGE, AND MUTAGENESIS OF PHE-175 AND PHE-221.
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;

    obj.scopes should have size (7)
    obj.scopes should contain("NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C)")
    obj.scopes should contain("FUNCTION")
    obj.scopes should contain("INTERACTION WITH PKC-3")
    obj.scopes should contain("SUBCELLULAR LOCATION")
    obj.scopes should contain("TISSUE SPECIFICITY")
    obj.scopes should contain("DEVELOPMENTAL STAGE")
    obj.scopes should contain("MUTAGENESIS OF PHE-175 AND PHE-221")

  }

  test("A valid RP with dot inside it.") {

    val rpLine = """RP   X-RAY CRYSTALLOGRAPHY (2.6 ANGSTROMS) OF 22-480, AND DISULFIDE BONDS.
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;

    obj.scopes should have size (2)
    obj.scopes should contain("X-RAY CRYSTALLOGRAPHY (2.6 ANGSTROMS) OF 22-480")
    obj.scopes should contain("DISULFIDE BONDS")
  }


}
