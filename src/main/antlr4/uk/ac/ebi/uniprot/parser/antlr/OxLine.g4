/*
OX   Taxonomy_database_Qualifier=Taxonomic code;
OX   NCBI_TaxID=9606;
*/

grammar OxLine;

ox_ox: 'OX   ' db '=' tax evidence? ';\n';

db : XDB;

XDB: 'NCBI_TaxID';

tax: TAX;

TAX: [1-9][0-9]*;

evidence: LEFT_B  EV_TAG (SEPARATOR EV_TAG)* RIGHT_B;
SEPARATOR: ', ';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{';
RIGHT_B : '}';