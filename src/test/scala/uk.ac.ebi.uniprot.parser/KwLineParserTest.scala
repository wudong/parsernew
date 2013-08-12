package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.dt.DtObjectParser
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.kw.KwObjectParser

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class KwLineParserTest extends FunSuite  {

  val kwOneLiner = "KW   Activator; Complete proteome; Reference proteome; Transcription.\n";

  val threeLiner = """KW   Activator; Complete proteome;
                     |KW   Reference proteome; Transcription;
                     |KW   Transcription regulation.
                     |""".stripMargin.replace("\r", "")

  test("A valid one line keyword") {
    val parser = new KwObjectParser
    val obj = parser.parse(kwOneLiner)

    obj should not be null;
    obj.keywords should have size (4);
    obj.keywords should contain ("Activator");
    obj.keywords should contain ("Complete proteome");
    obj.keywords should contain ("Reference proteome");
    obj.keywords should contain ("Transcription");
  }

  test("A valid tree line keyword") {
    val parser = new KwObjectParser
    val obj = parser.parse(threeLiner)

    obj should not be null;
    obj.keywords should have size (5);
    obj.keywords should contain ("Activator");
    obj.keywords should contain ("Complete proteome");
    obj.keywords should contain ("Reference proteome");
    obj.keywords should contain ("Transcription");
    obj.keywords should contain ("Transcription regulation");
  }

}
