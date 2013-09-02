/*
Node[; Node...].

OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;
OC   Mammalia; Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae;
OC   Homo.

*/

grammar OcLine;

oc_oc: 'OC   ' oc (';' OC_SEPARATOR oc)* '.\n';

oc: OcWord;

OC_SEPARATOR: ' '| '\nOC   ';

OcWord: [A-Z][A-Za-z]+;
