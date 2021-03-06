parser grammar CcLineParser;

options { tokenVocab=CcLineLexer;}

cc_cc: cc_lines+;

cc_lines: cc_common | cc_web_resource|cc_biophyiochemical
          |cc_interaction |cc_subcellular_location
          |cc_alternative_products|cc_sequence_caution
          |cc_mass_spectrometry |cc_rna_editing
          |cc_disease;

cc_properties_text_level2 : CHANGE_OF_LINE_LEVEL2 ?
           CC_PROPERTIES_TEXT (CHANGE_OF_LINE_LEVEL2 CC_PROPERTIES_TEXT)*;

cc_properties_text : CHANGE_OF_LINE ?
           CC_PROPERTIES_TEXT (CHANGE_OF_LINE CC_PROPERTIES_TEXT)*;

cc_common: CC_TOPIC_START CC_TOPIC_COMMON CC_COMMON_COLON_SPACE
           cc_common_text
           DOT NEW_LINE;
cc_common_text: CC_COMMON_TEXT_WORD (cc_common_text_separator CC_COMMON_TEXT_WORD)* ;
cc_common_text_separator: SPACE | CC_COMMON_CHANGE_OF_LINE|CC_COMMON_DOT_SPACE|CC_COMMON_DOT_CHANGE_OF_LINE;

//CC   -!- WEB RESOURCE: Name=ResourceName[; Note=FreeText][; URL=WWWAddress].
cc_web_resource:  CC_TOPIC_START CC_TOPIC_WEB_RESOURCE COLON SPACE
                  cc_web_resource_name
                  (SEMICOLON (SPACE | CHANGE_OF_LINE) cc_web_resource_note )?
                  (SEMICOLON (SPACE | CHANGE_OF_LINE) cc_web_resource_url)?
                   (SEMICOLON|DOT) NEW_LINE;
cc_web_resource_name: CC_WR_NAME_START cc_properties_text  ;
cc_web_resource_note: CC_WR_NOTE_START cc_properties_text ;
cc_web_resource_url: CC_WR_URL_START cc_properties_text ;

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
                 CC_HEADER_2 cc_biophyiochemical_absorption_bas
                 (NEW_LINE CC_HEADER_2  cc_biophyiochemical_absorption_note)?
                 NEW_LINE;
cc_biophyiochemical_absorption_bas:  CC_BP_ABS  CC_BP_DIGIT SPACE CC_BP_NM SEMICOLON;
cc_biophyiochemical_absorption_note : CC_BP_NOTE cc_properties_text_level2 SEMICOLON;
cc_biophyiochemical_ph:   CC_HEADER_1  CC_BP_PH_DEPENDENCE
                 cc_properties_text_level2  SEMICOLON NEW_LINE    ;
cc_biophyiochemical_temperature:   CC_HEADER_1  CC_BP_TEMPERATURE_DEPENDENCE
                 cc_properties_text_level2  SEMICOLON NEW_LINE      ;
cc_biophyiochemical_redox:   CC_HEADER_1  CC_BP_REDOX_POTENTIAL
                 cc_properties_text_level2  SEMICOLON NEW_LINE      ;
cc_biophyiochemical_kinetic: CC_HEADER_1 CC_BP_KINETIC_PARAMETERS COLON NEW_LINE
                  (CC_HEADER_2 cc_biophyiochemical_kinetic_km NEW_LINE)*
                  (CC_HEADER_2 cc_biophyiochemical_kinetic_bpmax NEW_LINE)*
                  (CC_HEADER_2 cc_biophyiochemical_kinetic_note NEW_LINE)?;
cc_biophyiochemical_kinetic_km: CC_BP_KM cc_properties_text_level2  SEMICOLON;
cc_biophyiochemical_kinetic_bpmax: CC_BP_VMAX cc_properties_text_level2  SEMICOLON;
cc_biophyiochemical_kinetic_note: CC_BP_NOTE cc_properties_text_level2  SEMICOLON;

cc_interaction: CC_TOPIC_START  CC_TOPIC_INTERACTION  NEW_LINE
                   cc_interaction_line+;
cc_interaction_line: CC_HEADER_1 cc_interaction_sp cc_interaction_nbexp cc_interaction_intact;
cc_interaction_sp: ( CC_IR_SELF | ( CC_IR_AC COLON (cc_interaction_gene|DASH) (SPACE CC_IR_XENO)?)) SEMICOLON SPACE;
cc_interaction_gene: CC_IR_GENENAME (SPACE CC_IR_GENENAME)*;
cc_interaction_nbexp: CC_IR_NBEXP INTEGER SEMICOLON SPACE;
cc_interaction_intact: CC_IR_INTACT CC_IR_AC COMA SPACE CC_IR_AC SEMICOLON NEW_LINE;

cc_subcellular_location: CC_TOPIC_START CC_TOPIC_SUBCELLUR_LOCATION COLON SPACE
                         (
                             (((cc_subcellular_location_molecule COLON (SPACE|CHANGE_OF_LINE) )?
                              cc_subcellular_location_section )?
                              (cc_subcellular_text_separator cc_subcellular_note)? )
                             | cc_subcellular_note
                         )
                         NEW_LINE;

cc_subcellular_location_section:
            cc_subcellular_location_location (cc_subcellular_text_separator cc_subcellular_location_location)*;

cc_subcellular_location_molecule: cc_subcellular_words ;
cc_subcellular_location_location :
                              (
                               (cc_subcellular_location_value)
                               |(cc_subcellular_location_value SEMICOLON cc_subcellular_text_separator cc_subcellular_location_value)
                               |(cc_subcellular_location_value SEMICOLON cc_subcellular_text_separator cc_subcellular_location_value
                                  SEMICOLON cc_subcellular_text_separator cc_subcellular_location_value)
                              ) DOT ;

cc_subcellular_location_value:
                cc_subcellular_words (cc_subcellular_location_flag)?;

cc_subcellular_note:
                CC_SL_NOTE cc_subcellular_note_value (cc_subcellular_text_separator cc_subcellular_note_value)*;
cc_subcellular_note_value:
                cc_subcellular_words (cc_subcellular_location_flag)? DOT;

cc_subcellular_location_flag: cc_subcellular_text_separator CC_SL_FLAG;
cc_subcellular_words: CC_SL_WORD (cc_subcellular_text_separator CC_SL_WORD)*;
cc_subcellular_text_separator: SPACE | CHANGE_OF_LINE;

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
cc_alternative_products_event_comment: CC_AP_COMMENT cc_properties_text_level2 SEMICOLON ;

cc_alternative_value: CC_AP_WORD ((SPACE|CHANGE_OF_LINE) CC_AP_WORD)*;

cc_alternative_products_name: CC_HEADER_1 CC_AP_NAME cc_alternative_value SEMICOLON
                              (SPACE cc_alternative_products_synonyms)?  NEW_LINE
                              CC_HEADER_2 cc_alternative_products_isoid
                              (SPACE |(NEW_LINE CC_HEADER_2))
                              cc_alternative_products_sequence
                              (NEW_LINE CC_HEADER_2 cc_alternative_products_note)?
                              ;
cc_alternative_products_synonyms:  CC_AP_SYNONYMS cc_alternative_value (COMA (SPACE|CHANGE_OF_LINE) cc_alternative_value)* SEMICOLON;
cc_alternative_products_isoid: CC_AP_ISOID cc_alternative_value (COMA SPACE cc_alternative_value)* SEMICOLON;
cc_alternative_products_sequence: CC_AP_SEQUENCE cc_alternative_products_sequence_value SEMICOLON;
cc_alternative_products_sequence_value:
                (
                CC_AP_DISPLAYED|CC_AP_EXTERNAL|CC_AP_NOT_DESCRIBED
                |CC_AP_VALUE_UNSURE
                |cc_alternative_products_sequence_value_identifiers
                );
cc_alternative_products_sequence_value_identifiers:
                CC_AP_FEATURE_IDENTIFIER (COMA (SPACE|CHANGE_OF_LINE) CC_AP_FEATURE_IDENTIFIER)*;
cc_alternative_products_note: CC_AP_NOTE cc_properties_text_level2 SEMICOLON;

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
cc_sequence_caution_position: CC_SC_POSITIONS cc_sequence_caution_position_value SEMICOLON;
cc_sequence_caution_position_value:  CC_SC_P_VALUE | (INTEGER (COMA SPACE INTEGER)*);
cc_sequence_caution_note: CC_SC_NOTE CC_SC_NOTE_TEXT SEMICOLON;
cc_sequence_caution_value: CC_SC_WORD (SPACE CC_SC_WORD)*;

//CC   -!- MASS SPECTROMETRY: Mass=mass(; Mass_error=error)?; Method=method; Range=ranges( (IsoformID))?(; Note=free_text)?; Source=references;
cc_mass_spectrometry:
        CC_TOPIC_START CC_TOPIC_MASS_SPECTROMETRY COLON SPACE
        cc_mass_spectrometry_mass ((SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_error)?
        (SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_method
        (SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_range
        //((SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_range)*
        ((SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_note)?
        (SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_source
        NEW_LINE;

cc_mass_spectrometry_mass:
        CC_MS_MASS CC_MS_V_NUMBER SEMICOLON ;
cc_mass_spectrometry_mass_error:
        CC_MS_MASS_ERROR CC_MS_V_NUMBER SEMICOLON;
cc_mass_spectrometry_mass_method:
        CC_MS_METHOD cc_mass_spectrometry_value SEMICOLON;
cc_mass_spectrometry_mass_range:
        CC_MS_RANGE cc_mass_spectrometry_mass_range_value (COMA (SPACE|CHANGE_OF_LINE) cc_mass_spectrometry_mass_range_value)*
        ( (SPACE|CHANGE_OF_LINE) CC_MS_R_V_LEFT_BRACKET CC_MS_R_V_ISO CC_MS_R_V_RIGHT_BRACKET)? SEMICOLON;
cc_mass_spectrometry_mass_range_value:
        cc_mass_spectrometry_mass_range_value_value DASH CHANGE_OF_LINE ? cc_mass_spectrometry_mass_range_value_value;
cc_mass_spectrometry_mass_range_value_value: INTEGER|CC_MS_R_V_UNKNOWN;
cc_mass_spectrometry_mass_note:
        CC_MS_NOTE cc_properties_text SEMICOLON;
cc_mass_spectrometry_mass_source:
         CC_MS_SOURCE  cc_mass_spectrometry_value SEMICOLON ;
cc_mass_spectrometry_value: CC_MS_V_WORD ((SPACE|CHANGE_OF_LINE) CC_MS_V_WORD)*;

cc_rna_editing:
      CC_TOPIC_START CC_TOPIC_RNA_EDITING COLON SPACE
      cc_rna_edigint_modified_position
      (SEMICOLON cc_re_separator_1 cc_rna_edigint_note)?
      DOT NEW_LINE;

cc_rna_edigint_modified_position:
      CC_RE_MODIFIED_POSITION (cc_rna_editing_position|CC_RE_MODIFIED_POSITION_UNDETERMINED|CC_RE_MODIFIED_POSITION_NOT_APPLICABLE);
cc_rna_editing_position:
      INTEGER (COMA cc_re_separator_1 INTEGER)*;
cc_re_separator_1: (SPACE | CC_RE_CHANGE_OF_LINE);

cc_rna_edigint_note: CC_RE_NOTE cc_common_text;
//cc_re_note_value : cc_re_note_value_detail (cc_re_separator_2 cc_re_note_value_detail)*;
//cc_re_note_value_detail : CC_RE_N_WORD (cc_re_separator_2 CC_RE_N_WORD)* DOT;
//cc_re_separator_2: (SPACE | CC_RE_N_CHANGE_OF_LINE);

cc_disease:
    CC_TOPIC_START CC_TOPIC_DISEASE SPACE
     (cc_disease_name  (SPACE|CHANGE_OF_LINE)
      cc_disease_abbr_min  (SPACE|CHANGE_OF_LINE))?
     ((cc_disease_description (SPACE|CHANGE_OF_LINE) cc_disease_note)
       |cc_disease_description
       |cc_disease_note)
     NEW_LINE;

cc_disease_name: cc_disease_text_text;
cc_disease_abbr_min: CC_D_ABBR_MIM;

cc_disease_description: cc_disease_text ((SPACE|CHANGE_OF_LINE) cc_disease_text)*;
cc_disease_note: CC_D_NOTE  cc_disease_text ((SPACE|CHANGE_OF_LINE) cc_disease_text)*;
cc_disease_text: cc_disease_text_text (CC_D_PUBMED)? DOT;
cc_disease_text_text: (CC_D_WORD) ((SPACE|CHANGE_OF_LINE) (CC_D_WORD))*;

