package uk.ac.ebi.uniprot.parser.impl.rl;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * User: wudong, Date: 21/08/13, Time: 16:44
 */
public class RlObject {

	private Ref reference;

	public static interface Ref {
	}

	public static class Book implements Ref {
		public List<String> editors = new ArrayList<String>();
		public String title;
	}

	public static class Thesis implements Ref {
		public String title;
	}

	public static class JournalArticle implements Ref {
		public String title;
	}

	public static class EPub implements Ref {
		public String title;
	}

	public static class Patent implements Ref {
		public String patentNumber;
	}

	public static class Unpublished implements Ref {

	}

	public static class Submission implements Ref {

	}

}
