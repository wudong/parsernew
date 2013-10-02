package uk.ac.ebi.uniprot.parser


import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject._


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class CcLineParserRNAEditingTest extends FunSuite {


  test("RNA Editing 1"){
        val lines = """CC   -!- RNA EDITING: Modified_positions=59, 78, 94, 98, 102, 121; Note=The
                      |CC       nonsense codon at position 59 is modified to a sense codon. The
                      |CC       stop codon at position 121 is created by RNA editing.
                      |""".stripMargin.replace("\r", "")

        val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
        val obj = parser.parse(lines)
        val cc2 = obj.ccs.get(0)

        cc2.`object`.isInstanceOf[RnaEditing]
        val re = cc2.`object`.asInstanceOf[RnaEditing]
        re.locations should have size (6)

        re.note should be ("The nonsense codon at position 59 is modified to a sense codon. " +
          "The stop codon at position 121 is created by RNA editing.")
  }

  test("RNA Editing 2"){
    val lines = """CC   -!- RNA EDITING: Modified_positions=11, 62, 72, 97, 117.
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.`object`.isInstanceOf[RnaEditing]
    val re = cc2.`object`.asInstanceOf[RnaEditing]
    re.locations should have size (5)
    re.locations.get(0) should be (11)
    re.locations.get(1) should be (62)
    re.note should be (null)
  }

  test("RNA Editing 3"){
    val lines = """CC   -!- RNA EDITING: Modified_positions=1, 56, 89, 103, 126, 164, 165,
                  |CC       167, 179, 191, 194, 212, 225, 242, 248, 252, 275, 300, 310, 313;
                  |CC       Note=The initiator methionine is created by RNA editing.
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val re = cc2.`object`.asInstanceOf[RnaEditing]
    re.locations should have size (20)
    re.locations.get(0) should be (1)
    re.locations.get(19) should be (313)
    re.note should be ("The initiator methionine is created by RNA editing.")
  }


  test("RNA Editing has position as Undetermined"){
    val lines = """CC   -!- RNA EDITING: Modified_positions=Undetermined; Note=Partially
                  |CC       edited. 11 sites are edited by Adar.
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val re = cc2.`object`.asInstanceOf[RnaEditing]
    re.locations should have size (0)
    re.locationEnum should be (RnaEditingLocationEnum.Undetermined)
    re.note should be ("Partially edited. 11 sites are edited by Adar.")
  }

}
