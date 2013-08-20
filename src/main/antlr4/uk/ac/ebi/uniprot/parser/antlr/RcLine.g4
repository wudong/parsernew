grammar RcLine;

rc_rc: RC_HEADER rc (rc_separator rc)* '\n';

rc : rc_token rc_text ';';

rc_separator: SPACE | CHANGE_OF_LINE;

rc_text: rc_value ((rc_value_separator rc_value)* (rc_value_separator 'and ' rc_value))? ;

rc_value_separator: ',' (' '  | CHANGE_OF_LINE);
rc_value: WORD (SPACE WORD)*;

rc_token: STRAIN| PLASMID | TRANSPOSON |TISSUE;

STRAIN : 'STRAIN=';
PLASMID : 'PLASMID=';
TRANSPOSON : 'TRANSPOSON=';
TISSUE : 'TISSUE=';

CHANGE_OF_LINE: '\nRC   ';
RC_HEADER: 'RC   ';

WORD: (LD)+ ;
SPACE: ' ';
fragment LD : ~[=;,\r\n ];