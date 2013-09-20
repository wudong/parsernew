
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


  ignore ("an entry with evidence"){
    val line =
    """ID   C5W346_ECOBD            Unreviewed;       170 AA.
      |AC   C5W346; C6VHH0;
      |DT   01-SEP-2009, integrated into UniProtKB/TrEMBL.
      |DT   01-SEP-2009, sequence version 1.
      |DT   21-SEP-2011, entry version 17.
      |DE   SubName: Full=SecA regulator SecM{EI3};
      |DE   SubName: Full=Secretion monitor that regulates SecA translation (General Secretory Pathway){EI4};
      |GN   Name=yacA{EI3}; OrderedLocusNames=B21_00097{EI2}, ECD_00098{EI2};
      |OS   Escherichia coli (strain B / BL21-DE3).
      |OC   Bacteria; Proteobacteria; Gammaproteobacteria; Enterobacteriales;
      |OC   Enterobacteriaceae; Escherichia.
      |OX   NCBI_TaxID=469008{EI2};
      |RN   [1]{EI2,EI3}
      |RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].
      |RC   STRAIN=B / BL21-DE3 [Korea]{EI2};
      |RX   PubMed=19786035; DOI=10.1016/j.jmb.2009.09.052;
      |RA   Jeong H., Barbe V., Lee C.H., Vallenet D., Yu D.S., Choi S.H.,
      |RA   Couloux A., Lee S.W., Yoon S.H., Cattolico L., Hur C.G., Park H.S.,
      |RA   Segurens B., Kim S.C., Oh T.K., Lenski R.E., Studier F.W.,
      |RA   Daegelen P., Kim J.F.;
      |RT   "Genome sequences of Escherichia coli B strains REL606 and
      |RT   BL21(DE3).";
      |RL   J. Mol. Biol. 394:644-652(2009).
      |RN   [2]{EI2}
      |RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].
      |RC   STRAIN=B / BL21-DE3 [Austria]{EI2};
      |RA   Leparc G., Striedner G., Bayer K., Kreil D., Krempl P.M.;
      |RT   "Sequencing and gene expression analysis of Escherichia coli BL21.";
      |RL   Submitted (JUN-2009) to the EMBL/GenBank/DDBJ databases.
      |DR   EMBL; CP001509; ACT41999.1; -; Genomic_DNA.{EI3}
      |DR   EMBL; AM946981; CAQ30614.1; -; Genomic_DNA.{EI4}
      |DR   ProteinModelPortal; C5W346; -.
      |DR   STRING; C6VHH0; -.
      |DR   EnsemblBacteria; EBESCT00000158229; EBESCP00000149220; EBESCG00000156299.
      |DR   EnsemblBacteria; EBESCT00000188594; EBESCP00000176258; EBESCG00000186912.
      |DR   GenomeReviews; AM946981_GR; B21_00097.
      |DR   GenomeReviews; CP001509_GR; ECD_00098.
      |DR   GeneTree; EBGT00050000010756; -.
      |DR   OMA; FAMAPQA; -.
      |DR   GO; GO:0005829; C:cytosol; IEA:HAMAP.
      |DR   GO; GO:0045182; F:translation regulator activity; IEA:InterPro.
      |DR   HAMAP; MF_01332; SecM; 1; -.
      |DR   InterPro; IPR009502; SecM.
      |DR   Pfam; PF06558; SecM; 1.
      |DR   PIRSF; PIRSF004572; SecM; 1.
      |DR   ProDom; PD089227; SecM; 1.
      |PE   3: Inferred from homology;
      |KW   Complete proteome{EI2}.
      |**
      |**   #################    INTERNAL SECTION    ##################
      |**EV EI2; ProtImp; -; -; 11-SEP-2009.
      |**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
      |**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.
      |SQ   SEQUENCE   170 AA;  18880 MW;  5F5B3001056CAD22 CRC64;
      |     MSGILTRWRQ FGKRYFWPHL LLGMVAASLG LPALSNAAEP NAPAKATTRN HEPSAKVNFG
      |     QLALLEANTR RPNSNYSVDY WHQHAIRTVI RHLSFAMAPQ TLPVAEESLP LQAQHLALLD
      |     TLSALLTQEG TPSEKGYRID YAHFTPQAKF STPVWISQAQ GIRAGPQRLT
      |//
      |""".stripMargin.replace("\r", "")

    val parser = (new DefaultUniprotLineParserFactory).createEntryParser();
    val obj = parser.parse(line)
  }

}
