/*
SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;
     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS
     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD
     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL
     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD
     SFRKIYTDLG WKFTPL
*/

grammar SqLine;

sq_blocks: sq_head sq_block;

sq_head: 'SQ' SPACE3 'SEQUENCE' SPACE3 sq_length SPACE1 'AA;' SPACE2 INT SPACE1 'MW;' SPACE2 crc SPACE1 'CRC64' ';' NEWLINE;

sq_length: INT;

sq_block: sq_line* sq_line_last;

sq_line: SPACE5 sq_letter_block6 NEWLINE;
sq_line_last: SPACE5 sq_letter_blocks SPACE1 sq_letters NEWLINE;

sq_letter_blocks: (sq_letter_block1|sq_letter_block2|sq_letter_block3|sq_letter_block4|sq_letter_block5);
sq_letter_block6 : sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10;
sq_letter_block5 : sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10;
sq_letter_block4 : sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10;
sq_letter_block3 : sq_letters_10 SPACE1 sq_letters_10 SPACE1 sq_letters_10;
sq_letter_block2 : sq_letters_10 SPACE1 sq_letters_10;
sq_letter_block1 : sq_letters_10;

sq_letters_10 : SQLETTER10 ;
sq_letters : (SQLETTER|SQLETTER2|SQLETTER3|SQLETTER4|SQLETTER5|SQLETTER6|SQLETTER7|SQLETTER8|SQLETTER9)|sq_letters_10;

SQLETTER10 : SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER;
SQLETTER9 :  SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER;
SQLETTER8 :  SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER;
SQLETTER7 :  SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER;
SQLETTER6 :  SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER;
SQLETTER5 :  SQLETTER SQLETTER SQLETTER SQLETTER SQLETTER ;
SQLETTER4 :  SQLETTER SQLETTER SQLETTER SQLETTER;
SQLETTER3 :  SQLETTER SQLETTER SQLETTER;
SQLETTER2 :  SQLETTER SQLETTER ;

crc: CRCLETTERS;

INT: DIGIT+;
CRCLETTERS :(DIGIT|UP_LETTER)+;

NEWLINE : '\n';
SPACE1 : ' ';
SPACE2 : '  ';
SPACE3 : '   ';
SPACE5 : '     ';
SQLETTER : [A-Z];
UP_LETTER : [A-Z];
DIGIT: [0-9];

