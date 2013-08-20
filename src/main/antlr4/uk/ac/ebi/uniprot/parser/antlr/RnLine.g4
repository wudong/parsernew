grammar RnLine;

rn_rn: 'RN   ' LEFT_BRACKET rn_number RIGHT_BRACKET '\n';

rn_number: INTEGER;

LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
INTEGER: INT_NO_ZERO  (INT_NO_ZERO|ZERO)*;

fragment INT_NO_ZERO: [1-9];
fragment ZERO: '0';