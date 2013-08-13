package uk.ac.ebi.uniprot.parser.impl.gn;

import uk.ac.ebi.uniprot.parser.antlr.GnLineBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class GnLineModelListener extends GnLineBaseListener {

    private GnLineObject object = new GnLineObject();

    public GnLineObject getObject() {
        return object;
    }
}
