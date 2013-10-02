package uk.ac.ebi.uniprot.parser


import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class CcLineParserDiseaseTest extends FunSuite {

  test ("disease's abbr has more than one word"){
    val diseaseLine =
      """CC   -!- DISEASE: Acyl-CoA dehydrogenase family, member 9, deficiency
      |CC       (ACAD9 deficiency) [MIM:611126]: A metabolic disorder with
      |CC       variable manifestations that include dilated cardiomyopathy, liver
      |CC       failure, muscle weakness, neurologic dysfunction, hypoglycemia and
      |CC       Reye-like episodes (brain edema and vomiting that may rapidly
      |CC       progress to seizures, coma and death). Note=The disease is caused
      |CC       by mutations affecting the gene represented in this entry.
      |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(diseaseLine)

    obj.ccs should have size (1)
    val cc = obj.ccs.get(0)
    val disease = cc.`object`.asInstanceOf[CcLineObject.Disease]
    disease.abbr should be ("ACAD9 deficiency")
    disease.mim should be ("611126")
    disease.name should be ("Acyl-CoA dehydrogenase family, member 9, deficiency")
    // disease.descriptions should have size (1)
    // disease.notes should have size (1)
  }

  test ("disease's abbr has more than one word"){
    val diseaseLine =
      """CC   -!- DISEASE: Severe combined immunodeficiency autosomal recessive T-
        |CC       cell-negative/B-cell-positive/NK-cell-positive (T(-)B(+)NK(+)
        |CC       SCID) [MIM:608971]: A form of severe.
        |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createCcLineParser();
    val obj = parser.parse(diseaseLine)

    obj.ccs should have size (1)
    val cc = obj.ccs.get(0)
    val disease = cc.`object`.asInstanceOf[CcLineObject.Disease]
    disease.abbr should be ("ACAD9 deficiency")
    disease.mim should be ("611126")
    disease.name should be ("Acyl-CoA dehydrogenase family, member 9, deficiency")
    // disease.descriptions should have size (1)
    // disease.notes should have size (1)
  }

}
