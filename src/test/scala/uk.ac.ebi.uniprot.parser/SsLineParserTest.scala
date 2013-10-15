package uk.ac.ebi.uniprot.parser

import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import scala.collection.JavaConverters._

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
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSsLineParser();
    val obj = parser.parse(ssLine)

    obj should not be (null)
    obj.ssIALines should have size (3)
    obj.ssIALines.asScala.map(_.topic).exists( _ != "EV") should be (false)

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
