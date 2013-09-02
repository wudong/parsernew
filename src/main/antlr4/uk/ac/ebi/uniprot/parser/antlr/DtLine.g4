/*
DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.
DT   19-JUL-2004, sequence version 1.
DT   18-APR-2012, entry version 24.
*/

grammar DtLine;

dt_dt: dt_integration_line dt_seqver_line dt_entryver_line;

dt_integration_line :  dt_head dt_date 'integrated into UniProtKB/' dt_database '.\n';

dt_seqver_line: dt_head dt_date 'sequence version ' dt_version '.\n';

dt_entryver_line: dt_head dt_date 'entry version ' dt_version '.\n';

dt_database : SWISSPROT | TREMBL;

dt_head : 'DT   ';

dt_date: DATE ', ';

dt_version: VERSION;

VERSION: [1-9][0-9]*;

SWISSPROT: 'Swiss-Prot';
TREMBL: 'Trembl';

DATE : [1-9] [0-9]? '-' MONTH_ABR '-' DIGIT DIGIT DIGIT DIGIT;
fragment MONTH_ABR : 'JAN' |'FEB'|'MAR' |'MAY'|'JUN' | 'JUL' | 'APR'| 'AGU'| 'SEP'| 'NOV'| 'OCT' |'DEC';
fragment DIGIT : [0-9];
