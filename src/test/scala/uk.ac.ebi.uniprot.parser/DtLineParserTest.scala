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
class DtLineParserTest extends FunSuite {

  val dtLine = """DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.
                 |DT   19-JUL-2004, sequence version 1.
                 |DT   18-APR-2012, entry version 24.
                 |""".stripMargin.replace("\r", "")

  test("A valid Swissprot DT") {
    val parser = (new DefaultUniprotLineParserFactory).createDtLineParser();
    val obj = parser.parse(dtLine)

    obj should not be null;
    expectResult(2011) {
      obj.integration_date.getYear
    }
    expectResult(6) {
      obj.integration_date.getMonthOfYear
    }
    expectResult(28) {
      obj.integration_date.getDayOfMonth
    }
    expectResult(true) {
      obj.isSiwssprot
    }

    expectResult(2004) {
      obj.seq_date.getYear
    }
    expectResult(7) {
      obj.seq_date.getMonthOfYear
    }
    expectResult(19) {
      obj.seq_date.getDayOfMonth
    }
    expectResult(1) {
      obj.seq_version
    }

    expectResult(2012) {
      obj.entry_date.getYear
    }
    expectResult(4) {
      obj.entry_date.getMonthOfYear
    }
    expectResult(18) {
      obj.entry_date.getDayOfMonth
    }
    expectResult(24) {
      obj.entry_version
    }
  }

}
