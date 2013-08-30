package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject.FTType

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class FtLineParserTest extends FunSuite {

  test("a ft line without text ") {
    val line = "FT   HELIX      33     83\n";
    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser();
    val obj = parser.parse(line)

    obj.fts should have size (1)
    val ft = obj.fts.get(0)
    ft.`type` should equal (FTType.HELIX)
    ft.ftId should be (null)
    ft.location_start should equal ("33")
    ft.location_end should equal ("83")
    ft.ft_text should be (null);
  }

  test("a ft line with text ") {
    val line = """FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and
                 |FT                                ADP-ribosyl cyclase activity.
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser();
    val obj = parser.parse(line)

    obj.fts should have size (1)
    val ft = obj.fts.get(0)
    ft.`type` should equal (FTType.MUTAGEN)
    ft.ftId should be (null)
    ft.location_start should equal ("119")
    ft.location_end should equal ("119")
    ft.ft_text should equal ("C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity");
  }

  test("a ft line with text and ftid ") {
    val line = """FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
                 |FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
                 |FT                                isoform 2).
                 |FT                                /FTId=VSP_004370.
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser();
    val obj = parser.parse(line)

    obj.fts should have size (1)
    val ft = obj.fts.get(0)
    ft.`type` should equal (FTType.VAR_SEQ)
    ft.ftId should equal ("VSP_004370")
    ft.location_start should equal ("33")
    ft.location_end should equal ("83")
    ft.ft_text should equal ("TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)");
  }

  test("three combined ft"){
    val line = """FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
                 |FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
                 |FT                                isoform 2).
                 |FT                                /FTId=VSP_004370.
                 |FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and
                 |FT                                ADP-ribosyl cyclase activity.
                 |FT   HELIX        33     83
                 |FT   TURN          3     33
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser();
    val obj = parser.parse(line)
    obj.fts should have size (4)

    expectResult((FTType.VAR_SEQ, "33", "83",
      "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)",
      "VSP_004370")){
      val ft1 = obj.fts.get(0)
      ( ft1.`type`, ft1.location_start, ft1.location_end, ft1.ft_text, ft1.ftId )
    }

    expectResult((FTType.MUTAGEN, "119", "119",
      "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity", null)){
      val ft1 = obj.fts.get(1)
      ( ft1.`type`, ft1.location_start, ft1.location_end, ft1.ft_text, ft1.ftId )
    }

    expectResult((FTType.HELIX, "33", "83",null, null)){
      val ft1 = obj.fts.get(2)
      ( ft1.`type`, ft1.location_start, ft1.location_end, ft1.ft_text, ft1.ftId )
    }

    expectResult((FTType.TURN, "3", "33",null,null)){
      val ft1 = obj.fts.get(3)
      ( ft1.`type`, ft1.location_start, ft1.location_end, ft1.ft_text, ft1.ftId )
    }
  }

}
