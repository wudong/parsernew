package uk.ac.ebi.uniprot.parser.impl.ft;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.FtLineParser;
import uk.ac.ebi.uniprot.parser.antlr.FtLineParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class FtLineModelListener extends FtLineParserBaseListener implements ParseTreeObjectExtractor<FtLineObject> {

	private FtLineObject object = new FtLineObject();

	private FtLineObject.FT ft ;

	@Override
	public void enterFt_line(@NotNull FtLineParser.Ft_lineContext ctx) {
		ft = new  FtLineObject.FT();
	}

	@Override
	public void exitFt_line(@NotNull FtLineParser.Ft_lineContext ctx) {
		object.fts.add(ft);
		ft = null;
	}

	@Override
	public void exitFt_key(@NotNull FtLineParser.Ft_keyContext ctx) {
	   ft.type = FtLineObject.FTType.valueOf(ctx.getText());
	}

	@Override
	public void exitEvidence(@NotNull FtLineParser.EvidenceContext ctx) {
		EvidenceInfo.processEvidence(object.getEvidenceInfo(), ft, ctx.EV_TAG());
	}

	@Override
	public void exitLoc_end(@NotNull FtLineParser.Loc_endContext ctx) {
		ft.location_end = ctx.FT_LOCATION().getText().trim();
	}

	@Override
	public void exitLoc_start(@NotNull FtLineParser.Loc_startContext ctx) {
		ft.location_start = ctx.FT_LOCATION().getText().trim();
	}

	@Override
	public void exitFt_text(@NotNull FtLineParser.Ft_textContext ctx) {
		ft.ft_text= ctx.getText();
	}

	@Override
	public void exitFt_id(@NotNull FtLineParser.Ft_idContext ctx) {
		ft.ftId = ctx.ID_WORD().getText();
	}

	public FtLineObject getObject() {
		return object;
	}
}
