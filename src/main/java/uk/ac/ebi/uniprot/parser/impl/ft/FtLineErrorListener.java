package uk.ac.ebi.uniprot.parser.impl.ft;

import uk.ac.ebi.uniprot.parser.DefaultErrorListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 23/10/2013
 * Time: 12:35
 * To change this template use File | Settings | File Templates.
 */
public class FtLineErrorListener extends DefaultErrorListener{
    public FtLineErrorListener(boolean inLax) {
        super(inLax);
    }
}
