package uk.ac.ebi.uniprot.antlr;

import com.google.common.base.Preconditions;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import uk.ac.ebi.uniprot.parser.antlr.TextHelperLexer;
import uk.ac.ebi.uniprot.parser.antlr.TextHelperParser;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * User: wudong, Date: 02/10/13, Time: 16:21
 */
public class TextHelper {

	private static TextHelperParser setUp(String s, int mode) {
		ANTLRInputStream in = new ANTLRInputStream(s);
		TextHelperLexer textHelperLexer = new TextHelperLexer(in);
		textHelperLexer.mode(mode);
		CommonTokenStream commonTokenStream = new CommonTokenStream(textHelperLexer);
		TextHelperParser textHelperParser = new TextHelperParser(commonTokenStream);
		return textHelperParser;
	}

	/**
	 * Remove the CHANGE_OF_LINE in FF text.
	 *
	 * @param string
	 * @return
	 */
	public static String removeChangeOfLine(String string) {
		TextHelperParser parser = setUp(string, TextHelperLexer.MODE_REMOVE_CHANGE_OF_LINE);
		TextHelperParser.Text_containing_change_of_lineContext rule = parser.text_containing_change_of_line();
		return rule.getText();
	}

	public static String[] parseCCDiseaseAbbrMim(String string) {
		TextHelperParser parser = setUp(string, TextHelperLexer.MODE_CC_DISEASE_ABBR_MIM);
		TextHelperParser.Text_cc_disease_abbr_mimContext context = parser.text_cc_disease_abbr_mim();
		Preconditions.checkState(context.p_text_cc_disease_abbr_mim_abbr()!=null, "Not be able to parse CC Disease Abbr and MIM for String: "+string);
		Preconditions.checkState(context.p_text_cc_disease_abbr_mim_mim()!=null, "Not be able to parse CC Disease Abbr and MIM for String: "+string);

		return new String[]{context.p_text_cc_disease_abbr_mim_abbr().getText(),
				context.p_text_cc_disease_abbr_mim_mim().CC_DISEASE_ABBR_MIM_VALUE().getText()};
	}

	public static List<String> parseCCDiseasePubmed(String text) {
		return new ArrayList<String>();
	}
}
