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
class CcLineParserAlternativeProductTest extends FunSuite {

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

  test("CC alternative produces sequence value can change line"){
    val lines =
      """CC   -!- ALTERNATIVE PRODUCTS:
        |CC       Event=Alternative splicing; Named isoforms=6;
        |CC       Name=1; Synonyms=A;
        |CC         IsoId=Q9V8R9-1; Sequence=Displayed;
        |CC       Name=2;
        |CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,
        |CC                                  VSP_000480, VSP_000481;
        |CC       Name=3; Synonyms=C;
        |CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;
        |CC       Name=4; Synonyms=B;
        |CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;
        |CC       Name=5;
        |CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;
        |CC         Note=No experimental confirmation available;
        |CC       Name=6; Synonyms=D;
        |CC         IsoId=Q9V8R9-6; Sequence=VSP_000478;
        |CC         Note=No experimental confirmation available;
        |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    val unit: AlternativeProducts = cc2.`object`.asInstanceOf[AlternativeProducts]
    unit.names should have size (6)
    unit.names.get(1).sequence_FTId should have size (5)
  }

}
