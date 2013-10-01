/*
OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
OG   Plasmid IncFII R1-19 (R1 drd-19).
*/
lexer grammar OgLineLexer;

OG_HEADER: 'OG   ';
HYDROGENOSOME: 'Hydrogenosome';
MITOCHONDRION: 'Mitochondrion';
NUCLEOMORPH: 'Nucleomorph';
PLASTID: 'Plastid';
APICOPLAST: 'Apicoplast';
ORGANELLAR_CHROMATOPHORE : 'Organellar chromatophore';
CYANELLE: 'Cyanelle';
CHLOROPLAST: 'Chloroplast';
NON_PHOTOSYNTHETIC_PLASTID: 'Non-photosynthetic plastid';
DOT_NEW_LINE: '.\n';
PLASMID_SPACE: 'Plasmid '                                            ->pushMode(PLASMID_VALUE_MODE) ;
PLASMID: 'Plasmid';

SEMICOLON: ';';
LEFT_BRACKET: '{'                                              -> pushMode (EVIDENCE_MODE);
CHANGE_OF_LINE: '\nOG   ';
SPACE: ' '  ;
AND: 'and';

mode PLASMID_VALUE_MODE;
DOT_NEW_LINE_V: '.\n'                    -> type (DOT_NEW_LINE), popMode;
COMA: ','                                -> popMode;
//CHANGE_OF_LINE_V: '\nOG   '            ->type (CHANGE_OF_LINE);
LEFT_BRACKET_V: '{'                      -> type (LEFT_BRACKET), pushMode (EVIDENCE_MODE);
PLASMID_VALUE: LD+;
fragment LD : ~[,.{];

mode EVIDENCE_MODE;
RIGHT_BRACKET: '}'                -> popMode;
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
COMA_E: ','                       -> type (COMA);




