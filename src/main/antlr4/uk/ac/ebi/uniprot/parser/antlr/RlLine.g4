grammar RlLine;

//This number in the Lexer to determine if the Lexer running on ePUB,
//for the purpose to handle free text in epub.
@members {
  private boolean inEpub=false;
  private boolean inBook=false;
}

rl_rl: RL_HEADER rl '\n';

rl: rl_journal| rl_epub |  rl_book | rl_unpublished|rl_thesis   |rl_patent  |rl_submission;

rl_journal: journal_abbrev SPACE volume':'first_page'-'last_page LEFT_BRACKET date_year RIGHT_BRACKET '.';
journal_abbrev: (WORD DOT?) (SPACE WORD DOT?)*;
volume: INTEGER;
first_page: INTEGER;
last_page: INTEGER;

rl_epub: EPUB_LEAD epub_text;
epub_text: EPUB_TEXT;
EPUB_LEAD: '(er) ' {inEpub=true;};
//This rule will only be activated for EPUB.
EPUB_TEXT:
    .+? '.'
    {inEpub}? {inEpub=false;};
//and it is disabled after EPUB is parsed.

rl_book: '(In) ' editor_names rl_book_separator EDS
        rl_book_title rl_book_separator rl_book_page COMMA rl_book_separator
        rl_book_press COMMA rl_book_separator rl_book_place SPACE LEFT_BRACKET date_year RIGHT_BRACKET '.';

rl_book_page: 'pp.'(INTEGER':')?INTEGER'-'INTEGER;
rl_book_title: BOOK_TITLE;
rl_book_press: WORD (SPACE WORD)*;
rl_book_place: WORD (SPACE WORD)*;

editor_names: NAME (COMMA rl_book_separator NAME)* ;
NAME: UP_CASE LOW_CASE+ SPACE UP_CASE DOT ('-'? UP_CASE DOT)* (SPACE ABBR)?;
EDS: '(eds.);' CHANGE_OF_LINE
     {inBook=true;};
BOOK_TITLE:
    .+? COMMA
    {inBook}? {inBook=false;};

rl_book_separator: SPACE|CHANGE_OF_LINE;

rl_unpublished: 'Unpublished observations' SPACE LEFT_BRACKET date_month_year RIGHT_BRACKET '.';

rl_thesis: 'Thesis ' LEFT_BRACKET date_year RIGHT_BRACKET COMMA SPACE institute_name COMMA SPACE country'.';
country: WORD (SPACE WORD)*;
institute_name: WORD (SPACE WORD)*;

rl_patent: 'Patent number ' patent_number COMMA SPACE date_day_month_year '.';
patent_number: (WORD | INTEGER)+;

rl_submission: 'Submitted ' LEFT_BRACKET date_month_year RIGHT_BRACKET ' to ' submission_db '.';

date_month_year: WORD'-'INTEGER;
date_day_month_year  : INTEGER'-'WORD'-'INTEGER;
date_year: INTEGER;

submission_db: EMBL|UNIPROTKB|PDB|PIR;

EMBL: 'the EMBL/GenBank/DDBJ databases';
UNIPROTKB: 'UniProtKB';
PDB: 'the PDB data bank';
PIR: 'the PIR data bank';

ABBR: 'Jr.'|'Sr.'|'II'|'III'|'IV'|'V'|'VI'|'VII'|'VIII';

SPACE: ' ';
DOT: '.';
COMMA: ',';
CHANGE_OF_LINE: '\nRL   ';

RL_HEADER: 'RL   ';
LEFT_BRACKET: '(';
RIGHT_BRACKET: ')';

INTEGER: INT+;
WORD: (UP_CASE|LOW_CASE|INT)+;

fragment INT : [0-9];
fragment UP_CASE: [A-Z];
fragment LOW_CASE: [a-z];
fragment NON_SPACE : ~[ \r\n\t];


