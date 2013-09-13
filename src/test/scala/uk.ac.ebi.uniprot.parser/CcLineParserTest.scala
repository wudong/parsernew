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

    cc.topic should equal(CcLineObject.CCTopicEnum.FUNCTION)
    cc.`object` should equal("This enzyme is necessary for target cell lysis in cell-mediated immune responses. " +
      "It cleaves after Lys or Arg. May be involved in apoptosis.")

    val cc2 = obj.ccs.get(1)
    cc2.topic should equal(CcLineObject.CCTopicEnum.CAUTION)
    cc2.`object` should equal("Exons 1a and 1b of the sequence reported in " +
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
    ap.events.get(0) should equal ("Alternative splicing")
    ap.namedIsoforms should equal ("3")
    ap.comment should equal ("Additional isoforms seem to exist. Experimental confirmation may be lacking for some isoforms")
    ap.names should have size (3)
    ap.names.get(0).name should equal ("1")
    ap.names.get(0).isoId should have size (1)
    ap.names.get(0).isoId.get(0) should equal ("O43918-1")
    ap.names.get(0).sequence_enum should equal (AlternativeNameSequenceEnum.Displayed)
    ap.names.get(0).sequence_FTId should be ('empty)
    ap.names.get(0).synNames should have size (1)
    ap.names.get(0).synNames.get(0) should equal ("AIRE-1")

    ap.names.get(1).name should equal ("2")
    ap.names.get(1).isoId should have size (1)
    ap.names.get(1).isoId.get(0) should equal ("O43918-2")
    ap.names.get(1).sequence_enum should be (null)
    ap.names.get(1).sequence_FTId should have size 1
    ap.names.get(1).sequence_FTId should contain ("VSP_004089")

    ap.names.get(1).synNames should have size (1)
    ap.names.get(1).synNames.get(0) should equal ("AIRE-2")

    ap.names.get(2).name should equal ("3")
    ap.names.get(2).isoId should have size (1)
    ap.names.get(2).isoId.get(0) should equal ("O43918-3")
    ap.names.get(2).sequence_enum should be (null)
    ap.names.get(2).sequence_FTId should have size 2
    ap.names.get(2).sequence_FTId should contain ("VSP_004089")
    ap.names.get(2).sequence_FTId should contain ("VSP_004090")

    ap.names.get(2).synNames should have size (1)
    ap.names.get(2).synNames.get(0) should equal ("AIRE-3")
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
    ap.events.get(0) should equal ("Alternative promoter usage")
    ap.namedIsoforms should equal ("2")

    ap.names should have size (2)
    ap.names.get(0).name should equal ("alpha")
    ap.names.get(0).isoId should have size (1)
    ap.names.get(0).isoId.get(0) should equal ("P12544-1")
    ap.names.get(0).sequence_enum should equal (AlternativeNameSequenceEnum.Displayed)
    ap.names.get(0).sequence_FTId should be ('empty)

    ap.names.get(1).name should equal ("beta")
    ap.names.get(1).isoId should have size (1)
    ap.names.get(1).isoId.get(0) should equal ("P12544-2")
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
    bp.kms.get(0) should equal ("1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees Celsius and at pH 7.6)")

    bp.vmaxs should have size (1)
    bp.vmaxs.get(0) should equal ("1.9 mmol/min/mg enzyme")
    bp.ph_dependence should equal ("Optimum pH is 7.75")
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

    wr.name should equal ("CD40Lbase")
    wr.note should equal ("CD40L defect database")
    wr.url should equal ("http://bioinf.uta.fi/CD40Lbase/")
  }

}
