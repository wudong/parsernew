package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.scalatest.matchers.ShouldMatchers._
import java.util


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RnLineParserTest extends FunSuite {

  test("A valid Rn Line ") {

    val rnLine = "RN   [1231]\n";

    val parser = (new DefaultUniprotLineParserFactory).createRnLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    expectResult(1231) {obj.number};
  }

  test("A valid Rn Line with evidence ") {

    val rnLine = "RN   [1]{EI2,EI3}\n";

    val parser = (new DefaultUniprotLineParserFactory).createRnLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.number should be (1)
    val list: util.List[String] = obj.evidenceInfo.evidences.get(1)
    list should not be (null)
    list should contain ("EI2")
    list should contain ("EI3")
  }

}
