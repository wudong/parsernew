/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
lexer grammar CommonLex;

fragment DIGIT : [0-9];
fragment LETTER : [a-z][A-Z];
LETTER_DIGIT: [a-zA-Z0-9];

INT : DIGIT+;
ENTRY_NAME: WORD '_' WORD;
ACCESSION: WORD;

MULTI_WORD: WORD (' ' WORD)* ;
WORD: LETTER_DIGIT+;

DATE : DIGIT DIGIT '-' MONTH_ABR '-' DIGIT DIGIT DIGIT DIGIT;
MONTH_ABR : 'JAN' |'FEB'|'MAR' |'MAY'|'JUN' | 'JUL' | 'APR'| 'AGU'| 'SEP'| 'NOV'| 'OCT' |'DEC';

DOT : '.';
SEMICOLON: ';';
NEWLINE : '\n';

SPACE1: ' ';
SPACE2: '  ';
SPACE3: '   ';
SPACE4: '    ';
SPACE5: '     ';
SPACE6: '      ';
SPACE9: '         ';
SPACE14: '              ';

