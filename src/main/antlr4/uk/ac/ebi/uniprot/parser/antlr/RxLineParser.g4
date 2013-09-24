/*
RX   Bibliographic_db=IDENTIFIER[; Bibliographic_db=IDENTIFIER...];

RX   MEDLINE=83283433; PubMed=6688356;
RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;
RX   MEDLINE=22709107; PubMed=12788972; DOI=10.1073/pnas.1130426100;
RX   AGRICOLA=IND20551642; DOI=10.1007/BF00224104;
*/

parser grammar RxLineParser;

options { tokenVocab=RxLineLexer;}

rx_rx : RX_HEADER rx  (SPACE rx )* NEW_LINE;

rx : (pubmed|doi|agri) SEMICOLON;

pubmed: PUBMED VALUE;
doi: DOI VALUE;
agri: AGRICOLA VALUE;


