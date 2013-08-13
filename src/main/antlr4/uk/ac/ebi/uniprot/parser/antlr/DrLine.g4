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

grammar DrLine;

dr_blocks:   dr_line+;

dr_line: dr_head dr_dbname '; ' (dr_one_attribute_line|dr_two_attribute_line|dr_four_attribute_line|dr_three_attribute_line) NEWLINE;

dr_head: DR SPACE3;
dr_dbname : Cap_Word | All_Cap_Word ;

dr_one_attribute_line:  dr_attribute '; ' empty_attribute '.';
dr_two_attribute_line:  dr_attribute '; ' dr_attribute '.';
dr_three_attribute_line: dr_attribute  '; ' dr_attribute '; ' dr_attribute '.';
dr_four_attribute_line: dr_attribute  '; ' dr_attribute '; ' (dr_attribute|empty_attribute) '; ' dr_attribute '.';

empty_attribute: '-';
dr_attribute: go_word |Word_With_Digit_Score |  Word_With_Version | INT | Cap_Word;
go_word:   go_word_go | go_word_p | go_word_iea;
go_word_go: GO_GO INT;
go_word_p: GO_P (Word_With_Digit_Score (PUNC+ Word_With_Digit_Score)*);
go_word_iea: GO_IEA (Cap_Word | Word_With_Digit_Score);

DR : 'DR';
GO_GO : 'GO:';
GO_P : 'P:';
GO_IEA : 'IEA:';

INT: DIGIT+;
All_Cap_Word: UP_LETTER+;
Cap_Word: UP_LETTER (UP_LETTER|LOW_LETTER)+;
Word_With_Digit_Score: (UP_LETTER|LOW_LETTER|DIGIT|'_'|'-')+;
Word_With_Version: Word_With_Digit_Score DOT INT;

NEWLINE: '\n';

UP_LETTER:[A-Z];
LOW_LETTER:[a-z];
DIGIT:[0-9];
DOT : '.';
SPACE3 : '   ';
PUNC : [','' ']  ;


