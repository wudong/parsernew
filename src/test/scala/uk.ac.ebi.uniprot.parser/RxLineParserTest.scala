package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.scalatest.matchers.ShouldMatchers._


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RxLineParserTest extends FunSuite {

  test("A valid Rx Line 1 ") {

    val rnLine = "RX   MEDLINE=83283433; PubMed=6688356;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rnLine)
    obj.rxs should have size (2);

    expectResult(("MEDLINE", "83283433")) {
      val v = obj.rxs.get(0);
      (v.`type`.name(), v.value)
    }

    expectResult(("PubMed", "6688356")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }

  }

  test("A valid Rx Line 2") {

    val rnLine = "RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rnLine)
    obj.rxs should have size (2);

    expectResult(("PubMed", "15626370")) {
      val v = obj.rxs.get(0);
      (v.`type`.name(), v.value)
    }

    expectResult(("DOI", "10.1016/j.toxicon.2004.10.011")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }

  }

  test("A valid Rx Line 3") {

    val rnLine = "RX   MEDLINE=22709107; PubMed=12788972; DOI=10.1073/pnas.1130426100;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rnLine)
    obj.rxs should have size (3);

    expectResult(("MEDLINE", "22709107")) {
      val v = obj.rxs.get(0);
      (v.`type`.name(), v.value)
    }

    expectResult(("PubMed", "12788972")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }

    expectResult(("DOI", "10.1073/pnas.1130426100")) {
      val v = obj.rxs.get(2);
      (v.`type`.name(), v.value)
    }
  }

  test("A valid Rx Line 4") {

    val rnLine = "RX   AGRICOLA=IND20551642; DOI=10.1007/BF00224104;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rnLine)
    obj.rxs should have size (2);

    expectResult(("AGRICOLA", "IND20551642")) {
      val v = obj.rxs.get(0);
      (v.`type`.name(), v.value)
    }

    expectResult(("DOI", "10.1007/BF00224104")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }

  }

}
