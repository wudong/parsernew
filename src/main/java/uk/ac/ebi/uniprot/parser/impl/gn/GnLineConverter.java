package uk.ac.ebi.uniprot.parser.impl.gn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.Gene;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.GeneName;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.GeneNameSynonym;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.ORFName;
import uk.ac.ebi.kraken.interfaces.uniprot.genename.OrderedLocusName;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfoConverter;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject.GnName;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineObject.GnObject;

public class GnLineConverter implements Converter<GnLineObject, List<Gene>> {
	private final EvidenceInfoConverter evConverter = new EvidenceInfoConverter();
	@Override
	public List<Gene> convert(GnLineObject f) {
		List<Gene> genes = new ArrayList<>();
		for(GnObject gno :f.gnObjects){
			Gene gene = DefaultUniProtFactory.getInstance().buildGene();
			for(GnName gn:gno.names){
				Map<Object, List<EvidenceId> > evidences = evConverter.convert(gn.getEvidenceInfo());
				switch(gn.type){
				case GENAME:
					if(!gn.names.isEmpty()){
						GeneName geneName = DefaultUniProtFactory.getInstance().buildGeneName();
						geneName.setValue(gn.names.get(0));
						EvidenceHelper.setEvidences(geneName, evidences, gn.names.get(0));
						gene.setGeneName(geneName);
					
					
					}
					break;				
				case SYNNAME:
					List<GeneNameSynonym> synonyms = new ArrayList<>();
					for(String name:gn.names){
						GeneNameSynonym synonym = DefaultUniProtFactory.getInstance().buildGeneNameSynonym();
						synonym.setValue(name);
						EvidenceHelper.setEvidences(synonym, evidences, name);
						synonyms.add(synonym);
					}
					gene.setGeneNameSynonyms(synonyms);
					break;
				case ORFNAME:
					List<ORFName> orfNames = new ArrayList<>();
					for(String name:gn.names){
						ORFName orfname = DefaultUniProtFactory.getInstance().buildORFName();
						orfname.setValue(name);
						EvidenceHelper.setEvidences(orfname, evidences, name);
						orfNames.add(orfname);
					}
					gene.setORFNames(orfNames);
					break;
				case OLNAME:
					List<OrderedLocusName> olnames = new ArrayList<>();
					for(String name:gn.names){
						OrderedLocusName olname = DefaultUniProtFactory.getInstance().buildOrderedLocusName();
						olname.setValue(name);
						EvidenceHelper.setEvidences(olname, evidences, name);
						olnames.add(olname);
					}
					gene.setOrderedLocusNames(olnames);
					break;
				}
			}
			genes.add(gene);
		}
		return genes;
	}
}
