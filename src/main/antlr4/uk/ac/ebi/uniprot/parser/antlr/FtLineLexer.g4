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
    private boolean inVarSeq=false;
}

FT_HEADER: 'FT   '                     {loc=0;inVarSeq=false;};
FT_LOCATION: SPACE+     ((('<' | '>' | '?') ?  ([1-9][0-9]*)) | '?')      {loc<2}? {loc++;};
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

NEW_LINE: '\n';
SPACE7: '       ' {loc==2}?                         -> pushMode(FT_CONTENT);
FTID: '\nFT                                /FTId='  -> pushMode(FTID_MODE);

mode FT_CONTENT;
ID_WORD: ('VSP_'| 'VAR_'|'PRO_'|'VSP_') [0-9]+;
FTID_2: '\nFT                                /FTId='      ->type(FTID), pushMode(FTID_MODE);

DOT : '.';
NEW_LINE_: '\n'                                      -> type (NEW_LINE), popMode;
CHANGE_OF_LINE : '\nFT                                ' {replaceChangeOfLine(inVarSeq);};
FT_LINE: LD ((DOT|LD|'/')* LD)?                       {!getText().startsWith("/FTId=")}?;
fragment LD : ~[\.\r\n\t{}];

LEFT_B : '{'                    -> pushMode(FT_EVIDENCE);

mode FTID_MODE;
fragment INTEGER: [0-9]+;
fragment ID_STARTER:  'VSP_'|'PRO_'|'VAR_'|'VSP_'|'CAR_';
FTID_VALUE: ID_STARTER  INTEGER;
FTID_DOT: '.'                   ->type(DOT), popMode;

mode FT_EVIDENCE;
COMA: ',';
EV_TAG : ('EI'|'EA') [1-9][0-9]*;
RIGHT_B : '}'                 -> popMode;
