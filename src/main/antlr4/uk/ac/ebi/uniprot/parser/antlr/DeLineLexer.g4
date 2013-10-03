lexer grammar DeLineLexer;

DE_RECNAME_START: 'DE   RecName: ';
DE_SUBNAME_START: 'DE   SubName: ';
DE_ALTNAME_START: 'DE   AltName: ';

SUB_DE_RECNAME_START: 'DE     RecName: ';
SUB_DE_SUBNAME_START: 'DE     SubName: ';
SUB_DE_ALTNAME_START: 'DE     AltName: ';

DE_FLAGS_START: 'DE   Flags: ';
SEMICOLON: ';';
SPACE: ' ';

DE_CONTAIN: 'DE   Contains:\n';
DE_INCLUDE: 'DE   Includes:\n';

ALTNAME_INN:           'DE   AltName: INN='                ->pushMode(NAME_VALUE_MODE);
ALTNAME_CD_ANTIGEN:    'DE   AltName: CD_antigen='         ->pushMode(NAME_VALUE_MODE);
ALTNAME_BIOTECH:       'DE   AltName: Biotech='            ->pushMode(NAME_VALUE_MODE);
ALTNAME_ALLERGEN:      'DE   AltName: Allergen='           ->pushMode(NAME_VALUE_MODE);

SUB_ALTNAME_INN:        'DE     AltName: INN='             ->pushMode(NAME_VALUE_MODE);
SUB_ALTNAME_CD_ANTIGEN: 'DE     AltName: CD_antigen='      ->pushMode(NAME_VALUE_MODE);
SUB_ALTNAME_BIOTECH:    'DE     AltName: Biotech='         ->pushMode(NAME_VALUE_MODE);
SUB_ALTNAME_ALLERGEN:   'DE     AltName: Allergen='        ->pushMode(NAME_VALUE_MODE);

FULL: 'Full='                                              ->pushMode(NAME_VALUE_MODE);
SHORT: 'Short='                                            ->pushMode(NAME_VALUE_MODE);
EC: 'EC='                                                  ->pushMode(EC_VALUE_MODE);

PRECURSOR: 'Precursor';
FRAGMENT: 'Fragment';
FRAGMENTS: 'Fragments';

CONTINUE_OF_NAME: 'DE            ';
SUB_CONTINUE_OF_NAME: 'DE              ';
END_OF_NAME: ';\n';

LEFT_B : '{'   ->  pushMode(EVIDENCE_MODE) ;

mode EVIDENCE_MODE;
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
RIGHT_B : '}'     -> popMode;
COMA: ',';

mode NAME_VALUE_MODE;
NAME_VALUE: NL+;
END_OF_NAME_N: ';\n'    -> type(END_OF_NAME), popMode;
LEFT_B_N : '{'   ->  type(LEFT_B), pushMode(EVIDENCE_MODE) ;
fragment NL: ~[;{}\n\r\t];

mode EC_VALUE_MODE;
EC_NAME_VALUE: EC_NAME_VALUE_V '.' EC_NAME_VALUE_V '.' EC_NAME_VALUE_V '.' EC_NAME_VALUE_V;
END_OF_NAME_EC: ';\n'     -> type (END_OF_NAME), popMode;
LEFT_B_EV : '{'   ->  type(LEFT_B), pushMode(EVIDENCE_MODE) ;
fragment EC_NAME_VALUE_V: '-'|('n'? DIGIT+);
fragment DIGIT: [0-9];

