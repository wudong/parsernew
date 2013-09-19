package uk.ac.ebi.kraken.parser.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.kraken.interfaces.uniprot.ProteinDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Flag;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FlagType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Name;
import uk.ac.ebi.kraken.interfaces.uniprot.description.NameType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Section;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject;

public class DeLineConverterTest {
	private DeLineConverter converter = new DeLineConverter();
	
	@Test
	public void test1(){
		/*
		 *     val deLines = """DE   RecName: Full=Annexin A5;
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
		 */
		DeLineObject deObject = new DeLineObject();
		deObject.recName = new DeLineObject.Name();
		deObject.recName.fullName = "Annexin A5";
		deObject.recName.shortNames.add( "Annexin-5");
		deObject.altName.add(createName("Annexin V"));
		deObject.altName.add(createName("Lipocortin V"));
		deObject.altName.add(createName("Placental anticoagulant protein I", "PAP-I"));
		deObject.altName.add(createName("PP4"));
		deObject.altName.add(createName("Thromboplastin inhibitor"));
		deObject.altName.add(createName("Vascular anticoagulant-alpha", "VAC-alpha"));
		deObject.altName.add(createName("Anchorin CII"));
		deObject.flag =DeLineObject.FlagType.Precursor;
		ProteinDescription pDesc = converter.convert(deObject);
		Name recName =pDesc.getRecommendedName();
		List<Name> names = new ArrayList<Name>();
		names.add(recName);
		validate( "Annexin A5", "Annexin-5", NameType.RECNAME, names);
		names = pDesc.getAlternativeNames();
		validate( "Annexin V",  null, NameType.ALTNAME, names);
		validate( "Lipocortin V",  null, NameType.ALTNAME, names);
		validate( "Placental anticoagulant protein I", "PAP-I", NameType.ALTNAME, names);
		validate( "PP4",  null, NameType.ALTNAME, names);
		validate( "Thromboplastin inhibitor",  null, NameType.ALTNAME, names);
		validate( "Vascular anticoagulant-alpha", "VAC-alpha", NameType.ALTNAME, names);
		validate( "Anchorin CII",  null, NameType.ALTNAME, names);
		List<Flag> flags =pDesc.getFlags();
		TestCase.assertEquals(1, flags.size());
		TestCase.assertEquals(FlagType.PRECURSOR, flags.get(0).getFlagType());
	}
	
	@Test
	public void test2(){
		DeLineObject deObject = new DeLineObject();
		deObject.recName = new DeLineObject.Name();
		deObject.recName.fullName = "Annexin A5";
		deObject.recName.shortNames.add( "Annexin-5");
		deObject.altName.add(createName("Annexin V"));
		deObject.altName.add(createName("Lipocortin V"));
		deObject.altName.add(createName("Placental anticoagulant protein I", "PAP-I"));
		deObject.altName.add(createName("PP4"));
		deObject.altName.add(createName("Thromboplastin inhibitor"));
		deObject.altName.add(createName("Vascular anticoagulant-alpha", "VAC-alpha"));
		// |DE            EC=1.1.1.1;
      //  |DE            EC=1.1.1.2;
		List<String> ecs = new ArrayList<>();
		ecs.add("1.1.1.1");
		ecs.add("1.1.1.2");
		deObject.altName.add(createName("Anchorin CII", new ArrayList<String>(), ecs));
		ProteinDescription pDesc = converter.convert(deObject);
		Name recName =pDesc.getRecommendedName();
		List<Name> names = new ArrayList<Name>();
		names.add(recName);
		validate( "Annexin A5", "Annexin-5", NameType.RECNAME, names);
		names = pDesc.getAlternativeNames();
		validate( "Annexin V",  null, NameType.ALTNAME, names);
		validate( "Lipocortin V",  null, NameType.ALTNAME, names);
		validate( "Placental anticoagulant protein I", "PAP-I", NameType.ALTNAME, names);
		validate( "PP4",  null, NameType.ALTNAME, names);
		validate( "Thromboplastin inhibitor",  null, NameType.ALTNAME, names);
		validate( "Vascular anticoagulant-alpha", "VAC-alpha", NameType.ALTNAME, names);
		validate( "Anchorin CII",  null, ecs, NameType.ALTNAME, names);
		List<Flag> flags =pDesc.getFlags();
		TestCase.assertEquals(0, flags.size());
	}
	
	@Test
	public void test3() {
		/**
		 * "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;
                    |DE   Includes:
                    |DE     RecName: Full=Glutamate N-acetyltransferase;
                    |DE              EC=2.3.1.35;
                    |DE     AltName: Full=Ornithine acetyltransferase;
                    |DE              Short=OATase;
                    |DE     AltName: Full=Ornithine transacetylase;
                    |DE   Includes:
                    |DE     RecName: Full=Amino-acid acetyltransferase;
                    |DE              EC=2.3.1.1;
                    |DE     AltName: Full=N-acetylglutamate synthase;
                    |DE              Short=AGS;
                    |DE   Contains:
                    |DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;
                    |DE   Contains:
                    |DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain;
		 */
		DeLineObject deObject = new DeLineObject();
		deObject.recName = new DeLineObject.Name();
		deObject.recName.fullName = "Arginine biosynthesis bifunctional protein argJ";
		DeLineObject.NameBlock incName1 = new DeLineObject.NameBlock();
		List<String> ecs = new ArrayList<>();
		ecs.add("2.3.1.35");
		incName1.recName =createName("Glutamate N-acetyltransferase", new ArrayList<String>(), ecs);
		incName1.altName.add(createName("Ornithine acetyltransferase", "OATase"));
		incName1.altName.add(createName("Ornithine transacetylase"));
		deObject.includedNames.add(incName1);
		DeLineObject.NameBlock incName2 = new DeLineObject.NameBlock();
		List<String> ecs2 = new ArrayList<>();
		ecs2.add("2.3.1.1");
		incName2.recName =createName("Amino-acid acetyltransferase", new ArrayList<String>(), ecs2);
		incName2.altName.add(createName("N-acetylglutamate synthase", "AGS"));
		deObject.includedNames.add(incName2);

		DeLineObject.NameBlock conName1 = new DeLineObject.NameBlock();		
		conName1.recName =createName("Arginine biosynthesis bifunctional protein argJ alpha chain");
		
		DeLineObject.NameBlock conName2 = new DeLineObject.NameBlock();		
		conName2.recName =createName("Arginine biosynthesis bifunctional protein argJ beta chain");
		deObject.containedNames.add(conName1);
		deObject.containedNames.add(conName2);
		ProteinDescription pDesc = converter.convert(deObject);
		
		Name recName =pDesc.getRecommendedName();
		List<Name> names = new ArrayList<Name>();
		names.add(recName);
		validate( "Arginine biosynthesis bifunctional protein argJ", null, NameType.RECNAME, names);
		names = pDesc.getAlternativeNames();
		TestCase.assertTrue(names.isEmpty());
		List<Section> included =pDesc.getIncludes();
		TestCase.assertEquals(2, included.size());
		Section included1= included.get(0);
		names =included1.getNames();
		 TestCase.assertEquals(3, names.size());
		validate( "Glutamate N-acetyltransferase", null, ecs, NameType.RECNAME, names);
		validate( "Ornithine acetyltransferase", "OATase", NameType.ALTNAME, names);
		validate( "Ornithine transacetylase", null, NameType.ALTNAME, names);
		
		Section included2= included.get(1);
		names =included2.getNames();
		 TestCase.assertEquals(2, names.size());
		validate( "Amino-acid acetyltransferase", null, ecs2, NameType.RECNAME, names);
		validate( "N-acetylglutamate synthase", "AGS", NameType.ALTNAME, names);
		
		
		List<Section> contained =pDesc.getContains();
		TestCase.assertEquals(2, contained.size());
		
		Section contained1= contained.get(0);
		names =contained1.getNames();
		 TestCase.assertEquals(1, names.size());
		validate( "Arginine biosynthesis bifunctional protein argJ alpha chain", null, NameType.RECNAME, names);
		Section contained2= contained.get(1);
		 names =contained2.getNames();
		 TestCase.assertEquals(1, names.size());
		 validate( "Arginine biosynthesis bifunctional protein argJ beta chain", null, NameType.RECNAME, names);
		
		
		
	}
	@Test
	public void testEvidence(){
		/*
		 *   val deLines = """DE   RecName: Full=Annexin A5{EI1};
                    |DE            Short=Annexin-5{EI1, EI2};
                    |DE   AltName: Full=Annexin V{EI1};
                    |DE   AltName: Full=Lipocortin V{EI1};
                    |DE   AltName: Full=Placental anticoagulant protein I{EI1};
                    |DE            Short=PAP-I{EI2};
                    |DE   AltName: Full=PP4{EI1};
                    |DE   AltName: Full=Thromboplastin inhibitor{EI3};
                    |DE   Flags: Precursor{EI1, EI2, EI3};
		 */
		DeLineObject deObject = new DeLineObject();
		deObject.recName = new DeLineObject.Name();
		deObject.recName.fullName = "Annexin A5";
		
		Map<String, List<String> > evidences = new TreeMap<>();
		List<String> evs = new ArrayList<>();
		evs.add("EI1");
		deObject.getEvidenceInfo().evidences.put("Annexin A5", evs);
		evidences.put("Annexin A5", evs);
		
		deObject.recName.shortNames.add( "Annexin-5");
		evs = new ArrayList<>();
		evs.add("EI2");
		deObject.getEvidenceInfo().evidences.put("Annexin-5", evs);
		evidences.put("Annexin-5", evs);
		deObject.altName.add(createName("Annexin V"));
		evs = new ArrayList<>();
		evs.add("EI1");
		deObject.getEvidenceInfo().evidences.put("Annexin V", evs);
		evidences.put("Annexin V", evs);
		deObject.altName.add(createName("Lipocortin V"));
		evs = new ArrayList<>();
		evs.add("EI1");
		deObject.getEvidenceInfo().evidences.put("Lipocortin V", evs);
		evidences.put("Lipocortin V", evs);
		
		deObject.altName.add(createName("Placental anticoagulant protein I", "PAP-I"));
		evs = new ArrayList<>();
		evs.add("EI1");
		deObject.getEvidenceInfo().evidences.put("Placental anticoagulant protein I", evs);
		evidences.put("Placental anticoagulant protein I", evs);
		evs = new ArrayList<>();
		evs.add("EI2");
		deObject.getEvidenceInfo().evidences.put("PAP-I", evs);
	
		evidences.put("PAP-I", evs);
		
		deObject.altName.add(createName("PP4"));
		evs = new ArrayList<>();
		evs.add("EI1");
		deObject.getEvidenceInfo().evidences.put("PP4", evs);
		evidences.put("PP4", evs);
		deObject.altName.add(createName("Thromboplastin inhibitor"));
		evs = new ArrayList<>();
		evs.add("EI3");
		deObject.getEvidenceInfo().evidences.put("Thromboplastin inhibitor", evs);
		evidences.put("Thromboplastin inhibitor", evs);
		
		deObject.flag =DeLineObject.FlagType.Precursor;
		evs = new ArrayList<>();
		evs.add("EI1");
		evs.add("EI2");
		evs.add("EI3");
		deObject.getEvidenceInfo().evidences.put(deObject.flag, evs);
		ProteinDescription pDesc = converter.convert(deObject);
		Name recName =pDesc.getRecommendedName();
		
		List<String> ecs = new ArrayList<>();
		List<Name> names = new ArrayList<Name>();
		names.add(recName);
		
		
		validate( "Annexin A5", "Annexin-5", ecs, NameType.RECNAME, names, evidences );
		
		names = pDesc.getAlternativeNames();
		validate( "Annexin V",  null, ecs, NameType.ALTNAME, names, evidences);
		validate( "Lipocortin V",  null, ecs, NameType.ALTNAME, names, evidences);
		validate( "Placental anticoagulant protein I", "PAP-I", ecs, NameType.ALTNAME, names, evidences);
		validate( "PP4",  null, ecs, NameType.ALTNAME, names, evidences);
		validate( "Thromboplastin inhibitor",  null, ecs, NameType.ALTNAME, names, evidences );
	
		List<Flag> flags =pDesc.getFlags();
		TestCase.assertEquals(1, flags.size());
		Flag flag = flags.get(0);
		TestCase.assertEquals(FlagType.PRECURSOR, flag.getFlagType());
		
		List<EvidenceId> evids = flag.getEvidenceIds();
		TestCase.assertEquals(3, evids.size());
		evs = new ArrayList<>();
		evs.add("EI1");
		evs.add("EI2");
		evs.add("EI3");
		for(EvidenceId ev:evids){
			TestCase.assertTrue(evs.contains(ev.getValue()));
		}
		
		
	}
	private void validate(List<String> expected, List<EvidenceId> vals){
		if((expected ==null) ||(expected.size()==0) )
			return;
		TestCase.assertEquals(expected.size(),  vals.size());
		for(EvidenceId val:vals){
			TestCase.assertTrue(expected.contains(val.getValue()));
		}
	}
	
	private void validate(String fullName, String shortName, NameType type,  List<Name> names){
		List<String> ecs = new ArrayList<>();
		validate(fullName, shortName, ecs, type, names);
	}
	private void validate(String fullName, String shortName, List<String> ecs, NameType type,  List<Name> names){
		validate(fullName, shortName, ecs, type, names, new TreeMap<String, List<String> >());
	}
	private void validate(String fullName, String shortName, List<String> ecs, NameType type,  List<Name> names, Map<String, List<String> > evidences){
		boolean fullNameFound =false;
		boolean shortNameFound =false;
	
		List<Field> ecFields = new ArrayList<>();
		for(Name name:names){
			if(type !=name.getNameType())
				continue;
			List<Field> fields = name.getFields();
		
			for(Field field:fields){
				if(field.getType() ==FieldType.FULL){
					if(field.getValue().equals(fullName)){
						fullNameFound =true;
						validate(evidences.get(fullName), field.getEvidenceIds());
						break;
					}
				}
			}
			if(fullNameFound){
				for(Field field:fields){
					if(shortName !=null){
						if(field.getType() ==FieldType.SHORT){
							if(field.getValue().equals(shortName)){
								validate(evidences.get(shortName), field.getEvidenceIds());
								shortNameFound =true;
							}
						}
					}	
					if(field.getType() ==FieldType.EC){
						ecFields.add(field);
					}
				}
				
			}
		}
		
		TestCase.assertTrue(fullNameFound);
		if(shortName !=null)
			TestCase.assertTrue(shortNameFound);
		if(ecs.size()>0){
			TestCase.assertEquals(ecs.size(), ecFields.size());
			for(String ec: ecs){
				boolean ecsFound =false;
				for(Field field:ecFields){
					if(ec.equals(field.getValue())){
						validate(evidences.get(ec), field.getEvidenceIds());
						ecsFound =true;
						break;
					}
				}
				TestCase.assertTrue(ecsFound);
			}
		}
	}
	private DeLineObject.Name createName(String fullName){
		List<String> shortNames = new ArrayList<>();
		return createName(fullName, shortNames);
	}
	private DeLineObject.Name createName(String fullName, String shortName){
		List<String> shortNames = new ArrayList<>();
		shortNames.add(shortName);
		return createName(fullName, shortNames);
	}
	private DeLineObject.Name createName(String fullName, List<String> shortNames){
		return createName(fullName, shortNames, new ArrayList<String>());
	}
	private DeLineObject.Name createName(String fullName, List<String> shortNames, List<String> ecs){
		DeLineObject.Name name = new DeLineObject.Name();
		name.fullName =fullName;
		name.shortNames.addAll(shortNames);
		name.ecs =ecs;
		return name;
	}
}
