grammar AcLine;
import CommonLex;

ac_blocks: ac_line ac_line*;

ac_line: ac_head accession+ NEWLINE;

ac_head: 'AC' SPACE3;

accession : ACCESSION SEMICOLON;
           
           
           