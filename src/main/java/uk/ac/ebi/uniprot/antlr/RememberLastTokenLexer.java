package uk.ac.ebi.uniprot.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

/**
 * Individual LineLexers can use the superClass option use this class as their super class, thus gain the
 * abilitity to remember the last token emitted.
 * <p>
 * <code>
 * options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }
 * </code>
 * </p>
 * <p/>
 * This is mainly used to deal with the change of line.
 * <br>
 * This class cannot be put under the uk.ac.ebi.uniprot.parser package cause parser is a keyword in the Antlr grammar file.
 */
public abstract class RememberLastTokenLexer extends Lexer {

	private Token lastToken;

	protected RememberLastTokenLexer(CharStream input) {
		super(input);
	}

	@Override
	public Token emit() {
		lastToken = super.emit();
		return lastToken;
	}

	/**
	 * This method is used by the lexer, when it sees an change of line, normally in the format of
	 * "CC    " in middle of a string, it will replace it with an space or nothing, depending on the last token's ending.
	 */
	public void replaceChangeOfLine() {
		//check the last token, if it is ending with "-"
		if (lastToken != null) {
			if (lastToken.getText().endsWith("-")) {
				this.setText("");
			} else {
				this.setText(" ");
			}
		}
	}

	public void replaceChangeOfLine(boolean seq) {
		if (!seq) {
			replaceChangeOfLine();
		} else {
			if (isSequenceLetter(lastToken.getText())) {
				this.setText("");
			} else {
				this.setText(" ");
			}
		}
	}

	public boolean isSequenceLetter(String se) {
		for (int i = 0; i < se.length(); i++) {
			if (se.charAt(i) > 'Z' || se.charAt(i) < 'A')
				return false;
		}
		return true;
	}
}
