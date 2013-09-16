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
class RaLineParserTest extends FunSuite {

  test("A valid Ra Line 2 with two first name.") {
    val rnLine = "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRaLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.authors should have size (4)

    obj.authors should contain ("Tan W.G.");
    obj.authors should contain ("Barkman T.J.");
    obj.authors should contain ("Gregory Chinchar V.");
    obj.authors should contain ("Essani K.");
  }

  test("A valid Ra Line ") {

    val rnLine = "RA   Galinier A., Perriere G., Duclos B.;\n";

    val parser = (new DefaultUniprotLineParserFactory).createRaLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.authors should have size (3)

    obj.authors should contain ("Galinier A.");
    obj.authors should contain ("Perriere G.");
    obj.authors should contain ("Duclos B.");
  }

  test("A valid Ra multiple Line ") {

    val rnLine = """RA   Galinier A., Bleicher F., Nasoff M.S., Baker H.V. II, Wolf R.E. Jr.,
                   |RA   Cozzone A.J., Cortay J.-C.;
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createRaLineParser();
    val obj = parser.parse(rnLine)

    obj should not be null;
    obj.authors should have size (7)

    obj.authors should contain ("Wolf R.E. Jr.");
    obj.authors should contain ("Cortay J.-C.");
    obj.authors should contain ("Baker H.V. II");
    obj.authors should contain ("Cozzone A.J.");
  }

}
