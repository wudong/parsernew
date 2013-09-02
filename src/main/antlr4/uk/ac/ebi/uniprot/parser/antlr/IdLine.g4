/*
ID   EntryName Status; SequenceLength.
*/
grammar IdLine;

id_id: id_head entry_name SPACE+ review_status SPACE+ length SPACE 'AA' DOT NEWLINE;

review_status: REVIEW_STATUS_REVIEWED | REVIEW_STATUS_UNREVIEWED;
               
id_head: 'ID   ';
length: INT;

entry_name: ENTRY_NAME;

REVIEW_STATUS_REVIEWED : 'Reviewed;';
REVIEW_STATUS_UNREVIEWED : 'Unreviewed;';

SPACE: ' ';
INT:[1-9][0-9]*;
DOT : '.' ;
NEWLINE: '\n';

//the max length of swissprot is 11.
//the max length of trembl if 12.
ENTRY_NAME: LETTERDIG+'_'LETTERDIG+;
fragment LETTERDIG: [A-Z0-9];
