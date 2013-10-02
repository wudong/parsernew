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

  test ("biophysiocalproperties 2"){
   val lines=  """CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                 |CC       Kinetic parameters:
                 |CC         KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
                 |CC         Celsius and at pH 7.6);
                 |CC         Vmax=1.9 mmol/min/mg enzyme;
                 |CC       pH dependence:
                 |CC         Optimum pH is 7.75;
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[BiophysicochemicalProperties]
    val bp = cc1.`object`.asInstanceOf[BiophysicochemicalProperties]

    bp.kms should have size (1)
    bp.kms.get(0) should be ("1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees Celsius and at pH 7.6)")

    bp.vmaxs should have size (1)
    bp.vmaxs.get(0) should be ("1.9 mmol/min/mg enzyme")
    bp.ph_dependence should be ("Optimum pH is 7.75")
  }

  test ("biophysiocalproperties 1"){
    val lines= """CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                 |CC       Kinetic parameters:
                 |CC         KM=71 uM for ATP;
                 |CC         KM=98 uM for ADP;
                 |CC         KM=1.5 mM for acetate;
                 |CC         KM=0.47 mM for acetyl phosphate;
                 |CC       Temperature dependence:
                 |CC         Optimum temperature is 65 degrees Celsius. Protected from
                 |CC         thermal inactivation by ATP;
                 |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[BiophysicochemicalProperties]
    val bp = cc1.`object`.asInstanceOf[BiophysicochemicalProperties]

    bp.bsorption_abs should be (0)
    bp.bsorption_note should be (null);

    bp.kms should have size (4)
    bp.kms should contain ("71 uM for ATP")
    bp.kms should contain ("98 uM for ADP")
    bp.kms should contain ("1.5 mM for acetate")
    bp.kms should contain ("0.47 mM for acetyl phosphate")

    bp.vmaxs should have size (0)

    bp.kp_note should be (null)
    bp.ph_dependence should be (null)
    bp.temperature_dependence should be ("Optimum temperature is 65 degrees Celsius. Protected from " +
      "thermal inactivation by ATP")
  }

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
