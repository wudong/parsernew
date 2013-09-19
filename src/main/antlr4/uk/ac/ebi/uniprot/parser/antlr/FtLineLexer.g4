/*
FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
FT                                isoform 2).
FT                                /FTId=VSP_004370.
*/

lexer grammar FtLineLexer;

tokens{FT_HEADER, NEW_LINE, CHANGE_OF_LINE}

@members {
    //number of location token has been parsed.
    private int loc = 0;
    private boolean ft = false;
}

FT_HEADER: 'FT   '                                 {loc=0;ft=false;};
FT_LOCATION: SPACE+[1-9][0-9]* {loc++;};
fragment SPACE: ' ';
FT_HEADER_2: 'FT                                ';

FT_KEY:
      'INIT_MET'|'SIGNAL'|'PROPEP'|'TRANSIT'|'CHAIN'|'PEPTIDE'|'TOPO_DOM'|'TRANSMEM'|
      'INTRAMEM'|'DOMAIN'|'REPEAT'|'CA_BIND'|'ZN_FING'|'DNA_BIND'|'NP_BIND'|
      'REGION'|'COILED'|'MOTIF'|'COMPBIAS'|'ACT_SITE'|'METAL'|'BINDING'|'SITE'|
      'NON_STD'|'MOD_RES'|'LIPID'|'CARBOHYD'|'DISULFID'|'CROSSLNK'|
      'VAR_SEQ'|'VARIANT'|'MUTAGEN'|'UNSURE'|'CONFLICT'|'NON_CONS'|
      'NON_TER'|'HELIX'|'STRAND'|'TURN'
      ;

SPACE7: '       ' {loc==2}?     {ft=true;};

COMA: ',';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{'  {ft=false;};
RIGHT_B : '}';

DOT: '.\n'           {ft=false;};
ID_WORD: ('VSP_'| 'CAR_'|'PRO_'|'VSP_') [0-9]+;
FTID: '/FTId=';
ALL_LETTER :  LT+ {ft}?;
CHANGE_OF_LINE: '\nFT                                ' {ft}?;
NEW_LINE:'\n';
fragment LT:~[.\n\r{}];
