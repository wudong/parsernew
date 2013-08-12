/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
lexer grammar CommonLex;



fragment DIGIT : [0-9];
fragment LETTER : [a-z][A-Z];
fragment LETTER_DIGIT: [a-zA-Z0-9];

INT : DIGIT+;
ENTRY_NAME: LETTER_DIGIT+ '_' LETTER_DIGIT+;
ACCESSION: LETTER_DIGIT+;

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

