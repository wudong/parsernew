/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

grammar DrLine;
import CommonLex;

dr_blocks:   dr_line+;

dr_line: dr_head  dr_dbname '; ' (dr_one_attribute_line|dr_two_attribute_line|dr_four_attribute_line|dr_three_attribute_line) NEWLINE;

dr_head: 'DR' SPACE3;

dr_one_attribute_line:  dr_attribute '; ' empty_attribute '.';
dr_two_attribute_line:  dr_attribute '; ' dr_attribute '.';
dr_three_attribute_line: dr_attribute  '; ' dr_attribute '; ' dr_attribute '.';
dr_four_attribute_line: dr_attribute  '; ' dr_attribute '; ' (dr_attribute|empty_attribute) '; ' dr_attribute '.';

dr_attribute: multi_word | with_version;
empty_attribute: '-';

dr_dbname : DBNAME;
DBNAME: UP_LETTER (UP_LETTER|LOW_LETTER)+;
UP_LETTER:[A-Z];
LOW_LETTER:[a-z];

multi_word: WORD (' ' WORD)* ;
with_version: WORD'.'INT;
WORD: LD (LD|PUNC)+;
LD : [a-zA-Z0-9];
PUNC: [':'','_''-'];


