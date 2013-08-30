/*
FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
FT                                isoform 2).
FT                                /FTId=VSP_004370.
*/

grammar FtLine;

@members {
  private int loc = 0;
  private boolean word=false;
}

ft_ft: ft_line+;

ft_line: FT_HEADER ft_key (SPACE+) loc_start (SPACE+) loc_end (SPACE7 ft_text DOT)? ft_id? NEW_LINE;

ft_id: CHANGE_OF_LINE '/FTId=' ID_WORD DOT ;

ft_text: WORD (separator WORD)*;
loc_start: FT_LOCATION;
loc_end: FT_LOCATION;

ft_key: FT_KEY;
FT_HEADER: 'FT   ' {loc=0;};

separator: (SPACE|CHANGE_OF_LINE);

FT_LOCATION: DIGIT+ {loc++;};

CHANGE_OF_LINE: '\nFT                                ';

FT_KEY:
      'INIT_MET'|'SIGNAL'|'PROPEP'|'TRANSIT'|'CHAIN'|'PEPTIDE'|'TOPO_DOM'|'TRANSMEM'|
      'INTRAMEM'|'DOMAIN'|'REPEAT'|'CA_BIND'|'ZN_FING'|'DNA_BIND'|'NP_BIND'|
      'REGION'|'COILED'|'MOTIF'|'COMPBIAS'|'ACT_SITE'|'METAL'|'BINDING'|'SITE'|
      'NON_STD'|'MOD_RES'|'LIPID'|'CARBOHYD'|'DISULFID'|'CROSSLNK'|
      'VAR_SEQ'|'VARIANT'|'MUTAGEN'|'UNSURE'|'CONFLICT'|'NON_CONS'|
      'NON_TER'|'HELIX'|'STRAND'|'TURN';

ID_WORD: ('VSP_'| 'CAR_'|'PRO_'|'VSP_') DIGIT+;

DOT: '.' {word=false;};
SPACE: ' ';
SPACE7: '       ' {loc==2}? {word=true;};
WORD: LETTER+ {word}?;
NEW_LINE : '\n';

fragment DIGIT: [0-9];
fragment LETTER: ~[ \n\r\t.];


