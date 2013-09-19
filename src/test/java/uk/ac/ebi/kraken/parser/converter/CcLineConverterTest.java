package uk.ac.ebi.kraken.parser.converter;

import static junit.framework.TestCase.*;

import java.util.List;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.CommentStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Absorption;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsEvent;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.AlternativeProductsIsoform;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Comment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.CommentType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.DomainComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.FunctionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.Interaction;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.InteractionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.InteractionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSequenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.IsoformSynonym;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.KineticParameters;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryCommentSource;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MassSpectrometryRange;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.MichaelisConstantUnit;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.RnaEditingLocationType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.SequenceCautionType;
import uk.ac.ebi.kraken.interfaces.uniprot.comments.WebResourceComment;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.AlternativeNameSequenceEnum;

public class CcLineConverterTest {
	private final CcLineConverter converter = new CcLineConverter();
	@Test
	public void testTextOnly(){
		/*
		 * CC   -!- FUNCTION: This enzyme is necessary for target cell lysis in cell-
        CC       mediated immune responses. It cleaves after Lys or Arg. May be
        CC       involved in apoptosis.
        CC   -!- DOMAIN: The di-lysine motif may confer endoplasmic reticulum
        CC       localization (By similarity).
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.FUNCTION;
		cc1.object ="This enzyme is necessary for target cell lysis in cell-mediated immune responses."
				+ " It cleaves after Lys or Arg. May be involved in apoptosis.";
		CcLineObject.CC cc2 =new CcLineObject.CC();
		cc2.topic =CcLineObject.CCTopicEnum.DOMAIN;
		cc2.object ="The di-lysine motif may confer endoplasmic reticulum localization (By similarity).";
		ccLineO.ccs.add(cc1);
		ccLineO.ccs.add(cc2);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(2, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.FUNCTION, comment1.getCommentType());
		assertTrue (comment1 instanceof FunctionComment);
		
		FunctionComment fcomment = (FunctionComment) comment1;
		assertEquals("This enzyme is necessary for target cell lysis in cell-mediated immune responses. It cleaves after Lys or Arg. May be involved in apoptosis",
				fcomment.getValue());
		assertEquals(CommentStatus.EXPERIMENTAL, fcomment.getCommentStatus());
		Comment comment2 =comments.get(1);
		assertEquals(CommentType.DOMAIN, comment2.getCommentType());
		assertTrue (comment2 instanceof DomainComment);
		
		DomainComment dcomment = (DomainComment) comment2;
		assertEquals("The di-lysine motif may confer endoplasmic reticulum localization", dcomment.getValue());
		assertEquals(CommentStatus.BY_SIMILARITY, dcomment.getCommentStatus());
	}
	@Test
	public void testWebResource(){
		//CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
        //CC       URL="http://bioinf.uta.fi/CD40Lbase/";
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.WEB_RESOURCE;
		CcLineObject.WebResource wr =new CcLineObject.WebResource();
		wr.name ="CD40Lbase";
		wr.note ="CD40L defect database";
		wr.url ="http://bioinf.uta.fi/CD40Lbase/";
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.WEBRESOURCE, comment1.getCommentType());
		assertTrue (comment1 instanceof WebResourceComment);
		
		WebResourceComment wcomment = (WebResourceComment) comment1;
		assertEquals("CD40Lbase", wcomment.getDatabaseName().getValue());
		assertEquals("CD40L defect database", wcomment.getDatabaseNote().getValue());
		assertEquals("http://bioinf.uta.fi/CD40Lbase/", wcomment.getDatabaseURL().getValue());
		
	}
	
	@Test
	public void testRNAEditing(){
		//CC   -!- RNA EDITING: Modified_positions=1, 56, 89, 103, 126, 164;
        //CC       Note=The initiator methionine is created by RNA editing.
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.RNA_EDITING;
		CcLineObject.RnaEditing rnaEd =new CcLineObject.RnaEditing();
		rnaEd.note ="The initiator methionine is created by RNA editing.";
		rnaEd.locations.add(1);
		rnaEd.locations.add(56);
		rnaEd.locations.add(89);
		rnaEd.locations.add(103);
		rnaEd.locations.add(126);
		rnaEd.locations.add(164);
	
		cc1.object =rnaEd;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.RNA_EDITING, comment1.getCommentType());
		assertTrue (comment1 instanceof RnaEditingComment);
		
		RnaEditingComment wcomment = (RnaEditingComment) comment1;
		
		assertEquals("The initiator methionine is created by RNA editing.", wcomment.getRnaEditingNote().getValue());
		assertEquals(RnaEditingLocationType.Known, wcomment.getLocationType());
		assertEquals(6, wcomment.getPositionsWithEvidences().size());
		assertEquals("1", wcomment.getPositionsWithEvidences().get(0).getPosition());
		assertEquals("126", wcomment.getPositionsWithEvidences().get(4).getPosition());
		
	}
	@Test
	public void testMassSpectrometry(){
		//CC   -!- MASS SPECTROMETRY: Mass=13822; Method=MALDI; Range=19-140 (P15522-
        //CC       2); Source=PubMed:10531593;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.MASS_SPECTROMETRY;
		CcLineObject.MassSpectrometry wr =new CcLineObject.MassSpectrometry();
		wr.mass =13822;
		wr.method ="MALDI";
		CcLineObject.MassSpectrometryRange mrange = new CcLineObject.MassSpectrometryRange ();
		mrange.start =19;
		mrange.end = 140;
		wr.ranges.add(mrange);
		wr.range_isoform ="P15522-2";
		wr.source ="PubMed:10531593";
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.MASS_SPECTROMETRY, comment1.getCommentType());
		assertTrue (comment1 instanceof MassSpectrometryComment);
		
		MassSpectrometryComment wcomment = (MassSpectrometryComment) comment1;
		 
		assertEquals(13822.0,wcomment.getMolWeight(),0.0001);
		assertEquals(0, wcomment.getMolWeightError(),0.0001);
	//	assertEquals(null, wcomment.getNote());
		List<MassSpectrometryRange> ranges=wcomment.getRanges();
		assertEquals(1, ranges.size());
		MassSpectrometryRange range = ranges.get(0);
		assertEquals("P15522-2", range.getIsoformId().getValue());
		assertEquals(19, range.getStart());
		assertEquals(140, range.getEnd());
		List<MassSpectrometryCommentSource> sources = wcomment.getSources();
		assertEquals(1, sources.size());
		MassSpectrometryCommentSource source = sources.get(0);
		assertEquals("PubMed:10531593", source.getValue());
		
	}
	

	@Test
	public void testSequenceCaution(){
		//CC   -!- SEQUENCE CAUTION:
        //CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;
       //CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;

		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;
		CcLineObject.SequenceCaution sc =new CcLineObject.SequenceCaution();
		CcLineObject.SequenceCautionObject sco1 =new CcLineObject.SequenceCautionObject();
		sco1.sequence ="CAI12537.1";
		sco1.type = CcLineObject.SequenceCautionType.Erroneous_gene_model_prediction;
		sc.sequenceCautionObjects.add(sco1);
		
		CcLineObject.SequenceCautionObject sco2 =new CcLineObject.SequenceCautionObject();
		sco2.sequence ="CAI39742.1";
		sco2.type = CcLineObject.SequenceCautionType.Erroneous_gene_model_prediction;
		sco2.positions.add(388);
		sco2.positions.add(399);
		sc.sequenceCautionObjects.add(sco2);
		
		cc1.object =sc;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(2, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.SEQUENCE_CAUTION, comment1.getCommentType());
		assertTrue (comment1 instanceof SequenceCautionComment);
		
		SequenceCautionComment wcomment = (SequenceCautionComment) comment1;
		
		Comment comment2 =comments.get(1);
		assertEquals(CommentType.SEQUENCE_CAUTION, comment2.getCommentType());
		assertTrue (comment2 instanceof SequenceCautionComment);
		
		SequenceCautionComment wcomment2 = (SequenceCautionComment) comment2;
		
		assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment.getType());
		assertEquals(0, wcomment.getPositions().size());
		assertEquals("CAI12537.1", wcomment.getSequence());
		
		
		assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment2.getType());
		assertEquals(2, wcomment2.getPositions().size());
		assertEquals("CAI39742.1", wcomment2.getSequence());
		assertEquals("388", wcomment2.getPositions().get(0).getValue() );
		assertEquals("399", wcomment2.getPositions().get(1).getValue() );
		
		
	}
	@Test
	public void testBioPhyChem(){
		//CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
		//CC       Absorption:\n" +
        //CC         Abs(max)=3 nm;\n" +
        //CC         Note=foo bar foo bar\n" 
        //CC       Kinetic parameters:
        //CC         KM=71 uM for ATP;
        //CC         KM=98 uM for ADP;
        //CC         KM=1.5 mM for acetate;
        //CC         KM=0.47 mM for acetyl phosphate;
        //CC       Temperature dependence:
        //CC         Optimum temperature is 65 degrees Celsius. Protected from
        //CC         thermal inactivation by ATP;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
		CcLineObject.BiophysicochemicalProperties wr =
				new CcLineObject.BiophysicochemicalProperties();
		wr.kms.add("71 uM for ATP");
		wr.kms.add("98 uM for ADP");
		wr.kms.add("1.5 mM for acetate");
		wr.kms.add("0.47 mM for acetyl phosphate");
		wr.temperature_dependence =
				"Optimum temperature is 65 degrees Celsius. Protected from " +
					      "thermal inactivation by ATP";
		wr.bsorption_abs =3;
		wr.bsorption_note="foo bar foo bar";
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
		assertTrue (comment1 instanceof BioPhysicoChemicalPropertiesComment);
		
		BioPhysicoChemicalPropertiesComment wcomment 
		= (BioPhysicoChemicalPropertiesComment) comment1;
		assertTrue( wcomment.hasAbsorptionProperty());
		assertFalse(wcomment.hasPHDependenceProperty());
		assertFalse(wcomment.hasRedoxPotentialProperty());
		assertTrue(wcomment.hasTemperatureDependenceProperty());
		KineticParameters kp =wcomment.getKineticParameters();
		assertEquals(0, kp.getMaximumVelocities().size());
		assertEquals(4, kp.getMichaelisConstants().size());
		List<MichaelisConstant>  mcs =kp.getMichaelisConstants();
		MichaelisConstant mc1 = mcs.get(0);
		assertEquals(71.0, mc1.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MICRO_MOL, mc1.getUnit());
		assertEquals("ATP", mc1.getSubstrate().getValue());
		
		MichaelisConstant mc3 = mcs.get(2);
		assertEquals(1.5, mc3.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MILLI_MOL, mc3.getUnit());
		assertEquals("acetate", mc3.getSubstrate().getValue());
		assertEquals("Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP",
				wcomment.getTemperatureDependence().getValue());
		Absorption ab =wcomment.getAbsorption();
		assertEquals(3, ab.getMax());
		assertEquals("foo bar foo bar", ab.getNote().getValue());
	}
	
	@Test
	public void testBioPhyChem2(){
		//CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
        //CC       Kinetic parameters:
        //CC         KM=71 uM for ATP;
        //CC         KM=98 uM for ADP;
		//CC       pH dependence:
        //CC         Optimum pH is 7.75;
        //CC       Temperature dependence:
        //CC         Optimum temperature is 65 degrees Celsius. Protected from
        //CC         thermal inactivation by ATP;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
		CcLineObject.BiophysicochemicalProperties wr =
				new CcLineObject.BiophysicochemicalProperties();
		wr.kms.add("71 uM for ATP");
		wr.kms.add("98 uM for ADP");
		wr.ph_dependence ="Optimum pH is 7.75";
		wr.temperature_dependence =
				"Optimum temperature is 65 degrees Celsius. Protected from " +
					      "thermal inactivation by ATP";
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, comment1.getCommentType());
		assertTrue (comment1 instanceof BioPhysicoChemicalPropertiesComment);
		
		BioPhysicoChemicalPropertiesComment wcomment 
		= (BioPhysicoChemicalPropertiesComment) comment1;
		assertEquals(false, wcomment.hasAbsorptionProperty());
		assertTrue(wcomment.hasPHDependenceProperty());
		assertFalse(wcomment.hasRedoxPotentialProperty());
		assertTrue(wcomment.hasTemperatureDependenceProperty());
		KineticParameters kp =wcomment.getKineticParameters();
		assertEquals(0, kp.getMaximumVelocities().size());
		assertEquals(2, kp.getMichaelisConstants().size());
		List<MichaelisConstant>  mcs =kp.getMichaelisConstants();
		MichaelisConstant mc1 = mcs.get(0);
		assertEquals(71.0, mc1.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MICRO_MOL, mc1.getUnit());
		assertEquals("ATP", mc1.getSubstrate().getValue());
		
		MichaelisConstant mc3 = mcs.get(1);
		assertEquals(98, mc3.getConstant(), 0.0001);
		assertEquals(MichaelisConstantUnit.MICRO_MOL, mc3.getUnit());
		assertEquals("ADP", mc3.getSubstrate().getValue());
		assertEquals("Optimum pH is 7.75", wcomment.getPHDependence().getValue());
		assertEquals("Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP",
				wcomment.getTemperatureDependence().getValue());
	}
	
	@Test
	public void testAlternatProduct(){
		//CC   -!- ALTERNATIVE PRODUCTS:
        //CC       Event=Alternative splicing; Named isoforms=3;
        //CC         Comment=Additional isoforms seem to exist. Experimental
        //CC         confirmation may be lacking for some isoforms;
        //CC       Name=1; Synonyms=AIRE-1;
        //CC         IsoId=O43918-1; Sequence=Displayed;
		//CC       Name=2; Synonyms=AIRE-2;
        //CC         IsoId=O43918-2; Sequence=VSP_004089;
		 //CC         Note=Major isoform found in 66-78% of cDNA clones
        //CC       Name=3; Synonyms=AIRE-3;
       //CC         IsoId=O43918-3; Sequence=VSP_004089, VSP_004090;
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.ALTERNATIVE_PRODUCTS;
		CcLineObject.AlternativeProducts wr =
				new CcLineObject.AlternativeProducts();
		wr.comment = "Additional isoforms seem to exist. "
				+ "Experimental confirmation may be lacking for some isoforms";
		wr.namedIsoforms ="3";
		wr.events.add("Alternative splicing");
		CcLineObject.AlternativeProductName alName1 = new 
				CcLineObject.AlternativeProductName();
		alName1.name ="1";
		alName1.synNames.add("AIRE-1");
		alName1.isoId.add("O43918-1");
		alName1.sequence_enum = AlternativeNameSequenceEnum.Displayed;
		
		CcLineObject.AlternativeProductName alName2 = new 
				CcLineObject.AlternativeProductName();
		alName2.name ="2";
		alName2.synNames.add("AIRE-2");
		alName2.isoId.add("O43918-2");
		alName2.note ="Major isoform found in 66-78% of cDNA clones";
		alName2.sequence_FTId.add("VSP_004089");
		
		CcLineObject.AlternativeProductName alName3 = new 
				CcLineObject.AlternativeProductName();
		alName3.name ="3";
		alName3.synNames.add("AIRE-3");
		alName3.isoId.add("O43918-3");
		alName3.sequence_FTId.add("VSP_004089");
		alName3.sequence_FTId.add("VSP_004090");
		
		wr.names.add(alName1);
		wr.names.add(alName2);
		wr.names.add(alName3);
		cc1.object =wr;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.ALTERNATIVE_PRODUCTS, comment1.getCommentType());
		assertTrue (comment1 instanceof AlternativeProductsComment);
		
		AlternativeProductsComment wcomment 
		= (AlternativeProductsComment) comment1;
		List<AlternativeProductsEvent> events =wcomment.getEvents();
		List<AlternativeProductsIsoform> isoforms =wcomment.getIsoforms();
		assertEquals(1, events.size());
		AlternativeProductsEvent event = events.get(0);
		assertEquals("Alternative splicing", event.getValue());
		assertEquals("Additional isoforms seem to exist. "
				+ "Experimental confirmation may be lacking for some isoforms", 
				wcomment.getComment().getValue());
		assertEquals(3, isoforms.size());
		AlternativeProductsIsoform isoform1 =isoforms.get(0);
		AlternativeProductsIsoform isoform2 =isoforms.get(1);
		AlternativeProductsIsoform isoform3 =isoforms.get(2);
		assertEquals("1", isoform1.getName().getValue());
		List<IsoformSynonym> syns1 =isoform1.getSynonyms();
		assertEquals(1, syns1.size());
		assertEquals(0, isoform1.getSequenceIds().size());
		assertEquals("AIRE-1", syns1.get(0).getValue());
		assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
		assertFalse(isoform1.hasNote());
		List<IsoformId> ids1 = isoform1.getIds();
		assertEquals(1, ids1.size());
		assertEquals("O43918-1", ids1.get(0).getValue());
		
		assertEquals("2", isoform2.getName().getValue());
		List<IsoformSynonym> syns2 =isoform2.getSynonyms();
		assertEquals(1, syns2.size());
		assertEquals("AIRE-2", syns2.get(0).getValue());
		List<IsoformSequenceId> seqIds2 =isoform2.getSequenceIds();
		assertEquals(1, seqIds2.size());
		assertEquals("VSP_004089", seqIds2.get(0).getValue());
	//	assertEquals(IsoformSequenceStatus.DISPLAYED, isoform2.getIsoformSequenceStatus());
		assertTrue(isoform2.hasNote());
		assertEquals("Major isoform found in 66-78% of cDNA clones", 
				isoform2.getNote().getValue());
		List<IsoformId> ids2 = isoform2.getIds();
		assertEquals(1, ids2.size());
		assertEquals("O43918-2", ids2.get(0).getValue());
		
		assertEquals("3", isoform3.getName().getValue());
		List<IsoformSynonym> syns3 =isoform3.getSynonyms();
		assertEquals(1, syns3.size());
		assertEquals("AIRE-3", syns3.get(0).getValue());
		List<IsoformSequenceId> seqIds3 =isoform3.getSequenceIds();
		assertEquals(2, seqIds3.size());
		assertEquals("VSP_004089", seqIds3.get(0).getValue());
		assertEquals("VSP_004090", seqIds3.get(1).getValue());
	//	assertEquals(IsoformSequenceStatus.DISPLAYED, isoform2.getIsoformSequenceStatus());
		assertFalse(isoform3.hasNote());
		List<IsoformId> ids3 = isoform3.getIds();
		assertEquals(1, ids3.size());
		assertEquals("O43918-3", ids3.get(0).getValue());
	}
	@Test
	public void testInteraction(){
		/*
		 CC   -!- INTERACTION:
         CC       Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;
         CC       Self; NbExp=1; IntAct=EBI-123485, EBI-123485;
         CC       Q8C1S0:2410018M14Rik (xeno); NbExp=1; IntAct=EBI-394562, EBI-398761;
        CC       localization (By similarity).
		 */
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.INTERACTION;
		
		CcLineObject.Interaction ia = new CcLineObject.Interaction();
		cc1.object =ia;
		CcLineObject.InteractionObject iao1 = new CcLineObject.InteractionObject();
		iao1.spAc = "Q9W1K5-1";
		iao1.gene = "CG11299";
		iao1.nbexp = 1;
		iao1.firstId = "EBI-133844";
		iao1.secondId ="EBI-212772";
		
		CcLineObject.InteractionObject iao2 = new CcLineObject.InteractionObject();
		iao2.isSelf =true;
		
		iao2.nbexp = 1;
		iao2.firstId = "EBI-123485";
		iao2.secondId ="EBI-123484";
		
		CcLineObject.InteractionObject iao3 = new CcLineObject.InteractionObject();
		iao3.spAc = "Q8C1S0";
		iao3.gene = "CG112992";
		iao3.nbexp = 1;
		iao3.firstId = "EBI-133844";
		iao3.secondId ="EBI-212775";
		iao3.xeno =true;
		
		ia.interactions.add(iao1);
		ia.interactions.add(iao2);
		ia.interactions.add(iao3);
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(1, comments.size());
		Comment comment1 = comments.get(0);
		assertEquals(CommentType.INTERACTION, comment1.getCommentType());
		assertTrue (comment1 instanceof InteractionComment);
		InteractionComment icomment = (InteractionComment) comment1;
		List<Interaction> interactions =icomment.getInteractions();
		assertEquals(3, interactions.size());
		Interaction inter1 = interactions.get(0);
		Interaction inter2 = interactions.get(1);
		Interaction inter3 = interactions.get(2);
		assertEquals("EBI-133844", inter1.getFirstInteractor().getValue());
		assertEquals("EBI-212772", inter1.getSecondInteractor().getValue());
		assertEquals("Q9W1K5-1", inter1.getInteractorUniProtAccession().getValue());
		assertEquals("CG11299",inter1.getInteractionGeneName().getValue());
		assertEquals(1,inter1.getNumberOfExperiments());
		assertEquals(InteractionType.BINARY ,inter1.getInteractionType());
		assertEquals("EBI-123485", inter2.getFirstInteractor().getValue());
		assertEquals("EBI-123484", inter2.getSecondInteractor().getValue());
		assertEquals(InteractionType.SELF ,inter2.getInteractionType());
		assertEquals(1,inter1.getNumberOfExperiments());
		
		assertEquals("EBI-133844", inter3.getFirstInteractor().getValue());
		assertEquals("EBI-212775", inter3.getSecondInteractor().getValue());
		assertEquals("Q8C1S0", inter3.getInteractorUniProtAccession().getValue());
		assertEquals("CG112992",inter3.getInteractionGeneName().getValue());
		assertEquals(1,inter3.getNumberOfExperiments());
		assertEquals(InteractionType.XENO ,inter3.getInteractionType());
		
	}
}
