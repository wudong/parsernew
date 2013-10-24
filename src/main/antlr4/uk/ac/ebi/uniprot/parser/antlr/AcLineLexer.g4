lexer grammar AcLineLexer;

AC_HEAD: 'AC   ';
SPACE1: ' ';
NEWLINE: '\n';
SEMICOLON: ';';

ACCESSION: [A-Z0-9]+  {uk.ac.ebi.uniprot.validator.Ac.accession(getText())}?;

           