/*
OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
OG   Plasmid IncFII R1-19 (R1 drd-19).
*/
parser grammar OgLineParser;

options { tokenVocab=OgLineLexer;}

og_og: hydrogenosome_line? mitochondrion_line? nucleomorph_line? plasmid_line? plastid_line*;

hydrogenosome_line:  OG_HEADER  HYDROGENOSOME evidence? DOT_NEW_LINE;
mitochondrion_line:  OG_HEADER  MITOCHONDRION evidence? DOT_NEW_LINE;
nucleomorph_line:  OG_HEADER  NUCLEOMORPH evidence? DOT_NEW_LINE;

plastid_line: OG_HEADER PLASTID (SEMICOLON SPACE plastid_name)? evidence? DOT_NEW_LINE;
plastid_name:  CHLOROPLAST | APICOPLAST | ORGANELLAR_CHROMATOPHORE    |CYANELLE| NON_PHOTOSYNTHETIC_PLASTID;

plasmid_line : OG_HEADER plasmid_names DOT_NEW_LINE;

plasmid_names: (plasmid_name
                (COMA (SPACE | CHANGE_OF_LINE) plasmid_name )*  COMA (SPACE | CHANGE_OF_LINE)
                    AND (SPACE | CHANGE_OF_LINE))? plasmid_name;

plasmid_name : (PLASMID|(PLASMID_SPACE PLASMID_VALUE)) evidence?;

evidence:   LEFT_BRACKET EV_TAG (COMA EV_TAG)* RIGHT_BRACKET;

