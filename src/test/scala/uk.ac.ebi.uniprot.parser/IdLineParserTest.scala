package impl

import org.scalatest.FunSuite
import uk.ac.ebi.uniprot.parser.ParseException
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject
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
class IdLineParserTest extends FunSuite  {

  test("A valid idLine should be parsed no problem") {

    val idLine_1 =
      "ID   001R_FRG3G              Reviewed;         256 AA.\n"


    val parser = (new DefaultUniprotLineParserFactory).createIdLineParser();

    val obj: IdLineObject = parser.parse(idLine_1)

    obj.entryName should equal ("001R_FRG3G")
    obj.reviewed should equal (true)
    obj.sequenceLength should equal (256)
  }

  test("A non-valid idLine should be throw exception") {

    val idLine_invalid = "ID   001R_FRG3G              Reviewed;         256 AA."

    val parser = (new DefaultUniprotLineParserFactory).createIdLineParser();

    intercept[ParseException]
      {parser.parse(idLine_invalid)}
  }

}
