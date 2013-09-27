lexer grammar RtLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RT_START  : 'RT   "' -> pushMode (RT_CONTENT);

mode RT_CONTENT;
CHANGE_OF_LINE : '\nRT   ' {replaceChangeOfLine();};
RT_LINE: LD ((TITLE_END|LD)* LD)?;
RT_ENDING : '";\n'  ->popMode;
fragment LD : ~[\.?!\r\n\t"];
TITLE_END: [\.?!];

