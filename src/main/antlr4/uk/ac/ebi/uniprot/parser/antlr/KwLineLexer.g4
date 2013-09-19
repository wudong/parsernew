/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

lexer grammar KwLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

KW_HEAD: 'KW   ';

CHANGE_OF_LINE : '\nKW   ' {replaceChangeOfLine();};
NEW_LINE : '\n';
SPACE: ' ';

SEMICOLON : ';';
DOT : '.';
COMA : ',';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{';
RIGHT_B : '}';

WORD: LD+;
fragment LD : ~[,.;{}\n ];

           