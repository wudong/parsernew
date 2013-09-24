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
class RtLineParserTest extends FunSuite {

  test("A valid RT Line") {
    val rpLine = "RT   \"A novel adapter protein employs a phosphotyrosine binding domain.\";\n";
    val parser = (new DefaultUniprotLineParserFactory).createRtLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;
    expectResult("A novel adapter protein employs a phosphotyrosine binding domain.") {obj.title};
  }

  test("A valid RT multi Line") {
    val rpLine = """RT   "New insulin-like proteins with atypical disulfide bond pattern
                   |RT   characterized in Caenorhabditis elegans by comparative sequence
                   |RT   analysis and homology modeling?";
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRtLineParser();
    val obj = parser.parse(rpLine)

    obj should not be null;
    expectResult("New insulin-like proteins with atypical disulfide bond pattern " +
      "characterized in Caenorhabditis elegans by comparative sequence " +
      "analysis and homology modeling?") {obj.title};
  }

}
