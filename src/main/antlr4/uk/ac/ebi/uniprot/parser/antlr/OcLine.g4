/*
Node[; Node...].

OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;
OC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;
OC   Homo.

*/

grammar OcLine;

@lexer::members{
    private boolean oc=false;
}

oc_oc: OC_HEADER oc (';' OC_SEPARATOR oc)* '.\n';

oc: OcWord;

OC_HEADER : 'OC   ' {oc=true;};
OC_SEPARATOR: (' '| '\nOC   ') {oc=true;};
SEMI_COLON: ';' {oc=false;};
DOT: '.' {oc=false;};

OcWord: OcWordLetter+ {oc}?;
fragment OcWordLetter : ~[;.\r\n\t];
