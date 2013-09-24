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
class OsLineParserTest extends FunSuite  {

  test("A valid one line os") {
    val osOneLiner = "OS   Solanum melongena (Eggplant) (Aubergine).\n";
    val parser = (new DefaultUniprotLineParserFactory).createOsLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null;
    expectResult("Solanum melongena (Eggplant) (Aubergine)"){
      obj.organism_species;
    };
  }

  test("A valid two line os") {
    val osOneLiner =
      """OS   Rous (strain Schmidt-Ruppin A) (Avian leukosis
        |OS   virus-RSA).
        |""".stripMargin.replace("\r", "");
    val parser = (new DefaultUniprotLineParserFactory).createOsLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null;
    expectResult("Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)"){
      obj.organism_species;
    };
  }

  test("A valid three line os") {
    val osOneLiner =
      """OS   Rous (strain Schmidt-Ruppin A)
        |OS   (Avian leukosis
        |OS   virus-RSA).
        |""".stripMargin.replace("\r", "");
    val parser = (new DefaultUniprotLineParserFactory).createOsLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null;
    expectResult("Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)"){
      obj.organism_species;
    };
  }

  test("A os line with / in it"){
      val osOneLiner =
        """OS   African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV).
          |""".stripMargin.replace("\r", "");
      val parser = (new DefaultUniprotLineParserFactory).createOsLineParser();
      val obj = parser.parse(osOneLiner)

      obj should not be null;
      expectResult("African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV)"){
        obj.organism_species;
      };
  }

}
