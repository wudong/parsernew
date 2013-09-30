
package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject.{FT, FTType}

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class FtLineParserTest extends FunSuite {

  test ("a ft value start with digit"){
    val line =
      """FT   CHAIN        20    873       104 kDa microneme/rhoptry antigen.
        |FT                                /FTId=PRO_0000232680.
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser();
    val obj = parser.parse(line)

    obj.fts should have size (1)
    val ft = obj.fts.get(0)
    ft.`type` should equal (FTType.CHAIN)
    ft.ftId should be ("PRO_0000232680")
    ft.location_start should equal ("20")
    ft.location_end should equal ("873")
    ft.ft_text should equal ("104 kDa microneme/rhoptry antigen");
  }

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
    ft.ft_text should equal ("TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)");
  }

  //TODO This need some serious change or reconsideration
  ignore("a ft VAR SEQ 's wrapping 1") {
    val line = """FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
                 |FT                                -> SECLTYGKQPLTSFHPFTSQMPP (in
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
    ft.ft_text should equal ("TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)");
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
      "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)",
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

  test("an ft "){
    val line  ="""FT   CHAIN         1    256       Putative transcription factor 001R.
                 |FT                                /FTId=PRO_0000410512.
                 |FT   COMPBIAS     14     17       Poly-Arg.
                 |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser()
    val obj = parser.parse(line)
    obj.fts should have size (2)

    val ft: FT = obj.fts.get(0)
    ft.`type` should be (FTType.CHAIN)
    ft.ftId should be ("PRO_0000410512")
    ft.ft_text should be ("Putative transcription factor 001R")
    ft.location_start should be ("1")
    ft.location_end should be ("256")

    val ft2: FT = obj.fts.get(1)
    ft2.`type` should be (FTType.COMPBIAS)
    ft2.ft_text should be ("Poly-Arg")
    ft2.location_start should be ("14")
    ft2.location_end should be ("17")
  }

  test("ft with evidence"){
    val line ="""FT   REGION      237    240       Sulfate 1 binding.
                |FT   REGION      275    277       Phosphate 2 binding{EI2}.
                |FT   BINDING     103    103       Sucrose; via carbonyl oxygen{EI1}.
                |FT   BINDING     139    139       Sucrose; via carbonyl oxygen{EI1}.
                |FT   BINDING     156    156       Phosphate 1{EI2}.
                |FT   BINDING     161    161       Phosphate 1{EI2}.
                |FT   BINDING     168    168       Sucrose{EI1,EI2}.
                |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser()
    val obj = parser.parse(line)
    obj.fts should have size (7)

    val ft1: FT = obj.fts.get(0)
    obj.getEvidenceInfo.evidences.get(ft1) should be (null)

    val ft2: FT = obj.fts.get(1)
    obj.getEvidenceInfo.evidences.get(ft2) should contain ("EI2")

    val ft3: FT = obj.fts.get(2)
    obj.getEvidenceInfo.evidences.get(ft3) should contain ("EI1")

    val ft4: FT = obj.fts.get(3)
    obj.getEvidenceInfo.evidences.get(ft4) should contain ("EI1")

    val ft5: FT = obj.fts.get(6)
    obj.getEvidenceInfo.evidences.get(ft5) should (contain ("EI1") and contain ("EI2"))
  }


  test("ft with with dot in the values."){
    val line ="""FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host
                |FT                                (Potential).
                |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser()
    val obj = parser.parse(line)
    obj.fts should have size (1)
    val ft: FT = obj.fts.get(0)
    ft.ft_text should be ("N-linked (GlcNAc...); by host (Potential)")
  }

  test("ft with ? as it is location"){
    val line="""FT   CHAIN         1    194       13S globulin basic chain.
               |FT                                /FTId=PRO_0000152877.
               |FT   DISULFID      7      ?       Interchain (between basic and acidic
               |FT                                chains) (Potential).
               |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser()
    val obj = parser.parse(line)
    obj.fts should have size (2)
    val ft: FT = obj.fts.get(1)
    ft.ft_text should be ("Interchain (between basic and acidic chains) (Potential)")
    ft.location_start should be ("7")
    ft.location_end should be ("?")
  }

  test ("ft with dot at an end of a word like Ref. "){
    val line="""FT   CONFLICT     32     32       D -> N (in Ref. 4; AAB70857).
               |FT   CONFLICT     72     72       A -> S (in Ref. 3 and 4).
               |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser()
    val obj = parser.parse(line)
    obj.fts should have size (2)
    val ft: FT = obj.fts.get(0)
    ft.ft_text should be ("D -> N (in Ref. 4; AAB70857)")

    val ft1: FT = obj.fts.get(1)
    ft1.ft_text should be ("A -> S (in Ref. 3 and 4)")
  }

  test ("ft can without text but with ftid"){
    val line="""FT   PROPEP      163    164
               |FT                                /FTId=PRO_0000032092.
               |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createFtLineParser()
    val obj = parser.parse(line)
    obj.fts should have size (1)
    val ft: FT = obj.fts.get(0)
    ft.location_start should be ("163")
    ft.location_end should be ("164")
    ft.ftId should be ("PRO_0000032092")
  }

}
