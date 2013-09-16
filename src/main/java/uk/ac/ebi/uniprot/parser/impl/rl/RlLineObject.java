package uk.ac.ebi.uniprot.parser.impl.rl;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * User: wudong, Date: 21/08/13, Time: 16:44
 */
public class RlLineObject {

    public Ref reference;

    public static interface Ref {
    }

    public static class Book implements Ref {
        public List<String> editors = new ArrayList<String>();
        public String title;
        public int year;
        public int page_start;
        public int page_end;
        public String press;
        public String place;
        public int volume;
    }

    public static class Thesis implements Ref {
        public String institute;
        public String country;
        public int year;
        //missing possible city
    }

    public static class JournalArticle implements Ref {
        public String journal;
        public int year;
        public int volume;
        public int first_page;
        public int last_page;
    }

    public static class EPub implements Ref {
        public String title;
    }

    public static class Patent implements Ref {
        public String patentNumber;
        public int year;
        public String month;
        public int day;
    }

    public static class Unpublished implements Ref {
        public String month;
        public int year;
    }

    public static class Submission implements Ref {
        public SubmissionDB db;
        public int year;
        public String month;
    }

    public static enum SubmissionDB {
        EMBL,
        UNIPROTKB,
        PDB,
        PIR
    }

}
