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
class OhLineParserTest extends FunSuite  {

  test("A valid one line oh") {
    val osOneLiner = "OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).\n";
    val parser = (new DefaultUniprotLineParserFactory).createOhLineParser();
    val obj = parser.parse(osOneLiner)

    obj.tax_id should equal (9598)
    obj.hostname should equal ("Pan troglodytes (Chimpanzee)")
  }

  test("oh line contains dot in size") {
    val osOneLiner = "OH   NCBI_TaxID=3662; Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata).\n";
    val parser = (new DefaultUniprotLineParserFactory).createOhLineParser();
    val obj = parser.parse(osOneLiner)

    obj.tax_id should equal (3662)
    obj.hostname should equal ("Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata)")
  }

}
