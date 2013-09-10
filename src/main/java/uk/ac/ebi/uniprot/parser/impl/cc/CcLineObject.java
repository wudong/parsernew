package uk.ac.ebi.uniprot.parser.impl.cc;

/**
 * <p/>
 * User: wudong, Date: 03/09/13, Time: 16:35
 */
public class CcLineObject {

	public CCTopicEnum topic;
	public String text;

	public static enum CCTopicEnum{
		ALLERGEN,FUNCTION
	}

}
