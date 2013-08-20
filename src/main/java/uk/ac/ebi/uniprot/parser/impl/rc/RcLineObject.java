package uk.ac.ebi.uniprot.parser.impl.rc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class RcLineObject {

    public List<RC> rcs = new ArrayList<RC>();

    public static enum RcTokenEnum {
        STRAIN,
        PLASMID,
        TRANSPOSON,
        TISSUE
    }

    public static class RC {
        public RcTokenEnum tokenType;
        public List<String> values = new ArrayList<String>();
    }


}
