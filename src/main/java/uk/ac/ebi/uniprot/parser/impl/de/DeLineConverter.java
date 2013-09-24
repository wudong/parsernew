package uk.ac.ebi.uniprot.parser.impl.de;

import uk.ac.ebi.kraken.interfaces.uniprot.ProteinDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.description.*;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeLineConverter implements Converter<DeLineObject, ProteinDescription> {
	private final DefaultUniProtFactory factory = DefaultUniProtFactory.getInstance();
	@Override
	public ProteinDescription convert(DeLineObject f) {
		 Map<Object, List<EvidenceId>> evidenceMap =EvidenceHelper.convert(f.getEvidenceInfo());
		ProteinDescription pd = factory.buildProteinDescription();
		if(f.recName !=null){
			pd.setRecommendedName(convert(f.recName,NameType.RECNAME, evidenceMap));
		}
		List<Name> altNames = new ArrayList<>();
		for(DeLineObject.Name altName: f.altName){
			altNames.add(convert(altName, NameType.ALTNAME, evidenceMap));
		}
		if(!f.alt_CD_antigen.isEmpty()){
			Name aName = convert(f.alt_CD_antigen, NameType.ALTNAME, FieldType.CD_ANTIGEN, evidenceMap);
			altNames.add(aName);
		}
		if(!f.alt_INN.isEmpty()){
			Name aName =convert(f.alt_INN, NameType.ALTNAME, FieldType.INN, evidenceMap);
			altNames.add(aName);
		}
		if((f.alt_Biotech !=null) &&(!f.alt_Biotech.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(f.alt_Biotech);
			Name aName =convert(values, NameType.ALTNAME, FieldType.BIOTECH, evidenceMap);
			altNames.add(aName);
		}
		if((f.alt_Allergen !=null) &&(!f.alt_Allergen.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(f.alt_Allergen);
			Name aName =convert(values, NameType.ALTNAME, FieldType.ALLERGEN, evidenceMap);
			altNames.add(aName);
		}
		pd.setAlternativeNames(altNames);
		List<Name> subNames = new ArrayList<>();
		for(DeLineObject.Name subName: f.subName){
			subNames.add(convert(subName, NameType.SUBNAME, evidenceMap));
		}
		pd.setSubNames(subNames);
		List<Section> contained = new ArrayList<>();
		for(DeLineObject.NameBlock containedName : f.containedNames){
			contained.add(convert(containedName, evidenceMap));
		}
		pd.setContains(contained);
		List<Section> included = new ArrayList<>();
		for(DeLineObject.NameBlock includedName : f.includedNames){
			included.add(convert(includedName, evidenceMap));
		}
		pd.setIncludes(included);
		List<Flag> flags =new ArrayList<>();

		for (DeLineObject.FlagType deflag: f.flags)   {
			switch(deflag){
			case Precursor:
				Flag flag =factory.buildFlag(FlagType.PRECURSOR);
				EvidenceHelper.setEvidences( flag, evidenceMap,deflag);
				flags.add(flag);
				break;
			case Fragment:
				Flag fflag =factory.buildFlag(FlagType.FRAGMENT);
				EvidenceHelper.setEvidences( fflag, evidenceMap, deflag);
				flags.add(fflag);
				break;
			case Precursor_Fragment:
				Flag pflag =factory.buildFlag(FlagType.PRECURSOR);
				EvidenceHelper.setEvidences( pflag, evidenceMap,deflag);
				flags.add(pflag);
				Flag fflag1 =factory.buildFlag(FlagType.FRAGMENT);
				EvidenceHelper.setEvidences( fflag1, evidenceMap, deflag);
				flags.add(fflag1);
			
				break;
			case Fragments:
				Flag fflag2 =factory.buildFlag(FlagType.FRAGMENTS);
				EvidenceHelper.setEvidences( fflag2, evidenceMap, deflag);
				flags.add(fflag2);
			break;
			}		
			pd.setFlags(flags);
		}
		return pd;
	}
	
	
	private Section convert(DeLineObject.NameBlock nameBlock, Map<Object, List<EvidenceId>> evidenceMap){
		Section section = factory.buildSection();
		List<Name> allNames = new ArrayList<>();
		if(nameBlock.recName !=null){
			allNames.add(convert(nameBlock.recName,NameType.RECNAME, evidenceMap ));
		}
		for(DeLineObject.Name altName: nameBlock.altName){
			allNames.add(convert(altName, NameType.ALTNAME, evidenceMap));
		}
		if(!nameBlock.alt_CD_antigen.isEmpty()){
			Name aName = convert(nameBlock.alt_CD_antigen, NameType.ALTNAME, FieldType.CD_ANTIGEN, evidenceMap);
			allNames.add(aName);
		}
		if(!nameBlock.alt_INN.isEmpty()){
			Name aName =convert(nameBlock.alt_INN, NameType.ALTNAME, FieldType.INN, evidenceMap);
			allNames.add(aName);
		}
		if((nameBlock.alt_Biotech !=null) &&(!nameBlock.alt_Biotech.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(nameBlock.alt_Biotech);
			Name aName =convert(values, NameType.ALTNAME, FieldType.BIOTECH, evidenceMap);
			allNames.add(aName);
		}
		if((nameBlock.alt_Allergen !=null) &&(!nameBlock.alt_Allergen.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(nameBlock.alt_Allergen);
			Name aName =convert(values, NameType.ALTNAME, FieldType.ALLERGEN, evidenceMap);
			allNames.add(aName);
		}
		for(DeLineObject.Name subName: nameBlock.subName){
			allNames.add(convert(subName, NameType.SUBNAME, evidenceMap));
		}
		section.setNames(allNames);
		return section;
	}
	private Name convert(List<String> vals, NameType nameType, FieldType fieldType,
			Map<Object, List<EvidenceId>> evidenceMap){
		Name aName = factory.buildName();
		aName.setNameType(nameType);
		List<Field> fields = new ArrayList<>();
		for(String val:vals){
			Field field = factory.buildField();
			field.setType(fieldType);
			field.setValue(val);
			EvidenceHelper.setEvidences( field, evidenceMap, val);
			fields.add(field);
		}
		return aName;
	}
	private Name convert(DeLineObject.Name val, NameType nameType, 
			 Map<Object, List<EvidenceId>> evidenceMap){
		Name aName = factory.buildName();
		aName.setNameType(nameType);
		aName.setFields(convert(val, evidenceMap));
		return aName;
	
	}

	private List<Field> convert(DeLineObject.Name val,  Map<Object, List<EvidenceId>> evidenceMap){
		List<Field> fields = new ArrayList<>();
		if((val.fullName !=null) ||(!val.fullName.isEmpty())){
			Field field = factory.buildField();
			field.setType(FieldType.FULL);
			field.setValue(val.fullName);
			EvidenceHelper.setEvidences( field, evidenceMap, val.fullName);
			fields.add(field);
		}
		for(String shortName:val.shortNames){
			Field field = factory.buildField();
			field.setType(FieldType.SHORT);
			field.setValue(shortName);
			EvidenceHelper.setEvidences(field, evidenceMap, shortName);
			fields.add(field);
		}
		for(String ec:val.ecs){
			Field field = factory.buildField();
			field.setType(FieldType.EC);
			field.setValue(ec);
			EvidenceHelper.setEvidences(field, evidenceMap, ec);
			fields.add(field);
		}
		return fields;
	}

}
