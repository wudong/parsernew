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
class OgLineParserTest extends FunSuite  {

  test("A valid one line og") {
    val osOneLiner = "OG   Plasmid R6-5, Plasmid IncFII R100 (NR1).\n";
    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidName should have size (2)
    obj.plasmidName should contain ("R6-5")
    obj.plasmidName should contain ("IncFII R100 (NR1)")

  }


  test("A valid tow line og") {
    val osOneLiner = """OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
                       |OG   Plasmid IncFII R1-19 (R1 drd-19).
                       |""";
    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidName should have size (2)
    obj.plasmidName should contain ("Plastid")
    obj.plasmidName should contain ("Non-photosynthetic plastid")
  }

}
