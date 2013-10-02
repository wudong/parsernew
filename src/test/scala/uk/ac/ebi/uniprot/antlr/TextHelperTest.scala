package uk.ac.ebi.uniprot.antlr

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
class TextHelperTest extends FunSuite {

  test("remove change_of_line ") {
    val string =
      """FASFSD
        |CC    FCD""".stripMargin.replace("\r", "");
    val line: String = TextHelper.removeChangeOfLine(string)
    line should be ("FASFSD FCD")
  }

  test("remove change_of_line 2") {
    val string =
      """FASFSD-
        |CC    FCD""".stripMargin.replace("\r", "");
    val line: String = TextHelper.removeChangeOfLine(string)
    line should be ("FASFSD-FCD")
  }

}
