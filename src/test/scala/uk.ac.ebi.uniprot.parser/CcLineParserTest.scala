package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class CcLineParserTest extends FunSuite {

  test("a valid three lineer with more than one secondary acc ") {

    val ac_three_lineer_moreacc =
      """CC   -!- FUNCTION: This enzyme is necessary for target cell lysis in cell-
        |CC       mediated immune responses. It cleaves after Lys or Arg. May be
        |CC       involved in apoptosis.
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(ac_three_lineer_moreacc)

    obj.topic should equal (CcLineObject.CCTopicEnum.FUNCTION)
    obj.text should equal ("This enzyme is necessary for target cell lysis in cell- mediated immune responses. " +
      "It cleaves after Lys or Arg. May be involved in apoptosis.")

  }


}
