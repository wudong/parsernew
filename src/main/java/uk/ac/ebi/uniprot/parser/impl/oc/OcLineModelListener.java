package uk.ac.ebi.uniprot.parser.impl.oc;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OcLineParser;
import uk.ac.ebi.uniprot.parser.antlr.OcLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OcLineModelListener extends OcLineParserBaseListener implements ParseTreeObjectExtractor<OcLineObject> {

	private OcLineObject object = new OcLineObject();

	@Override
	public void exitOc(@NotNull OcLineParser.OcContext ctx) {
		object.nodes.add(ctx.getText());
	}

	public OcLineObject getObject() {
		return object;
	}
}
