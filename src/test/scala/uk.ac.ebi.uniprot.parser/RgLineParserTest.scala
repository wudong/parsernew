package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.scalatest.matchers.ShouldMatchers._


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RgLineParserTest extends FunSuite {

  test("A valid Rg Line ") {

    val rnLine = "RG   The mouse genome sequencing consortium;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRgLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.reference_groups should have size (1)
    obj.reference_groups should contain ("The mouse genome sequencing consortium");
  }

  test("two valid Rg Line ") {

    val rnLine =
      """RG   The mouse genome sequencing consortium;
        |RG   The something else consortium;
        |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRgLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.reference_groups should have size (2)
    obj.reference_groups should contain ("The mouse genome sequencing consortium");
    obj.reference_groups should contain ("The something else consortium");
  }

}
