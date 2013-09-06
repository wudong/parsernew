package uk.ac.ebi.uniprot.parser.impl.cc;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.WritableToken;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.CcLineParserBaseListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class CcLineModelListener extends CcLineParserBaseListener implements ParseTreeObjectExtractor<CcLineObject> {

	private CcLineObject object = new CcLineObject();


	public CcLineObject getObject() {
		return object;
	}
}
