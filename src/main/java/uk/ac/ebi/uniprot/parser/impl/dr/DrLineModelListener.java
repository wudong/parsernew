package uk.ac.ebi.uniprot.parser.impl.dr;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.antlr.DrLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.DrLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class DrLineModelListener extends DrLineBaseListener {

    private DrLineObject object = new DrLineObject();

    public DrLineObject getObject() {
        return object;
    }

    @Override
    public void exitDr_line(@NotNull DrLineParser.Dr_lineContext ctx) {
        String text1 = ctx.dr_dbname().getText();

        DrLineObject.DrObject drObject = new DrLineObject.DrObject();
        drObject.DbName = text1;

        if (ctx.dr_four_attribute_line() != null) {
            DrLineParser.Dr_four_attribute_lineContext dr_four_attribute_lineContext = ctx.dr_four_attribute_line();
            drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(0).getText());
            drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(1).getText());
            if (dr_four_attribute_lineContext.empty_attribute() != null) {
                drObject.attributes.add("-");
                drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(2).getText());
            } else {
                drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(2).getText());
                drObject.attributes.add(dr_four_attribute_lineContext.dr_attribute(3).getText());
            }

        } else if (ctx.dr_three_attribute_line() != null) {
            DrLineParser.Dr_three_attribute_lineContext dr_three_attribute_lineContext = ctx.dr_three_attribute_line();
            drObject.attributes.add(dr_three_attribute_lineContext.dr_attribute(0).getText());
            drObject.attributes.add(dr_three_attribute_lineContext.dr_attribute(1).getText());
            drObject.attributes.add(dr_three_attribute_lineContext.dr_attribute(2).getText());
        } else if (ctx.dr_two_attribute_line() != null) {
            DrLineParser.Dr_two_attribute_lineContext dr_two_attribute_lineContext = ctx.dr_two_attribute_line();
            drObject.attributes.add(dr_two_attribute_lineContext.dr_attribute(0).getText());
            drObject.attributes.add(dr_two_attribute_lineContext.dr_attribute(1).getText());
        } else if (ctx.dr_one_attribute_line() != null) {
            DrLineParser.Dr_one_attribute_lineContext dr_one_attribute_lineContext = ctx.dr_one_attribute_line();
            drObject.attributes.add(dr_one_attribute_lineContext.dr_attribute().getText());
        }

        object.drObjects.add(drObject);
    }

}
