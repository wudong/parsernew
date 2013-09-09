parser grammar CcLineParser;

options { tokenVocab=CcLineLexer;}

cc_cc: cc_lines+;

cc_lines: cc_common | cc_web_resource|cc_biophyiochemical |cc_interaction |cc_subcellular_location;

cc_common: CC_TOPIC_START CC_TOPIC_COMMON COLON SPACE
           cc_common_text
           NEW_LINE;
cc_common_text: CC_COMMON_TEXT_WORD (cc_common_text_separator CC_COMMON_TEXT_WORD)* ;
cc_common_text_separator: SPACE | CHANGE_OF_LINE;

//CC   -!- WEB RESOURCE: Name=ResourceName[; Note=FreeText][; URL=WWWAddress].
cc_web_resource:  CC_TOPIC_START CC_TOPIC_WEB_RESOURCE COLON SPACE
                  cc_web_resource_name
                  (SEMICOLON cc_web_separator cc_web_resource_note )?
                  (SEMICOLON cc_web_separator cc_web_resource_url)?
                   (SEMICOLON|DOT) NEW_LINE;
cc_web_separator: SPACE | CHANGE_OF_LINE;
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
                 CC_HEADER_2  CC_BP_ABS  CC_BP_DIGIT SPACE CC_BP_NM SEMICOLON NEW_LINE
                 CC_HEADER_2  CC_BP_NOTE CC_BP_TEXT SEMICOLON NEW_LINE;
cc_biophyiochemical_ph:   CC_HEADER_1  CC_BP_PH_DEPENDENCE
                 CC_BP_TEXT SEMICOLON NEW_LINE    ;
cc_biophyiochemical_temperature:   CC_HEADER_1  CC_BP_TEMPERATURE_DEPENDENCE
                 CC_BP_TEXT SEMICOLON NEW_LINE      ;
cc_biophyiochemical_redox:   CC_HEADER_1  CC_BP_REDOX_POTENTIAL
                 CC_BP_TEXT SEMICOLON NEW_LINE      ;
cc_biophyiochemical_kinetic: CC_HEADER_1 CC_BP_KINETIC_PARAMETERS COLON NEW_LINE
                  (CC_HEADER_2 CC_BP_KM CC_BP_TEXT SEMICOLON NEW_LINE)*
                  (CC_HEADER_2 CC_BP_VMAX CC_BP_TEXT SEMICOLON NEW_LINE)*
                  (CC_HEADER_2 CC_BP_NOTE CC_BP_TEXT SEMICOLON NEW_LINE)?;

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
cc_subcellular_text_separator: SPACE | CHANGE_OF_LINE;






