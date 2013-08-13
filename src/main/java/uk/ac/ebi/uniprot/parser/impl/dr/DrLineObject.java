package uk.ac.ebi.uniprot.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 13/08/13
 * Time: 09:20
 * To change this template use File | Settings | File Templates.
 */
public class DrLineObject {

    public List<DrObject> drObjects = new ArrayList<DrObject>();

    public static class DrObject {
        public String DbName;
        public List<String> attributes = new ArrayList<String>();
    }
}
