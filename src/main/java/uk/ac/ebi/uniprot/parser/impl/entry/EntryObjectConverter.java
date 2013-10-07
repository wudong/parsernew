package uk.ac.ebi.uniprot.parser.impl.entry;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.citationsNew.Citation;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.ac.AcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ac.UniProtAcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.gn.GnLineConverter;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineConverter;
import uk.ac.ebi.uniprot.parser.impl.kw.KwLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oc.OcLineConverter;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineConverter;
import uk.ac.ebi.uniprot.parser.impl.os.OsLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ox.OxLineConverter;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineConverter;

public class EntryObjectConverter implements Converter<EntryObject, UniProtEntry> {
	private final static AcLineConverter acLineConverter = new AcLineConverter();
	private final static CcLineConverter ccLineConverter = new CcLineConverter();
	private final static DeLineConverter deLineConverter = new DeLineConverter();
	private final static DrLineConverter drLineConverter = new DrLineConverter();
	private final static DtLineConverter dtLineConverter = new DtLineConverter();
	private final static FtLineConverter ftLineConverter = new FtLineConverter();
	private final static GnLineConverter gnLineConverter = new GnLineConverter();
	private final static IdLineConverter idLineConverter = new IdLineConverter();
	private final static KwLineConverter kwLineConverter = new KwLineConverter();
	private final static OcLineConverter ocLineConverter = new OcLineConverter();
	private final static OgLineConverter OgLineConverter = new OgLineConverter();
	
	private final static OhLineConverter ohLineConverter = new OhLineConverter();
	private final static OsLineConverter osLineConverter = new OsLineConverter();
	private final static OxLineConverter oxLineConverter = new OxLineConverter();
	private final static PeLineConverter peLineConverter = new PeLineConverter();

	private final static SqLineConverter sqLineConverter = new SqLineConverter();
	private final static SsLineConverter ssLineConverter = new SsLineConverter();
	
	private final static ReferenceObjectConverter refObjConverter = new ReferenceObjectConverter();
	
	@Override
	public UniProtEntry convert(EntryObject f) {
		UniProtEntry entry  =idLineConverter.convert(f.id);
		UniProtAcLineObject acLineObj =acLineConverter.convert(f.ac);
		entry.setPrimaryUniProtAccession(acLineObj.primaryAccession);
		entry.setSecondaryUniProtAccessions(acLineObj.secondAccessions);
		entry.setEntryAudit(dtLineConverter.convert(f.dt));
		if(f.cc !=null)
			entry.setComments(ccLineConverter.convert(f.cc));
		entry.setProteinDescription(deLineConverter.convert(f.de));
		entry.setDatabaseCrossReferencesNew(drLineConverter.convert(f.dr));
		if(f.ft !=null){
			entry.setFeatures(ftLineConverter.convert(f.ft));
		}
		if(f.gn !=null)
			entry.setGenes(gnLineConverter.convert(f.gn));
		if(f.kw !=null){
			entry.setKeywords(kwLineConverter.convert(f.kw));
		}
		entry.setTaxonomy(ocLineConverter.convert(f.oc));
		if(f.og !=null){
			entry.setOrganelles(OgLineConverter.convert(f.og));
		}
		if(f.oh !=null){
			entry.getOrganismHosts().add(ohLineConverter.convert(f.oh));
		}

		entry.setOrganism(osLineConverter.convert(f.os));
		
		entry.getNcbiTaxonomyIds().add(oxLineConverter.convert(f.ox));
		entry.setProteinExistence(peLineConverter.convert(f.pe));
		entry.setSequence(sqLineConverter.convert(f.sq));
		List<Citation> citations = new ArrayList<>();
		for(EntryObject.ReferenceObject refObj: f.ref){
			citations.add(refObjConverter.convert(refObj));
		}
		entry.setCitationsNew(citations);
		//star star line not available
		return entry;
	}

}
