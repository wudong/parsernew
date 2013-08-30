grammar DeLine;

@members {private boolean ec=false;}

de_de: rec_name? alt_name*  alt_allergen? alt_biotech? alt_cdantigen* alt_inn* sub_name?
       included_de* contained_de* flags?;

rec_name: 'DE   RecName: ' full_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*;
sub_name: 'DE   SubName: ' full_name (CONTINUE_OF_NAME ec)*;
alt_name: 'DE   AltName: ' (
            alt_name_1 | alt_name_2 | alt_name_3
          ) ;

alt_name_1 : full_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*           ;
alt_name_2 : short_name (CONTINUE_OF_NAME short_name)* (CONTINUE_OF_NAME ec)*           ;
alt_name_3 : ec (CONTINUE_OF_NAME ec)*           ;

alt_allergen:   'DE   AltName: Allergen' NAME_VALUE;
alt_biotech:    'DE   AltName: Biotech' NAME_VALUE;
alt_cdantigen:  'DE   AltName: CD_antigen' NAME_VALUE;
alt_inn:        'DE   AltName: INN' NAME_VALUE;

flags: 'DE   Flags: ' flag_value ';\n';
flag_value: PRECURSOR | FRAGMENT | PRECURSOR_FRAGMENT |FRAGMENTS;

contained_de: DE_CONTAIN  sub_rec_name? sub_alt_name*  sub_alt_allergen? sub_alt_biotech? sub_alt_cdantigen* sub_alt_inn* sub_sub_name?   ;
included_de:  DE_INCLUDE sub_rec_name? sub_alt_name*  sub_alt_allergen? sub_alt_biotech? sub_alt_cdantigen* sub_alt_inn* sub_sub_name?  ;

sub_rec_name: 'DE     RecName: ' full_name (SUB_CONTINUE_OF_NAME short_name)* (SUB_CONTINUE_OF_NAME ec)*;
sub_sub_name: 'DE     SubName: ' full_name (SUB_CONTINUE_OF_NAME ec)*;
sub_alt_name: 'DE     AltName: ' (
            sub_alt_name_1 | sub_alt_name_2 | sub_alt_name_3
          ) ;

sub_alt_name_1 : full_name (SUB_CONTINUE_OF_NAME short_name)* (SUB_CONTINUE_OF_NAME ec)*           ;
sub_alt_name_2 : short_name (SUB_CONTINUE_OF_NAME short_name)* (SUB_CONTINUE_OF_NAME ec)*           ;
sub_alt_name_3 : ec (SUB_CONTINUE_OF_NAME ec)*   ;

sub_alt_allergen:   'DE     AltName: Allergen' NAME_VALUE;
sub_alt_biotech:    'DE     AltName: Biotech' NAME_VALUE;
sub_alt_cdantigen:  'DE     AltName: CD_antigen' NAME_VALUE;
sub_alt_inn:        'DE     AltName: INN' NAME_VALUE;

full_name: 'Full' NAME_VALUE;
short_name: 'Short' NAME_VALUE;
ec: EC EC_NAME_VALUE ';\n';

DE_CONTAIN: 'DE   Contains:\n';
DE_INCLUDE: 'DE   Includes:\n';

FULL: 'Full';
SHORT: 'Short';
EC: 'EC=' {ec=true;};
EC_NAME_VALUE: DIGIT+ '.' DIGIT+ '.' DIGIT+ '.' DIGIT+ {ec=false;};

PRECURSOR: 'Precursor';
FRAGMENT: 'Fragment';
PRECURSOR_FRAGMENT: 'Precursor, Fragment';
FRAGMENTS: 'Fragments';

CONTINUE_OF_NAME: 'DE            ';
SUB_CONTINUE_OF_NAME: 'DE              ';

//the name will consume the line-ending as well.
NAME_VALUE: '='.+? ';\n' {!ec}?;

fragment DIGIT: [0-9];
