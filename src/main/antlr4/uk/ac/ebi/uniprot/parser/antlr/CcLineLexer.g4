lexer grammar CcLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

//define the tokens that is common in all the modes.
tokens { CC_TOPIC_START, SPACE, SEMICOLON, COMA,
 COLON, DOT, NEW_LINE, CHANGE_OF_LINE, CHANGE_OF_LINE_LEVEL2,DOT_NEW_LINE,
 CC_HEADER_1, CC_HEADER_2, INTEGER, DASH, QUESTION_MARK}

CC_TOPIC_START  : 'CC   -!- ';
CC_TOPIC_COMMON : ('ALLERGEN'|'BIOTECHNOLOGY'|'CATALYTIC ACTIVITY'|'CAUTION'|'COFACTOR'
                |'DEVELOPMENTAL STAGE'|'DISRUPTION PHENOTYPE'|'DOMAIN'
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
                 'INTERACTION:'                       -> pushMode ( CC_INTERACTION );
CC_TOPIC_SUBCELLUR_LOCATION:
                 'SUBCELLULAR LOCATION'              -> pushMode ( CC_SUBCELLULAR_LOCATION );
CC_TOPIC_ALTERNATIVE_PRODUCTS:
                 'ALTERNATIVE PRODUCTS'              -> pushMode ( CC_ALTERNATIVE_PRODUCTS );
CC_TOPIC_MASS_SPECTROMETRY:
                 'MASS SPECTROMETRY'                 -> pushMode ( CC_MASS_SPECTROMETRY );
CC_TOPIC_SEQUENCE_CAUTION:
                 'SEQUENCE CAUTION'                  -> pushMode ( CC_SEQUENCE_CAUTION );
CC_TOPIC_RNA_EDITING:
                'RNA EDITING'                        -> pushMode ( CC_RNA_EDITING );
CC_TOPIC_DISEASE:
                 'DISEASE:'                           -> pushMode ( CC_DISEASE );


//the common mode for most of the CC lines;
mode CC_COMMON;
CC_COMMON_CC_TOPIC_START  : 'CC   -!- '               -> popMode, type(CC_TOPIC_START) ;
CC_COMMON_COLON_SPACE: ': '                           -> pushMode (CC_COMMON_TEXT) ;

mode CC_COMMON_TEXT;
CC_COMMON_CHANGE_OF_LINE: '\nCC       '              {replaceChangeOfLine();};
CC_COMMON_SPACE : ' '                                -> type (SPACE);
CC_COMMON_DOT: '.'                                   -> type (DOT);
CC_COMMON_NEW_LINE : '\n'                            -> type (NEW_LINE), popMode;
CC_COMMON_DOT_SPACE : '. '                            ;
CC_COMMON_DOT_CHANGE_OF_LINE : '.\nCC       '         ;
CC_COMMON_TEXT_WORD: TL+                     {!getText().endsWith(".")}?;
fragment TL: ~[\n\r\t ];

mode CC_PROPERTIES_TEXT_MODE;
CC_PROPERTIES_TEXT_END : ';'                                -> type (SEMICOLON), popMode;
CC_PROPERTIES_TEXT_CHANGE_LINE_1: '\nCC         '           {setType(CHANGE_OF_LINE_LEVEL2);replaceChangeOfLine();};
CC_PROPERTIES_TEXT_CHANGE_LINE_2: '\nCC       '             {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
CC_PROPERTIES_TEXT: CC_PROPERTIES_TEXT_LETTER+;
fragment CC_PROPERTIES_TEXT_LETTER: ~[\n\r;];

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
CC_BP_NOTE: 'Note='                           -> pushMode( CC_PROPERTIES_TEXT_MODE );
CC_BP_KINETIC_PARAMETERS: 'Kinetic parameters';
CC_BP_KM: 'KM='                               -> pushMode( CC_PROPERTIES_TEXT_MODE );
CC_BP_VMAX: 'Vmax='                           -> pushMode( CC_PROPERTIES_TEXT_MODE );
CC_BP_PH_DEPENDENCE: 'pH dependence:'         -> pushMode( CC_PROPERTIES_TEXT_MODE );
CC_BP_REDOX_POTENTIAL: 'Redox potential:'     -> pushMode( CC_PROPERTIES_TEXT_MODE );
CC_BP_TEMPERATURE_DEPENDENCE: 'Temperature dependence:' -> pushMode( CC_PROPERTIES_TEXT_MODE );
CC_BP_NM : 'nm';
CC_BP_DIGIT: '~'?[1-9][0-9]*;
CC_BP_COLON : ':'                            -> type (COLON);
CC_BP_SPACE : ' '                            -> type (SPACE);
CC_BP_SEMICOLON : ';'                            -> type (SEMICOLON);
CC_BP_NEW_LINE: '\n'                             -> type (NEW_LINE);

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
CC_IR_COLON : ':'                               -> type (COLON), pushMode(CC_INTERACTION_GENE_NAME);
CC_IR_COMA : ','                               -> type (COMA);
CC_IR_AC: [A-Za-z0-9][-\.A-Za-z0-9]*;


mode CC_INTERACTION_GENE_NAME;
CC_IR_XENO: '(xeno)';
CC_IR_GENENAME: (~[ ;])+ ;
CC_IR_G_SPACE: ' '                    -> type(SPACE);
CC_IR_G_DASH: '-'                     -> type (DASH);
CC_IR_G_SEMICOLON: ';'                -> popMode, type(SEMICOLON);


mode CC_SUBCELLULAR_LOCATION;
CC_SL_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_SL_COLON : ':'                               -> type (COLON);
CC_SL_SPACE : ' '                            -> type (SPACE);
CC_SL_DOT : '.'                              -> type (DOT);
CC_SL_NEW_LINE: '\n'                         -> type (NEW_LINE);
CC_SL_SEMICOLON : ';'                        -> type (SEMICOLON);
CC_SL_NOTE: 'Note='                           -> pushMode(CC_SUBCELLULAR_LOCATION_NOTE);
CC_SL_CHANGE_OF_LINE: '\nCC       '           {replaceChangeOfLine(); setType(CHANGE_OF_LINE);};
CC_SL_FLAG: CC_SL_BY_SIMILARITY| CC_SL_BY_PROBABLE|CC_SL_BY_POTENTIAL;
CC_SL_WORD: (CC_SL_WORD_LETTER|CC_SL_DOT)* CC_SL_WORD_LETTER;
CC_SL_BY_SIMILARITY:'(By similarity)';
CC_SL_BY_PROBABLE:'(Probable)';
CC_SL_BY_POTENTIAL:'(Potential)';
fragment CC_SL_WORD_LETTER: ~[ :.;=\n\r\t];

mode CC_SUBCELLULAR_LOCATION_NOTE;
CC_SL_N_NEW_LINE: '\n'                         -> type (NEW_LINE), popMode;
CC_SL_N_DOT : '.'                              -> type (DOT);
CC_SL_N_SPACE : ' '                            -> type (SPACE);
CC_SL_N_CHANGE_OF_LINE: '\nCC       '         {replaceChangeOfLine(); setType(CHANGE_OF_LINE);};
CC_SL_N_FLAG:
        (CC_SL_N_BY_SIMILARITY
        | CC_SL_N_BY_PROBABLE
        |CC_SL_N_BY_POTENTIAL)                  -> type (CC_SL_FLAG);
CC_SL_N_BY_SIMILARITY:'(By similarity)'         -> type (CC_SL_BY_SIMILARITY);
CC_SL_N_BY_PROBABLE:'(Probable)'                -> type (CC_SL_BY_PROBABLE);
CC_SL_N_BY_POTENTIAL:'(Potential)'              -> type (CC_SL_BY_POTENTIAL);
CC_SL_N_NOTE_WORD:  CC_SL_N_WORD_LETTER+        {!getText().endsWith(".")}?    -> type (CC_SL_WORD);
fragment CC_SL_N_WORD_LETTER: ~[ \n\r\t];


mode CC_ALTERNATIVE_PRODUCTS;
CC_AP_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_AP_COLON : ':'                               -> type (COLON);
CC_AP_COMA : ','                               -> type (COMA);
CC_AP_SPACE : ' '                            -> type (SPACE);
CC_AP_DOT : '.'                            -> type (DOT);
CC_AP_NEW_LINE: '\n'                             -> type (NEW_LINE);
CC_AR_HEADER_1 : 'CC       '                  ->  type (CC_HEADER_1) ;
CC_AR_HEADER_2 : 'CC         '                  ->  type (CC_HEADER_2) ;
CC_AP_COMMENT: 'Comment='                          ->  pushMode ( CC_PROPERTIES_TEXT_MODE ) ;
CC_AP_NOTE: 'Note='                                 -> pushMode ( CC_PROPERTIES_TEXT_MODE );
CC_AP_NAME: 'Name='                                -> pushMode ( CC_ALTERNATIVE_PRODUCTS_VALUE ) ;
CC_AP_SYNONYMS: 'Synonyms='                        -> pushMode ( CC_ALTERNATIVE_PRODUCTS_VALUE ) ;
CC_AP_ISOID: 'IsoId='                              -> pushMode ( CC_ALTERNATIVE_PRODUCTS_VALUE ) ;
CC_AP_SEQUENCE: 'Sequence='                        -> pushMode ( CC_ALTERNATIVE_PRODUCTS_SEQUENCE_VALUE ) ;
CC_AP_EVENT: 'Event='                               -> pushMode ( CC_ALTERNATIVE_PRODUCTS_VALUE ) ;
CC_AP_NAMED_ISOFORMS: 'Named isoforms='             -> pushMode ( CC_ALTERNATIVE_PRODUCTS_VALUE ) ;

mode CC_ALTERNATIVE_PRODUCTS_VALUE;
CC_AP_SEMICOLON : ';'                              -> popMode, type (SEMICOLON);
CC_AP_VALUE_SPACE : ' '                            -> type (SPACE);
CC_AP_VALUE_COMA : ','                             -> type (COMA);
CC_AR_CHANGE_OF_LINE : '\nCC       '               ->  type (CHANGE_OF_LINE);
CC_AP_WORD:  (CC_AP_WORD_LETTER|CC_AP_VALUE_COMA)*CC_AP_WORD_LETTER;
fragment CC_AP_WORD_LETTER: ~[ ,;\n\r\t];

mode CC_ALTERNATIVE_PRODUCTS_SEQUENCE_VALUE;
CC_AP_SEMICOLON_SV : ';'                               -> popMode, type (SEMICOLON);
CC_AP_VALUE_SPACE_SV : ' '                             -> type (SPACE);
CC_AP_VALUE_COMA_SV : ','                              -> type (COMA);
CC_AP_VALUE_CHANGE_LINE : '\nCC               ' CC_AP_VALUE_SPACE_SV+
                                                       -> type (CHANGE_OF_LINE);
CC_AP_DISPLAYED: 'Displayed' ;
CC_AP_EXTERNAL: 'External';
CC_AP_NOT_DESCRIBED: 'Not described';
CC_AP_VALUE_UNSURE : '?'                        -> type (QUESTION_MARK);
CC_AP_FEATURE_IDENTIFIER: 'VSP_' 'new'? [0-9]+;

mode CC_SEQUENCE_CAUTION;
CC_SC_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_SC_SEMICOLON : ';'                               -> type (SEMICOLON);
CC_SC_COLON : ':'                               -> type (COLON);
CC_SC_SPACE : ' '                               -> type (SPACE);
CC_SC_HEADER_1: 'CC       '                 -> type (CC_HEADER_1);
CC_SC_NEW_LINE: '\n'                                -> type (NEW_LINE);
CC_SC_SEQUENCE : 'Sequence=';
CC_SC_TYPE : 'Type=';
CC_SC_POSITIONS : 'Positions='                -> pushMode ( CC_SEQUENCE_CAUTION_POSITION );
CC_SC_NOTE : 'Note='                          -> pushMode ( CC_SEQUENCE_CAUTION_NOTE );
CC_SC_TYPE_VALUE: 'Frameshift' | 'Erroneous initiation' | 'Erroneous termination'
                   |'Erroneous gene model prediction'|'Erroneous translation'
                   |'Miscellaneous discrepancy';
CC_SC_WORD: CC_SC_WORD_LETTER+;
fragment CC_SC_WORD_LETTER: ~[ =;\n\r\t];

mode CC_SEQUENCE_CAUTION_NOTE;
CC_SC_NOTE_NEW_LINE: '\n'                                -> type (NEW_LINE), popMode;
CC_SC_NOTE_SEMICOLON : ';'                               -> type (SEMICOLON);
CC_SC_NOTE_TEXT: CC_SC_NOTE_TEXT_L+ ;
fragment  CC_SC_NOTE_TEXT_L: ~[;\n\r\t];

mode CC_SEQUENCE_CAUTION_POSITION;
CC_SC_P_SEMICOLON : ';'                               -> popMode, type (SEMICOLON);
CC_SC_P_SPACE : ' '                                   -> type (SPACE);
CC_SC_P_COMA : ','                                    -> type (COMA);
CC_SC_P_INT : [1-9][0-9]*                             -> type (INTEGER);
CC_SC_P_VALUE: 'Several';

//the cc web resource model;
//CC   -!- WEB RESOURCE: Name=ResourceName[; Note=FreeText][; URL=WWWAddress];
mode CC_WEB_RESOURCE;
CC_WR_CC_TOPIC_START  : 'CC   -!- '                 -> popMode, type(CC_TOPIC_START) ;
CC_WR_NAME_START: 'Name='                           -> pushMode(CC_PROPERTIES_TEXT_MODE);
CC_WR_NOTE_START: 'Note='                           -> pushMode(CC_PROPERTIES_TEXT_MODE);
CC_WR_URL_START: 'URL='                             -> pushMode(CC_PROPERTIES_TEXT_MODE);
CC_WR_CHANGE_OF_LINE: '\nCC       '     {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
CC_WR_SPACE : ' '                                   -> type (SPACE);
//CC_WR_SEMICOLON : ';'                               -> type (SEMICOLON);
CC_WR_COLON: ':'                                    -> type (COLON);
CC_WR_NEW_LINE: '\n'                                -> type (NEW_LINE);

/*
mode CC_WEB_RESOURCE_URL;
fragment CC_WR_TEXT_LETTER: ~[;.];
CC_WR_URL: '"' .+? '"';
CC_WR_TEXT: CC_WR_TEXT_LETTER+;
CC_WR_WORD_END_1 : ';'                                -> type (SEMICOLON), popMode ;
*/

//CC   -!- MASS SPECTROMETRY: Mass=mass(; Mass_error=error)?; Method=method; Range=ranges( (IsoformID))?(; Note=free_text)?; Source=references;
mode CC_MASS_SPECTROMETRY;
CC_MS_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_MS_COLON : ':'                               -> type (COLON);
CC_MS_SPACE : ' '                               -> type (SPACE);
CC_MS_NEW_LINE: '\n'                                -> type (NEW_LINE);
CC_MS_MASS: 'Mass='                               -> pushMode ( CC_MASS_SPECTROMETRY_VALUE );
CC_MS_MASS_ERROR: 'Mass_error='                    -> pushMode ( CC_MASS_SPECTROMETRY_VALUE );
CC_MS_METHOD: 'Method='                            -> pushMode ( CC_MASS_SPECTROMETRY_VALUE );
CC_MS_RANGE: 'Range='                              -> pushMode ( CC_MASS_SPECTROMETRY_RANGE_VALUE );
CC_MS_NOTE: 'Note='                               -> pushMode ( CC_PROPERTIES_TEXT_MODE );
CC_MS_SOURCE: 'Source='                           -> pushMode ( CC_MASS_SPECTROMETRY_VALUE );
CC_MS_CHANGE_OF_LINE: '\nCC       '               -> type (CHANGE_OF_LINE);

mode CC_MASS_SPECTROMETRY_VALUE;
CC_MS_V_SEMICOLON : ';'                               -> popMode, type (SEMICOLON);
CC_MS_V_SPACE : ' '                                   -> type (SPACE);
CC_MS_V_CHANGE_OF_LINE : '\nCC       '                {setType(CHANGE_OF_LINE);replaceChangeOfLine();}   ;
CC_MS_V_NUMBER :  [0-9]+ ('.'[0-9]+)?           ;
CC_MS_V_WORD:  CS_MS_V_LETTER+;
fragment CS_MS_V_LETTER: ~[ ;\n\r\t];

mode CC_MASS_SPECTROMETRY_RANGE_VALUE;
CC_MS_R_V_SEMICOLON : ';'                               -> popMode, type (SEMICOLON);
CC_MS_R_V_COMA : ','                                    -> type (COMA);
CC_MS_R_V_LEFT_BRACKET : '('                            -> pushMode (CC_MASS_SPECTROMETRY_RANGE_VALUE_ISOFORM);
CC_MS_R_V_SPACE : ' '                                   -> type (SPACE);
CC_MS_R_V_DASH : '-'                                    -> type (DASH);
CC_MS_R_V_INTEGER: [1-9][0-9]*                          -> type (INTEGER);
CC_MS_R_V_UNKNOWN: '?'                                  ;
CC_MS_R_V_CHANGE_OF_LINE : '\nCC       '                -> type (CHANGE_OF_LINE);

mode CC_MASS_SPECTROMETRY_RANGE_VALUE_ISOFORM;
CC_MS_R_V_RIGHT_BRACKET : ')'                           -> popMode;
CC_MS_R_V_ISO : CC_MS_R_V_ISO_L+                        ;
fragment CC_MS_R_V_ISO_L : ~[)]                         ;

mode CC_RNA_EDITING;
CC_RE_TOPIC_START  : 'CC   -!- '              ->  popMode, type(CC_TOPIC_START) ;
CC_RE_MODIFIED_POSITION: 'Modified_positions=';
CC_RE_MODIFIED_POSITION_UNDETERMINED: 'Undetermined';
CC_RE_MODIFIED_POSITION_NOT_APPLICABLE: 'Not_applicable';
CC_RE_INT: [1-9][0-9]*                        -> type (INTEGER);
CC_RE_COMA: ','                        -> type (COMA);
CC_RE_SEMICOLON: ';'                        -> type (SEMICOLON);
CC_RE_COLON: ':'                        -> type (COLON);
CC_RE_DOT: '.'                        -> type (DOT);
CC_RE_SPACE: ' '                        -> type (SPACE);
CC_RE_CHANGE_OF_LINE : '\nCC       '     {replaceChangeOfLine();}   ;
CC_RE_NOTE: 'Note='                      -> pushMode (CC_COMMON_TEXT);
CC_RE_NEW_LINE: '\n'                          -> type (NEW_LINE);

/*
mode CC_RNA_EDITING_NOTE;
CC_RE_N_SPACE: ' '                              -> type (SPACE);
CC_RE_N_WORD :  CS_RE_LETTER+;
CC_RE_N_CHANGE_OF_LINE : '\nCC       '          {replaceChangeOfLine();}   ;
CC_RE_N_NEW_LINE: '\n'                          -> type (NEW_LINE), popMode;
CC_RE_N_DOT: '.'                                -> type (DOT);
fragment CS_RE_LETTER: ~[ .\n\r\t];
*/

mode CC_DISEASE;
CC_D_TOPIC_START  : 'CC   -!- '           -> popMode, type(CC_TOPIC_START) ;
CC_D_NOTE: 'Note='                        ;
CC_D_SPACE: ' '                           -> type (SPACE);
CC_D_DOT: '.'                             -> type (DOT);

CC_D_NEW_LINE: '\n'                            -> type (NEW_LINE);
CC_D_CHANGE_OF_LINE : '\nCC       '       {setType(CHANGE_OF_LINE); replaceChangeOfLine();};
CC_D_PUBMED: '(PubMed:' INTEGER (CC_D_COMA CC_D_SPACE|CC_D_CHANGE_OF_LINE INTEGER) ')' ;

CC_D_ABBR_MIM: CC_D_ABBR (CC_D_SPACE|CC_D_CHANGE_OF_LINE) CC_D_MIM;
fragment CC_D_ABBR: '(' CC_D_WORD ((CC_D_SPACE|CC_D_CHANGE_OF_LINE) CC_D_WORD)* ')';
fragment CC_D_MIM: '[MIM:' INTEGER ']:'             ;

CC_D_WORD: (CC_D_L+)   {!getText().endsWith(".")}?;
fragment INTEGER: [1-9][0-9]*             ;
fragment CC_D_L: ~[ \n\r\t=];
fragment CC_D_COMA: ','     ;

