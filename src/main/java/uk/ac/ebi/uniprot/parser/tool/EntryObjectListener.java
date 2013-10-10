package uk.ac.ebi.uniprot.parser.tool;

import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 09/10/2013
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
public interface EntryObjectListener {

    public void processEntryObject(EntryObject object);
}
