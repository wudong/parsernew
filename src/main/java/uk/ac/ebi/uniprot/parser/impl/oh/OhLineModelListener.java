package uk.ac.ebi.uniprot.parser.impl.oh;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OhLineParser;
import uk.ac.ebi.uniprot.parser.antlr.OhLineParserBaseListener;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OhLineModelListener extends OhLineParserBaseListener implements ParseTreeObjectExtractor<OhLineObject> {

	private OhLineObject object = new OhLineObject();

	@Override
	public void exitHostname(@NotNull OhLineParser.HostnameContext ctx) {
		object.hostname = ctx.getText();
	}

	@Override
	public void exitTax(@NotNull OhLineParser.TaxContext ctx) {
		object.tax_id = Integer.parseInt(ctx.getText());
	}

	public OhLineObject getObject() {
		return object;
	}
}
