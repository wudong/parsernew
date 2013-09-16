/*
GN   Name=<name>; Synonyms=<name1>[, <name2>...]; OrderedLocusNames=<name1>[, <name2>...];
GN   ORFNames=<name1>[, <name2>...];
//each and every name need to have an evidence.
--Constrains not checked.
*/

grammar GnLine;

@lexer::members {
  private boolean name=false;
}

gn_gn: gn_line_block (GN_NAME_SEPARATOR gn_line_block)* NEWLINE;

gn_line_block: 'GN   '
        one_name ((SPACE|CHANGE_OF_LINE) one_name)*
        ;

one_name: (gene_name|syn_name|orf_name|ol_name);

gene_name: NAME name SEMICOLON;
syn_name: SYNONYMS names SEMICOLON;
orf_name: ORFNAMES names SEMICOLON;
ol_name:  OLNAMES names SEMICOLON;

name: GENE_NAME evidence?;
names: name (COMMA (SPACE|CHANGE_OF_LINE) name)*;
evidence: '{' EV_TAG (COMMA SPACE EV_TAG)* '}';

CHANGE_OF_LINE: '\nGN   ';
GN_NAME_SEPARATOR: '\nGN   and' NEWLINE;

NAME : 'Name=' {name=true;};
SYNONYMS : 'Synonyms=' {name=true;};
ORFNAMES : 'ORFNames=' {name=true;};
OLNAMES: 'OrderedLocusNames='{name=true;};
SEMICOLON: ';' {name=false;};
COMMA: ',';
SPACE3: '   ';
SPACE: ' ';
NEWLINE : '\n';

EV_TAG : ('EI'|'EA') DIGIT+;

GENE_NAME:GENE_LETTER_STARTER GENE_LETTER+ {name}?;
fragment DIGIT: [0-9];
fragment GENE_LETTER: ~[,;\n\r\t{}];
fragment GENE_LETTER_STARTER: ~[ ,;\n\r\t{}];



