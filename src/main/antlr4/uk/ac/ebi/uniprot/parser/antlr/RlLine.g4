grammar RlLine;

rl_rl: ;

rl: rl_journal| rl_epub |  rl_book | rl_unpublished|rl_thesis   |rl_patent  |rl_submission;

rl_journal: journal_abbrev volumn':'first_page'-'last_page LEFT_BRACKET date_year RIGHT_BRACKET '.';
journal_abbrev: ;
volumn: INTEGER;
first_page: INTEGER;
last_page: INTEGER;

rl_epub: '(er)' epub_text '.';
epub_text: ;

rl_book:;

rl_unpublished:;  'Unpublished observations' LEFT_BRACKET date_month_year RIGHT_BRACKET '.';

rl_thesis: 'Thesis ' LEFT_BRACKET date_year RIGHT_BRACKET', 'institute_name', 'country'.';
country: WORDS;
institute_name: WORDS;

rl_patent:; 'Patent number ' patent_number ', ' date_day_month_year '.';
patent_number: WORD_INT;

rl_submission: 'Submitted ' LEFT_BRACKET date_month_year RIGHT_BRACKET 'to ' submission_db '.';

date_month_year: ;
date_day_month_year  :;
date_year: INTEGER+;

submission_db: EMBL|UNIPROTKB|PDB|PIR;

EMBL: 'the EMBL/GenBank/DDBJ databases';
UNIPROTKB: 'UniProtKB';
PDB: 'the PDB data bank';
PIR: 'the PIR data bank';

WORDS: WORD (SPACE WORD)*;

WORD: LT+;
INTEGER: INT+;
WORD_INT: (LT|INT)+;

RL_HEADER: 'RL   ';
LEFT_BRACKET: '(';
RIGHT_BRACKET: ')';
fragment INT : [0-9];
fragment LT: [A-Za-z];
