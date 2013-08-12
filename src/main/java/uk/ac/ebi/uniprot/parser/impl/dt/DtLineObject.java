package uk.ac.ebi.uniprot.parser.impl.dt;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 12/08/13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class DtLineObject {
    public int entry_version;
    public DateTime entry_date;
    public int seq_version;
    public DateTime seq_date;
    public DateTime integration_date;
    public Boolean isSiwssprot;
}
