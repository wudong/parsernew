package uk.ac.ebi.uniprot.parser.impl.og;

import org.antlr.v4.runtime.misc.NotNull;

import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OgLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.OgLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OgLineModelListener extends OgLineBaseListener implements ParseTreeObjectExtractor<OgLineObject> {



	private OgLineObject object = new OgLineObject();

	@Override
	public void exitPlasmid_name(@NotNull OgLineParser.Plasmid_nameContext ctx) {
		object.plasmidName.add(ctx.getText());
	}

	public OgLineObject getObject() {
		return object;
	}
}
