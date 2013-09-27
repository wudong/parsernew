package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject.{NameBlock, FlagType, Name}

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

    obj.flags should contain (FlagType.Precursor)

    obj.altName should have size (7)

    expectResult(("Annexin V", 0, 0)) {
      val name: Name = obj.altName.get(0)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Lipocortin V", 0, 0)) {
      val name: Name = obj.altName.get(1)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Placental anticoagulant protein I", "PAP-I", 1, 0)) {
      val name: Name = obj.altName.get(2)
      (name.fullName,
        name.shortNames.get(0),
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Anchorin CII", 0, 0)) {
      val name: Name = obj.altName.get(6)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

  }

  test("A Good DE With Evidence") {
    val deLines = """DE   RecName: Full=Annexin A5{EI1};
                    |DE            Short=Annexin-5{EI1,EI2};
                    |DE   AltName: Full=Annexin V{EI1};
                    |DE   AltName: Full=Lipocortin V{EI1};
                    |DE   AltName: Full=Placental anticoagulant protein I{EI1};
                    |DE            Short=PAP-I{EI1};
                    |DE   AltName: Full=PP4{EI1};
                    |DE   AltName: Full=Thromboplastin inhibitor{EI1};
                    |DE   AltName: Full=Vascular anticoagulant-alpha{EI1};
                    |DE            Short=VAC-alpha{EI1};
                    |DE   AltName: Full=Anchorin CII{EI1};
                    |DE   Flags: Precursor{EI1,EI2,EI3};
                    |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createDeLineParser();
    val obj = parser.parse(deLines)

    obj should not be null;

    obj.recName should not be null;
    obj.recName.fullName should equal("Annexin A5")

    obj.getEvidenceInfo.evidences.get("Annexin A5") should not be null
    obj.getEvidenceInfo.evidences.get("Annexin A5") should have size 1
    obj.getEvidenceInfo.evidences.get("Annexin A5") should contain ("EI1")

    obj.recName.shortNames should have size (1)
    obj.recName.shortNames should contain("Annexin-5")


    obj.getEvidenceInfo.evidences.get("Annexin-5") should not be null
    obj.getEvidenceInfo.evidences.get("Annexin-5") should have size 2
    obj.getEvidenceInfo.evidences.get("Annexin-5") should contain ("EI1")
    obj.getEvidenceInfo.evidences.get("Annexin-5") should contain ("EI2")

    obj.recName.ecs should be('empty)

    obj.flags should contain(FlagType.Precursor)
    obj.getEvidenceInfo.evidences.get(FlagType.Precursor) should not be null
    obj.getEvidenceInfo.evidences.get(FlagType.Precursor) should have size 3
    obj.getEvidenceInfo.evidences.get(FlagType.Precursor) should contain ("EI1")
    obj.getEvidenceInfo.evidences.get(FlagType.Precursor) should contain ("EI2")
    obj.getEvidenceInfo.evidences.get(FlagType.Precursor) should contain ("EI3")

    obj.altName should have size (7)

    expectResult(("Annexin V", 0, 0)) {
      val name: Name = obj.altName.get(0)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Lipocortin V", 0, 0)) {
      val name: Name = obj.altName.get(1)
      (name.fullName,
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Placental anticoagulant protein I", "PAP-I", 1, 0)) {
      val name: Name = obj.altName.get(2)
      (name.fullName,
        name.shortNames.get(0),
        name.shortNames.size(),
        name.ecs.size())
    }

    expectResult(("Anchorin CII", 0, 0)) {
      val name: Name = obj.altName.get(6)
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

    obj.altName should have size (3)

    expectResult((null, 1, 2, "PAP-I", "1.1.1.1", "1.1.1.2")) {
      val altNname1 = obj.altName.get(0)
      (altNname1.fullName,
        altNname1.shortNames.size(),
        altNname1.ecs.size(),
        altNname1.shortNames.get(0),
        altNname1.ecs.get(0),
        altNname1.ecs.get(1)
        )
    }

    expectResult((null, 0, 1, "1.1.1.1")) {
      val altNname1 = obj.altName.get(1)
      (altNname1.fullName,
        altNname1.shortNames.size(),
        altNname1.ecs.size(),
        altNname1.ecs.get(0)
        )
    }

    expectResult((null, 3, 0, "PAP-I", "PAP.1", "PAP.2")) {
      val altNname1 = obj.altName.get(2)
      (altNname1.fullName,
        altNname1.shortNames.size(),
        altNname1.ecs.size(),
        altNname1.shortNames.get(0),
        altNname1.shortNames.get(1),
        altNname1.shortNames.get(2)
        )
    }

  }

  test("DE with include and contains") {
    val deLines = """DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;
                    |DE   Includes:
                    |DE     RecName: Full=Glutamate N-acetyltransferase;
                    |DE              EC=2.3.1.35;
                    |DE     AltName: Full=Ornithine acetyltransferase;
                    |DE              Short=OATase;
                    |DE     AltName: Full=Ornithine transacetylase;
                    |DE   Includes:
                    |DE     RecName: Full=Amino-acid acetyltransferase;
                    |DE              EC=2.3.1.-;
                    |DE     AltName: Full=N-acetylglutamate synthase;
                    |DE              Short=AGS;
                    |DE   Contains:
                    |DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;
                    |DE   Contains:
                    |DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain;
                    |""".stripMargin.replace("\r", "");

    val obj = (new DefaultUniprotLineParserFactory).createDeLineParser().parse(deLines)
    obj.recName should not be (null)
    obj.recName.fullName should equal("Arginine biosynthesis bifunctional protein argJ")
    obj.recName.ecs should be ('empty)
    obj.recName.shortNames should be ('empty)
    obj.subName should be('empty)
    obj.altName should be('empty)
    obj.alt_Allergen should be (null)
    obj.alt_Biotech should be (null)
    obj.alt_CD_antigen should be ('empty)
    obj.alt_INN should be ('empty)

    obj.includedNames should have size (2)
    val block1: NameBlock = obj.includedNames.get(0)
    block1.recName.fullName should equal("Glutamate N-acetyltransferase")
    block1.recName.ecs should have size (1)
    block1.recName.ecs.get(0) should equal("2.3.1.35")
    block1.recName.shortNames should be('empty)

    block1.altName should have size (2)
    block1.altName.get(0).fullName should equal("Ornithine acetyltransferase")
    block1.altName.get(0).shortNames should have size (1)
    block1.altName.get(0).shortNames.get(0) should equal("OATase")
    block1.altName.get(0).ecs should be('empty)

    block1.altName.get(1).fullName should equal("Ornithine transacetylase")
    block1.altName.get(1).ecs should be('empty)
    block1.altName.get(1).shortNames should be('empty)

    val block2: NameBlock = obj.includedNames.get(1)
    block2.recName.fullName should equal("Amino-acid acetyltransferase")
    block2.recName.ecs should have size (1)
    block2.recName.ecs.get(0) should equal("2.3.1.-")
    block2.recName.shortNames should be('empty)

    block2.altName should have size (1)
    block2.altName.get(0).fullName should equal("N-acetylglutamate synthase")
    block2.altName.get(0).shortNames should have size (1)
    block2.altName.get(0).shortNames.get(0) should equal("AGS")
    block2.altName.get(0).ecs should be('empty)

    obj.containedNames should have size (2)
    val block3: NameBlock = obj.containedNames.get(0)
    block3.recName.fullName should equal("Arginine biosynthesis bifunctional protein argJ alpha chain")
    block3.recName.ecs should be('empty)
    block3.recName.shortNames should be('empty)
    block3.altName should be('empty)
    block3.subName should be('empty)

    val block4: NameBlock = obj.containedNames.get(1)
    block4.recName.fullName should equal("Arginine biosynthesis bifunctional protein argJ beta chain")
    block4.recName.ecs should be('empty)
    block4.recName.shortNames should be('empty)
    block4.altName should be('empty)
    block4.subName should be('empty)
  }


  test("flag precursor fragments"){
    val deLines = """DE   RecName: Full=UI;
                    |DE   Contains:
                    |DE     RecName: Full=Urophysin;
                    |DE   Contains:
                    |DE     RecName: Full=Urotensin-1;
                    |DE     AltName: Full=Urotensin I;
                    |DE   Flags: Precursor; Fragments;
                    |""".stripMargin.replace("\r", "");

    val obj = (new DefaultUniprotLineParserFactory).createDeLineParser().parse(deLines)
    obj.flags should contain (FlagType.Precursor)
    obj.flags should contain (FlagType.Fragments)
  }

  test ("altname Allergen"){
    val deLines = """DE   RecName: Full=13S globulin seed storage protein 3;
                    |DE   AltName: Full=Legumin-like protein 3;
                    |DE   AltName: Allergen=Fag e 1;
                    |""".stripMargin.replace("\r", "");

    val obj = (new DefaultUniprotLineParserFactory).createDeLineParser().parse(deLines)
    obj.alt_Allergen should be ("Fag e 1")
  }
}
