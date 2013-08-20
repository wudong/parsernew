grammar AcLine;
import CommonLex;

ac_ac: ac_line+;

ac_line: ac_head (accession SPACE1)* (accession NEWLINE);

ac_head: 'AC' SPACE3;

accession : ACCESSION SEMICOLON ;
           
           
           