package uk.ac.ebi.uniprot.parser.impl.ox;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OxLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.OxLineParser;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OxLineModelListener extends OxLineBaseListener implements ParseTreeObjectExtractor<OxLineObject> {

	private OxLineObject object = new OxLineObject();

	@Override
	public void exitTax(@NotNull OxLineParser.TaxContext ctx) {
		String text = ctx.getText();
		object.taxonomy_id = Integer.parseInt(text);
	}

	public OxLineObject getObject() {
		return object;
	}
}
