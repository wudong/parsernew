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
    expectResult("NUCLEOTIDE SEQUENCE [MRNA]") {
      obj.position
    };

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
                   | """.stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRpLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;
    expectResult("NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C), FUNCTION, INTERACTION " +
      "WITH PKC-3, SUBCELLULAR LOCATION, TISSUE SPECIFICITY, DEVELOPMENTAL " +
      "STAGE, AND MUTAGENESIS OF PHE-175 AND PHE-221") {
      obj.position
    };
  }


}
