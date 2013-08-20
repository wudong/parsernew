grammar FtLine;

ft_ft: ft_line+;

ft_line: FT_HEADER ft_name space ft_location space ft_location ((SPACE7 ft_content)|NEWLINE) ft_id? ;

ft_location : (LT?'1') | (INT GT?);

ft_name: FT_NAME ;

ft_content: .+? DOT_NEWLINE;

ft_id: FT_NEW_LINE_HEADER '/FTId=' ft_id_content DOT_NEWLINE;

space : SPACE1 | SPACE7 | SPACE;

ft_id_content : FT_ID_CONTENT ;

FT_NEW_LINE_HEADER:'FT                                ';
FT_HEADER : 'FT   ';
CHANGE_LINE: NEWLINE FT_NEW_LINE_HEADER;

CAR : 'CAR_';
PRO: 'PRO_';
VAR: 'VAR_';
VSP: 'VSP_';

SPACE1 : ' ';
SPACE7: '       ';
SPACE: SPACE1+;
DOT_NEWLINE: DOT NEWLINE;
NEWLINE : '\n';
DOT : '.';

INT : DIGIT+;
FT_NAME : UP_LETTER+ '_'? UP_LETTER+;
FT_ID_CONTENT: (CAR|PRO|VAR|VSP) INT;
Word_With_Digit_Score: (UP_LETTER|LOW_LETTER|DIGIT|'_'|'-')+;
GT : '>'     ;
LT : '<'      ;
DIGIT : [0-9]  ;
UP_LETTER: [A-Z];
LOW_LETTER: [a-z];
