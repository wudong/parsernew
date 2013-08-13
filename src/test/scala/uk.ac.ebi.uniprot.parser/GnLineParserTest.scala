package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.dr.{DrLineObject, DrObjectParser}

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class GnLineParserTest extends FunSuite  {

  val gnLine = """""";


  test("A valid GN Line blocks") {
    val parser = new DrObjectParser
    val obj = parser.parse(gnLine)

    obj should not be null

  }

}
