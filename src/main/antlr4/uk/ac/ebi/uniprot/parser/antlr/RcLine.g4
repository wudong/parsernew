grammar RcLine;

rc_rc: RC_HEADER rc (rc_separator rc)* '\n';

rc : rc_token rc_text ';';

rc_separator: SPACE | CHANGE_OF_LINE;

rc_text: rc_value ((rc_value_separator rc_value)* (rc_value_separator 'and ' rc_value))? ;

rc_value_separator: COMA (' '  | CHANGE_OF_LINE);
rc_value: rc_value_v evidence?;
rc_value_v: WORD (SPACE WORD)*;

rc_token: STRAIN| PLASMID | TRANSPOSON |TISSUE;

STRAIN : 'STRAIN=';
PLASMID : 'PLASMID=';
TRANSPOSON : 'TRANSPOSON=';
TISSUE : 'TISSUE=';

CHANGE_OF_LINE: '\nRC   ';
RC_HEADER: 'RC   ';

evidence: LEFT_B  EV_TAG (COMA EV_TAG)* RIGHT_B;
COMA: ',';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{';
RIGHT_B : '}';

WORD: (LD)+ ;
SPACE: ' ';
fragment LD : ~[=;,\r\n {}];