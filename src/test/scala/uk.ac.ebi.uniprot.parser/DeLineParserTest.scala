package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class DeLineParserTest extends FunSuite {

  test("de") {
    val deLines = """DE   RecName: Full=Annexin A5;
                    |DE            Short=Annexin-5;
                    |DE   AltName: Full=Annexin V;
                    |DE   AltName: Full=Lipocortin V;
                    |DE   AltName: Full=Placental anticoagulant protein I;
                    |DE            Short=PAP-I;
                    |DE   AltName: Full=PP4;
                    |DE   AltName: Full=Thromboplastin inhibitor;
                    |DE   AltName: Full=Vascular anticoagulant-alpha;
                    |DE            Short=VAC-alpha;
                    |DE   AltName: Full=Anchorin CII;
                    |DE   Flags: Precursor;
                    |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createDeLineParser();
    val obj = parser.parse(deLines)

  }

}
