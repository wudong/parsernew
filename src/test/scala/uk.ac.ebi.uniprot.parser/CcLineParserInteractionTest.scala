package uk.ac.ebi.uniprot.parser

import scala.collection.JavaConverters._

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject._


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class CcLineParserInteractionTest extends FunSuite {


  test ("interaction 1"){
    val lines = """CC   -!- INTERACTION:
                  |CC       P11450:fcp3c; NbExp=1; IntAct=EBI-126914, EBI-159556;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-126914")
    unit.secondId should be ("EBI-159556")
    unit.gene should be ("fcp3c")
    unit.spAc should be ("P11450")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }

  test ("interaction 2"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-133844")
    unit.secondId should be ("EBI-212772")
    unit.gene should be ("CG11299")
    unit.spAc should be ("Q9W1K5-1")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }


  test ("interaction"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-133844")
    unit.secondId should be ("EBI-212772")
    unit.gene should be ("CG11299")
    unit.spAc should be ("Q9W1K5-1")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }

  test ("interaction 3"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q8NI08:-; NbExp=1; IntAct=EBI-80809, EBI-80799;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-80809")
    unit.secondId should be ("EBI-80799")
    unit.gene should be ("-")
    unit.spAc should be ("Q8NI08")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }
  test ("interaction 4"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-123485")
    unit.secondId should be ("EBI-123485")
    unit.gene should be (null)
    unit.spAc should be (null)
    unit.isSelf should be (true)
    unit.xeno should be (false)
    unit.nbexp should be (1)
  }
  test ("interaction 5"){
    val lines = """CC   -!- INTERACTION:
                  |CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (1)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-394562")
    unit.secondId should be ("EBI-398761")
    unit.gene should be ("2410018M14Rik")
    unit.spAc should be ("Q8C1S0")
    unit.isSelf should be (false)
    unit.xeno should be (true)
    unit.nbexp should be (1)
  }

  test ("two interactions case"){
    val lines = """CC   -!- INTERACTION:
                  |CC       P51617:IRAK1; NbExp=1; IntAct=EBI-448466, EBI-358664;
                  |CC       P51617:IRAK1; NbExp=1; IntAct=EBI-448472, EBI-358664;
                  |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)
    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[Interaction]
    val ir = cc1.`object`.asInstanceOf[Interaction]

    ir.interactions should have size (2)
    val unit: InteractionObject = ir.interactions.get(0)
    unit.firstId should be ("EBI-448466")
    unit.secondId should be ("EBI-358664")
    unit.gene should be ("IRAK1")
    unit.spAc should be ("P51617")
    unit.isSelf should be (false)
    unit.xeno should be (false)
    unit.nbexp should be (1)

    val unit2: InteractionObject = ir.interactions.get(1)
    unit2.firstId should be ("EBI-448472")
    unit2.secondId should be ("EBI-358664")
    unit2.gene should be ("IRAK1")
    unit2.spAc should be ("P51617")
    unit2.isSelf should be (false)
    unit2.xeno should be (false)
    unit2.nbexp should be (1)
  }

  test("CC interaction with dot in acc"){
    val lines =
      """CC   -!- INTERACTION:
      |CC       G5EC23:hcf-1; NbExp=2; IntAct=EBI-318108, EBI-4480523;
      |CC       Q11184:let-756; NbExp=3; IntAct=EBI-318108, EBI-3843983;
      |CC       Q10666:pop-1; NbExp=2; IntAct=EBI-318108, EBI-317870;
      |CC       Q21921:sir-2.1; NbExp=3; IntAct=EBI-318108, EBI-966082;
      |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.topic should be (CCTopicEnum.INTERACTION)
    val ic = cc2.`object`.asInstanceOf[Interaction]
    ic.interactions should have size (4)

    val genes = ic.interactions.asScala.map( x=> x.gene )

    genes should contain ("hcf-1")
    genes should contain ("let-756")
    genes should contain ("pop-1")
    genes should contain ("sir-2.1")
  }

  test("CC interaction has () in genes."){
    val lines =
      """CC   -!- INTERACTION:
        |CC       Q9W4W2:fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;
        |CC       Q9VKM1:piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;
        |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.topic should be (CCTopicEnum.INTERACTION)
    val ic = cc2.`object`.asInstanceOf[Interaction]
    ic.interactions should have size (2)

    val genes = ic.interactions.asScala.map( x=> x.gene )

    genes should contain ("fs(1)Yb")
    genes should contain ("piwi")
  }

  test("CC interaction has / in genes."){
    val lines =
      """CC   -!- INTERACTION:
        |CC       Q67XQ1:At1g03430; NbExp=2; IntAct=EBI-1100967, EBI-1100725;
        |CC       Q9C5A5:At5g08720/T2K12_70; NbExp=3; IntAct=EBI-1100967, EBI-1998000;
        |CC       Q9SSW0:AZF3; NbExp=2; IntAct=EBI-1100967, EBI-1807790;
        |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.topic should be (CCTopicEnum.INTERACTION)
    val ic = cc2.`object`.asInstanceOf[Interaction]
    ic.interactions should have size (3)

    val genes = ic.interactions.asScala.map( x=> x.gene )

    genes should contain ("At5g08720/T2K12_70")
  }

  test("CC interaction has : in genes."){
    val lines =
      """CC   -!- INTERACTION:
        |CC       Q9V3G9:EG:BACR37P7.5; NbExp=1; IntAct=EBI-175067, EBI-162998;
        |""".stripMargin.replace("\r", "")
    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)
    val cc2 = obj.ccs.get(0)

    cc2.topic should be (CCTopicEnum.INTERACTION)
    val ic = cc2.`object`.asInstanceOf[Interaction]
    ic.interactions should have size (1)

    val genes = ic.interactions.asScala.map( x=> x.gene )

    genes should contain ("EG:BACR37P7.5")
  }



}
