lexer grammar RpLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RP_START  : 'RP   '             -> pushMode (RP_CONTENT);

mode RP_CONTENT;
RP_ENDING : '.\n'               -> popMode;
AND : 'AND'                              ;
CHANGE_OF_LINE : '\nRP   '      {replaceChangeOfLine();} ;
SPACE: ' ';
COMA: ',';
WORD: LD+;
fragment LD : ~[ ,.\r\na-z];