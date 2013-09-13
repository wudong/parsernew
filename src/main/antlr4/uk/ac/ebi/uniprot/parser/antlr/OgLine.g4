/*
OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
OG   Plasmid IncFII R1-19 (R1 drd-19).
*/
grammar OgLine;

@members {
  private boolean plasmid=false;
}

og_og: hydrogenosome_line? mitochondrion_line? nucleomorph_line? plasmid_line? plastid_line*;

hydrogenosome_line:  'OG   '  HYDROGENOSOME evidence? DOT'\n';
mitochondrion_line:  'OG   '  MITOCHONDRION evidence? DOT'\n';
nucleomorph_line:  'OG   '  NUCLEOMORPH evidence? DOT'\n';

plastid_line: 'OG   ' PLASTID ('; ' plastid_name)? evidence? DOT'\n';
plastid_name:  CHLOROPLAST | APICOPLAST | ORGANELLAR_CHROMATOPHORE    |CYANELLE| NON_PHOTOSYNTHETIC_PLASTID;

plasmid_line : 'OG   ' plasmid_names DOT'\n';

plasmid_names: (plasmid_name (COMMA plasmid_separator plasmid_name )*  COMMA ' and' plasmid_separator)? plasmid_name;

plasmid_name : PLASMID PLASMID_VALUE evidence?;
PLASMID_VALUE : LD+ {plasmid}?;

plasmid_separator: SPACE | change_of_line;
change_of_line : '\n' 'OG   ';

HYDROGENOSOME: 'Hydrogenosome';
MITOCHONDRION: 'Mitochondrion';
NUCLEOMORPH: 'Nucleomorph';
PLASTID: 'Plastid';
APICOPLAST: 'Apicoplast';
ORGANELLAR_CHROMATOPHORE : 'Organellar chromatophore';
CYANELLE: 'Cyanelle';
CHLOROPLAST: 'Chloroplast';
NON_PHOTOSYNTHETIC_PLASTID: 'Non-photosynthetic plastid';
PLASMID: 'Plasmid ' {plasmid=true;};

evidence: LEFT_B  EV_TAG (COMMA SPACE EV_TAG)* RIGHT_B;
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{' {plasmid=false;};
RIGHT_B : '}';

SPACE : ' ';


COMMA: ',' {plasmid=false;};
DOT: '.' {plasmid=false;};

fragment LD : ~[,.{}];

