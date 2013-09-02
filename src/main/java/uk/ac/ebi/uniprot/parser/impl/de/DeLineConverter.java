package uk.ac.ebi.uniprot.parser.impl.de;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.ProteinDescription;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Flag;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FlagType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Name;
import uk.ac.ebi.kraken.interfaces.uniprot.description.NameType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Section;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;

public class DeLineConverter implements Converter<DeLineObject, ProteinDescription> {
	private final DefaultUniProtFactory factory = DefaultUniProtFactory.getInstance();
	@Override
	public ProteinDescription convert(DeLineObject f) {
		ProteinDescription pd = factory.buildProteinDescription();
		if(f.recName !=null){
			pd.setRecommendedName(convert(f.recName,NameType.RECNAME ));
		}
		List<Name> altNames = new ArrayList<>();
		for(DeLineObject.Name altName: f.altName){
			altNames.add(convert(altName, NameType.ALTNAME));
		}
		if(!f.alt_CD_antigen.isEmpty()){
			Name aName = convert(f.alt_CD_antigen, NameType.ALTNAME, FieldType.CD_ANTIGEN);
			altNames.add(aName);
		}
		if(!f.alt_INN.isEmpty()){
			Name aName =convert(f.alt_INN, NameType.ALTNAME, FieldType.INN);
			altNames.add(aName);
		}
		if((f.alt_Biotech !=null) &&(!f.alt_Biotech.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(f.alt_Biotech);
			Name aName =convert(values, NameType.ALTNAME, FieldType.BIOTECH);
			altNames.add(aName);
		}
		if((f.alt_Allergen !=null) &&(!f.alt_Allergen.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(f.alt_Allergen);
			Name aName =convert(values, NameType.ALTNAME, FieldType.ALLERGEN);
			altNames.add(aName);
		}
		pd.setAlternativeNames(altNames);
		List<Name> subNames = new ArrayList<>();
		for(DeLineObject.Name subName: f.subName){
			subNames.add(convert(subName, NameType.SUBNAME));
		}
		pd.setSubNames(subNames);
		List<Section> contained = new ArrayList<>();
		for(DeLineObject.NameBlock containedName : f.containedNames){
			contained.add(convert(containedName));
		}
		pd.setContains(contained);
		List<Section> included = new ArrayList<>();
		for(DeLineObject.NameBlock includedName : f.includedNames){
			included.add(convert(includedName));
		}
		pd.setIncludes(included);
		List<Flag> flags =new ArrayList<>();
		if(f.flag !=null){
			switch(f.flag){
			case Precursor:
				Flag flag =factory.buildFlag(FlagType.PRECURSOR);
				flags.add(flag);
				break;
			case Fragment:
				Flag fflag =factory.buildFlag(FlagType.FRAGMENT);
				flags.add(fflag);
			
				break;
			case Precursor_Fragment:
				Flag pflag =factory.buildFlag(FlagType.PRECURSOR);
				flags.add(pflag);
				Flag fflag1 =factory.buildFlag(FlagType.FRAGMENT);
				flags.add(fflag1);
			
				break;
			case Fragments:
				Flag fflag2 =factory.buildFlag(FlagType.FRAGMENTS);
				flags.add(fflag2);
			break;
			}		
			pd.setFlags(flags);
		}
		return pd;
	}
	
	
	private Section convert(DeLineObject.NameBlock nameBlock){
		Section section = factory.buildSection();
		List<Name> allNames = new ArrayList<>();
		if(nameBlock.recName !=null){
			allNames.add(convert(nameBlock.recName,NameType.RECNAME ));
		}
		for(DeLineObject.Name altName: nameBlock.altName){
			allNames.add(convert(altName, NameType.ALTNAME));
		}
		if(!nameBlock.alt_CD_antigen.isEmpty()){
			Name aName = convert(nameBlock.alt_CD_antigen, NameType.ALTNAME, FieldType.CD_ANTIGEN);
			allNames.add(aName);
		}
		if(!nameBlock.alt_INN.isEmpty()){
			Name aName =convert(nameBlock.alt_INN, NameType.ALTNAME, FieldType.INN);
			allNames.add(aName);
		}
		if((nameBlock.alt_Biotech !=null) &&(!nameBlock.alt_Biotech.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(nameBlock.alt_Biotech);
			Name aName =convert(values, NameType.ALTNAME, FieldType.BIOTECH);
			allNames.add(aName);
		}
		if((nameBlock.alt_Allergen !=null) &&(!nameBlock.alt_Allergen.isEmpty())){
			List<String> values = new ArrayList<>();
			values.add(nameBlock.alt_Allergen);
			Name aName =convert(values, NameType.ALTNAME, FieldType.ALLERGEN);
			allNames.add(aName);
		}
		for(DeLineObject.Name subName: nameBlock.subName){
			allNames.add(convert(subName, NameType.SUBNAME));
		}
		section.setNames(allNames);
		return section;
	}
	private Name convert(List<String> vals, NameType nameType, FieldType fieldType){
		Name aName = factory.buildName();
		aName.setNameType(nameType);
		List<Field> fields = new ArrayList<>();
		for(String val:vals){
			Field field = factory.buildField();
			field.setType(fieldType);
			field.setValue(val);
			fields.add(field);
		}
		return aName;
	}
	private Name convert(DeLineObject.Name val, NameType nameType){
		Name aName = factory.buildName();
		aName.setNameType(nameType);
		aName.setFields(convert(val));
		return aName;
	
	}
	private List<Field> convert(DeLineObject.Name val){
		List<Field> fields = new ArrayList<>();
		if((val.fullName !=null) ||(!val.fullName.isEmpty())){
			Field field = factory.buildField();
			field.setType(FieldType.FULL);
			field.setValue(val.fullName);
			fields.add(field);
		}
		for(String shortName:val.shortNames){
			Field field = factory.buildField();
			field.setType(FieldType.SHORT);
			field.setValue(shortName);
			fields.add(field);
		}
		for(String ec:val.ecs){
			Field field = factory.buildField();
			field.setType(FieldType.EC);
			field.setValue(ec);
			fields.add(field);
		}
		return fields;
	}

}
