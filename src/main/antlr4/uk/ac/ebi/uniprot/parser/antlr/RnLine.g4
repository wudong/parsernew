grammar RnLine;

rn_rn: 'RN   ' LEFT_BRACKET rn_number RIGHT_BRACKET evidence?'\n';

rn_number: INTEGER;

LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
INTEGER: [1-9][0-9]*;

evidence: LEFT_B  EV_TAG (SEPARATOR EV_TAG)* RIGHT_B;
SEPARATOR: ',';
EV_TAG : ('EI'|'EA') INTEGER;
LEFT_B : '{';
RIGHT_B : '}';
