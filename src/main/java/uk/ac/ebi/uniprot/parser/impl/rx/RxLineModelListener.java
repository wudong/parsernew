package uk.ac.ebi.uniprot.parser.impl.rx;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RxLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.RxLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RxLineModelListener extends RxLineBaseListener implements ParseTreeObjectExtractor<RxLineObject> {

    private RxLineObject object = new RxLineObject();

    @Override
    public void exitRx(@NotNull RxLineParser.RxContext ctx) {
        RxLineObject.RX rx = new RxLineObject.RX();
        if (ctx.agri() != null) {
            String text = ctx.agri().agriid().getText();
            rx.type = RxLineObject.DB.AGRICOLA;
            rx.value = text;
        } else if (ctx.pubmed() != null) {
            String text = ctx.pubmed().pubid().getText();
            rx.type = RxLineObject.DB.PubMed;
            rx.value = text;
        } else if (ctx.doi() != null) {
            String text = ctx.doi().doid().getText();
            rx.type = RxLineObject.DB.DOI;
            rx.value = text;
//        } else if (ctx.med() != null) {
//            String text = ctx.med().medid().getText();
//            rx.type = RxLineObject.DB.MEDLINE;
//            rx.value = text;
        }
        object.rxs.add(rx);
    }

    public RxLineObject getObject() {
        return object;
    }
}
