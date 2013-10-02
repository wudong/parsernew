parser grammar RlLineParser;

options { tokenVocab=RlLineLexer;}

rl_rl: RL_HEADER rl;

rl: rl_journal| rl_epub |  rl_book | rl_unpublished|rl_thesis   |rl_patent  |rl_submission;

rl_journal: journal_abbr SPACE journal_volume J_YEAR J_END;
journal_abbr: J_WORD ((SPACE|DASH) (J_WORD|DASH))*;
journal_volume: J_WORD COLON journal_first_page DASH journal_last_page;
journal_first_page: J_WORD;
journal_last_page: J_WORD;

rl_epub: EP EP_WORD EP_END;

rl_book: BOOK (book_editors CHANGE_OF_LINE)?
              book_name COMA (SPACE|CHANGE_OF_LINE)
              book_page COMA (SPACE|CHANGE_OF_LINE)
              ((book_publisher COMA (SPACE|CHANGE_OF_LINE))?
              book_city (SPACE|CHANGE_OF_LINE))?
              BOOK_YEAR;
book_editors:  book_editor (COMA (SPACE|CHANGE_OF_LINE) book_editor)* (SPACE|CHANGE_OF_LINE) BOOK_EDS;
book_editor:   BOOK_WORD (SPACE BOOK_WORD)*;

book_name: BOOK_WORD COMA? ((SPACE|CHANGE_OF_LINE) BOOK_WORD COMA?)*  ;
book_page: (BOOK_PP (book_page_volume)? book_page_first DASH book_page_last)|BOOK_WORD;
book_page_volume: BOOK_V_WORD COLON;
book_page_first: BOOK_V_WORD;
book_page_last: BOOK_V_WORD;
book_publisher: BOOK_WORD ((SPACE|CHANGE_OF_LINE) BOOK_WORD)* ;
book_city: BOOK_WORD ((SPACE|CHANGE_OF_LINE) BOOK_WORD)*;

rl_unpublished:  UP UP_YEAR_MONTH UP_END;

rl_thesis: THESIS THESIS_YEAR COMA SPACE thesis_institution COMA SPACE thesis_country THESIS_END;
thesis_institution:  THESIS_WORD (SPACE THESIS_WORD)*;
thesis_country: THESIS_WORD (SPACE THESIS_WORD)*;

rl_patent: PATENT patent_number COMA SPACE PATENT_DATE PATENT_END;
patent_number: PATENT_WORD;

rl_submission:  SUBMISSION  SUBMISSION_YEAR SUBMISSION_TO submission_db SUBMISSION_END;
submission_db: EMBL|UNIPROT|PDB|PIR;


