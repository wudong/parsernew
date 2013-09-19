/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

parser grammar KwLineParser;

options { tokenVocab=KwLineLexer;}

kw_kw:   kw_line+;

kw_line: KW_HEAD keyword (SEMICOLON separator keyword)*
         DOT NEW_LINE;

keyword: keyword_v evidence?;
keyword_v: WORD (separator WORD)*;

separator: CHANGE_OF_LINE|SPACE;
evidence: LEFT_B  EV_TAG (COMA EV_TAG)* RIGHT_B;

           