
package uk.ac.ebi.uniprot.parser

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

import org.scalatest.matchers.ShouldMatchers._
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject.ReferenceObject

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class EntryParserTest extends FunSuite {

  test("a valid full entry test ") {

    val e = """ID   001R_FRG3G              Reviewed;         256 AA.
              |AC   Q6GZX4;
              |DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.
              |DT   19-JUL-2004, sequence version 1.
              |DT   18-APR-2012, entry version 24.
              |DE   RecName: Full=Putative transcription factor 001R;
              |GN   ORFNames=FV3-001R;
              |OS   Frog virus 3 (isolate Goorha) (FV-3).
              |OC   Viruses; dsDNA viruses, no RNA stage; Iridoviridae; Ranavirus.
              |OX   NCBI_TaxID=654924;
              |OH   NCBI_TaxID=8295; Ambystoma (mole salamanders).
              |OH   NCBI_TaxID=30343; Hyla versicolor (chameleon treefrog).
              |OH   NCBI_TaxID=8316; Notophthalmus viridescens (Eastern newt) (Triturus viridescens).
              |OH   NCBI_TaxID=8404; Rana pipiens (Northern leopard frog).
              |OH   NCBI_TaxID=45438; Rana sylvatica (Wood frog).
              |RN   [1]
              |RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].
              |RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;
              |RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;
              |RT   "Comparative genomic analyses of frog virus 3, type species of the
              |RT   genus Ranavirus (family Iridoviridae).";
              |RL   Virology 323:70-84(2004).
              |DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
              |DR   RefSeq; YP_031579.1; NC_005946.1.
              |DR   ProteinModelPortal; Q6GZX4; -.
              |DR   GeneID; 2947773; -.
              |DR   ProtClustDB; CLSP2511514; -.
              |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
              |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
              |DR   GO; GO:0006351; P:transcription, DNA-dependent; IEA:UniProtKB-KW.
              |DR   InterPro; IPR007031; Poxvirus_VLTF3.
              |DR   Pfam; PF04947; Pox_VLTF3; 1.
              |PE   4: Predicted;
              |KW   Activator; Complete proteome; Reference proteome; Transcription;
              |KW   Transcription regulation.
              |FT   CHAIN         1    256       Putative transcription factor 001R.
              |FT                                /FTId=PRO_0000410512.
              |FT   COMPBIAS     14     17       Poly-Arg.
              |SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;
              |     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS
              |     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD
              |     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL
              |     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD
              |     SFRKIYTDLG WKFTPL
              |//
              |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createEntryParser();
    val obj = parser.parse(e)
    obj.ac should not be (null)
    obj.ac.primaryAcc should be ("Q6GZX4")
    obj.ac.secondaryAcc should be ('empty)

    obj.id should not be (null)
    obj.id should have ('reviewed (true), 'entryName ("001R_FRG3G"), 'sequenceLength (256) )

    obj.ref should have size (1)
    val refo: ReferenceObject = obj.ref.get(0)
    refo.ra.authors should have size (4)
    refo.rc should be (null)
    refo.rg should be (null)
    refo.rn.number should be (1)
  }

}
