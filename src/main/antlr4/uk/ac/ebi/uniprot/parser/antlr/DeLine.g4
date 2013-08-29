grammar DeLine;

de_de: rec_name? alt_name*  alt_allergen? alt_biotech? alt_cdantigen* alt_inn* sub_name? flags?;

rec_name: 'DE   RecName: ' full_name (CHANGE_OF_LINE short_name)* (CHANGE_OF_LINE ec)*;
sub_name: 'DE   SubName: ' full_name (CHANGE_OF_LINE ec)*;
alt_name: 'DE   RecName: ' (
          (full_name (CHANGE_OF_LINE short_name)* (CHANGE_OF_LINE ec)*)|
          (short_name (CHANGE_OF_LINE short_name)* (CHANGE_OF_LINE ec)*)|
          (ec  (CHANGE_OF_LINE ec)*)
          ) ;

alt_allergen:   'DE   AltName: Allergen=' NAME_VALUE;
alt_biotech:    'DE   AltName: Biotech=' NAME_VALUE;
alt_cdantigen:  'DE   AltName: CD_antigen=' NAME_VALUE;
alt_inn:        'DE   AltName: INN=' NAME_VALUE;

flags: 'Flags: ' flag_value ';\n';
flag_value: PRECURSOR | FRAGMENT | PRECURSOR_FRAGMENT |FRAGMENTS;

PRECURSOR: 'Precursor';
FRAGMENT: 'Fragment';
PRECURSOR_FRAGMENT: 'Precursor, Fragment';
FRAGMENTS: 'Fragments';

full_name: 'FULL=' NAME_VALUE;
short_name: 'Short=' NAME_VALUE;
ec: 'EC=' EC_NAME_VALUE ';\n';

//the name will consume the line-ending as well.
EC_NAME_VALUE: DIGIT+ '.' DIGIT+ '.' DIGIT+ '.' DIGIT+;
NAME_VALUE: .+? ';\n';

CHANGE_OF_LINE: '\nDE              ';

fragment DIGIT: [0-9];
