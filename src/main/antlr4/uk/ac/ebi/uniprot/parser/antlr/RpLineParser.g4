parser grammar RpLineParser;

options { tokenVocab=RpLineLexer;}

rp_rp: RP_START rp_scope (
            (COMA (SPACE|CHANGE_OF_LINE) rp_scope)*
            COMA (SPACE|CHANGE_OF_LINE) AND (SPACE|CHANGE_OF_LINE) rp_scope
            )?
            RP_ENDING;
rp_scope: WORD ((SPACE|CHANGE_OF_LINE) (WORD|AND))*;
