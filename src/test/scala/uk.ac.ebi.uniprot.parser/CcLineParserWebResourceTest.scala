package uk.ac.ebi.uniprot.parser


import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject._


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class CcLineParserWebResourceTest extends FunSuite {

  test ("web resource 1"){
    val lines = """CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
                  |CC       URL="http://bioinf.uta.fi/CD40Lbase/";
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[WebResource]
    val wr = cc1.`object`.asInstanceOf[WebResource]

    wr.name should be ("CD40Lbase")
    wr.note should be ("CD40L defect database")
    wr.url should be ("http://bioinf.uta.fi/CD40Lbase/")
  }

  test ("web resource 2"){
    val lines = """CC   -!- WEB RESOURCE: Name=Functional Glycomics Gateway - GTase;
                  |CC       Note=Beta1,4-N-acetylgalactosaminyltransferase III.;
                  |CC       URL="http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507";
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[WebResource]
    val wr = cc1.`object`.asInstanceOf[WebResource]

    wr.name should be ("Functional Glycomics Gateway - GTase")
    wr.note should be ("Beta1,4-N-acetylgalactosaminyltransferase III.")
    wr.url should be ("http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507")
  }




}
