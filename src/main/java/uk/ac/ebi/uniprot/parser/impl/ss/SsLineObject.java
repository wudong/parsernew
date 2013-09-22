package uk.ac.ebi.uniprot.parser.impl.ss;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


/**
 * <p/>
 * User: wudong, Date: 22/09/13, Time: 23:12
 */
public class SsLineObject {


	public List<Ev> evs = new ArrayList<Ev>();

	public static class Ev {
		public String evtag;
		public String xref;
		public String value_1;
		public String value_2;
		public DateTime date;
	}
}
