/*
FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
FT                                -> SECLTYGKQPLTSFHPFTSQMPP (in
FT                                isoform 2).
FT                                /FTId=VSP_004370.
*/

lexer grammar FtLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

tokens{FT_HEADER, NEW_LINE, CHANGE_OF_LINE}

@members {
    //number of location token has been parsed.
    private int loc = 0;
    private boolean ft = false;
    private boolean inVarSeq=false;
}

FT_HEADER: 'FT   '                     {loc=0;ft=false;inVarSeq=false;};
FT_LOCATION: SPACE+ ('<'|'>')? [1-9][0-9]*               {loc<2}? {loc++;};
fragment SPACE: ' ';
FT_HEADER_2: 'FT                                ';



FT_KEY:
      'INIT_MET'|'SIGNAL'|'PROPEP'|'TRANSIT'|'CHAIN'|'PEPTIDE'|'TOPO_DOM'|'TRANSMEM'|
      'INTRAMEM'|'DOMAIN'|'REPEAT'|'CA_BIND'|'ZN_FING'|'DNA_BIND'|'NP_BIND'|
      'REGION'|'COILED'|'MOTIF'|'COMPBIAS'|'ACT_SITE'|'METAL'|'BINDING'|'SITE'|
      'NON_STD'|'MOD_RES'|'LIPID'|'CARBOHYD'|'DISULFID'|'CROSSLNK'|
      'VARIANT'|'MUTAGEN'|'UNSURE'|'CONFLICT'|'NON_CONS'|
      'NON_TER'|'HELIX'|'STRAND'|'TURN';
FT_KEY_VAR_SEQ:  'VAR_SEQ' {inVarSeq=true;};

SPACE7: '       ' {loc==2}?     {ft=true;};

COMA: ',';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
LEFT_B : '{'  {ft=false;};
RIGHT_B : '}';

DOT: '.\n'           {ft=false;};
ID_WORD: ('VSP_'| 'CAR_'|'PRO_'|'VSP_') [0-9]+;
FTID: '/FTId=';
ALL_LETTER :  LT+ {ft&&!getText().endsWith(".")}?;
CHANGE_OF_LINE: '\nFT                                ' {ft}?   {replaceChangeOfLine(inVarSeq);};
NEW_LINE:'\n';
fragment LT:~[\n\r{}];
