grammar RtLine;

rt_rt: RT_START multi_word RT_ENDING;

CHANGE_OF_LINE : '\nRT   ';
RT_START  : 'RT   "';
RT_ENDING : TITLE_END '";\n';

TITLE_END: [.?!];

multi_word:  WORD (separator WORD)*;
separator: SPACE | CHANGE_OF_LINE;

WORD: (LD)+ ;
SPACE: ' ';
fragment LD : ~[".?!\r\n ];