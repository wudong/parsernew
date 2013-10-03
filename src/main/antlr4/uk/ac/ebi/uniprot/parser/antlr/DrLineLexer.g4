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

lexer grammar DrLineLexer;

tokens {SEPARATOR}

DR_HEADER : 'DR   ';
DB_NAME: [A-Za-z][A-Za-z0-9\-]* -> pushMode (DR_ATTR);

mode DR_ATTR;
END_OF_LINE: '\n'         -> popMode;
SEPARATOR: '; '  ;
DASH: '-';
SPACE: ' ';
ATTRIBUTE: LT ((LT|DOT|SEMICOLON)* LT)?;
DOT: '.';
fragment SEMICOLON: ';';
fragment LT: ~[ {;.\n\r\t];

LEFT_B : '{' -> pushMode(EVIDENCE_MODE);

mode EVIDENCE_MODE;
COMA: ',';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
RIGHT_B : '}'  -> popMode;


