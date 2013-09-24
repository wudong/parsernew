lexer grammar SsLineLexer;

/*

**
**   #################    INTERNAL SECTION    ##################
**EV EI2; ProtImp; -; -; 11-SEP-2009.
**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.

*/


STAR_LINE: '**\n';
IS_LINE: '**   #################    INTERNAL SECTION    ##################\n';
STAR_EV: '**EV ';

EV_TAG : ('EI'|'EA') INT;

DASH: '-';
DOT_NEWLINE: '.\n';
VALUE: VL+;
SEPARATOR: '; ';

DATE : [0-9] [0-9] '-' MONTH_ABR '-' DIGIT DIGIT DIGIT DIGIT;
fragment MONTH_ABR : 'JAN' |'FEB'|'MAR' |'MAY'|'JUN' | 'JUL' | 'APR'| 'AGU'| 'SEP'| 'NOV'| 'OCT' |'DEC';
fragment DIGIT: [0-9];
fragment INT : [1-9][0-9]*;
fragment VL: ~[;\n\t\r];