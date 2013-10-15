package uk.ac.ebi.uniprot.parser.impl.ss;

import java.util.ArrayList;
import java.util.List;


/**
 * <p/>
 * User: wudong, Date: 22/09/13, Time: 23:12
 */
public class SsLineObject {

	public List<SsLine> ssIALines = new ArrayList<SsLine>() ;
	public List<String> ssSourceLines = new ArrayList<String>();

    public static class SsLine {
        public String topic;
        public String text;
    }
}
