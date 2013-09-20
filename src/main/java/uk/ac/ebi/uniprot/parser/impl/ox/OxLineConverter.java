package uk.ac.ebi.uniprot.parser.impl.ox;

import java.util.List;
import java.util.Map;

import uk.ac.ebi.kraken.interfaces.uniprot.NcbiTaxonomyId;
import uk.ac.ebi.kraken.interfaces.uniprot.evidences.EvidenceId;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class OxLineConverter implements Converter<OxLineObject, NcbiTaxonomyId> {

	@Override
	public NcbiTaxonomyId convert(OxLineObject f) {
		Map<Object, List<EvidenceId> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		NcbiTaxonomyId taxid = DefaultUniProtFactory.getInstance().buildNcbiTaxonomyId();
		taxid.setValue("" +f.taxonomy_id);
		EvidenceHelper.setEvidences(taxid, evidences, f.taxonomy_id);
		return taxid;
	}

}
