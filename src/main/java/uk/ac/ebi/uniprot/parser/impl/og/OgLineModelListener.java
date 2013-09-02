package uk.ac.ebi.uniprot.parser.impl.og;

import org.antlr.v4.runtime.misc.NotNull;

import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OgLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.OgLineParser;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class OgLineModelListener extends OgLineBaseListener implements ParseTreeObjectExtractor<OgLineObject> {

	private OgLineObject object = new OgLineObject();

	@Override
	public void exitPlasmid_name(@NotNull OgLineParser.Plasmid_nameContext ctx) {
		String text = ctx.PLASMID_VALUE().getText();
		object.plasmidNames.add(text);
	}

	@Override
	public void enterHydrogenosome_line(@NotNull OgLineParser.Hydrogenosome_lineContext ctx) {
		object.hydrogenosome = true;
	}

	@Override
	public void exitNucleomorph_line(@NotNull OgLineParser.Nucleomorph_lineContext ctx) {
		object.nucleomorph = true;
	}

	@Override
	public void exitMitochondrion_line(@NotNull OgLineParser.Mitochondrion_lineContext ctx) {
		object.mitochondrion = true;
	}

	@Override
	public void exitPlastid_line(@NotNull OgLineParser.Plastid_lineContext ctx) {
		OgLineParser.Plastid_nameContext plastid_nameContext = ctx.plastid_name();
		if (plastid_nameContext == null) {
			object.plastid = true;
		} else {
			if (plastid_nameContext.APICOPLAST() != null) {
				object.plastid_Apicoplast = true;
			} else if (plastid_nameContext.CYANELLE() != null) {
				object.plastid_Cyanelle = true;
			} else if (plastid_nameContext.ORGANELLAR_CHROMATOPHORE() != null) {
				object.plastid_Organellar_chromatophore = true;
			} else if (plastid_nameContext.NON_PHOTOSYNTHETIC_PLASTID() != null) {
				object.plastid_Non_photosynthetic = true;
			} else if (plastid_nameContext.CHLOROPLAST() != null) {
				object.plastid_Chloroplast = true;
			}
		}
	}

	public OgLineObject getObject() {
		return object;
	}
}
