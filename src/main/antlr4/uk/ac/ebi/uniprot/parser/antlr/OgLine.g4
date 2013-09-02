/*
OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
OG   Plasmid IncFII R1-19 (R1 drd-19).
*/
grammar OgLine;

@members {
  private boolean plasmid=false;
}

og_og: hydrogenosome_line? mitochondrion_line? nucleomorph_line? plasmid_line? plastid_line*;

hydrogenosome_line:  'OG   '  HYDROGENOSOME DOT'\n';
mitochondrion_line:  'OG   '  MITOCHONDRION DOT'\n';
nucleomorph_line:  'OG   '  NUCLEOMORPH DOT'\n';

plastid_line: 'OG   ' PLASTID ('; ' plastid_name)? DOT'\n';
plastid_name:  CHLOROPLAST | APICOPLAST | ORGANELLAR_CHROMATOPHORE    |CYANELLE| NON_PHOTOSYNTHETIC_PLASTID;

plasmid_line : 'OG   ' plasmid_names DOT'\n';

plasmid_names: (plasmid_name (COMMA plasmid_separator plasmid_name )*  COMMA ' and' plasmid_separator)? plasmid_name;


plasmid_name : PLASMID PLASMID_VALUE;
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

SPACE : ' ';

COMMA: ',' {plasmid=false;};
DOT: '.' {plasmid=false;};

fragment LD : ~[,.];

