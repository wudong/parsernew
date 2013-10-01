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
class CcLineParserMassSpectrometryTest extends FunSuite {

  test("mass spectrometry 1") {
    val lines = """CC   -!- MASS SPECTROMETRY: Mass=24948; Mass_error=6; Method=MALDI;
                  |CC       Range=1-228; Source=PubMed:11101899;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[MassSpectrometry]
    val ms = cc1.`object`.asInstanceOf[MassSpectrometry]

    ms.mass should be (24948)
    ms.mass_error should be (6)
    ms.method should be ("MALDI")
    ms.ranges should have size (1)
    val range: MassSpectrometryRange = ms.ranges.get(0)
    range should have ('start (1), 'end (228))
    ms.range_isoform should be (null)
    ms.note should be (null)
    ms.source should be ("PubMed:11101899")
  }

  test("mass spectrometry 2") {
    val lines = """CC   -!- MASS SPECTROMETRY: Mass=13822; Method=MALDI; Range=19-140 (P15522-
                  |CC       2); Source=PubMed:10531593;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[MassSpectrometry]
    val ms = cc1.`object`.asInstanceOf[MassSpectrometry]

    ms.mass should be (13822)
    ms.mass_error should be (0)
    ms.method should be ("MALDI")
    ms.ranges should have size (1)
    val range: MassSpectrometryRange = ms.ranges.get(0)
    range should have ('start (19), 'end (140))
    ms.range_isoform should be ("P15522-2")
    ms.note should be (null)
    ms.source should be ("PubMed:10531593")
  }

  test("mass spectrometry with more than one range.") {
    val lines = """CC   -!- MASS SPECTROMETRY: Mass=514.2; Method=Electrospray; Range=51-54,
                  |CC       71-74, 91-94, 132-135, 148-151; Note=The measured mass is that of
                  |CC       RPGW-amide; Source=PubMed:10799681;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[MassSpectrometry]
    val ms = cc1.`object`.asInstanceOf[MassSpectrometry]

    ms.mass should be (514.2f)
    ms.mass_error should be (0)
    ms.method should be ("Electrospray")
    ms.ranges should have size (5)
    val range: MassSpectrometryRange = ms.ranges.get(0)
    range should have ('start (51), 'end (54))

    val range2: MassSpectrometryRange = ms.ranges.get(4)
    range2 should have ('start (148), 'end (151))

    ms.note should be ("The measured mass is that of RPGW-amide")
    ms.source should be ("PubMed:10799681")
  }

  test("CC mass's range can be '?'"){
    val lines =
      """CC   -!- MASS SPECTROMETRY: Mass=9571; Method=Electrospray; Range=1-?;
        |CC       Source=Ref.1;
        |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val unit: MassSpectrometry = cc2.`object`.asInstanceOf[MassSpectrometry]
    unit.ranges should have size (1)
    unit.ranges.get(0).start should be (1)
    unit.ranges.get(0).end should be (0)
    unit.ranges.get(0).end_unknown should be (true)
  }

  test("CC mass's contain number"){
    val lines =
      """CC   -!- MASS SPECTROMETRY: Mass=7190; Method=MALDI; Range=1-67;
        |CC       Note=Variant 6.01; Source=PubMed:11344362;
        |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val unit: MassSpectrometry = cc2.`object`.asInstanceOf[MassSpectrometry]
    unit.note should be ("Variant 6.01")
  }

}
