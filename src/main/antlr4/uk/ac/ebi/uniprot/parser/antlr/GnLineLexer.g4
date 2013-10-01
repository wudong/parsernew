/*
GN   Name=<name>; Synonyms=<name1>[, <name2>...]; OrderedLocusNames=<name1>[, <name2>...];
GN   ORFNames=<name1>[, <name2>...];
//each and every name need to have an evidence.
--Constrains not checked.
*/

lexer grammar GnLineLexer;

GN_HEADER: 'GN   ';
CHANGE_OF_LINE: '\nGN   ';
GN_NAME_SEPARATOR: '\nGN   and' NEWLINE;

NAME : 'Name='                 -> pushMode(GENE_NAME_MODE);
SYNONYMS : 'Synonyms='         -> pushMode(GENE_NAME_MODE);
ORFNAMES : 'ORFNames='         -> pushMode(GENE_NAME_MODE);
OLNAMES: 'OrderedLocusNames='  -> pushMode(GENE_NAME_MODE);
SPACE: ' ';
NEWLINE : '\n';

mode GENE_NAME_MODE;
SEMICOLON: ';'                    -> popMode;
SPACE_GN: ' '                     -> type (SPACE);
COMA: ',';
CHANGE_OF_LINE_GN: '\nGN   '      -> type (CHANGE_OF_LINE);
LEFT_BRACKET: '{'                 -> pushMode(EVIDENCE_MODE);

GENE_NAME: GL_WORD (SPACE GL_WORD)*;
fragment GL_WORD: GL+;
fragment GL: ~[ ,;{];

mode EVIDENCE_MODE;
RIGHT_BRACKET: '}'                -> popMode;
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
COMA_E: ','                       -> type (COMA);




