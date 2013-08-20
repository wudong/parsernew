/*
OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
OG   Plasmid IncFII R1-19 (R1 drd-19).
*/
grammar OgLine;

og_og:  plasmid_lines;
//og_og: hydrogenosome_line? mitochondrion_line? nucleomorph_line? plasmid_lines? plastid_line*;

hydrogenosome_line:  'OG   '  HYDROGENOSOME '.\n';
mitochondrion_line:  'OG   '  MITOCHONDRION '.\n';
nucleomorph_line:  'OG   '  NUCLEOMORPH '.\n';

plastid_line: 'OG   ' PLASTID ('; ' plastid_name)? '.\n';
plastid_name:  APICOPLAST | ORGANELLAR_CHROMATOPHORE    |CYANELLE| NON_PHOTOSYNTHETIC_PLASTID;

plasmid_lines: plasmid_line*  last_plasmid_line;

plasmid_line : 'OG   '  (plasmid_name ', ')+ 'and\n';
last_plasmid_line : 'OG   ' plasmid_name (', ' plasmid_name)* '.\n';

plasmid_name : PLASMID bracket_multi_word+;
bracket_multi_word: (multi_word | '(' multi_word ')' );
multi_word:  WORD (SPACE WORD)*;

HYDROGENOSOME: 'Hydrogenosome';
MITOCHONDRION: 'Mitochondrion';
NUCLEOMORPH: 'Nucleomorph';
PLASTID: 'Plastid';
APICOPLAST: 'Apicoplast';
ORGANELLAR_CHROMATOPHORE : 'Organellar chromatophore';
CYANELLE: 'Cyanelle';
NON_PHOTOSYNTHETIC_PLASTID: 'Non-photosynthetic plastid';
PLASMID: 'Plasmid ';

WORD: (LD)+ ;
SPACE: ' ';
fragment LD : [a-zA-Z0-9\-];