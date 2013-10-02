lexer grammar RlLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }
tokens {CHANGE_OF_LINE, SPACE, COMA, DASH, SEMICOLON, COLON}

RL_HEADER: 'RL   ';
EP: '(er) '   ->  pushMode (MODE_EP);
BOOK: '(In) ' -> pushMode (MODE_BOOK);
UP : 'Unpublished observations ' -> pushMode (MODE_UP);
THESIS : 'Thesis '  ->pushMode (MODE_THESIS);
PATENT : 'Patent number '  ->pushMode (MODE_PATENT);
SUBMISSION : 'Submitted '  ->pushMode (MODE_SUBMISSION);

//the default mode contains lexer for the Journal.
/*
RL   Journal_abbrev Volume:First_page-Last_page(YYYY).
*/
J_END: '.\n';
J_YEAR: '('[0-9]*')';
J_WORD:  J_WORD_L+;
J_DASH: '-'  ->type(DASH);
J_COLON: ':'  ->type(COLON);
J_SPACE: ' ' ->type(SPACE);
fragment J_WORD_L: ~[ :\n\r\t()\-];

//RL   (er) Free text.
mode MODE_EP;
EP_END: '.\n' -> popMode ;
EP_WORD: EP_L+;
fragment EP_L: ~[.\n\r\t];

/*
RL   (In) Editor_1 I.[, Editor_2 I., Editor_X I.] (eds.);
RL   Book_name, pp.[Volume:]First_page-Last_page, Publisher, City (YYYY).
*/
mode MODE_BOOK;
BOOK_CHANGE_OF_LINE:  '\nRL   ' {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
BOOK_EDS: '(eds.);';
BOOK_B_COMA :','       -> type (COMA);
BOOK_SPACE: ' '    ->type (SPACE);
BOOK_YEAR:  '('[0-9]+').\n';
BOOK_PP: 'pp.'    -> pushMode(MODE_BOOK_VOLUME);
BOOK_WORD: BOOK_L+   {!getText().startsWith("pp.")}?;
fragment BOOK_L: ~[ ,;\n\r\t];

mode MODE_BOOK_VOLUME;
BOOK_COMA: ','     ->type (COMA), popMode;
BOOK_V_CHANGE_OF_LINE:  '\nRL   ' {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
BOOK_DASH: '-'     ->type (DASH);
BOOK_COLON: ':'     ->type (COLON);
BOOK_V_SPACE: ' '     ->type (SPACE);
BOOK_V_WORD: BOOK_V_L+;
fragment BOOK_V_L: ~[ :,\-\n\r\t.];

/*
RL   Unpublished observations (MMM-YYYY).
*/
mode MODE_UP;
UP_END: '.\n'     ->popMode ;
UP_YEAR_MONTH:    '('.*?')';

/*
RL   Thesis (Year), Institution_name, Country.
*/
mode MODE_THESIS;
THESIS_END : '.\n'  -> popMode;
THESIS_YEAR: '(' [0-9]+ ')';
THESIS_COMA: ','     ->type (COMA);
THESIS_SPACE: ' '    ->type (SPACE);
THESIS_WORD: THESIS_L+;
fragment THESIS_L: ~[ ,\n\r\t.];

/*
RL   Patent number Pat_num, DD-MMM-YYYY.
*/
mode MODE_PATENT;
PATENT_END : '.\n'  -> popMode;
PATENT_DATE: [0-9][0-9]'-'[A-Z][A-Z][A-Z]'-'[1-9][0-9][0-9][0-9];
PATENT_WORD: (PATENT_L_NO_DOT|'.')+ PATENT_L_NO_DOT ;
PATENT_COMA: ','     ->type (COMA);
PATENT_SPACE: ' '    ->type (SPACE);
fragment PATENT_L_NO_DOT: ~[ ,\n\r\t.];

/*
RL   Submitted (MMM-YYYY) to Database_name.
*/
mode MODE_SUBMISSION;
SUBMISSION_END : '.\n'  -> popMode;
SUBMISSION_YEAR: '(' .*? ')';
SUBMISSION_SPACE: ' '    ->type (SPACE);
SUBMISSION_TO: ' to ';
EMBL: 'the EMBL/GenBank/DDBJ databases';
UNIPROT: 'UniProtKB';
PDB: 'the PDB data bank';
PIR: 'the PIR data bank';