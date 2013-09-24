lexer grammar RtLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }


RT_START  : 'RT   "' -> pushMode (FT_CONTENT);

mode FT_CONTENT;
CHANGE_OF_LINE : '\nRT   ' {replaceChangeOfLine();};
RT_LINE: (TITLE_END|LD)*LD;
fragment LD : ~[.?!\r\n\t"];

RT_ENDING : '";\n'  ->popMode;
TITLE_END: [.?!];

