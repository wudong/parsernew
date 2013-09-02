package uk.ac.ebi.uniprot.parser.impl.og;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.OgLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.OgLineParser;

import java.util.ArrayList;
import java.util.List;

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

		OgLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			processEvidence(text, terminalNodes);
		}
	}

	@Override
	public void enterHydrogenosome_line(@NotNull OgLineParser.Hydrogenosome_lineContext ctx) {
		object.ogs.add(OgLineObject.OgEnum.HYDROGENOSOME);
		OgLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			processEvidence(OgLineObject.OgEnum.HYDROGENOSOME, terminalNodes);
		}
	}

	@Override
	public void exitNucleomorph_line(@NotNull OgLineParser.Nucleomorph_lineContext ctx) {
		object.ogs.add(OgLineObject.OgEnum.NUCLEOMORPH);
		OgLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			processEvidence(OgLineObject.OgEnum.NUCLEOMORPH, terminalNodes);
		}
	}

	@Override
	public void exitMitochondrion_line(@NotNull OgLineParser.Mitochondrion_lineContext ctx) {
		object.ogs.add(OgLineObject.OgEnum.MITOCHONDRION);
		OgLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			processEvidence(OgLineObject.OgEnum.MITOCHONDRION, terminalNodes);
		}
	}

	@Override
	public void exitPlastid_line(@NotNull OgLineParser.Plastid_lineContext ctx) {
		OgLineParser.Plastid_nameContext plastid_nameContext = ctx.plastid_name();
		OgLineObject.OgEnum og = null;
		if (plastid_nameContext == null) {
			og = OgLineObject.OgEnum.PLASTID;
		} else {
			if (plastid_nameContext.APICOPLAST() != null) {
				og = OgLineObject.OgEnum.PLASTID_APICOPLAST;
			} else if (plastid_nameContext.CYANELLE() != null) {
				og = OgLineObject.OgEnum.PLASTID_CYANELLE;
			} else if (plastid_nameContext.ORGANELLAR_CHROMATOPHORE() != null) {
				og = OgLineObject.OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE;
			} else if (plastid_nameContext.NON_PHOTOSYNTHETIC_PLASTID() != null) {
				og = OgLineObject.OgEnum.PLASTID_NON_PHOTOSYNTHETIC;
			} else if (plastid_nameContext.CHLOROPLAST() != null) {
				og = OgLineObject.OgEnum.PLASTID_CHLOROPLAST;
			}
		}
		object.ogs.add(og);

		OgLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			processEvidence(og, terminalNodes);
		}
	}

	public void processEvidence(Object key, List<TerminalNode> terminalNodes) {
		List<String> strings = new ArrayList<String>();
		for (TerminalNode terminalNode : terminalNodes) {
			strings.add(terminalNode.getText());
		}
		object.getEvidenceInfo().evidences.put(key, strings);
	}

	public OgLineObject getObject() {
		return object;
	}
}
