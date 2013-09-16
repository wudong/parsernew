package uk.ac.ebi.uniprot.parser.impl.sq;

import uk.ac.ebi.kraken.interfaces.common.Sequence;
import uk.ac.ebi.kraken.model.factories.DefaultUniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class SqLineConverter implements Converter<SqLineObject, Sequence> {
	private final DefaultUniProtFactory factory= DefaultUniProtFactory.getInstance();
	@Override
	public Sequence convert(SqLineObject f) {
		Sequence seq = factory.buildSequence();
		seq.setCRC64(f.crc64);
		seq.setMolecularWeight(f.weight);
		seq.setValue(f.sequence);
		return seq;
	}

}
