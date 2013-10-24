package uk.ac.ebi.uniprot.validator;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 23/10/2013
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class Ac {

    /**
     * Check if it is a valid accession.
     *
     * @param acc
     * @return
     */
    public static boolean accession(String acc){
        return acc.length()<=7;
    }
}
