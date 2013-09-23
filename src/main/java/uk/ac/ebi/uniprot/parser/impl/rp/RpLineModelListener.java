package uk.ac.ebi.uniprot.parser.impl.rp;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RpLineParser;
import uk.ac.ebi.uniprot.parser.antlr.RpLineParserBaseListener;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RpLineModelListener extends RpLineParserBaseListener implements ParseTreeObjectExtractor<RpLineObject> {

	private RpLineObject object = new RpLineObject();

	public RpLineObject getObject() {
		return object;
	}

	@Override
	public void exitRp_scope(@NotNull RpLineParser.Rp_scopeContext ctx) {
		String scope = ctx.getText();
		object.scopes.add(scope);
	}
}
