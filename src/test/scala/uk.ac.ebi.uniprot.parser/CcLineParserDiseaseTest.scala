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

    //TODO

  }

}
