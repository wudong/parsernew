/*
RX   Bibliographic_db=IDENTIFIER[; Bibliographic_db=IDENTIFIER...];

RX   MEDLINE=83283433; PubMed=6688356;
RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;
RX   MEDLINE=22709107; PubMed=12788972; DOI=10.1073/pnas.1130426100;
RX   AGRICOLA=IND20551642; DOI=10.1007/BF00224104;
*/

lexer grammar RxLineLexer;

RX_HEADER : 'RX   ' ;
PUBMED: 'PubMed=' -> pushMode (RX_VALUE);
DOI: 'DOI='  -> pushMode (RX_VALUE);
AGRICOLA: 'AGRICOLA='  -> pushMode (RX_VALUE);

mode RX_VALUE;
SEMICOLON_SPACE: '; '  ->popMode;
SEMICOLON_NEW_LINE: ';\n'  ->popMode;
VALUE: LD (LD|SEMICOLON)* LD;
fragment SEMICOLON: ';';
fragment LD: ~[;\r\t\n ] ;
