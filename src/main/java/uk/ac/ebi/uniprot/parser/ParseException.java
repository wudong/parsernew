package uk.ac.ebi.uniprot.parser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class ParseException extends RuntimeException{

	private final String parsingString;

    public ParseException(String message, String parsingString, Throwable parent){
		super(message, parent);
	    this.parsingString = parsingString;
    }

	public String getParsingString() {
		return parsingString;
	}

}
