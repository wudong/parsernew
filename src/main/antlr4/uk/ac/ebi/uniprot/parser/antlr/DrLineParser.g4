/*
DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
DR   RefSeq; YP_031579.1; NC_005946.1.
DR   ProteinModelPortal; Q6GZX4; -.
DR   GeneID; 2947773; -.
DR   ProtClustDB; CLSP2511514; -.
DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
DR   GO; GO:0006351; P:transcription, DNA-dependent; IEA:UniProtKB-KW.
DR   InterPro; IPR007031; Poxvirus_VLTF3.
DR   Pfam; PF04947; Pox_VLTF3; 1.
*/

parser grammar DrLineParser;

options { tokenVocab=DrLineLexer;}

dr_dr:   dr_line+;

dr_line:
       DR_HEADER DB_NAME SEPARATOR
       (dr_two_attribute_line|dr_four_attribute_line|dr_three_attribute_line)
       DOT (evidence)? END_OF_LINE;

dr_two_attribute_line:  dr_attribute SEPARATOR dr_attribute;
dr_three_attribute_line: dr_attribute SEPARATOR dr_attribute SEPARATOR dr_attribute ;
dr_four_attribute_line: dr_attribute  SEPARATOR dr_attribute SEPARATOR dr_attribute SEPARATOR dr_attribute;

dr_attribute: DASH | (ATTRIBUTE (SPACE ATTRIBUTE)*);

evidence: LEFT_B  EV_TAG (COMA EV_TAG)* RIGHT_B;