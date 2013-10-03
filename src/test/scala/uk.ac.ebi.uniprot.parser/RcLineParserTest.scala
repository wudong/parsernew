package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.rc.RcLineObject.RcTokenEnum


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RcLineParserTest extends FunSuite {

  test("A valid Rc Line") {

    val rnLine = "RC   STRAIN=Sprague-Dawley; TISSUE=Liver;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.rcs should have size (2);

    expectResult(("STRAIN", "Sprague-Dawley")) {
      val rc = obj.rcs.get(0);
      (rc.tokenType.name(), rc.values.get(0))
    }
    expectResult(("TISSUE", "Liver")) {
      val rc = obj.rcs.get(1);
      (rc.tokenType.name(), rc.values.get(0))
    }

  }

  test("A valid Rc one line with and") {

    val rnLine = "RC   STRAIN=Holstein; TISSUE=Lymph node, and Mammary gland;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.rcs should have size (2);

    expectResult(("STRAIN", "Holstein")) {
      val rc = obj.rcs.get(0);
      (rc.tokenType.name(), rc.values.get(0))
    }

    expectResult(("TISSUE", "Lymph node", "Mammary gland")) {
      val rc = obj.rcs.get(1);
      (rc.tokenType.name(), rc.values.get(0), rc.values.get(1))
    }

  }

  test("A valid Rc two line with and") {

    val rnLine = """RC   STRAIN=AL.012, AZ.026, AZ.180, DC.005, GA.039, GA2181, IL.014, IL2.17,
                   |RC   IN.018, KY.172, KY2.37, LA.013, MI.035, MN.001, MNb027, and VA.015;
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null
    obj.rcs should have size (1);

    expectResult(("STRAIN", "AL.012", "MNb027", "VA.015")) {
      val rc = obj.rcs.get(0)
      rc.values should have size (16)

      (rc.tokenType.name(), rc.values.get(0), rc.values.get(14), rc.values.get(15))
    }

  }

  test("A valid Rc three line with and") {

    val rnLine = """RC   STRAIN=AL.012, AZ.026, AZ.180, DC.005, GA.039, GA2181, IL.014, IL2.17,
                   |RC   IN.018, KY.172, KY2.37, LA.013, MI.035, MN.001, MNb027, and VA.015;
                   |RC   TISSUE=Liver;
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null
    obj.rcs should have size (2);

    expectResult(("STRAIN", "AL.012", "MNb027", "VA.015")) {
      val rc = obj.rcs.get(0)
      rc.values should have size (16)
      (rc.tokenType.name(), rc.values.get(0), rc.values.get(14), rc.values.get(15))
    }

    expectResult(("TISSUE", "Liver")) {
      val rc = obj.rcs.get(1)
      (rc.tokenType.name(), rc.values.get(0))
    }
  }

  test("RC with with evidence") {
    val rcLines = """RC   STRAIN=XM1OR{EI7}, XMO3R{EI2}, XMO4R{EI3}, XMO5F{EI1}, XMO5R{EI4},
                    |RC   XMO8F{EI5}, and XMO8R{EI6};
                    |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rcLines)
  }

  test("RC with value contains / and changing line") {
    val rcLines = """RC   STRAIN=ATCC 6260 / CBS 566 / DSM 6381 / JCM 1539 / NBRC 10279 / NRRL
                    |RC   Y-324;
                    |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rcLines)
    obj.rcs should have size (1)
    obj.rcs.get(0).tokenType should be (RcTokenEnum.STRAIN);
    obj.rcs.get(0).values should have size (1)
    obj.rcs.get(0).values.get(0) should be ("ATCC 6260 / CBS 566 / DSM 6381 / JCM 1539 / NBRC 10279 / NRRL Y-324")
  }


  test("RC with value , inside word") {
    val rcLines = """RC   STRAIN=PP24[03,07,10];
                    |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRcLineParser();
    val obj = parser.parse(rcLines)
    obj.rcs should have size (1)
    obj.rcs.get(0).tokenType should be (RcTokenEnum.STRAIN);
    obj.rcs.get(0).values should have size (1)
    obj.rcs.get(0).values.get(0) should be ("PP24[03,07,10]")
  }


}
