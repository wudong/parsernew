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

    val rnLine = "RX   PubMed=12788972; DOI=10.1073/pnas.1130426100;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rnLine)
    obj.rxs should have size (2);

    expectResult(("PubMed", "12788972")) {
      val v = obj.rxs.get(0);
      (v.`type`.name(), v.value)
    }

    expectResult(("DOI", "10.1073/pnas.1130426100")) {
      val v = obj.rxs.get(1);
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

  test("a DOI"){
    val rxLine = "RX   PubMed=16912294; DOI=10.1128/JVI.00464-06;\n"

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rxLine)
    obj.rxs should have size (2);

    expectResult(("PubMed", "16912294")) {
      val v = obj.rxs.get(0);
      (v.`type`.name(), v.value)
    }

    expectResult(("DOI", "10.1128/JVI.00464-06")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }

  }


  test("another strangely formatted DOI"){
    val rxLine = "RX   PubMed=14577811; DOI=10.1597/1545-1569(2003)040<0632:AMMITS>2.0.CO;2;\n"

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rxLine)
    obj.rxs should have size (2);


    expectResult(("DOI", "10.1597/1545-1569(2003)040<0632:AMMITS>2.0.CO;2")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }
  }

  ignore("DOI contains space."){
    val rxLine = "RX   PubMed=15060122; DOI=10.1136/jmg 2003.012781;\n"

    val parser = (new DefaultUniprotLineParserFactory).createRxLineParser();
    val obj = parser.parse(rxLine)
    obj.rxs should have size (2);


    expectResult(("DOI", "10.1136/jmg 2003.012781")) {
      val v = obj.rxs.get(1);
      (v.`type`.name(), v.value)
    }
  }

}
