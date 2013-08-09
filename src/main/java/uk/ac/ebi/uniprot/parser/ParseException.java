package uk.ac.ebi.uniprot.parser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class ParseException extends RuntimeException{

    public ParseException(){}

    public ParseException(String s){
        super(s);
    }
}
