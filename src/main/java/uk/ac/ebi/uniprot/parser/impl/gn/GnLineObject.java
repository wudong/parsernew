package uk.ac.ebi.uniprot.parser.impl.gn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class GnLineObject {

    public List<GnObject> gnObjects = new ArrayList<GnObject>();

    public static class GnObject {
        public String gene_name;
        public List<String> syn_name = new ArrayList<String>();
        public List<String> orf_name = new ArrayList<String>();
        public List<String> ol_name = new ArrayList<String>();
    }

}
