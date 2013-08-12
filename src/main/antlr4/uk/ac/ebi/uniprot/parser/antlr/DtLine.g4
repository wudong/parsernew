/*
DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.
DT   19-JUL-2004, sequence version 1.
DT   18-APR-2012, entry version 24.
*/

grammar DtLine;
import CommonLex;

dt_blocks: dt_integration_line dt_seqver_line dt_entryver_line;

dt_integration_line :  dt_head dt_date 'integrated into UniProtKB/' dt_database DOT NEWLINE;

dt_seqver_line: dt_head dt_date 'sequence version ' dt_version DOT NEWLINE;

dt_entryver_line: dt_head dt_date 'entry version ' dt_version DOT NEWLINE;

dt_database : SWISSPROT | TREMBL;

dt_head : 'DT' SPACE3;

dt_date: DATE ', ';

dt_version: INT;

SWISSPROT: 'Swiss-Prot';
TREMBL: 'Trembl';