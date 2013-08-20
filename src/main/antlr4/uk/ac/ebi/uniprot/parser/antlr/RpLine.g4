grammar RpLine;


rp_rp: RP_START multi_word RP_ENDING;

CHANGE_OF_LINE : '\nRP   ';
RP_START  : 'RP   ';
RP_ENDING : '.\n';

multi_word:  WORD (separator WORD)*;
separator: SPACE | CHANGE_OF_LINE;

WORD: (LD)+ ;
SPACE: ' ';
fragment LD : ~[.\r\n a-z];