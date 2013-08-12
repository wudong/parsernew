package uk.ac.ebi.uniprot.parser.impl.dt;

import org.antlr.v4.runtime.misc.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.ac.ebi.uniprot.parser.antlr.DtLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.DtLineParser;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class DtLineModelListener extends DtLineBaseListener {

    private DtLineObject object = new DtLineObject();
    private DateTimeFormatter formatter
            = DateTimeFormat.forPattern("dd-MMM-yyyy");

    @Override
    public void exitDt_entryver_line(@NotNull DtLineParser.Dt_entryver_lineContext ctx) {
        String text = ctx.dt_date().DATE().getText();
        object.entry_date = DateTime.parse(text, formatter);

        String text1 = ctx.dt_version().getText();
        object.entry_version = Integer.parseInt(text1);
    }

    @Override
    public void exitDt_integration_line(@NotNull DtLineParser.Dt_integration_lineContext ctx) {
        String text = ctx.dt_date().DATE().getText();
        object.integration_date = DateTime.parse(text, formatter);
        if (ctx.dt_database().SWISSPROT() != null) {
            object.isSiwssprot = true;
        } else if (ctx.dt_database().TREMBL() != null) {
            object.isSiwssprot = false;
        }
    }

    @Override
    public void exitDt_seqver_line(@NotNull DtLineParser.Dt_seqver_lineContext ctx) {
        String text = ctx.dt_date().DATE().getText();
        object.seq_date = DateTime.parse(text, formatter);

        String text1 = ctx.dt_version().getText();
        object.seq_version = Integer.parseInt(text1);
    }

    public DtLineObject getObject() {
        return object;
    }
}
