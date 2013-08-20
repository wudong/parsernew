/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

grammar IdLine;
import CommonLex;

id_id: id_head entry_name SPACE14 review_status SPACE9 INT SPACE1 'AA' DOT NEWLINE;

review_status: REVIEW_STATUS_REVIEWED | REVIEW_STATUS_UNREVIEWED;
               
id_head: 'ID' SPACE3;

entry_name: ENTRY_NAME;

REVIEW_STATUS_REVIEWED : 'Reviewed;';
REVIEW_STATUS_UNREVIEWED : 'Unreviewed;';                        
