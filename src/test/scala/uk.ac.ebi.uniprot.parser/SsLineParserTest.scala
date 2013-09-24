package uk.ac.ebi.uniprot.parser

import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineObject.Ev
import org.joda.time.DateTime

/**
 *
 * <p/>
 * User: wudong, Date: 22/09/13, Time: 23:19
 */
@RunWith(classOf[JUnitRunner])
class SsLineParserTest extends FunSuite{

  test("A valid SQ Line blocks") {

    val ssLine = """|**
                   |**   #################    INTERNAL SECTION    ##################
                   |**EV EI2; ProtImp; -; -; 11-SEP-2009.
                   |**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
                   |**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSsLineParser();
    val obj = parser.parse(ssLine)

    obj should not be (null)
    obj.evs should have size (3)

    val ev: Ev = obj.evs.get(0)
    ev should have ('evtag ("EI2"), 'xref ("ProtImp"), 'value_1 ("-"), 'value_2 ("-"),
      'dateTime (new DateTime(2009, 9, 11,0,0)))

    val ev2: Ev = obj.evs.get(1)
    ev2 should have ('evtag ("EI3"), 'xref ("EMBL"), 'value_1 ("-"), 'value_2 ("ACT41999.1"),
      'dateTime (new DateTime(2010, 6, 22,0,0)))

    val ev3: Ev = obj.evs.get(2)
    ev3 should have ('evtag ("EI4"), 'xref ("EMBL"), 'value_1 ("-"), 'value_2 ("CAQ30614.1"),
      'dateTime (new DateTime(2009, 12, 18,0,0)))
  }
}
