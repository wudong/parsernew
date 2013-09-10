lexer grammar CcLineLexer;

//define the tokens that is common in all the modes.
tokens { CC_TOPIC_START, SPACE, SEMICOLON, COMA,
 COLON, DOT, NEW_LINE, CHANGE_OF_LINE,
 CC_HEADER_1, CC_HEADER_2, INTEGER, DASH}

CC_TOPIC_START  : 'CC   -!- ';
CC_TOPIC_COMMON : ('ALLERGEN'|'BIOTECHNOLOGY'|'CATALYTIC ACTIVITY'|'CAUTION'|'COFACTOR'
                |'DEVELOPMENTAL STAGE'|'DISEASE'|'DISRUPTION PHENOTYPE'|'DOMAIN'
                |'ENZYME REGULATION'|'FUNCTION'|'INDUCTION'|'MISCELLANEOUS'
                |'PATHWAY'|'PHARMACEUTICAL'|'POLYMORPHISM'|'PTM'|'SIMILARITY'
                |'SUBUNIT'|'TISSUE SPECIFICITY'|'TOXIC DOSE'
                )
                                                     -> pushMode( CC_COMMON );
CC_TOPIC_WEB_RESOURCE  : 'WEB RESOURCE'              -> pushMode ( CC_WEB_RESOURCE );
CC_TOPIC_BIOPHYSICOCHEMICAL_PROPERTIES :
                  'BIOPHYSICOCHEMICAL PROPERTIES'
                                                     -> pushMode ( CC_BIOPHYSICOCHEMICAL_PROPERTIES );
CC_TOPIC_INTERACTION:
                 'INTERACTION'                       -> pushMode ( CC_INTERACTION );
CC_TOPIC_SUBCELLUR_LOCATION:
                 'SUBCELLULAR LOCATION'              -> pushMode ( CC_SUBCELLULAR_LOCATION );


//the common mode for most of the CC lines;
mode CC_COMMON;
CC_COMMON_CC_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_COMMON_CHANGE_OF_LINE: '\nCC       '              -> type (CHANGE_OF_LINE) ;
CC_COMMON_SPACE : ' '                                -> type (SPACE);
CC_COMMON_SEMICOLON : ';'                            -> type (SEMICOLON);
CC_COMMON_COLON: ':'                                 -> type (COLON);
CC_COMMON_DOT : '.'                                  -> type (DOT);
CC_COMMON_NEW_LINE: '\n'                             -> type (NEW_LINE);
CC_COMMON_TEXT_WORD: TL+;
fragment TL: ~[\n\r\t ];

/*CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
  CC       Absorption:
  CC         Abs(max)=xx nm;
  CC         Note=free_text;
  CC       Kinetic parameters:
  CC         KM=xx unit for substrate [(free_text)];
  CC         Vmax=xx unit enzyme [free_text];
  CC         Note=free_text;
*/
mode CC_BIOPHYSICOCHEMICAL_PROPERTIES;
CC_BP_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_BP_HEADER_1 : 'CC       '                  ->  type (CC_HEADER_1) ;
CC_BP_HEADER_2 : 'CC         '                ->  type (CC_HEADER_2) ;
CC_BP_ABSORPTION: 'Absorption';
CC_BP_ABS: 'Abs(max)=';
CC_BP_NOTE: 'Note='                           -> pushMode( CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT );
CC_BP_KINETIC_PARAMETERS: 'Kinetic parameters';
CC_BP_KM: 'KM='                               -> pushMode( CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT );
CC_BP_VMAX: 'Vmax='                           -> pushMode( CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT );
CC_BP_PH_DEPENDENCE: 'pH dependence:'         -> pushMode( CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT );
CC_BP_REDOX_POTENTIAL: 'Redox potential:'     -> pushMode( CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT );
CC_BP_TEMPERATURE_DEPENDENCE: 'Temperature dependence:' -> pushMode( CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT );
CC_BP_NM : 'nm';
CC_BP_DIGIT: [1-9][0-9]*;
CC_BP_COLON : ':'                            -> type (COLON);
CC_BP_SPACE : ' '                            -> type (SPACE);
CC_BP_SEMICOLON : ';'                            -> type (SEMICOLON);
CC_BP_NEW_LINE: '\n'                             -> type (NEW_LINE);

mode CC_BIOPHYSICOCHEMICAL_PROPERTIES_TEXT;
CC_BP_TEXT_END : ';'                                -> popMode, type (SEMICOLON) ;
fragment CC_BP_TEXT_LETTER: ~[\n\r;];
CC_BP_TEXT_CHANGE_LINE: '\nCC         ';
CC_BP_TEXT : CC_BP_TEXT_CHANGE_LINE ?
            CC_BP_TEXT_LETTER+ (CC_BP_TEXT_CHANGE_LINE CC_BP_TEXT_LETTER+)*;

//the cc web resource model;
//CC   -!- WEB RESOURCE: Name=ResourceName[; Note=FreeText][; URL=WWWAddress].
mode CC_WEB_RESOURCE;
CC_WR_CC_TOPIC_START  : 'CC   -!- '                 -> popMode, type(CC_TOPIC_START) ;
CC_WR_NAME_START: 'Name='                           -> pushMode(CC_WEB_RESOURCE_TEXT);
CC_WR_NOTE_START: 'Note='                           -> pushMode(CC_WEB_RESOURCE_TEXT);
CC_WR_URL_START: 'URL='                             -> pushMode(CC_WEB_RESOURCE_TEXT);
CC_WR_CHANGE_OF_LINE: '\nCC       '                 -> type (CHANGE_OF_LINE);
CC_WR_SPACE : ' '                                   -> type (SPACE);
CC_WR_SEMICOLON : ';'                               -> type (SEMICOLON);
CC_WR_COLON: ':'                                    -> type (COLON);
CC_WR_DOT : '.'                                     -> type (DOT);
CC_WR_NEW_LINE: '\n'                                -> type (NEW_LINE);

mode CC_WEB_RESOURCE_TEXT;
fragment CC_WR_TEXT_LETTER: ~[;.];
CC_WR_URL: '"' .+? '"';
CC_WR_TEXT: CC_WR_TEXT_LETTER+;
CC_WR_WORD_END_1 : ';'                                -> type (SEMICOLON), popMode ;
CC_WR_WORD_END_2 : '.'                                -> type (DOT), popMode ;

/*
CC   -!- INTERACTION:
CC       {{SP_Ac:identifier[ (xeno)]}|Self}; NbExp=n; IntAct=IntAct_Protein_Ac, IntAct_Protein_Ac;
*/
mode CC_INTERACTION;
CC_IR_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_IR_HEADER_1 : 'CC       '                  ->  type (CC_HEADER_1) ;
CC_IR_SELF: 'Self';
CC_IR_NBEXP: 'NbExp=';
CC_IR_INTACT: 'IntAct=';
CC_IR_SPACE : ' '                            -> type (SPACE);
CC_IR_NEW_LINE: '\n'                             -> type (NEW_LINE);
CC_IR_INTEGER: [1-9][0-9]*                             -> type (INTEGER);
CC_IR_SEMICOLON : ';'                               -> type (SEMICOLON);
CC_IR_COLON : ':'                               -> type (COLON);
CC_IR_COMA : ','                               -> type (COMA);
CC_IR_XENO: '(xeno)';
CC_IR_AC: [A-Za-z0-9][-A-Za-z0-9]*;
CC_IR_DASH: '-'                                -> type (DASH);


mode CC_SUBCELLULAR_LOCATION;
CC_SL_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_SL_COLON : ':'                               -> type (COLON);
CC_SL_COMA : ','                               -> type (COMA);
CC_SL_SPACE : ' '                            -> type (SPACE);
CC_SL_DOT : '.'                            -> type (DOT);
CC_SL_NEW_LINE: '\n'                             -> type (NEW_LINE);
CC_SL_SEMICOLON : ';'                               -> type (SEMICOLON);
CC_SL_CHANGE_OF_LINE: '\nCC       '                 -> type (CHANGE_OF_LINE);
CC_SL_NOTE: 'Note=';
CC_SL_FLAG: CC_SL_BY_SIMILARITY| CC_SL_BY_PROBABLE|CC_SL_BY_POTENTIAL;
CC_SL_WORD: CC_SL_WORD_LETTER+ CC_SL_COMA?;
CC_SL_BY_SIMILARITY:'(By similarity)';
CC_SL_BY_PROBABLE:'(Probable)';
CC_SL_BY_POTENTIAL:'(Potential)';
fragment CC_SL_WORD_LETTER: ~[ :,.;\n\r\t];








