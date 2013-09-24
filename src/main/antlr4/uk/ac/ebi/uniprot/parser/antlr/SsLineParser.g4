parser grammar SsLineParser;

options { tokenVocab=SsLineLexer;}

/*

**
**   #################    INTERNAL SECTION    ##################
**EV EI2; ProtImp; -; -; 11-SEP-2009.
**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.

*/
ss_ss:  STAR_LINE IS_LINE
       ev_lines+;

ev_lines: STAR_EV EV_TAG SEPARATOR ev_db_name SEPARATOR
          value_1 SEPARATOR value_2 SEPARATOR DATE
          DOT_NEWLINE;
ev_db_name: VALUE;
value_1: DASH|VALUE;
value_2:DASH|VALUE;








