package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
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

  test("A valid Rn Line ") {

    val rnLine = "RN   [1231]\n";

    val parser = (new DefaultUniprotLineParserFactory).createRnLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    expectResult(1231) {obj.number};

  }

}
