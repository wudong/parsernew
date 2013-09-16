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
class OcLineParserTest extends FunSuite  {

  test("A valid one line oc") {
    val osOneLiner = "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi.\n";
    val parser = (new DefaultUniprotLineParserFactory).createOcLineParser();
    val obj = parser.parse(osOneLiner)

    obj.nodes should have size (6)
    obj.nodes should contain ("Eukaryota")
    obj.nodes should contain ("Metazoa")
    obj.nodes should contain ("Chordata")
    obj.nodes should contain ("Craniata")
    obj.nodes should contain ("Vertebrata")
    obj.nodes should contain ("Euteleostomi")
  }

  test("A valid tow line oc") {
    val osOneLiner = "OC   Eukaryota; Metazoa; Chordata; Craniata;\n" +
                     "OC   Vertebrata; Euteleostomi.\n";
    val parser = (new DefaultUniprotLineParserFactory).createOcLineParser();
    val obj = parser.parse(osOneLiner)

    obj.nodes should have size (6)
    obj.nodes should contain ("Eukaryota")
    obj.nodes should contain ("Metazoa")
    obj.nodes should contain ("Chordata")
    obj.nodes should contain ("Craniata")
    obj.nodes should contain ("Vertebrata")
    obj.nodes should contain ("Euteleostomi")
  }

  test ("an oc") {
    val osOneLiner= "OC   Viruses; dsDNA viruses, no RNA stage; Iridoviridae; Ranavirus.\n"
    val parser = (new DefaultUniprotLineParserFactory).createOcLineParser();
    val obj = parser.parse(osOneLiner)

    obj.nodes should have size (4)
    obj.nodes should contain ("Viruses")
    obj.nodes should contain ("dsDNA viruses, no RNA stage")
    obj.nodes should contain ("Iridoviridae")
    obj.nodes should contain ("Ranavirus")
  }

}
