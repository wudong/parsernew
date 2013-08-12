package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import uk.ac.ebi.uniprot.parser.impl.id.{IDObjectParser, IdLineObject}
import uk.ac.ebi.uniprot.parser.impl.ac.AcObjectParser

import org.scalatest.matchers.ShouldMatchers._

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class AcLineParserTest extends FunSuite  {

  val ac_one_line ="AC   Q6GZX4;\n"

  test("a valid one line one primary acc ") {

    val parser = new AcObjectParser;
    val obj = parser.parse(ac_one_line)

    expectResult("Q6GZX4"){
      obj.primaryAcc
    }

    obj.secondaryAcc should be ('empty)
  }

  val ac_one_line_moreacc ="AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"

  test("a valid one line with more than one secondary acc ") {

    val parser = new AcObjectParser;
    val obj = parser.parse(ac_one_line_moreacc)

    expectResult("Q6GZX4"){
      obj.primaryAcc
    }

    obj.secondaryAcc should have size (2);
    obj.secondaryAcc should contain ("Q6GZX5");
    obj.secondaryAcc should contain ("Q6GZX6");
  }

  val ac_three_lineer_moreacc =
    """AC   Q6GZX4; Q6GZX5; Q6GZX6;
      |AC   Q6GZX7; Q6GZX8; Q6GZX9;
      |AC   Q6GZX0;
      |""".stripMargin.replace("\r","");

  test("a valid three lineer with more than one secondary acc ") {

    val parser = new AcObjectParser;
    val obj = parser.parse(ac_three_lineer_moreacc)

    expectResult("Q6GZX4"){
      obj.primaryAcc
    }

    obj.secondaryAcc should have size (6);
    obj.secondaryAcc should contain ("Q6GZX5");
    obj.secondaryAcc should contain ("Q6GZX6");
    obj.secondaryAcc should contain ("Q6GZX7");
    obj.secondaryAcc should contain ("Q6GZX8");
    obj.secondaryAcc should contain ("Q6GZX9");
    obj.secondaryAcc should contain ("Q6GZX0");

  }


}
