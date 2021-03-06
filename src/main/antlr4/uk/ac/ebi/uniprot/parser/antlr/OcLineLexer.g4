/*
Node[; Node...].

OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;
OC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;
OC   Homo.

*/

lexer grammar OcLineLexer;

OC_HEADER : 'OC   '   ->pushMode(OC_CONTENT);

mode OC_CONTENT;
CHANGE_OF_LINE: '\nOC   ';
SPACE: ' ';
SEMI_COLON: ';';
DOT_NEW_LINE: '.\n'     ->popMode;
DOT: '.';

OcWord: OL+;
fragment OL : ~[ \.;\r\n\t];


