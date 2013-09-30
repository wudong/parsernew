package uk.ac.ebi.uniprot.parser.impl.rc;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RcLineParser;
import uk.ac.ebi.uniprot.parser.antlr.RcLineParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RcLineModelListener extends RcLineParserBaseListener implements ParseTreeObjectExtractor<RcLineObject> {

    private RcLineObject object = new RcLineObject();

	@Override
    public void exitRc(@NotNull RcLineParser.RcContext ctx) {
        RcLineParser.Rc_tokenContext rc_tokenContext = ctx.rc_token();
        RcLineObject.RcTokenEnum type = null;
        if (rc_tokenContext.PLASMID() != null) {
            type = RcLineObject.RcTokenEnum.PLASMID;
        } else if (rc_tokenContext.STRAIN() != null) {
            type = RcLineObject.RcTokenEnum.STRAIN;
        } else if (rc_tokenContext.TISSUE() != null) {
            type = RcLineObject.RcTokenEnum.TISSUE;
        } else if (rc_tokenContext.TRANSPOSON() != null) {
            type = RcLineObject.RcTokenEnum.TRANSPOSON;
        }

        RcLineObject.RC rc = new RcLineObject.RC();
        rc.tokenType=type;

        List<RcLineParser.Rc_valueContext> rc_valueContexts = ctx.rc_text().rc_value();
        for (RcLineParser.Rc_valueContext rc_valueContext : rc_valueContexts) {
            String text = rc_valueContext.rc_value_v().getText();
            rc.values.add(text);

	        RcLineParser.EvidenceContext evidence = rc_valueContext.evidence();
	        if (evidence !=null){
		        EvidenceInfo.processEvidence(rc.getEvidenceInfo(), text, evidence.EV_TAG());
	        }
        }
        object.rcs.add(rc);
    }

    public RcLineObject getObject() {
        return object;
    }
}
