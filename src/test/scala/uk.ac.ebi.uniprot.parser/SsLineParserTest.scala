package uk.ac.ebi.uniprot.parser

import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

import org.scalatest.matchers.ShouldMatchers._


/**
 *
 * <p/>
 * User: wudong, Date: 22/09/13, Time: 23:19
 */
@RunWith(classOf[JUnitRunner])
class SsLineParserTest extends FunSuite{

  test("A valid SS Internal Annotation") {

    val ssLine = """|**
                   |**   #################    INTERNAL SECTION    ##################
                   |**EV EI2; ProtImp; -; -; 11-SEP-2009.
                   |**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
                   |**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.
                   |**DG dg-000-000-614_P;
                   |**ZB YOK, 19-NOV-2002;
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSsLineParser();
    val obj = parser.parse(ssLine)

    obj should not be (null)
    obj.ssIALines should have size (2)
    obj.ssEVLines should have size (3)

    obj.ssEVLines.get(0).id should be ("EI2")
    obj.ssEVLines.get(0).db should be ("ProtImp")
    obj.ssEVLines.get(0).attr_1 should be ("-")
    obj.ssEVLines.get(0).attr_2 should be ("-")
    obj.ssEVLines.get(0).date.getYear should be (2009)
    obj.ssEVLines.get(0).date.getMonthOfYear should be (9)
    obj.ssEVLines.get(0).date.getDayOfMonth should be (11)
    
    obj.ssEVLines.get(2).id should be ("EI4")
    obj.ssEVLines.get(2).db should be ("EMBL")
    obj.ssEVLines.get(2).attr_1 should be ("-")
    obj.ssEVLines.get(2).attr_2 should be ("CAQ30614.1")
    obj.ssEVLines.get(2).date.getYear should be (2010)
    obj.ssEVLines.get(2).date.getMonthOfYear should be (12)
    obj.ssEVLines.get(2).date.getDayOfMonth should be (18)

    obj.ssIALines.get(0).topic should be ("DG")
    obj.ssIALines.get(0).text should be ("dg-000-000-614_P;")
    obj.ssIALines.get(1).topic should be ("ZB")
    obj.ssIALines.get(1).text should be ("YOK, 19-NOV-2002;")
  }


  test("A valid SS Internal Annotation and source") {

    val ssLine = """|**
                   |**   #################    INTERNAL SECTION    ##################
                   |**EV EI2; ProtImp; -; -; 11-SEP-2009.
                   |**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
                   |**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.
                   |**   LOCUS       547119         38 aa
                   |**               22-SEP-1994
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSsLineParser();
    val obj = parser.parse(ssLine)

    obj should not be (null)
    obj.ssSourceLines should have size (2)
    obj.ssSourceLines.get(0) should be ("LOCUS       547119         38 aa")
    obj.ssSourceLines.get(1) should be ("            22-SEP-1994")
  }
}
