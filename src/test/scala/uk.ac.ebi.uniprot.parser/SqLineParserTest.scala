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
class SqLineParserTest extends FunSuite {

  test("A valid SQ Line blocks") {

    val sqLine = """SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;
                   |     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS
                   |     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD
                   |     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL
                   |     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD
                   |     SFRKIYTDLG WKFTPL
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSqLineParser();
    val obj = parser.parse(sqLine)

    obj should not be null;
    expectResult(256) {obj.length};
    expectResult(29735) {obj.weight};

    expectResult("B4840739BF7D4121") {obj.crc64};
    expectResult(256) {obj.sequence.length};
    assert (obj.sequence.startsWith("MAFSAEDVLK"))
    assert (obj.sequence.endsWith("WKFTPL"))
  }

  test("A one line valid SQ Line blocks") {

    val sqLine = """SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;
                   |     MAFSAEDVLK WKFTPL
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSqLineParser();
    val obj = parser.parse(sqLine)

    obj should not be null;
    expectResult(256) {obj.length};
    expectResult(29735) {obj.weight};

    expectResult("B4840739BF7D4121") {obj.crc64};
    expectResult(16) {obj.sequence.length};

    assert (obj.sequence.startsWith("MAFSAEDVLK"))
    assert (obj.sequence.endsWith("WKFTPL"))
  }


  test("SQ line 1"){
    val sqLine = """SQ   SEQUENCE   128 AA;  13707 MW;  9DD5475975A4405F CRC64;
                   |     MRSIKPLRCC NAHGRHVSQE YGRCTLLLFR EKLFLQTGLV CNKQCNAPNN DGAESKHHGI
                   |     HHGSRGALAL RGAGVHLLAS AALGPRVLAG LVPTGRSVQG SVGQCGRVAQ IGRARDVAAR
                   |     KQESYCEK
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSqLineParser();
    val obj = parser.parse(sqLine)
    assert (obj.sequence.endsWith("KQESYCEK"))
  }

  test("SQ line 2"){
    val sqLine = """SQ   SEQUENCE   128 AA;  13707 MW;  9DD5475975A4405F CRC64;
                   |     MRSIKPLRCC NAHGRHVSQE YGRCTLLLFR EKLFLQTGLV CNKQCNAPNN DGAESKHHGI
                   |     HHGSRGALAL RGAGVHLLAS AALGPRVLAG LVPTGRSVQG SVGQCGRVAQ IGRARDVAAR
                   |     HHGSRGALAL D
                   |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createSqLineParser();
    val obj = parser.parse(sqLine)
    assert (obj.sequence.endsWith("HHGSRGALALD"))
  }

}
