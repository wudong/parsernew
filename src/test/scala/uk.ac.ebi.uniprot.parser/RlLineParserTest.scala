package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.rl.RlLineObject


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RlLineParserTest extends FunSuite {

  test("A valid RL Journal Article ") {
    val line = "RL   J. Mol. Biol. 168:321-331(1983).\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.JournalArticle])

    val journal = obj.reference.asInstanceOf[RlLineObject.JournalArticle]

    expectResult(("J. Mol. Biol.", 1983, "168", "321", "331")) {
      (journal.journal, journal.year, journal.volume, journal.first_page, journal.last_page);
    }
  }


  test("A valid RL Journal Article 2") {
    val line = "RL   Int. J. Parasitol. 0:0-0(2005).\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.JournalArticle])

    val journal = obj.reference.asInstanceOf[RlLineObject.JournalArticle]

    expectResult(("Int. J. Parasitol.", 2005, "0", "0", "0")) {
      (journal.journal, journal.year, journal.volume, journal.first_page, journal.last_page);
    }
  }

  test("A valid RL epub") {
    val line = "RL   (er) Plant Gene Register PGR98-023.\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.EPub])

    val epub = obj.reference.asInstanceOf[RlLineObject.EPub]

    expectResult("Plant Gene Register PGR98-023") {
      epub.title;
    }
  }

  test("A valid RL submission") {
    val line = "RL   Submitted (OCT-1995) to the EMBL/GenBank/DDBJ databases.\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.Submission])

    val sub = obj.reference.asInstanceOf[RlLineObject.Submission]

    expectResult(("EMBL", 1995, "OCT")) {
      (sub.db.name(), sub.year, sub.month)
    }
  }

  test("A valid RL pattrn") {
    val line = "RL   Patent number WO9010703, 20-SEP-1990.\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.Patent])

    val pat = obj.reference.asInstanceOf[RlLineObject.Patent]

    expectResult(("WO9010703", 1990, "SEP", 20)) {
      (pat.patentNumber, pat.year, pat.month, pat.day)
    }
  }

  test("A valid RL thesis") {
    val line = "RL   Thesis (1977), University of Geneva, Switzerland.\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.Thesis])

    val pat = obj.reference.asInstanceOf[RlLineObject.Thesis]

    expectResult(("University of Geneva", "Switzerland", 1977)) {
      (pat.institute, pat.country, pat.year)
    }
  }

  test("A valid RL unpublished") {
    val line = "RL   Unpublished observations (OCT-1978).\n";

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.Unpublished])

    val pat = obj.reference.asInstanceOf[RlLineObject.Unpublished]

    expectResult(("OCT", 1978)) {
      (pat.month, pat.year)
    }
  }


  test("A valid RL book 1") {
    val line = """RL   (In) Boyer P.D. (eds.);
                 |RL   The enzymes (3rd ed.), pp.11:397-547, Academic Press, New York (1975).
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.Book])

    val book = obj.reference.asInstanceOf[RlLineObject.Book]
    book.editors should have size (1)
    book.editors should contain("Boyer P.D.")

    expectResult(("The enzymes (3rd ed.)", "11", "397", "547", "Academic Press", "New York", 1975)) {
      (book.title, book.volume, book.page_start, book.page_end, book.press, book.place, book.year)
    }
  }

  test("A valid RL book 2") {
    val line = """RL   (In) Rich D.H., Gross E. (eds.);
                 |RL   Proceedings of the 7th American peptide symposium, pp.69-72, Pierce
                 |RL   Chemical Co., Rockford Il. (1981).
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)

    assert(obj.reference.isInstanceOf[RlLineObject.Book])

    val book = obj.reference.asInstanceOf[RlLineObject.Book]
    book.editors should have size (2)
    book.editors should contain("Rich D.H.")
    book.editors should contain("Gross E.")

    expectResult(("Proceedings of the 7th American peptide symposium", null, "69", "72", "Pierce Chemical Co.", "Rockford Il.", 1981)) {
      (book.title, book.volume, book.page_start, book.page_end, book.press, book.place, book.year)
    }
  }

  test ("journal name can contain dash"){
    val line =
      """RL   Hoppe-Seyler's Z. Physiol. Chem. 362:1665-1669(1981).
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)
    assert(obj.reference.isInstanceOf[RlLineObject.JournalArticle])
    val journal = obj.reference.asInstanceOf[RlLineObject.JournalArticle]

    journal.journal should be ("Hoppe-Seyler's Z. Physiol. Chem.")
  }

  test ("journal name can contain dash 2"){
    val line =
      """RL   Abstr. - Soc. Neurosci. 25:168-168(1999).
        |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createRlLineParser()
    val obj = parser.parse(line)
    assert(obj.reference.isInstanceOf[RlLineObject.JournalArticle])
    val journal = obj.reference.asInstanceOf[RlLineObject.JournalArticle]

    journal.journal should be ("Abstr. - Soc. Neurosci.")
  }

}
