lexer grammar RpLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RP_START  : 'RP   '             -> pushMode (RP_CONTENT);

mode RP_CONTENT;
RP_ENDING : '.\n'               -> popMode;
AND : 'AND'                              ;
CHANGE_OF_LINE : '\nRP   '      {replaceChangeOfLine();} ;
SPACE: ' ';
COMA_SPACE: ', ';
COMA: ',';
COMA_CHANGE_OF_LINE: COMA CHANGE_OF_LINE  -> type (COMA_SPACE);
WORD: (LD|COMA)*LD        {!getText().endsWith(".")}?;
fragment LD : ~[ ,\r\na-z];