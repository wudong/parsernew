package impl

import org.scalatest.FunSuite
import uk.ac.ebi.uniprot.parser.ParseException
import java.io.StringReader
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.id.{IDLineParser, IdLineObject}

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class IdLineParserTest extends FunSuite  {

  val idLine_1 = "ID   001R_FRG3G              Reviewed;         256 AA.\n"
  val idLine_invalid = "ID   001R_FRG3G              Reviewed;         256 AA."

  test("A valid idLine should be parsed no problem") {

    val parser = new IDLineParser;

    val obj: IdLineObject = parser.parse(idLine_1)

    assert(obj.entryName === "001R_FRG3G")
    assert(obj.reviewed)
  }

  test("A non-valid idLine should be throw exception") {
    val parser = new IDLineParser;
    intercept[ParseException]
      {parser.parse(idLine_invalid)}
  }


}
