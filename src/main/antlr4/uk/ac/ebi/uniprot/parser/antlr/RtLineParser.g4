parser grammar RtLineParser;

options { tokenVocab=RtLineLexer;}

rt_rt: RT_START rt_line RT_ENDING;

rt_line: RT_LINE (CHANGE_OF_LINE RT_LINE)* TITLE_END ;

