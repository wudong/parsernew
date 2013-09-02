package uk.ac.ebi.uniprot.parser.impl.rl;

import org.antlr.v4.runtime.WritableToken;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.RlLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.RlLineParser;
import uk.ac.ebi.uniprot.parser.antlr.RpLineLexer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class RlLineModelListener extends RlLineBaseListener implements ParseTreeObjectExtractor<RlLineObject> {

    private RlLineObject object = new RlLineObject();

    @Override
    public void exitRl_journal(@NotNull RlLineParser.Rl_journalContext ctx) {
        RlLineObject.JournalArticle journalArticle = new RlLineObject.JournalArticle();
        journalArticle.journal = ctx.journal_abbrev().getText();
        journalArticle.volume = Integer.parseInt(ctx.volume().getText());
        journalArticle.first_page = Integer.parseInt(ctx.first_page().getText());
        journalArticle.last_page = Integer.parseInt(ctx.last_page().getText());
        journalArticle.year = Integer.parseInt(ctx.date_year().getText());
        object.reference = journalArticle;
    }

    @Override
    public void exitRl_epub(@NotNull RlLineParser.Rl_epubContext ctx) {
        RlLineObject.EPub epub = new RlLineObject.EPub();
        String text = ctx.epub_text().getText();
        epub.title = text.substring(0, text.length() - 1);
        object.reference = epub;
    }

    @Override
    public void exitRl_patent(@NotNull RlLineParser.Rl_patentContext ctx) {
        RlLineObject.Patent patent = new RlLineObject.Patent();
        patent.patentNumber = ctx.patent_number().getText();
        RlLineParser.Date_day_month_yearContext date_day_month_yearContext = ctx.date_day_month_year();

        patent.day = Integer.parseInt(date_day_month_yearContext.INTEGER(0).getText());
        patent.month = date_day_month_yearContext.WORD().getText();
        patent.year = Integer.parseInt(date_day_month_yearContext.INTEGER(1).getText());

        object.reference = patent;
    }

    @Override
    public void exitRl_book(@NotNull RlLineParser.Rl_bookContext ctx) {
        RlLineObject.Book book = new RlLineObject.Book();

        book.title = ctx.rl_book_title().getText();
        List<TerminalNode> name = ctx.editor_names().NAME();

        for (TerminalNode terminalNode : name) {
            String text = terminalNode.getText();
            book.editors.add(text);
        }

        book.year = Integer.parseInt(ctx.date_year().getText());

        int size = ctx.rl_book_page().INTEGER().size();
        if (size == 2) {
            book.volume = 0;
            book.page_start = Integer.parseInt(ctx.rl_book_page().INTEGER(0).getText());
            book.page_end = Integer.parseInt(ctx.rl_book_page().INTEGER(1).getText());
        } else if (size == 3) {
            book.volume = Integer.parseInt(ctx.rl_book_page().INTEGER(0).getText());
            book.page_start = Integer.parseInt(ctx.rl_book_page().INTEGER(1).getText());
            book.page_end = Integer.parseInt(ctx.rl_book_page().INTEGER(2).getText());
        }

        book.place = ctx.rl_book_place().getText();
        book.press = ctx.rl_book_press().getText();

        object.reference = book;
    }


    @Override
    public void exitRl_unpublished(@NotNull RlLineParser.Rl_unpublishedContext ctx) {
        RlLineObject.Unpublished unp = new RlLineObject.Unpublished();

        unp.month = ctx.date_month_year().WORD().getText();
        unp.year = Integer.parseInt(ctx.date_month_year().INTEGER().getText());
        object.reference = unp;
    }

    @Override
    public void exitRl_submission(@NotNull RlLineParser.Rl_submissionContext ctx) {
        RlLineObject.Submission sub = new RlLineObject.Submission();
        sub.month = ctx.date_month_year().WORD().getText();
        sub.year = Integer.parseInt(ctx.date_month_year().INTEGER().getText());
        if (ctx.submission_db().EMBL() != null) {
            sub.db = RlLineObject.SubmissionDB.EMBL;
        } else if (ctx.submission_db().PDB() != null) {
            sub.db = RlLineObject.SubmissionDB.PDB;
        } else if (ctx.submission_db().UNIPROTKB() != null) {
            sub.db = RlLineObject.SubmissionDB.UNIPROTKB;
        } else if (ctx.submission_db().PIR() != null) {
            sub.db = RlLineObject.SubmissionDB.PIR;
        }
        object.reference = sub;
    }

    @Override
    public void exitRl_thesis(@NotNull RlLineParser.Rl_thesisContext ctx) {
        RlLineObject.Thesis thesis = new RlLineObject.Thesis();
        thesis.country = ctx.country().getText();
        thesis.institute = ctx.institute_name().getText();
        thesis.year = Integer.parseInt(ctx.date_year().getText());
        object.reference = thesis;
    }

    @Override
    public void exitRl_book_separator(@NotNull RlLineParser.Rl_book_separatorContext ctx) {
        if (ctx.CHANGE_OF_LINE() != null) {
            WritableToken symbol = (WritableToken) ctx.CHANGE_OF_LINE().getSymbol();
            symbol.setText(" ");
            symbol.setType(RpLineLexer.SPACE);
        }
    }

    public RlLineObject getObject() {
        return object;
    }
}
