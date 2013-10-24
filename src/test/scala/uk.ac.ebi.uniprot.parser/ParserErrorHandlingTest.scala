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
class ParserErrorHandlingTest extends FunSuite {

  test("Error handling in AC with the defaultErrorListener") {

    //there is an extra space after AC.
    val ac_one_line = "AC    Q6GZX4;\n"

    val parser = (new DefaultUniprotLineParserFactory).createAcLineParser();

    val e = intercept[ParseException]{
      parser.parse(ac_one_line)
    }
    System.out.println (e.getDetailedMessage)
  }

}
