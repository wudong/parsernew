/*
RA   Galinier A., Bleicher F., Negre D., Perriere G., Duclos B.,
RA   Cozzone A.J., Cortay J.-C.;
*/

grammar RaLine;

ra_ra: RA_HEADER names RA_END;

names: name (',' separator name)*;

name: FIRST_NAME SPACE LAST_NAME (SPACE ABBR)?;
FIRST_NAME: UP_CASE LOW_CASE+;
LAST_NAME: UP_CASE DOT ('-'? UP_CASE DOT)*;
ABBR: 'Jr.'|'Sr.'|'II'|'III'|'IV'|'V'|'VI'|'VII'|'VIII';

RA_HEADER: 'RA   ';
RA_END: ';\n';

separator: CHANGE_OF_LINE|SPACE;

SPACE: ' ';
CHANGE_OF_LINE: '\nRA   ';

fragment DOT : '.';
fragment UP_CASE: [A-Z];
fragment  LOW_CASE: [a-z];
