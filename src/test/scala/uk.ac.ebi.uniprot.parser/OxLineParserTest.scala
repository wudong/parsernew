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
class OxLineParserTest extends FunSuite  {

  test("A valid one line oc") {
    val osOneLiner = "OX   NCBI_TaxID=562;\n";
    val parser = (new DefaultUniprotLineParserFactory).createOxLineParser();
    val obj = parser.parse(osOneLiner)

    obj.taxonomy_id should equal (562)


  }

}
