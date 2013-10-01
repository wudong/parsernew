package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject.OgEnum

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class OgLineParserTest extends FunSuite {


  test("A og with no plasmid value"){
    val osOneLiner = "OG   Plasmid.\n";
    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (0)
    obj.ogs should contain (OgEnum.PLASMID)
  }

  test("A valid one line og") {
    val osOneLiner = "OG   Plasmid IncFII R1-19 (R1 drd-19).\n";
    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (1)
    obj.plasmidNames should contain("IncFII R1-19 (R1 drd-19)")
  }


  test("A valid tow line og") {
    val osOneLiner = """OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
                       |OG   Plasmid IncFII R1-19 (R1 drd-19).
                       |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (3)
    obj.plasmidNames should contain("R6-5")
    obj.plasmidNames should contain("IncFII R100 (NR1)")
    obj.plasmidNames should contain("IncFII R1-19 (R1 drd-19)")
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
    obj.plasmidNames should contain("R6-5")

    obj.ogs should have size (9)
    obj.ogs should contain(OgEnum.HYDROGENOSOME)
    obj.ogs should contain(OgEnum.MITOCHONDRION)
    obj.ogs should contain(OgEnum.NUCLEOMORPH)
    obj.ogs should contain(OgEnum.PLASTID)
    obj.ogs should contain(OgEnum.PLASTID_APICOPLAST)
    obj.ogs should contain(OgEnum.PLASTID_CHLOROPLAST)
    obj.ogs should contain(OgEnum.PLASTID_CYANELLE)
    obj.ogs should contain(OgEnum.PLASTID_NON_PHOTOSYNTHETIC)
    obj.ogs should contain(OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE)
  }

  test("A valid FULL og with Evidence") {
    val osOneLiner = """OG   Hydrogenosome{EI1}.
                       |OG   Mitochondrion{EI1}.
                       |OG   Nucleomorph{EI2}.
                       |OG   Plasmid R6-5{EI1,EI2}.
                       |OG   Plastid{EI1}.
                       |OG   Plastid; Apicoplast{EI1}.
                       |OG   Plastid; Chloroplast{EI1}.
                       |OG   Plastid; Organellar chromatophore{EI1}.
                       |OG   Plastid; Cyanelle{EI1}.
                       |OG   Plastid; Non-photosynthetic plastid{EI2,EI3}.
                       |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createOgLineParser();
    val obj = parser.parse(osOneLiner)

    obj should not be null
    obj.plasmidNames should have size (1)
    obj.plasmidNames should contain("R6-5")

    obj.evidence.evidences.get("R6-5") should not be (null)
    obj.evidence.evidences.get("R6-5") should have size (2)
    obj.evidence.evidences.get("R6-5") should contain("EI1")
    obj.evidence.evidences.get("R6-5") should contain("EI2")

    obj.evidence.evidences.get(OgEnum.HYDROGENOSOME) should not be (null)
    obj.evidence.evidences.get(OgEnum.HYDROGENOSOME) should have size (1)
    obj.evidence.evidences.get(OgEnum.HYDROGENOSOME) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.MITOCHONDRION) should not be (null)
    obj.evidence.evidences.get(OgEnum.MITOCHONDRION) should have size (1)
    obj.evidence.evidences.get(OgEnum.MITOCHONDRION) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.NUCLEOMORPH) should not be (null)
    obj.evidence.evidences.get(OgEnum.NUCLEOMORPH) should have size (1)
    obj.evidence.evidences.get(OgEnum.NUCLEOMORPH) should contain("EI2")

    obj.evidence.evidences.get(OgEnum.PLASTID) should not be (null)
    obj.evidence.evidences.get(OgEnum.PLASTID) should have size (1)
    obj.evidence.evidences.get(OgEnum.PLASTID) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.PLASTID_APICOPLAST) should not be (null)
    obj.evidence.evidences.get(OgEnum.PLASTID_APICOPLAST) should have size (1)
    obj.evidence.evidences.get(OgEnum.PLASTID_APICOPLAST) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.PLASTID_CHLOROPLAST) should not be (null)
    obj.evidence.evidences.get(OgEnum.PLASTID_CHLOROPLAST) should have size (1)
    obj.evidence.evidences.get(OgEnum.PLASTID_CHLOROPLAST) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE) should not be (null)
    obj.evidence.evidences.get(OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE) should have size (1)
    obj.evidence.evidences.get(OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.PLASTID_CYANELLE) should not be (null)
    obj.evidence.evidences.get(OgEnum.PLASTID_CYANELLE) should have size (1)
    obj.evidence.evidences.get(OgEnum.PLASTID_CYANELLE) should contain("EI1")

    obj.evidence.evidences.get(OgEnum.PLASTID_NON_PHOTOSYNTHETIC) should not be (null)
    obj.evidence.evidences.get(OgEnum.PLASTID_NON_PHOTOSYNTHETIC) should have size (2)
    obj.evidence.evidences.get(OgEnum.PLASTID_NON_PHOTOSYNTHETIC) should contain("EI2")
    obj.evidence.evidences.get(OgEnum.PLASTID_NON_PHOTOSYNTHETIC) should contain("EI3")

  }

}
