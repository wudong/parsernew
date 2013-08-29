grammar DeLine;

de_de: rec_name? alt_name*  alt_allergen? alt_biotech? alt_cdantigen* alt_inn* sub_name? flags?;

rec_name: 'DE   RecName: ' full_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*;
sub_name: 'DE   SubName: ' full_name (CONTINUE_OF_NAME ec)*;
alt_name: 'DE   AltName: ' (
            alt_name_1 | alt_name_2 | alt_name_3
          ) ;

alt_name_1 : full_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*           ;
alt_name_2 : short_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*           ;
alt_name_3 : ec (CONTINUE_OF_NAME ec)*           ;

full_name: 'Full' NAME_VALUE;
short_name: 'Short' NAME_VALUE;
ec: 'EC' EC_NAME_VALUE ';\n';

alt_allergen:   'DE   AltName: Allergen' NAME_VALUE;
alt_biotech:    'DE   AltName: Biotech' NAME_VALUE;
alt_cdantigen:  'DE   AltName: CD_antigen' NAME_VALUE;
alt_inn:        'DE   AltName: INN' NAME_VALUE;

flags: 'Flags: ' flag_value ';\n';
flag_value: PRECURSOR | FRAGMENT | PRECURSOR_FRAGMENT |FRAGMENTS;

FULL: 'Full';
SHORT: 'Short';
EC: 'EC';

PRECURSOR: 'Precursor';
FRAGMENT: 'Fragment';
PRECURSOR_FRAGMENT: 'Precursor, Fragment';
FRAGMENTS: 'Fragments';

CONTINUE_OF_NAME: 'DE            ';

//the name will consume the line-ending as well.
EC_NAME_VALUE: DIGIT+ '.' DIGIT+ '.' DIGIT+ '.' DIGIT+;
NAME_VALUE: '='.+? ';\n';

fragment DIGIT: [0-9];
