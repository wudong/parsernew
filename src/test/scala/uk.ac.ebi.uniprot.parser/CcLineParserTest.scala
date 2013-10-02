package uk.ac.ebi.uniprot.parser


import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject._


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class CcLineParserTest extends FunSuite {

  test("a valid CC ") {

    val ccline =
      """CC   -!- FUNCTION: This enzyme is necessary for target cell lysis in cell-
        |CC       mediated immune responses. It cleaves after Lys or Arg. May be
        |CC       involved in apoptosis.
        |CC   -!- CAUTION: Exons 1a and 1b of the sequence reported in
        |CC       PubMed:17180578 are of human origin, however exon 2 shows strong
        |CC       similarity to the rat sequence.
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(ccline)

    obj.ccs should have size (2)
    val cc = obj.ccs.get(0)

    cc.topic should be(CcLineObject.CCTopicEnum.FUNCTION)
    cc.`object` should be("This enzyme is necessary for target cell lysis in cell-mediated immune responses. " +
      "It cleaves after Lys or Arg. May be involved in apoptosis")

    val cc2 = obj.ccs.get(1)
    cc2.topic should be(CcLineObject.CCTopicEnum.CAUTION)
    cc2.`object` should be("Exons 1a and 1b of the sequence reported in " +
      "PubMed:17180578 are of human origin, however exon 2 shows strong " +
      "similarity to the rat sequence")
  }


  test("CC content has dot inside") {
    val lines = """CC   -!- SUBUNIT: Interacts with daf-16 and sir-2.1.
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.topic should be (CCTopicEnum.SUBUNIT)
    val sc = cc2.`object`.asInstanceOf[String]
    sc should be ("Interacts with daf-16 and sir-2.1")
  }


}
