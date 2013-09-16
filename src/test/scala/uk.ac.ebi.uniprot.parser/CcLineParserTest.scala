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
      "It cleaves after Lys or Arg. May be involved in apoptosis.")

    val cc2 = obj.ccs.get(1)
    cc2.topic should be(CcLineObject.CCTopicEnum.CAUTION)
    cc2.`object` should be("Exons 1a and 1b of the sequence reported in " +
      "PubMed:17180578 are of human origin, however exon 2 shows strong " +
      "similarity to the rat sequence.")
  }

  test ("alternative products"){
    val lines= """CC   -!- ALTERNATIVE PRODUCTS:
                |CC       Event=Alternative splicing; Named isoforms=3;
                |CC         Comment=Additional isoforms seem to exist. Experimental
                |CC         confirmation may be lacking for some isoforms;
                |CC       Name=1; Synonyms=AIRE-1;
                |CC         IsoId=O43918-1; Sequence=Displayed;
                |CC       Name=2; Synonyms=AIRE-2;
                |CC         IsoId=O43918-2; Sequence=VSP_004089;
                |CC       Name=3; Synonyms=AIRE-3;
                |CC         IsoId=O43918-3; Sequence=VSP_004089, VSP_004090;
                |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[AlternativeProducts]
    val ap = cc1.`object`.asInstanceOf[AlternativeProducts]
    ap.events should have size (1)
    ap.events.get(0) should be ("Alternative splicing")
    ap.namedIsoforms should be ("3")
    ap.comment should be ("Additional isoforms seem to exist. Experimental confirmation may be lacking for some isoforms")
    ap.names should have size (3)
    ap.names.get(0).name should be ("1")
    ap.names.get(0).isoId should have size (1)
    ap.names.get(0).isoId.get(0) should be ("O43918-1")
    ap.names.get(0).sequence_enum should be (AlternativeNameSequenceEnum.Displayed)
    ap.names.get(0).sequence_FTId should be ('empty)
    ap.names.get(0).synNames should have size (1)
    ap.names.get(0).synNames.get(0) should be ("AIRE-1")

    ap.names.get(1).name should be ("2")
    ap.names.get(1).isoId should have size (1)
    ap.names.get(1).isoId.get(0) should be ("O43918-2")
    ap.names.get(1).sequence_enum should be (null)
    ap.names.get(1).sequence_FTId should have size 1
    ap.names.get(1).sequence_FTId should contain ("VSP_004089")

    ap.names.get(1).synNames should have size (1)
    ap.names.get(1).synNames.get(0) should be ("AIRE-2")

    ap.names.get(2).name should be ("3")
    ap.names.get(2).isoId should have size (1)
    ap.names.get(2).isoId.get(0) should be ("O43918-3")
    ap.names.get(2).sequence_enum should be (null)
    ap.names.get(2).sequence_FTId should have size 2
    ap.names.get(2).sequence_FTId should contain ("VSP_004089")
    ap.names.get(2).sequence_FTId should contain ("VSP_004090")

    ap.names.get(2).synNames should have size (1)
    ap.names.get(2).synNames.get(0) should be ("AIRE-3")
  }

  test ("alternative products 1"){
    val lines= """CC   -!- ALTERNATIVE PRODUCTS:
                 |CC       Event=Alternative promoter usage; Named isoforms=2;
                 |CC       Name=alpha;
                 |CC         IsoId=P12544-1; Sequence=Displayed;
                 |CC       Name=beta;
                 |CC         IsoId=P12544-2; Sequence=VSP_038571, VSP_038572;
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[AlternativeProducts]
    val ap = cc1.`object`.asInstanceOf[AlternativeProducts]
    ap.events should have size (1)
    ap.events.get(0) should be ("Alternative promoter usage")
    ap.namedIsoforms should be ("2")

    ap.names should have size (2)
    ap.names.get(0).name should be ("alpha")
    ap.names.get(0).isoId should have size (1)
    ap.names.get(0).isoId.get(0) should be ("P12544-1")
    ap.names.get(0).sequence_enum should be (AlternativeNameSequenceEnum.Displayed)
    ap.names.get(0).sequence_FTId should be ('empty)

    ap.names.get(1).name should be ("beta")
    ap.names.get(1).isoId should have size (1)
    ap.names.get(1).isoId.get(0) should be ("P12544-2")
    ap.names.get(1).sequence_enum should be (null)
    ap.names.get(1).sequence_FTId should have size (2)
    ap.names.get(1).sequence_FTId should  contain ("VSP_038571")
    ap.names.get(1).sequence_FTId should  contain ("VSP_038572")
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

  test ("web resource 1"){
    val lines = """CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
                |CC       URL="http://bioinf.uta.fi/CD40Lbase/";
                |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[WebResource]
    val wr = cc1.`object`.asInstanceOf[WebResource]

    wr.name should be ("CD40Lbase")
    wr.note should be ("CD40L defect database")
    wr.url should be ("http://bioinf.uta.fi/CD40Lbase/")
  }

  test ("interaction 1"){
    val lines = """CC   -!- INTERACTION:
                  |CC       P11450:fcp3c; NbExp=1; IntAct=EBI-126914, EBI-159556;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-126914")
    unit.secondId should be ("EBI-159556")
    unit.gene should be ("fcp3c")
    unit.spAc should be ("P11450")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }

  test ("interaction 2"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-133844")
    unit.secondId should be ("EBI-212772")
    unit.gene should be ("CG11299")
    unit.spAc should be ("Q9W1K5-1")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }


  test ("interaction"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-133844")
    unit.secondId should be ("EBI-212772")
    unit.gene should be ("CG11299")
    unit.spAc should be ("Q9W1K5-1")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }

  test ("interaction 3"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q8NI08:-; NbExp=1; IntAct=EBI-80809, EBI-80799;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-80809")
    unit.secondId should be ("EBI-80799")
    unit.gene should be ("-")
    unit.spAc should be ("Q8NI08")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }
  test ("interaction 4"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-123485")
    unit.secondId should be ("EBI-123485")
    unit.gene should be (null)
    unit.spAc should be (null)
    unit.isSelf should be (true)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }
  test ("interaction 5"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-394562")
    unit.secondId should be ("EBI-398761")
    unit.gene should be ("2410018M14Rik")
    unit.spAc should be ("Q8C1S0")
    unit.isSelf should be (false)
    unit.xeno should be (true)
    unit.nbexp should be (1)
  }

  test ("two interactions case"){
    val lines = """CC   -!- INTERACTION:
                  |CC       P51617:IRAK1; NbExp=1; IntAct=EBI-448466, EBI-358664;
                  |CC       P51617:IRAK1; NbExp=1; IntAct=EBI-448472, EBI-358664;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (2)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-448466")
    unit.secondId should be ("EBI-358664")
    unit.gene should be ("IRAK1")
    unit.spAc should be ("P51617")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)

    val unit2: InteractionObject = ir.interactions.get(1)
    unit2.firstId should be ("EBI-448472")
    unit2.secondId should be ("EBI-358664")
    unit2.gene should be ("IRAK1")
    unit2.spAc should be ("P51617")
    unit2.isSelf should be (false)
    unit2.xeno should be (false)
    unit2.nbexp should be (1)
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
    co.position should be (null)
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
    co.position should be ("388")
    co.note should be ("Translated as Ser")
  }


  test("sequence caution 2 lines.") {
    val lines = """CC   -!- SEQUENCE CAUTION:
                  |CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;
                  |CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction;
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
    co.position should be (null)

    val co2: SequenceCautionObject = sc.sequenceCautionObjects.get(1)
    co2.`type` should be (SequenceCautionType.Erroneous_gene_model_prediction)
    co2.sequence should be ("CAI39742.1")
    co2.note should be (null)
    co2.position should be (null)
  }

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
    ms.range_start should be (1)
    ms.range_end should be (228)
    ms.range_isoform should be (null)
    ms.range_note should be (null)
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
    ms.range_start should be (19)
    ms.range_end should be (140)
    ms.range_isoform should be ("P15522-2")
    ms.range_note should be (null)
    ms.source should be ("PubMed:10531593")

  }
}
