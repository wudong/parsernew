grammar RnLine;

rn_rn: 'RN   ' LEFT_BRACKET rn_number RIGHT_BRACKET '\n';

rn_number: INTEGER;

LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
INTEGER: [1-9][0-9]*;
