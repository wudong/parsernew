package uk.ac.ebi.uniprot.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.DBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.FourFieldDBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.ThreeFieldDBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.TwoFieldDBCrossReference;
import uk.ac.ebi.kraken.interfaces.uniprot.dbxNew.XRefDBType;
import uk.ac.ebi.kraken.model.factories.DefaultXRefNewFactory;

public class DrLineConverter implements Converter<DrLineObject, List<DBCrossReference> > {
	private final DefaultXRefNewFactory factory =DefaultXRefNewFactory.getInstance();
	@Override
	public List<DBCrossReference> convert(DrLineObject f) {
		List<DBCrossReference> dbXrefs = new ArrayList<>();
		for(DrLineObject.DrObject drline: f.drObjects){
			XRefDBType dbtype =XRefDBType.getType(drline.DbName);
			DBCrossReference xref =factory.buildDBCrossReference(dbtype);
			if(xref instanceof TwoFieldDBCrossReference){
				((TwoFieldDBCrossReference) xref).setFirst(factory.buildXDBAttribute(drline.attributes.get(0)));
				((TwoFieldDBCrossReference) xref).setSecond(factory.buildXDBAttribute(drline.attributes.get(1)));
				
			}else if(xref instanceof ThreeFieldDBCrossReference){
				((ThreeFieldDBCrossReference) xref).setFirst(factory.buildXDBAttribute(drline.attributes.get(0)));
				((ThreeFieldDBCrossReference) xref).setSecond(factory.buildXDBAttribute(drline.attributes.get(1)));
				((ThreeFieldDBCrossReference) xref).setThird(factory.buildXDBAttribute(drline.attributes.get(2)));
			}else if(xref instanceof FourFieldDBCrossReference){
				((FourFieldDBCrossReference) xref).setFirst(factory.buildXDBAttribute(drline.attributes.get(0)));
				((FourFieldDBCrossReference) xref).setSecond(factory.buildXDBAttribute(drline.attributes.get(1)));
				((FourFieldDBCrossReference) xref).setThird(factory.buildXDBAttribute(drline.attributes.get(2)));
				((FourFieldDBCrossReference) xref).setFourth(factory.buildXDBAttribute(drline.attributes.get(3)));
			}
			//set evidence ids
			dbXrefs.add(xref);
		}
		return dbXrefs;
	}

}
