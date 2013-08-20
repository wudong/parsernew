/*
RG   The mouse genome sequencing consortium;
*/

grammar RgLine;

rg_rg: 'RG   ' words ';\n';

words : WORD (SPACE WORD)*;
WORD: (LOW_CASE|UP_CASE)+;

SPACE :  ' ';
fragment UP_CASE: [A-Z];
fragment  LOW_CASE: [a-z];
