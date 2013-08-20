grammar OsLine;

os_os : 'OS   ' all_words '.\n';

all_words: words (separator  words)*;

words: multi_word
      | '(' multi_word ')';

multi_word:  WORD (separator WORD)*;

separator: SPACE | CHANGE_OF_LINE;
CHANGE_OF_LINE : '\nOS   ';

WORD: (LD)+ ;
SPACE: ' ';
fragment LD : [a-zA-Z0-9\-];