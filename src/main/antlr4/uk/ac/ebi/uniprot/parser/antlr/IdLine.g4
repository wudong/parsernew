/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

grammar IdLine;
import CommonLex;

id_line: id_head entry_name SPACE14 review_status SPACE9 DIGITS SPACE1 'AA' id_dot_new_line;

review_status: REVIEW_STATUS_REVIEWED | REVIEW_STATUS_UNREVIEWED;
               
id_head: 'ID' SPACE3;

entry_name: ENTRY_NAME;

id_dot_new_line  :  DOT NEWLINE;

REVIEW_STATUS_REVIEWED : 'Reviewed;';
REVIEW_STATUS_UNREVIEWED : 'Unreviewed;';                        
