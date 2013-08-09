/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
lexer grammar CommonLex;

DIGITS : '1'..'9' '0'..'9'*; 

ENTRY_NAME: .+'_'.+;
ACCESSION: .+;

DOT : '.';
SEMICOLON: ';';
NEWLINE :  '\r'? '\n' ;

SPACE1: ' ';
SPACE2: '  ';
SPACE3: '   ';
SPACE4: '    ';
SPACE5: '     ';
SPACE6: '      ';
SPACE9: '         ';
SPACE14: '              ';

