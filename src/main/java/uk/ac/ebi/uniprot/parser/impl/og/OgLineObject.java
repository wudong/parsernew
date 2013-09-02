package uk.ac.ebi.uniprot.parser.impl.og;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class OgLineObject {

	public boolean hydrogenosome;
	public boolean mitochondrion;
	public boolean nucleomorph;


	public boolean plastid;
	public boolean plastid_Apicoplast;
	public boolean plastid_Chloroplast;
	public boolean plastid_Organellar_chromatophore;
	public boolean plastid_Cyanelle;
	public boolean plastid_Non_photosynthetic;

    public List<String> plasmidNames = new ArrayList<String>();

}
