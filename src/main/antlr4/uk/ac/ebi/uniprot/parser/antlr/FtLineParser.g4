/*
FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
FT                                isoform 2).
FT                                /FTId=VSP_004370.
*/

parser grammar FtLineParser;

options { tokenVocab=FtLineLexer;}

ft_ft: ft_line+;

ft_line: FT_HEADER ft_key loc_start loc_end
        (NEW_LINE|ft_value)
         ;
ft_value: SPACE7 ft_text evidence? DOT (CHANGE_OF_LINE ft_id DOT)? NEW_LINE  ;
ft_key: FT_KEY|FT_KEY_VAR_SEQ;
ft_id: FTID ID_WORD ;

ft_text: FT_LINE DOT? (CHANGE_OF_LINE FT_LINE DOT?)*;
loc_start:   FT_LOCATION;
loc_end:    FT_LOCATION;

evidence: LEFT_B  EV_TAG (COMA EV_TAG)* RIGHT_B;


