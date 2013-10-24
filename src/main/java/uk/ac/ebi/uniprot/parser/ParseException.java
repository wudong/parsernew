package uk.ac.ebi.uniprot.parser;

import com.google.common.base.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class ParseException extends RuntimeException{


    private final String originalString;
	private final int currentLineNumber;
	private final int currentCharPosition;

    public ParseException(String message, String originalString,
                           int currentLineNumber, int currentCharPosition, Throwable e) {
          super(message, e);
          this.originalString = originalString;
          this.currentLineNumber = currentLineNumber;
          this.currentCharPosition = currentCharPosition;
    }

    public String getDetailedMessage(){
        String errorMessage =
                String.format("Parsing Error while parsing the input String parsing error message is: \n %s \n"
                        , super.getMessage());

        if (!Strings.isNullOrEmpty(originalString)){
            //show the inputString line only.
            //find the line according to the
            String[] split = originalString.split("\n");
            String currentLine = split[currentLineNumber-1];

            errorMessage +=
                    String.format("Error happen on [%d:%d] of the original string. The error line is: \n %s",
                            currentLineNumber, currentCharPosition, currentLine);
        }
        return errorMessage;
    }

    public String getOriginalString(){
        return  originalString;
    }
}
