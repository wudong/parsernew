parser grammar SsLineParser;

options { tokenVocab=SsLineLexer;}

/*

**
**   #################    INTERNAL SECTION    ##################
**EV EI2; ProtImp; -; -; 11-SEP-2009.
**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.
**   LOCUS       547119         38 aa
**               22-SEP-1994
**   DEFINITION  indolepyruvate ferredoxin oxidoreductase beta subunit, IOR
**               beta
**               {N-terminal} [Pyrococcus furiosus, DSM 3638, Peptide
**               Partial, 38
**               aa].
**   ACCESSION   547119
**   PID         g547119
**   DBSOURCE    GenBank journal-scan: sequence-id  149185
**   KEYWORDS    .
**   SOURCE      Pyrococcus furiosus DSM 3638.
**     ORGANISM  Pyrococcus furiosus
**               Archaea; Euryarchaeota; Thermococcales; Thermococcaceae;
**               Pyrococcus.
**   REFERENCE   1  (residues 1 to 38)
**     AUTHORS   Mai,X. and Adams,M.W.
**     TITLE     Indolepyruvate ferredoxin oxidoreductase from the
**               hyperthermophilic
**               archaeon Pyrococcus furiosus. A new enzyme involved in
**               peptide
**               fermentation
**     JOURNAL   J. Biol. Chem. 269 (24), 16726-16732 (1994)
**     MEDLINE   94266888
**     REMARK    GenBank staff at the National Library of Medicine created
**               this
**               entry [NCBI gibbsq 149185] from the original journal
**               article.
**               This sequence comes from Fig.2.
**   COMMENT     Method: direct peptide sequencing.
**   FEATURES             Location/Qualifiers
**        source          1..38
**                        /organism="Pyrococcus furiosus"
**        Protein         1..38
**                        /partial
**                        /note="IOR beta"
**                        /product="indolepyruvate ferredoxin
**               oxidoreductase beta
**                        subunit"
*/
ss_ss:  STAR_LINE IS_LINE
       ss_line_ia* ss_line_source*;

ss_line_ia: STAR_STAR TOPIC SPACE
       IA_TEXT LINE_END;

ss_line_source: STAR_STAR_SOURCE  SOURCE_TEXT LINE_END;














