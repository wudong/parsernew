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
class CcLineParserBiophycheTest extends FunSuite {

  test ("biophysiocalproperties 2"){
   val lines=  """CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                 |CC       Kinetic parameters:
                 |CC         KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees
                 |CC         Celsius and at pH 7.6);
                 |CC         Vmax=1.9 mmol/min/mg enzyme;
                 |CC       pH dependence:
                 |CC         Optimum pH is 7.75;
                 |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[BiophysicochemicalProperties]
    val bp = cc1.`object`.asInstanceOf[BiophysicochemicalProperties]

    bp.kms should have size (1)
    bp.kms.get(0) should be ("1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees Celsius and at pH 7.6)")

    bp.vmaxs should have size (1)
    bp.vmaxs.get(0) should be ("1.9 mmol/min/mg enzyme")
    bp.ph_dependence should be ("Optimum pH is 7.75")
  }

  test ("biophysiocalproperties 1"){
    val lines= """CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                 |CC       Kinetic parameters:
                 |CC         KM=71 uM for ATP;
                 |CC         KM=98 uM for ADP;
                 |CC         KM=1.5 mM for acetate;
                 |CC         KM=0.47 mM for acetyl phosphate;
                 |CC       Temperature dependence:
                 |CC         Optimum temperature is 65 degrees Celsius. Protected from
                 |CC         thermal inactivation by ATP;
                 |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[BiophysicochemicalProperties]
    val bp = cc1.`object`.asInstanceOf[BiophysicochemicalProperties]

    bp.bsorption_abs should be (0)
    bp.bsorption_note should be (null);

    bp.kms should have size (4)
    bp.kms should contain ("71 uM for ATP")
    bp.kms should contain ("98 uM for ADP")
    bp.kms should contain ("1.5 mM for acetate")
    bp.kms should contain ("0.47 mM for acetyl phosphate")

    bp.vmaxs should have size (0)

    bp.kp_note should be (null)
    bp.ph_dependence should be (null)
    bp.temperature_dependence should be ("Optimum temperature is 65 degrees Celsius. Protected from " +
      "thermal inactivation by ATP")
  }

  test ("biophysiocalproperties 3"){
    val lines=  """CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                  |CC       Absorption:
                  |CC         Abs(max)=578 nm;
                  |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[BiophysicochemicalProperties]
    val bp = cc1.`object`.asInstanceOf[BiophysicochemicalProperties]

    bp.bsorption_abs should be (578)
    bp.bsorption_note should be (null)
  }

  test ("biophysiocalproperties 4"){
    val lines=  """CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                  |CC       Absorption:
                  |CC         Abs(max)=~596 nm;
                  |CC         Note=In the presence of anions, the maximum absorption shifts to
                  |CC         about 575 nm;
                  |""".stripMargin.replace("\r", "");

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(lines)

    obj.ccs should have size (1)

    val cc1 = obj.ccs.get(0)
    cc1.`object`.isInstanceOf[BiophysicochemicalProperties]
    val bp = cc1.`object`.asInstanceOf[BiophysicochemicalProperties]

    bp.bsorption_abs should be (596)
    bp.bsorption_abs_approximate should be (true)
  }
}
