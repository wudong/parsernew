package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.sq.SqObjectParser
import uk.ac.ebi.uniprot.parser.impl.pe.PeObjectParser
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class PeLineParserTest extends FunSuite {

  test("A valid SQ Line blocks") {

    val peLine = """PE   1: Evidence at protein level;
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createPeLineParser();
    val obj = parser.parse(peLine)

    obj should not be null;
    expectResult(1) {obj.level};

  }

}
