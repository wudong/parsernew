package uk.ac.ebi.uniprot.parser.tool;

import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 09/10/2013
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
public class ListenerJob implements Runnable{

    private final EntryObjectListener listener;
    private final EntryObject object;

    public ListenerJob(EntryObjectListener listener, EntryObject object) {

        this.listener = listener;
        this.object = object;
    }

    @Override
    public void run() {
        listener.processEntryObject(object);
    }
}
