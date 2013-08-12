/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

grammar KwLine;
import CommonLex;

kw_blocks:   kw_line* kw_line_last;

kw_line: kw_head (keyword '; ')* keyword ';' NEWLINE;
kw_line_last: kw_head (keyword '; ')* keyword DOT NEWLINE;

kw_head: 'KW' SPACE3;

keyword: MULTI_WORD;

MULTI_WORD: WORD (' ' WORD)* ;
WORD: LD+;

LD : [a-zA-Z0-9];



           
           
           