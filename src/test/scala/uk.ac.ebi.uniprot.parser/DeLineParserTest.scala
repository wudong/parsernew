package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject.{FlagType, Name}

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class DeLineParserTest extends FunSuite {

  test("A Good DE") {
    val deLines = """DE   RecName: Full=Annexin A5;
                    |DE            Short=Annexin-5;
                    |DE   AltName: Full=Annexin V;
                    |DE   AltName: Full=Lipocortin V;
                    |DE   AltName: Full=Placental anticoagulant protein I;
                    |DE            Short=PAP-I;
                    |DE   AltName: Full=PP4;
                    |DE   AltName: Full=Thromboplastin inhibitor;
                    |DE   AltName: Full=Vascular anticoagulant-alpha;
                    |DE            Short=VAC-alpha;
                    |DE   AltName: Full=Anchorin CII;
                    |DE   Flags: Precursor;
                    |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createDeLineParser();
    val obj = parser.parse(deLines)

    obj should not be null;

    obj.recName should not be null;
    obj.recName.fullName should equal("Annexin A5")

    obj.recName.shortNames should have size (1)
    obj.recName.shortNames should contain("Annexin-5")
    obj.recName.ecs should be('empty)

    obj.flag should equal(FlagType.Precursor)

    obj.altname should have size (7)

    expectResult(("Annexin V", 0, 0)) {
      val name: Name = obj.altname.get(0)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Lipocortin V", 0, 0)) {
      val name: Name = obj.altname.get(1)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Placental anticoagulant protein I", "PAP-I", 1, 0)) {
      val name: Name = obj.altname.get(2)
      (name.fullName,
        name.shortNames.get(0),
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Anchorin CII", 0, 0)) {
      val name: Name = obj.altname.get(6)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

  }

  test("Another Good DE") {
    val deLines = """DE   AltName: Short=PAP-I;
                    |DE            EC=1.1.1.1;
                    |DE            EC=1.1.1.2;
                    |DE   AltName: EC=1.1.1.1;
                    |DE   AltName: Short=PAP-I;
                    |DE            Short=PAP.1;
                    |DE            Short=PAP.2;
                    |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createDeLineParser();
    val obj = parser.parse(deLines)

    obj should not be null
    obj.recName should be(null)

    obj.altname should have size (3)

    expectResult((null, 1, 2, "PAP-I", "1.1.1.1", "1.1.1.2")) {
      val altNname1 = obj.altname.get(0)
      (altNname1.fullName,
        altNname1.shortNames.size(),
        altNname1.ecs.size(),
        altNname1.shortNames.get(0),
        altNname1.ecs.get(0),
        altNname1.ecs.get(1)
        )
    }

    expectResult((null, 0, 1, "1.1.1.1")) {
      val altNname1 = obj.altname.get(1)
      (altNname1.fullName,
        altNname1.shortNames.size(),
        altNname1.ecs.size(),
        altNname1.ecs.get(0)
        )
    }

    expectResult((null, 3, 0, "PAP-I", "PAP.1", "PAP.2")) {
      val altNname1 = obj.altname.get(2)
      (altNname1.fullName,
        altNname1.shortNames.size(),
        altNname1.ecs.size(),
        altNname1.shortNames.get(0),
        altNname1.shortNames.get(1),
        altNname1.shortNames.get(2)
        )
    }

  }

}
