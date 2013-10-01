parser grammar RpLineParser;

options { tokenVocab=RpLineLexer;}

rp_rp: RP_START rp_scope (
            (COMA_SPACE rp_scope)*
            COMA_SPACE AND (SPACE|CHANGE_OF_LINE) rp_scope
            )?
            RP_ENDING;
rp_scope: WORD ((SPACE|CHANGE_OF_LINE) (WORD|AND))*;
