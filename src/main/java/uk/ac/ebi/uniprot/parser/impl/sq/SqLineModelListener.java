package uk.ac.ebi.uniprot.parser.impl.sq;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.SqLineParser;
import uk.ac.ebi.uniprot.parser.antlr.SqLineParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.dt.DtLineObject;
import uk.ac.ebi.uniprot.parser.impl.sq.SqLineObject;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class SqLineModelListener extends SqLineParserBaseListener implements ParseTreeObjectExtractor<SqLineObject> {

    private SqLineObject object = new SqLineObject();
    private StringBuilder sb = new StringBuilder();

    @Override
    public void exitSq_letters_10(@NotNull SqLineParser.Sq_letters_10Context ctx) {
        sb.append(ctx.getText());
    }

    @Override
    public void exitCrc(@NotNull SqLineParser.CrcContext ctx) {
        object.crc64 = ctx.getText();
    }

	@Override
	public void exitMolecular_weight(@NotNull SqLineParser.Molecular_weightContext ctx) {
		object.weight = Integer.parseInt(ctx.getText());
	}

	@Override
    public void exitSq_letters(@NotNull SqLineParser.Sq_lettersContext ctx) {
        sb.append(ctx.getText());
    }

    @Override
    public void exitSq_length(@NotNull SqLineParser.Sq_lengthContext ctx) {
        object.length = Integer.parseInt(ctx.getText());
    }

    @Override
    public void exitSq_block(@NotNull SqLineParser.Sq_blockContext ctx) {
        object.sequence = sb.toString();
    }

    public SqLineObject getObject() {
        return object;
    }
}
