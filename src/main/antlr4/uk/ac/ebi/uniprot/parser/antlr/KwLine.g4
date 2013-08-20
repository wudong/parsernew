/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

grammar KwLine;

kw_kw:   kw_line* kw_line_last;

kw_line: kw_head (keyword '; ')* keyword ';' NEWLINE;
kw_line_last: kw_head (keyword '; ')* keyword DOT NEWLINE;

kw_head: 'KW   ';

keyword: MULTI_WORD;
NEWLINE: '\n';
DOT : '.';

MULTI_WORD: WORD (' ' WORD)* ;
WORD: LD+;

fragment LD : [a-zA-Z0-9'-''_'];



           
           
           