parser grammar CcLineParser;

options { tokenVocab=CcLineLexer;}

cc_cc: cc_lines+;

cc_lines: cc_common | cc_web_resource|cc_biophyiochemical
          |cc_interaction |cc_subcellular_location
          |cc_alternative_products|cc_sequence_caution
          |cc_mass_spectrometry;

cc_properties_text : CC_PROPERTIES_TEXT_CHANGE_LINE ?
           CC_PROPERTIES_TEXT (CC_PROPERTIES_TEXT_CHANGE_LINE CC_PROPERTIES_TEXT)*;

cc_common: CC_TOPIC_START CC_TOPIC_COMMON COLON SPACE
           cc_common_text
           NEW_LINE;
cc_common_text: CC_COMMON_TEXT_WORD (cc_common_text_separator CC_COMMON_TEXT_WORD)* ;
cc_common_text_separator: SPACE | CC_COMMON_CHANGE_OF_LINE;

//CC   -!- WEB RESOURCE: Name=ResourceName[; Note=FreeText][; URL=WWWAddress].
cc_web_resource:  CC_TOPIC_START CC_TOPIC_WEB_RESOURCE COLON SPACE
                  cc_web_resource_name
                  (SEMICOLON cc_web_separator cc_web_resource_note )?
                  (SEMICOLON cc_web_separator cc_web_resource_url)?
                   (SEMICOLON|DOT) NEW_LINE;
cc_web_separator: SPACE | CC_WR_CHANGE_OF_LINE;
cc_web_resource_name: CC_WR_NAME_START CC_WR_TEXT  ;
cc_web_resource_note: CC_WR_NOTE_START CC_WR_TEXT ;
cc_web_resource_url: CC_WR_URL_START CC_WR_URL  ;

cc_biophyiochemical: CC_TOPIC_START
                     CC_TOPIC_BIOPHYSICOCHEMICAL_PROPERTIES COLON NEW_LINE
                     cc_biophyiochemical_properties +;
cc_biophyiochemical_properties:
                    cc_biophyiochemical_absorption
                    |cc_biophyiochemical_ph
                    |cc_biophyiochemical_redox
                    |cc_biophyiochemical_temperature
                    |cc_biophyiochemical_kinetic
                    ;
cc_biophyiochemical_absorption: CC_HEADER_1  CC_BP_ABSORPTION COLON NEW_LINE
                 CC_HEADER_2 cc_biophyiochemical_absorption_bas  NEW_LINE
                 CC_HEADER_2  cc_biophyiochemical_absorption_note NEW_LINE;
cc_biophyiochemical_absorption_bas:  CC_BP_ABS  CC_BP_DIGIT SPACE CC_BP_NM SEMICOLON;
cc_biophyiochemical_absorption_note : CC_BP_NOTE cc_properties_text SEMICOLON;
cc_biophyiochemical_ph:   CC_HEADER_1  CC_BP_PH_DEPENDENCE
                 cc_properties_text  SEMICOLON NEW_LINE    ;
cc_biophyiochemical_temperature:   CC_HEADER_1  CC_BP_TEMPERATURE_DEPENDENCE
                 cc_properties_text  SEMICOLON NEW_LINE      ;
cc_biophyiochemical_redox:   CC_HEADER_1  CC_BP_REDOX_POTENTIAL
                 cc_properties_text  SEMICOLON NEW_LINE      ;
cc_biophyiochemical_kinetic: CC_HEADER_1 CC_BP_KINETIC_PARAMETERS COLON NEW_LINE
                  (CC_HEADER_2 cc_biophyiochemical_kinetic_km NEW_LINE)*
                  (CC_HEADER_2 cc_biophyiochemical_kinetic_bpmax NEW_LINE)*
                  (CC_HEADER_2 cc_biophyiochemical_kinetic_note SEMICOLON NEW_LINE)?;
cc_biophyiochemical_kinetic_km: CC_BP_KM cc_properties_text  SEMICOLON;
cc_biophyiochemical_kinetic_bpmax: CC_BP_VMAX cc_properties_text  SEMICOLON;
cc_biophyiochemical_kinetic_note: CC_BP_NOTE cc_properties_text  SEMICOLON;

cc_interaction: CC_TOPIC_START  CC_TOPIC_INTERACTION  COLON NEW_LINE
                   cc_interaction_line+;
cc_interaction_line: CC_HEADER_1 cc_interaction_sp cc_interaction_nbexp cc_interaction_intact;
cc_interaction_sp: ( CC_IR_SELF | ( CC_IR_AC COLON (CC_IR_AC|DASH) (SPACE CC_IR_XENO)?)) SEMICOLON SPACE;
cc_interaction_nbexp: CC_IR_NBEXP INTEGER SEMICOLON SPACE;
cc_interaction_intact: CC_IR_INTACT CC_IR_AC COMA SPACE CC_IR_AC SEMICOLON NEW_LINE;

cc_subcellular_location: CC_TOPIC_START CC_TOPIC_SUBCELLUR_LOCATION COLON SPACE
                         (
                             (((cc_subcellular_location_molecule COLON SPACE)?
                              cc_subcellular_location_location (cc_subcellular_text_separator cc_subcellular_location_location)*?)
                              (cc_subcellular_text_separator cc_subcellular_note)? )
                             | cc_subcellular_note
                         )
                         NEW_LINE;

cc_subcellular_location_molecule: cc_subcellular_words ;
cc_subcellular_location_location :
                              (
                               (cc_subcellular_location_value)|
                               (cc_subcellular_location_value SEMICOLON cc_subcellular_text_separator cc_subcellular_location_value)|
                               (cc_subcellular_location_value SEMICOLON cc_subcellular_text_separator cc_subcellular_location_value
                                                             SEMICOLON cc_subcellular_text_separator cc_subcellular_location_value)
                              ) DOT ;

cc_subcellular_location_value:
                cc_subcellular_words (cc_subcellular_location_flag)?;

cc_subcellular_note:
                CC_SL_NOTE cc_subcellular_words DOT;

cc_subcellular_location_flag: cc_subcellular_text_separator CC_SL_FLAG;
cc_subcellular_words: CC_SL_WORD (cc_subcellular_text_separator CC_SL_WORD)*;
cc_subcellular_text_separator: SPACE | CC_SL_CHANGE_OF_LINE;

cc_alternative_products:
               CC_TOPIC_START CC_TOPIC_ALTERNATIVE_PRODUCTS COLON NEW_LINE
               cc_alternative_products_event
               cc_alternative_products_name (NEW_LINE cc_alternative_products_name)*
               NEW_LINE;
cc_alternative_products_event:
               CC_HEADER_1 cc_alternative_products_event_event SPACE cc_alternative_products_event_namedisoforms
               (NEW_LINE CC_HEADER_2 cc_alternative_products_event_comment)? NEW_LINE;
cc_alternative_products_event_event: CC_AP_EVENT cc_alternative_value
                                    (COMA SPACE cc_alternative_value)* SEMICOLON;
cc_alternative_products_event_namedisoforms: CC_AP_NAMED_ISOFORMS cc_alternative_value SEMICOLON;
cc_alternative_products_event_comment: CC_AP_COMMENT cc_properties_text SEMICOLON ;

cc_alternative_value: CC_AP_WORD (SPACE CC_AP_WORD)*;

cc_alternative_products_name: CC_HEADER_1 CC_AP_NAME cc_alternative_value SEMICOLON
                              (SPACE cc_alternative_products_synonyms)?  NEW_LINE
                              CC_HEADER_2 cc_alternative_products_isoid
                              (SPACE |(NEW_LINE CC_HEADER_2))
                              cc_alternative_products_sequence
                              (NEW_LINE CC_HEADER_2 cc_alternative_products_note)?
                              ;
cc_alternative_products_synonyms:  CC_AP_SYNONYMS cc_alternative_value (COMA SPACE cc_alternative_value)* SEMICOLON;
cc_alternative_products_isoid: CC_AP_ISOID cc_alternative_value (COMA SPACE cc_alternative_value)* SEMICOLON;
cc_alternative_products_sequence: CC_AP_SEQUENCE cc_alternative_products_sequence_value SEMICOLON;
cc_alternative_products_sequence_value:
                (CC_AP_DISPLAYED|CC_AP_EXTERNAL|CC_AP_NOT_DESCRIBED|cc_alternative_products_sequence_value_identifiers);
cc_alternative_products_sequence_value_identifiers:
                CC_AP_FEATURE_IDENTIFIER (COMA SPACE CC_AP_FEATURE_IDENTIFIER)*;
cc_alternative_products_note: CC_AP_NOTE cc_properties_text SEMICOLON;

/*
CC   -!- SEQUENCE CAUTION:
         Sequence=Sequence; Type=Type;[ Positions=Positions;][ Note=Note;]
*/
cc_sequence_caution:
           CC_TOPIC_START CC_TOPIC_SEQUENCE_CAUTION COLON NEW_LINE
           cc_sequence_caution_line +;
cc_sequence_caution_line:
           CC_HEADER_1 cc_sequence_caution_sequence SPACE
            cc_sequence_caution_type
           (SPACE cc_sequence_caution_position)?
           (SPACE cc_sequence_caution_note)?
           NEW_LINE;
cc_sequence_caution_sequence: CC_SC_SEQUENCE cc_sequence_caution_value SEMICOLON;
cc_sequence_caution_type: CC_SC_TYPE CC_SC_TYPE_VALUE SEMICOLON;
cc_sequence_caution_position: CC_SC_POSITIONS cc_sequence_caution_value SEMICOLON;
cc_sequence_caution_note: CC_SC_NOTE cc_sequence_caution_value SEMICOLON;
cc_sequence_caution_value: CC_SC_WORD (SPACE CC_SC_WORD)*;

//CC   -!- MASS SPECTROMETRY: Mass=mass(; Mass_error=error)?; Method=method; Range=ranges( (IsoformID))?(; Note=free_text)?; Source=references;
cc_mass_spectrometry:
        CC_TOPIC_START CC_TOPIC_MASS_SPECTROMETRY COLON SPACE
        cc_mass_spectrometry_mass (SPACE cc_mass_spectrometry_mass_error)?
        SPACE cc_mass_spectrometry_mass_method
        (SPACE|CC_MS_CHANGE_OF_LINE) cc_mass_spectrometry_mass_range
        ((SPACE|CC_MS_CHANGE_OF_LINE) cc_mass_spectrometry_mass_note)?
        (SPACE|CC_MS_CHANGE_OF_LINE) cc_mass_spectrometry_mass_source
        NEW_LINE;

cc_mass_spectrometry_mass:
        CC_MS_MASS CC_MS_V_NUMBER SEMICOLON ;
cc_mass_spectrometry_mass_error:
        CC_MS_MASS_ERROR CC_MS_V_NUMBER SEMICOLON;
cc_mass_spectrometry_mass_method:
        CC_MS_METHOD cc_mass_spectrometry_value SEMICOLON;
cc_mass_spectrometry_mass_range:
        CC_MS_RANGE cc_mass_spectrometry_mass_range_value SEMICOLON;
cc_mass_spectrometry_mass_range_value:
        CC_MS_R_V_NUMBER DASH CC_MS_R_V_CHANGE_OF_LINE ? CC_MS_R_V_NUMBER
        ( SPACE CC_MS_R_V_LEFT_BRACKET CC_MS_R_V_WORD CC_MS_R_V_RIGHT_BRACKET)?;
cc_mass_spectrometry_mass_note:
        CC_MS_NOTE cc_mass_spectrometry_value SEMICOLON;
cc_mass_spectrometry_mass_source:
         CC_MS_SOURCE  cc_mass_spectrometry_value SEMICOLON ;
cc_mass_spectrometry_value: CC_MS_V_WORD ((SPACE|CC_MS_V_CHANGE_OF_LINE) CC_MS_V_WORD)*;














