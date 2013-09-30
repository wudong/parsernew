grammar DeLine;

@lexer::members {
private boolean name=false;
}

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

alt_allergen:    ALTNAME_ALLERGEN NAME_VALUE evidence? END_OF_NAME;
alt_biotech:     ALTNAME_BIOTECH NAME_VALUE evidence? END_OF_NAME;
alt_cdantigen:   ALTNAME_CD_ANTIGEN NAME_VALUE evidence? END_OF_NAME;
alt_inn:         ALTNAME_INN NAME_VALUE evidence? END_OF_NAME;

flags: 'DE   Flags: ' flag_value  ('; ' flag_value)?  END_OF_NAME;
flag_value: (PRECURSOR | FRAGMENT |FRAGMENTS ) evidence?;

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

sub_alt_allergen:   SUB_ALTNAME_ALLERGEN NAME_VALUE evidence? END_OF_NAME;
sub_alt_biotech:    SUB_ALTNAME_BIOTECH NAME_VALUE evidence? END_OF_NAME;
sub_alt_cdantigen:  SUB_ALTNAME_CD_ANTIGEN NAME_VALUE evidence? END_OF_NAME;
sub_alt_inn:        SUB_ALTNAME_INN NAME_VALUE evidence? END_OF_NAME;

full_name: FULL NAME_VALUE evidence? END_OF_NAME;
short_name: SHORT NAME_VALUE evidence? END_OF_NAME;
ec: EC EC_NAME_VALUE evidence? END_OF_NAME;

DE_CONTAIN: 'DE   Contains:\n';
DE_INCLUDE: 'DE   Includes:\n';

ALTNAME_INN:           'DE   AltName: INN=' {name=true;};
ALTNAME_CD_ANTIGEN:    'DE   AltName: CD_antigen=' {name=true;};
ALTNAME_BIOTECH:       'DE   AltName: Biotech=' {name=true;};
ALTNAME_ALLERGEN:      'DE   AltName: Allergen=' {name=true;};

SUB_ALTNAME_INN:        'DE     AltName: INN=' {name=true;};
SUB_ALTNAME_CD_ANTIGEN: 'DE     AltName: CD_antigen=' {name=true;};
SUB_ALTNAME_BIOTECH:    'DE     AltName: Biotech=' {name=true;};
SUB_ALTNAME_ALLERGEN:   'DE     AltName: Allergen=' {name=true;};

FULL: 'Full=' {name=true;};
SHORT: 'Short=' {name=true;};
EC: 'EC=';
EC_NAME_VALUE: EC_NAME_VALUE_V '.' EC_NAME_VALUE_V '.' EC_NAME_VALUE_V '.' EC_NAME_VALUE_V;
fragment EC_NAME_VALUE_V: '-'|('n'? DIGIT+);
END_OF_NAME: ';\n'{name=false;};

PRECURSOR: 'Precursor';
FRAGMENT: 'Fragment';
FRAGMENTS: 'Fragments';

CONTINUE_OF_NAME: 'DE            ';
SUB_CONTINUE_OF_NAME: 'DE              ';

evidence: LEFT_B  EV_TAG (COMMA EV_TAG)* RIGHT_B;
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{' {name=false;}  ;
RIGHT_B : '}';
COMMA: ',';

//the name will consume the line-ending as well.
NAME_VALUE: NL+ {name}?;

fragment DIGIT: [0-9];
fragment NL: ~[;{}\n\r\t];
