package uk.ac.ebi.uniprot.antlr

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._

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

  test ("parse cc disease abbr"){
    val string = """(T(-)B(+)NK(+)
                 |CC       SCID) [MIM:608971]:""".stripMargin.replace("\r", "");
    val mim: Array[String] = TextHelper.parseCCDiseaseAbbrMim(string)
    mim(0) should be ("T(-)B(+)NK(+) SCID")
    mim(1) should be ("608971")
  }

  test ("parse cc disease abbr 2"){
    val string = """(CD3ZID)
                 |CC       [MIM:610163]:""".stripMargin.replace("\r", "");
    val mim: Array[String] = TextHelper.parseCCDiseaseAbbrMim(string)
    mim(0) should be ("CD3ZID")
    mim(1) should be ("610163")
  }

}
