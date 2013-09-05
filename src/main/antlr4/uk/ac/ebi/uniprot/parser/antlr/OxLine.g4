/*
OX   Taxonomy_database_Qualifier=Taxonomic code;
OX   NCBI_TaxID=9606;
*/

grammar OxLine;

ox_ox: 'OX   ' db '=' tax ';\n';

db : XDB;

XDB: 'NCBI_TaxID';

tax: TAX;

TAX: [1-9][0-9]*;

