package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import java.util

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class KwLineParserTest extends FunSuite  {

  test("A valid one line keyword") {

    val kwOneLiner = "KW   Activator; Complete proteome; Reference proteome; Transcription.\n";
    val parser = (new DefaultUniprotLineParserFactory).createKwLineParser();
    val obj = parser.parse(kwOneLiner)

    obj should not be null;
    obj.keywords should have size (4);
    obj.keywords should contain ("Activator");
    obj.keywords should contain ("Complete proteome");
    obj.keywords should contain ("Reference proteome");
    obj.keywords should contain ("Transcription");
  }

  test("A valid tree line keyword") {
    val threeLiner = """KW   Activator; Complete proteome;
                       |KW   Reference proteome; Transcription;
                       |KW   Transcription regulation.
                       |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createKwLineParser();
    val obj = parser.parse(threeLiner)

    obj should not be null;
    obj.keywords should have size (5);
    obj.keywords should contain ("Activator");
    obj.keywords should contain ("Complete proteome");
    obj.keywords should contain ("Reference proteome");
    obj.keywords should contain ("Transcription");
    obj.keywords should contain ("Transcription regulation");
  }


  test("A valid  keyword with evidence.") {
    val threeLiner = """KW   Activator{EI1}; Complete proteome{EI1};
                       |KW   Reference proteome; Transcription{EI1,EI2};
                       |KW   Transcription regulation.
                       |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createKwLineParser();
    val obj = parser.parse(threeLiner)

    obj should not be null;
    obj.keywords should have size (5);
    obj.keywords should contain ("Activator");
    obj.keywords should contain ("Complete proteome");
    obj.keywords should contain ("Reference proteome");
    obj.keywords should contain ("Transcription");
    obj.keywords should contain ("Transcription regulation");

    obj.evidenceInfo.evidences should have size (3)
    val list: util.List[String] = obj.evidenceInfo.evidences.get("Activator")
    list should not be (null)
    list should contain ("EI1")

    val list2: util.List[String] = obj.evidenceInfo.evidences.get("Complete proteome")
    list2 should not be (null)
    list2 should contain ("EI1")
    
    val list3: util.List[String] = obj.evidenceInfo.evidences.get("Transcription")
    list3 should not be (null)
    list3 should contain ("EI1")
    list3 should contain ("EI2")

  }

}
