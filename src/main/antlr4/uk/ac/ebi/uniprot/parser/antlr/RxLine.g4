/*
RX   Bibliographic_db=IDENTIFIER[; Bibliographic_db=IDENTIFIER...];

RX   MEDLINE=83283433; PubMed=6688356;
RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;
RX   MEDLINE=22709107; PubMed=12788972; DOI=10.1073/pnas.1130426100;
RX   AGRICOLA=IND20551642; DOI=10.1007/BF00224104;
*/

grammar RxLine;

rx_rx : RX_HEADER (rx ';' ) (' ' rx ';' )* '\n';

rx : med|pubmed|doi|agri;

med: MEDLINE medid ;
medid: INTEGER;

pubmed: PubMed pubid;
pubid: INTEGER;

doi: DOI doid;
doid: DOITEXT_1 '/' (DOITEXT_1|DOITEXT_2);

agri: AGRICOLA agriid;
agriid: AGRITEXT;

AGRITEXT: 'IND' INTEGER;

INTEGER: INT_NONZERO (INT_NONZERO|ZERO)*;

DOITEXT_1: INTEGER ('.' INTEGER)+;
DOITEXT_2: (LD | INT_NONZERO | ZERO | '.'|'-')+;

RX_HEADER : 'RX   ';

MEDLINE : 'MEDLINE=';
PubMed: 'PubMed=';
DOI: 'DOI=';
AGRICOLA: 'AGRICOLA=';

fragment LD: [A-Za-z];
fragment INT_NONZERO: [1-9];
fragment  ZERO: '0';

