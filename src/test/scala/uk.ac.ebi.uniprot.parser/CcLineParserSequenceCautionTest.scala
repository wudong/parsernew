package uk.ac.ebi.uniprot.parser

import scala.collection.JavaConverters._

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
class CcLineParserSequenceCautionTest extends FunSuite {

  test("sequence caution 1") {
    val lines = """CC   -!- SEQUENCE CAUTION:
                  |CC       Sequence=CAI24940.1; Type=Erroneous gene model prediction;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[SequenceCaution]
    val sc = cc1.`object`.asInstanceOf[SequenceCaution]

    sc.sequenceCautionObjects should have size (1)
    val co: SequenceCautionObject = sc.sequenceCautionObjects.get(0)
    co.`type` should be (SequenceCautionType.Erroneous_gene_model_prediction)
    co.sequence should be ("CAI24940.1")
    co.note should be (null)
    co.positions should be ('empty)
  }

  test("sequence caution with all field") {
    val lines = """CC   -!- SEQUENCE CAUTION:
                  |CC       Sequence=AAG34697.1; Type=Erroneous termination; Positions=388; Note=Translated as Ser;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[SequenceCaution]
    val sc = cc1.`object`.asInstanceOf[SequenceCaution]

    sc.sequenceCautionObjects should have size (1)
    val co: SequenceCautionObject = sc.sequenceCautionObjects.get(0)
    co.`type` should be (SequenceCautionType.Erroneous_termination)
    co.sequence should be ("AAG34697.1")
    co.positions should have size (1)
    co.positions.asScala should equal (List(388))
    co.note should be ("Translated as Ser")
  }

  test("sequence caution 2 lines.") {
    val lines = """CC   -!- SEQUENCE CAUTION:
                  |CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;
                  |CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[SequenceCaution]
    val sc = cc1.`object`.asInstanceOf[SequenceCaution]

    sc.sequenceCautionObjects should have size (2)
    val co: SequenceCautionObject = sc.sequenceCautionObjects.get(0)
    co.`type` should be (SequenceCautionType.Erroneous_gene_model_prediction)
    co.sequence should be ("CAI12537.1")
    co.note should be (null)
    co.positions should be ('empty)

    val co2: SequenceCautionObject = sc.sequenceCautionObjects.get(1)
    co2.`type` should be (SequenceCautionType.Erroneous_gene_model_prediction)
    co2.sequence should be ("CAI39742.1")
    co2.note should be (null)
    co2.positions.asScala should equal (List(388, 399))
  }

  test("sequence caution has position value as 'several'"){
    val lines = """CC   -!- SEQUENCE CAUTION:
                  |CC       Sequence=AAA25676.1; Type=Frameshift; Positions=Several;
                  |CC       Sequence=CAD59919.1; Type=Frameshift; Positions=519;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val sc = cc2.`object`.asInstanceOf[SequenceCaution]
    sc.sequenceCautionObjects should have size (2)
    val sco: SequenceCautionObject = sc.sequenceCautionObjects.get(0)
    sco.`type` should be (SequenceCautionType.Frameshift)
    sco.sequence should be ("AAA25676.1")
    sco.positionValue should be ("Several")
  }


  test("sequence caution has keyword Frameshift in the note "){
    val lines = """CC   -!- SEQUENCE CAUTION:
                  |CC       Sequence=AAA85813.1; Type=Frameshift; Positions=134; Note=Frameshift correction allows the C-terminal sequence to be compatible with the results of mass spectrometry and X-ray crystallography;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val sc = cc2.`object`.asInstanceOf[SequenceCaution]
    sc.sequenceCautionObjects should have size (1)
    val sco: SequenceCautionObject = sc.sequenceCautionObjects.get(0)
    sco.`type` should be (SequenceCautionType.Frameshift)
    sco.note should be ("Frameshift correction allows the C-terminal sequence to be compatible with the results of mass spectrometry and X-ray crystallography")
  }

}
