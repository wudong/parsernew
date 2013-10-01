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
class CcLineParserSubcullarLocationTest extends FunSuite {

  test("subcellur location 1"){

    val lines = """CC   -!- SUBCELLULAR LOCATION: Cytoplasm. Endoplasmic reticulum membrane;
                   |CC       Peripheral membrane protein. Golgi apparatus membrane; Peripheral
                   |CC       membrane protein.
                   |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[SubcullarLocation]
    val sl = cc1.`object`.asInstanceOf[SubcullarLocation]

    sl.notes should be ('empty)
    sl.molecule should be (null)

    sl.locations should have size (3)
    val l1: LocationObject = sl.locations.get(0)
    l1.subcellular_location should be ("Cytoplasm")
    l1.subcellular_location_flag should be (null)

    val l2: LocationObject = sl.locations.get(1)
    l2.subcellular_location should be ("Endoplasmic reticulum membrane")
    l2.subcellular_location_flag should be (null)
    l2.topology should be ("Peripheral membrane protein")
    l2.topology_flag should be (null)

    val l3: LocationObject = sl.locations.get(2)
    l3.subcellular_location should be ("Golgi apparatus membrane")
    l3.topology should be ("Peripheral membrane protein")
  }

  test("subcellur location 2"){
    val lines = """CC   -!- SUBCELLULAR LOCATION: Cell membrane; Peripheral membrane protein
                  |CC       (By similarity). Secreted (By similarity). Note=The last 22 C-
                  |CC       terminal amino acids may participate in cell membrane attachment.
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[SubcullarLocation]
    val sl = cc1.`object`.asInstanceOf[SubcullarLocation]

    sl.notes should have size (1)
    sl.notes.get(0).note should be ("The last 22 C-terminal amino acids may participate in cell membrane attachment");

    sl.locations should have size (2)
    val l1: LocationObject = sl.locations.get(0)
    l1.subcellular_location should be ("Cell membrane")
    l1.topology should be ("Peripheral membrane protein")
    l1.topology_flag should be (LocationFlagEnum.By_similarity)

    val l2: LocationObject = sl.locations.get(1)
    l2.subcellular_location should be ("Secreted")
    l2.subcellular_location_flag should be (LocationFlagEnum.By_similarity)
  }


  test("subcellur location 3"){
    val lines =  """CC   -!- SUBCELLULAR LOCATION: Isoform 2: Cytoplasm (Probable).
                    |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.`object`.isInstanceOf[SubcullarLocation]
    val sl2 = cc2.`object`.asInstanceOf[SubcullarLocation]
    sl2.molecule should be ("Isoform 2")
    sl2.locations should have size (1)
    val l3: LocationObject = sl2.locations.get(0)
    l3.subcellular_location should be ("Cytoplasm")
    l3.subcellular_location_flag should be (LocationFlagEnum.Probable)
  }

  test("subcellur location with more than one note separated by DOT"){
     val lines=  """CC   -!- SUBCELLULAR LOCATION: Golgi apparatus, trans-Golgi network
                    |CC       membrane; Multi-pass membrane protein (By similarity).
                    |CC       Note=Predominantly found in the trans-Golgi network (TGN). Not
                    |CC       redistributed to the plasma membrane in response to elevated
                    |CC       copper levels.
                    |""".stripMargin.replace("\r", "")


    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.`object`.isInstanceOf[SubcullarLocation]
    val sl2 = cc2.`object`.asInstanceOf[SubcullarLocation]
    sl2.notes should have size (2)
    sl2.notes.get(0).note should be ("Predominantly found in the trans-Golgi network (TGN)")
    sl2.notes.get(1).note should be ("Not redistributed to the plasma membrane in response to elevated copper levels")
  }

  test("Note having flags."){
    val lines=  """CC   -!- SUBCELLULAR LOCATION: Apical cell membrane; Single-pass type II
                  |CC       membrane protein. Melanosome. Note=Identified by mass spectrometry
                  |CC       in melanosome fractions from stage I to stage IV. Localized to the
                  |CC       plasma membrane when associated with SLC7A5 or SLC7A8. Localized
                  |CC       to the placental apical membrane. Located selectively at cell-cell
                  |CC       adhesion sites (By similarity). Colocalized with SLC7A8/LAT2 at
                  |CC       the basolateral membrane of kidney proximal tubules and small
                  |CC       intestine epithelia. Expressed in both luminal and abluminal
                  |CC       membranes of brain capillary endothelial cells (By similarity).
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.`object`.isInstanceOf[SubcullarLocation]
    val sl2 = cc2.`object`.asInstanceOf[SubcullarLocation]
    sl2.notes should have size (6)
    sl2.notes.get(3).noteFlag should be (LocationFlagEnum.By_similarity)
    sl2.notes.get(5).noteFlag should be (LocationFlagEnum.By_similarity)
  }

  test("note containing ';'"){
    val lines=  """CC   -!- SUBCELLULAR LOCATION: Cell membrane; Multi-pass membrane protein.
                  |CC       Endosome. Note=Interaction with SNX27 mediates recruitment to
                  |CC       early endosomes, while interaction with SLC9A3R1 and EZR might
                  |CC       target the protein to specialized subcellular regions, such as
                  |CC       microvilli (By similarity). Interacts (via C-terminus) with GRK5;
                  |CC       this interaction is promoted by 5-HT (serotonin) (By similarity).
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)
    val sl2 = cc2.`object`.asInstanceOf[SubcullarLocation]
    sl2.notes should have size (2)
    sl2.notes.get(0).noteFlag should be (LocationFlagEnum.By_similarity)
    sl2.notes.get(1).noteFlag should be (LocationFlagEnum.By_similarity)
  }

  test("name contains DOT inside"){
    val lines=  """CC   -!- SUBCELLULAR LOCATION: Isoform UL12.5: Host mitochondrion.
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)
    val sl2 = cc2.`object`.asInstanceOf[SubcullarLocation]

    sl2.molecule should be ("Isoform UL12.5")
    sl2.locations should have size (1)
    sl2.locations.get(0).subcellular_location should be ("Host mitochondrion")
  }



}
