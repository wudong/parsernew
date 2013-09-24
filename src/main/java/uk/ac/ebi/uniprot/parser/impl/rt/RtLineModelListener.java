package uk.ac.ebi.uniprot.parser.impl.rt;

import org.antlr.v4.runtime.WritableToken;
import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.*;
import uk.ac.ebi.uniprot.parser.impl.rt.RtLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RtLineModelListener extends RtLineParserBaseListener implements ParseTreeObjectExtractor<RtLineObject> {

    private RtLineObject object = new RtLineObject();

	@Override
	public void exitRt_line(@NotNull RtLineParser.Rt_lineContext ctx) {
		object.title = ctx.getText();
	}

	public RtLineObject getObject() {
        return object;
    }
}
