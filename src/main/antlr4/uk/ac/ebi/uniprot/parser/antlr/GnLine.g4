/*
GN   Name=<name>; Synonyms=<name1>[, <name2>...]; OrderedLocusNames=<name1>[, <name2>...];
GN   ORFNames=<name1>[, <name2>...];
--Constrains not checked.
---
*/

grammar GnLine;

gn_gn: gn_line_block (GN_NAME_SEPARATOR gn_line_block)*;

gn_line_block: gn_header
        one_name ((SPACE|change_line) one_name)*
        NEWLINE;

one_name: (gene_name|syn_name|orf_name|ol_name);

gene_name: NAME EQ name SEMICOLON;
syn_name: SYNONYMS EQ names SEMICOLON;
orf_name: ORFNAMES EQ names SEMICOLON;
ol_name:  OLNAMES EQ names SEMICOLON;

gn_header: GN SPACE3;

name: GENE_NAME;
names: name (COMMA (SPACE|change_line) name)*;

change_line: NEWLINE gn_header;

GN_NAME_SEPARATOR: GN SPACE3 'and' NEWLINE;

NAME : 'Name';
SYNONYMS : 'Synonyms';
ORFNAMES : 'ORFNames';
OLNAMES: 'OrderedLocusNames';
COMMA: ',';
SEMICOLON: ';';
EQ : '=';
GN : 'GN';
SPACE3: '   ';
SPACE: ' ';
NEWLINE : '\n';

GENE_NAME:GENE_LETTER+;
GENE_LETTER: [A-Za-z0-9'-'];
