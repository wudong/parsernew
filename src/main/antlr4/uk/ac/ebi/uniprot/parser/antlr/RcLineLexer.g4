lexer grammar RcLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RC_HEADER: 'RC   ';

STRAIN : 'STRAIN='           ->pushMode(RC_VALUE_MODE);
PLASMID : 'PLASMID='         ->pushMode(RC_VALUE_MODE);
TRANSPOSON : 'TRANSPOSON='   ->pushMode(RC_VALUE_MODE);
TISSUE : 'TISSUE='           ->pushMode(RC_VALUE_MODE);
CHANGE_OF_LINE: '\nRC   ';
SPACE: ' ';
NEW_LINE : '\n';

mode RC_VALUE_MODE;
SEMICOLON: ';'             ->popMode;
COMA: ',';
LEFT_B : '{'              ->pushMode(EVIDENCE_MODE);
AND : 'and';
WORD: (LD)+ ;
SPACE_: ' '                -> type(SPACE);
CHANGE_OF_LINE_: '\nRC   '  {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
fragment LD : ~[;, \r\n\t{];

mode EVIDENCE_MODE ;
COMA_: ','   ->type(COMA);
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
RIGHT_B : '}'  ->popMode;

