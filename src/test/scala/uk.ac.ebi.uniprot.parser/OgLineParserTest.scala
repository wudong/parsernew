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
class OgLineParserTest extends FunSuite  {

  test("A valid one line og") {
    val osOneLiner = "OG   Plasmid IncFII R1-19 (R1 drd-19).\n";
    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (1)
    obj.plasmidNames should contain ("IncFII R1-19 (R1 drd-19)")
  }


  test("A valid tow line og") {
    val osOneLiner = """OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
                       |OG   Plasmid IncFII R1-19 (R1 drd-19).
                       |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (3)
    obj.plasmidNames should contain ("R6-5")
    obj.plasmidNames should contain ("IncFII R100 (NR1)")
    obj.plasmidNames should contain ("IncFII R1-19 (R1 drd-19)")
  }

  test("A valid FULL og") {
    val osOneLiner = """OG   Hydrogenosome.
                       |OG   Mitochondrion.
                       |OG   Nucleomorph.
                       |OG   Plasmid R6-5.
                       |OG   Plastid.
                       |OG   Plastid; Apicoplast.
                       |OG   Plastid; Chloroplast.
                       |OG   Plastid; Organellar chromatophore.
                       |OG   Plastid; Cyanelle.
                       |OG   Plastid; Non-photosynthetic plastid.
                       |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (1)
    obj.plasmidNames should contain ("R6-5")

    obj.hydrogenosome should be (true)
    obj.mitochondrion should be (true)
    obj.nucleomorph should be (true)
    obj.plastid should be (true)
    obj.plastid_Apicoplast should be (true)
    obj.plastid_Organellar_chromatophore should be (true)
    obj.plastid_Cyanelle should be (true)
    obj.plastid_Chloroplast should be (true)
    obj.plastid_Non_photosynthetic should be (true)
  }

}
