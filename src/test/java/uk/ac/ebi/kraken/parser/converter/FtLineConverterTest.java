package uk.ac.ebi.kraken.parser.converter;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.features.Feature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureLocation;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureLocationModifier;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureSequence;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureStatus;
import uk.ac.ebi.kraken.interfaces.uniprot.features.FeatureType;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HasAlternativeSequence;
import uk.ac.ebi.kraken.interfaces.uniprot.features.HelixFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.MutagenFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.NpBindFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.SignalFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.VarSeqFeature;
import uk.ac.ebi.kraken.interfaces.uniprot.features.VariantFeature;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject;

public class FtLineConverterTest {
	private final FtLineConverter converter = new FtLineConverter();
	@Test
	public void test1(){
		//"FT   HELIX      33     83
		//"FT    SIGNAL     <1     34       Potential.
		FtLineObject fobj  =new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT ();
		ft.type =FtLineObject.FTType.HELIX;
		ft.location_start ="33";
		ft.location_end ="83";
		fobj.fts.add(ft);
		FtLineObject.FT ft2 = new FtLineObject.FT ();
		ft2.type =FtLineObject.FTType.SIGNAL;
		ft2.location_start ="<1";
		ft2.location_end ="34";
		ft2.ft_text ="Potential";
		fobj.fts.add(ft2);
		
		FtLineObject.FT ft3 = new FtLineObject.FT ();
		ft3.type =FtLineObject.FTType.NP_BIND;
		ft3.location_start ="1";
		ft3.location_end =">17";
		ft3.ft_text ="NAD (By similarity)";
		fobj.fts.add(ft3);
		
		FtLineObject.FT ft4 = new FtLineObject.FT ();
		ft4.type =FtLineObject.FTType.NP_BIND;
		ft4.location_start ="1";
		ft4.location_end =">17";
		ft4.ft_text ="NAD";
		fobj.fts.add(ft4);
		
		List<Feature> features = converter.convert(fobj);
		assertEquals(4, features.size());
		Feature feature1 = features.get(0);
		Feature feature2 = features.get(1);
		Feature feature3 = features.get(2);
		Feature feature4 = features.get(3);
		assertTrue(feature1 instanceof HelixFeature);
		assertTrue(feature2 instanceof SignalFeature);
		assertTrue(feature3 instanceof NpBindFeature);
		assertTrue(feature4 instanceof NpBindFeature);
		validateLocation(feature1.getFeatureLocation(),
				33, 83, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.HELIX, feature1.getType());
		assertEquals( feature1.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		validateLocation(feature2.getFeatureLocation(),
				1, 34, FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.SIGNAL, feature2.getType());
		assertEquals( feature2.getFeatureStatus(),FeatureStatus.POTENTIAL );
		assertEquals(((SignalFeature)feature2).getFeatureDescription().getValue(), "");
		
		validateLocation(feature3.getFeatureLocation(),
				1, 17, FeatureLocationModifier.EXACT, FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE);
		assertEquals(FeatureType.NP_BIND, feature3.getType());
		assertEquals( feature3.getFeatureStatus(),FeatureStatus.BY_SIMILARITY );
		assertEquals(((NpBindFeature)feature3).getFeatureDescription().getValue(), "NAD");
		
		validateLocation(feature4.getFeatureLocation(),
				1, 17, FeatureLocationModifier.EXACT, FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE);
		assertEquals(FeatureType.NP_BIND, feature4.getType());
		assertEquals( feature4.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		assertEquals(((NpBindFeature)feature4).getFeatureDescription().getValue(), "NAD");
		
		
	}
	
	
	@Test 
	public void testMutagen() throws Exception {
		/*
		 *  """FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and
                 |FT                                ADP-ribosyl cyclase activity.
		 */
		FtLineObject fobj  =new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT ();
		ft.type =FtLineObject.FTType.MUTAGEN;
		ft.location_start ="119";
		ft.location_end ="119";
		ft.ft_text ="C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity";
		fobj.fts.add(ft);
		List<Feature> features = converter.convert(fobj);
		assertEquals(1, features.size());
		Feature feature1 = features.get(0);
		validateLocation(feature1.getFeatureLocation(),
				119, 119, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.MUTAGEN, feature1.getType());
		assertEquals( feature1.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		assertTrue(feature1 instanceof MutagenFeature);
		MutagenFeature mFeature = (MutagenFeature) feature1;
		assertEquals("Loss of cADPr hydrolase and ADP-ribosyl cyclase activity",
				mFeature.getMutagenReport().getValue());
		List<String> altSeq = new ArrayList<String>();
		altSeq.add("R");
		altSeq.add("E");
		altSeq.add("A");
		validateAltSeq(mFeature, "C", altSeq );
	
	}
	
	@Test 
	public void testVarSeq() throws Exception {
		/**
		 *  
		 "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
	                 |FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
	                 |FT                                isoform 2).
	                 |FT                                /FTId=VSP_004370.
		 */
	
		FtLineObject fobj  =new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT ();
		ft.type =FtLineObject.FTType.VAR_SEQ;
		ft.location_start ="33";
		ft.location_end ="83";
		ft.ft_text ="TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)";
		ft.ftId = "VSP_004370";
		fobj.fts.add(ft);
		List<Feature> features = converter.convert(fobj);
		assertEquals(1, features.size());
		Feature feature1 = features.get(0);
		validateLocation(feature1.getFeatureLocation(),
				33, 83, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.VAR_SEQ, feature1.getType());
		assertEquals( feature1.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		assertTrue(feature1 instanceof VarSeqFeature);
		VarSeqFeature mFeature = (VarSeqFeature) feature1;
		assertEquals(1, mFeature.getVarsplicIsoforms().size());
		assertEquals("2", mFeature.getVarsplicIsoforms().get(0).getValue());
		assertEquals("VSP_004370", mFeature.getFeatureId().getValue());
		List<String> altSeq = new ArrayList<String>();
		altSeq.add("SECLTYGKQPLTSFHPFTSQMPP");
		validateAltSeq(mFeature, "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG", altSeq );
	
	}


	@Test 
	public void testVariant() throws Exception {
			/**
			 * FT   VARIANT     102    102       V -> I (in HH2; dbSNP:rs55642501).
		FT                                /FTId=VAR_030972.                       /FTId=VSP_004370.
		 */
	
		FtLineObject fobj  =new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT ();
		ft.type =FtLineObject.FTType.VARIANT;
		ft.location_start ="102";
		ft.location_end ="102";
		ft.ft_text ="V -> I (in HH2; dbSNP:rs55642501)";
		ft.ftId = "VAR_030972";
		fobj.fts.add(ft);
		List<Feature> features = converter.convert(fobj);
		assertEquals(1, features.size());
		Feature feature1 = features.get(0);
		validateLocation(feature1.getFeatureLocation(),
				102, 102, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.VARIANT, feature1.getType());
		assertEquals( feature1.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		assertTrue(feature1 instanceof VariantFeature);
		VariantFeature mFeature = (VariantFeature) feature1;
		assertEquals("in HH2; dbSNP:rs55642501", mFeature.getVariantReport().getValue());
		assertEquals("VAR_030972", mFeature.getFeatureId().getValue());
		List<String> altSeq = new ArrayList<String>();
		altSeq.add("I");
		validateAltSeq(mFeature, "V", altSeq );
	
	}
	
	@Test
	public void testEvidence(){
		//"FT   HELIX      33     83{EI1,EI2}
		//"FT    SIGNAL     <1     34       Potential{EI2,EI3}.
		FtLineObject fobj  =new FtLineObject();
		FtLineObject.FT ft = new FtLineObject.FT ();
		ft.type =FtLineObject.FTType.HELIX;
		ft.location_start ="33";
		ft.location_end ="83";
		List<String> evIds = new ArrayList<String>();
		evIds.add("EI1");
		evIds.add("EI2");
		fobj.evidenceInfo.evidences.put(ft, evIds);
		fobj.fts.add(ft);
		FtLineObject.FT ft2 = new FtLineObject.FT ();
		ft2.type =FtLineObject.FTType.SIGNAL;
		ft2.location_start ="<1";
		ft2.location_end ="34";
		ft2.ft_text ="Potential";
		evIds = new ArrayList<String>();
		evIds.add("EI2");
		evIds.add("EI3");
		fobj.evidenceInfo.evidences.put(ft2, evIds);
		fobj.fts.add(ft2);
		
		FtLineObject.FT ft3 = new FtLineObject.FT ();
		ft3.type =FtLineObject.FTType.NP_BIND;
		ft3.location_start ="1";
		ft3.location_end =">17";
		ft3.ft_text ="NAD (By similarity)";
		evIds = new ArrayList<String>();
		evIds.add("EI3");
		fobj.evidenceInfo.evidences.put(ft3, evIds);		
		fobj.fts.add(ft3);
		
		FtLineObject.FT ft4 = new FtLineObject.FT ();
		ft4.type =FtLineObject.FTType.NP_BIND;
		ft4.location_start ="1";
		ft4.location_end =">17";
		ft4.ft_text ="NAD";
		fobj.fts.add(ft4);
		
		List<Feature> features = converter.convert(fobj);
		assertEquals(4, features.size());
		Feature feature1 = features.get(0);
		Feature feature2 = features.get(1);
		Feature feature3 = features.get(2);
		Feature feature4 = features.get(3);
		assertTrue(feature1 instanceof HelixFeature);
		assertTrue(feature2 instanceof SignalFeature);
		assertTrue(feature3 instanceof NpBindFeature);
		assertTrue(feature4 instanceof NpBindFeature);
		
		List<EvidenceId> eviIds = feature1.getEvidenceIds();
		assertEquals(2, eviIds.size());
		EvidenceId eviId = eviIds.get(0);
		EvidenceId eviId2 = eviIds.get(1);
		assertEquals("EI1", eviId.getValue());
		assertEquals("EI2", eviId2.getValue());
		
		eviIds = feature2.getEvidenceIds();
		assertEquals(2, eviIds.size());
		 eviId = eviIds.get(0);
		 eviId2 = eviIds.get(1);
		assertEquals("EI2", eviId.getValue());
		assertEquals("EI3", eviId2.getValue());
		
		eviIds = feature3.getEvidenceIds();
		assertEquals(1, eviIds.size());
		 eviId = eviIds.get(0);	
		assertEquals("EI3", eviId.getValue());
		eviIds = feature4.getEvidenceIds();
		assertEquals(0, eviIds.size());
		
		validateLocation(feature1.getFeatureLocation(),
				33, 83, FeatureLocationModifier.EXACT, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.HELIX, feature1.getType());
		assertEquals( feature1.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		validateLocation(feature2.getFeatureLocation(),
				1, 34, FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE, FeatureLocationModifier.EXACT);
		assertEquals(FeatureType.SIGNAL, feature2.getType());
		assertEquals( feature2.getFeatureStatus(),FeatureStatus.POTENTIAL );
		assertEquals(((SignalFeature)feature2).getFeatureDescription().getValue(), "");
		
		validateLocation(feature3.getFeatureLocation(),
				1, 17, FeatureLocationModifier.EXACT, FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE);
		assertEquals(FeatureType.NP_BIND, feature3.getType());
		assertEquals( feature3.getFeatureStatus(),FeatureStatus.BY_SIMILARITY );
		assertEquals(((NpBindFeature)feature3).getFeatureDescription().getValue(), "NAD");
		
		validateLocation(feature4.getFeatureLocation(),
				1, 17, FeatureLocationModifier.EXACT, FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE);
		assertEquals(FeatureType.NP_BIND, feature4.getType());
		assertEquals( feature4.getFeatureStatus(),FeatureStatus.EXPERIMENTAL );
		assertEquals(((NpBindFeature)feature4).getFeatureDescription().getValue(), "NAD");
		
		
	}
	
	
	private void validateAltSeq(HasAlternativeSequence as, String val, List<String> target ){
		assertEquals(val, as.getOriginalSequence().getValue());
		List<FeatureSequence>  altSeq =as.getAlternativeSequences();
		assertEquals(target.size(), altSeq.size());
		for(FeatureSequence fs:altSeq){
			target.contains(fs.getValue());
		}
		
	}
	private void validateLocation(FeatureLocation floc, int start, int end, 
			FeatureLocationModifier startF, FeatureLocationModifier endF ){
		assertEquals(floc.getStartModifier(), startF);
		assertEquals(floc.getEndModifier(), endF);
		if(!startF.equals(FeatureLocationModifier.UNKOWN))
			assertEquals(floc.getStart(), start);
		if(!startF.equals(FeatureLocationModifier.UNKOWN))
		assertEquals(floc.getEnd(), end);
	}
}
