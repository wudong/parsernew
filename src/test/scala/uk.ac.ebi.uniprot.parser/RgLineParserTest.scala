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
    expectResult("The mouse genome sequencing consortium") {obj.reference_group};

  }

}
