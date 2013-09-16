/*
OH   NCBI_TaxID=TaxID; HostName.

The length of an OH line may exceed 75 characters.
*/

grammar OhLine;

@lexer::members{
private boolean hostname = false;
}

oh_oh: 'OH   ' 'NCBI_TaxID=' tax  SEPARATOR hostname  LINE_END;

tax: TAX;
hostname: HOSTNAME;

TAX: [1-9][0-9]+;

SEPARATOR: '; ' {hostname=true;};
LINE_END: '.\n' {hostname=false;};

HOSTNAME: HL+ {hostname}?;

fragment HL: ~[.];



