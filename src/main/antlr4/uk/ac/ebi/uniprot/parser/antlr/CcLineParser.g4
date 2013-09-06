parser grammar CcLineParser;

options { tokenVocab=CcLineLexer;}

cc_cc: cc_lines+;

cc_lines: cc_common | cc_web_resource|cc_biophyiochemical;

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





