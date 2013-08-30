package uk.ac.ebi.uniprot.parser;


public interface Converter<F, T> {
	T convert(F f);
}
