package uk.ac.ebi.uniprot.parser.impl.de;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.DeLineParser;
import uk.ac.ebi.uniprot.parser.antlr.DeLineParserBaseListener;
import uk.ac.ebi.uniprot.parser.impl.EvidenceInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class DeLineModelListener extends DeLineParserBaseListener implements ParseTreeObjectExtractor<DeLineObject> {

	private DeLineObject object = new DeLineObject();

	//temp object for the Include/Contain blocks.
	private DeLineObject.NameBlock block;

	public DeLineObject getObject() {
		return object;
	}

	@Override
	public void exitRec_name(@NotNull DeLineParser.Rec_nameContext ctx) {
		object.recName = new DeLineObject.Name();
		object.recName.fullName = ctx.full_name().NAME_VALUE().getText();

		DeLineParser.EvidenceContext evidence = ctx.full_name().evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), object.recName.fullName, terminalNodes);
		}

		List<DeLineParser.Short_nameContext> short_nameContexts = ctx.short_name();
		for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
			String name = short_nameContext.NAME_VALUE().getText();
			object.recName.shortNames.add(name);

			DeLineParser.EvidenceContext evidence1 = short_nameContext.evidence();
			if (evidence1 != null) {
				List<TerminalNode> terminalNodes = evidence1.EV_TAG();
				EvidenceInfo.processEvidence(object.getEvidenceInfo(), name, terminalNodes);
			}

		}
		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			String text = ecContext.EC_NAME_VALUE().getText();
			object.recName.ecs.add(text);
			DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
			if (evidence1 != null) {
				List<TerminalNode> terminalNodes = evidence1.EV_TAG();
				EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
			}
		}

	}

	@Override
	public void exitAlt_name(@NotNull DeLineParser.Alt_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();
		DeLineParser.Alt_name_1Context alt_name_1Context = ctx.alt_name_1();
		DeLineParser.Alt_name_2Context alt_name_2Context = ctx.alt_name_2();
		DeLineParser.Alt_name_3Context alt_name_3Context = ctx.alt_name_3();

		if (alt_name_1Context != null) {
			if (alt_name_1Context.full_name() != null) {
				name.fullName = alt_name_1Context.full_name().NAME_VALUE().getText();
				DeLineParser.EvidenceContext evidence = alt_name_1Context.full_name().evidence();
				if (evidence != null) {
					List<TerminalNode> terminalNodes = evidence.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), name.fullName, terminalNodes);
				}
			}

			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_1Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				String text = short_nameContext.NAME_VALUE().getText();
				name.shortNames.add(text);

				DeLineParser.EvidenceContext evidence = short_nameContext.evidence();
				if (evidence != null) {
					List<TerminalNode> terminalNodes = evidence.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}

			List<DeLineParser.EcContext> ec = alt_name_1Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				String text = ecContext.EC_NAME_VALUE().getText();
				name.ecs.add(text);
				DeLineParser.EvidenceContext evidence = ecContext.evidence();
				if (evidence != null) {
					List<TerminalNode> terminalNodes = evidence.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
		} else if (alt_name_2Context != null) {
			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_2Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				String text = short_nameContext.NAME_VALUE().getText();
				name.shortNames.add(text);
				DeLineParser.EvidenceContext evidence = short_nameContext.evidence();
				if (evidence != null) {
					List<TerminalNode> terminalNodes = evidence.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
			List<DeLineParser.EcContext> ec = alt_name_2Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				String text = ecContext.EC_NAME_VALUE().getText();
				name.ecs.add(text);
				DeLineParser.EvidenceContext evidence = ecContext.evidence();
				if (evidence != null) {
					List<TerminalNode> terminalNodes = evidence.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
		} else if (alt_name_3Context != null) {
			List<DeLineParser.EcContext> ec = alt_name_3Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				String text = ecContext.EC_NAME_VALUE().getText();
				name.ecs.add(text);
				DeLineParser.EvidenceContext evidence = ecContext.evidence();
				if (evidence != null) {
					List<TerminalNode> terminalNodes = evidence.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
		}
		object.altName.add(name);
	}

	@Override
	public void exitSub_name(@NotNull DeLineParser.Sub_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();

		name.fullName = ctx.full_name().NAME_VALUE().getText();
		DeLineParser.EvidenceContext evidence = ctx.full_name().evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), name.fullName, terminalNodes);
		}

		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			String text = ecContext.EC_NAME_VALUE().getText();
			name.ecs.add(text);
			DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
			if (evidence1 != null) {
				List<TerminalNode> terminalNodes = evidence1.EV_TAG();
				EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
			}
		}

		object.subName.add(name);
	}


	@Override
	public void exitAlt_biotech(@NotNull DeLineParser.Alt_biotechContext ctx) {
		object.alt_Biotech = ctx.NAME_VALUE().getText();
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), object.alt_Biotech, terminalNodes);
		}
	}

	@Override
	public void exitAlt_inn(@NotNull DeLineParser.Alt_innContext ctx) {
		String text = ctx.NAME_VALUE().getText();
		object.alt_INN.add(text);
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
		}

	}

	@Override
	public void exitAlt_allergen(@NotNull DeLineParser.Alt_allergenContext ctx) {
		object.alt_Allergen = ctx.NAME_VALUE().getText();
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), object.alt_Allergen, terminalNodes);
		}
	}

	@Override
	public void exitAlt_cdantigen(@NotNull DeLineParser.Alt_cdantigenContext ctx) {
		String text = ctx.NAME_VALUE().getText();
		object.alt_CD_antigen.add(text);
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
		}
	}

	@Override
	public void exitSub_alt_biotech(@NotNull DeLineParser.Sub_alt_biotechContext ctx) {
		block.alt_Biotech = ctx.NAME_VALUE().getText();
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), block.alt_Biotech, terminalNodes);
		}
	}

	@Override
	public void exitSub_alt_inn(@NotNull DeLineParser.Sub_alt_innContext ctx) {
		String text = ctx.NAME_VALUE().getText();
		block.alt_INN.add(text);
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
		}
	}

	@Override
	public void exitSub_alt_allergen(@NotNull DeLineParser.Sub_alt_allergenContext ctx) {
		block.alt_Allergen = ctx.NAME_VALUE().getText();
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), block.alt_Allergen, terminalNodes);
		}
	}

	@Override
	public void exitSub_alt_cdantigen(@NotNull DeLineParser.Sub_alt_cdantigenContext ctx) {
		String text = ctx.NAME_VALUE().getText();
		block.alt_CD_antigen.add(text);
		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
		}
	}

	@Override
	public void exitSub_rec_name(@NotNull DeLineParser.Sub_rec_nameContext ctx) {
		block.recName = new DeLineObject.Name();
		block.recName.fullName = ctx.full_name().NAME_VALUE().getText();

		DeLineParser.EvidenceContext evidence = ctx.full_name().evidence();
		if (evidence != null) {
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), block.recName.fullName, terminalNodes);
		}

		List<DeLineParser.Short_nameContext> short_nameContexts = ctx.short_name();
		for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
			String text = short_nameContext.NAME_VALUE().getText();
			block.recName.shortNames.add(text);

			DeLineParser.EvidenceContext evidence1 = short_nameContext.evidence();
			if (evidence1 != null) {
				List<TerminalNode> terminalNodes = evidence1.EV_TAG();
				EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
			}

		}
		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			String text = ecContext.EC_NAME_VALUE().getText();
			block.recName.ecs.add(text);
			DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
			if (evidence1 != null) {
				List<TerminalNode> terminalNodes = evidence1.EV_TAG();
				EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
			}
		}
	}


	@Override
	public void exitSub_alt_name(@NotNull DeLineParser.Sub_alt_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();
		DeLineParser.Sub_alt_name_1Context alt_name_1Context = ctx.sub_alt_name_1();
		DeLineParser.Sub_alt_name_2Context alt_name_2Context = ctx.sub_alt_name_2();
		DeLineParser.Sub_alt_name_3Context alt_name_3Context = ctx.sub_alt_name_3();

		if (alt_name_1Context != null) {
			DeLineParser.Full_nameContext full_nameContext = alt_name_1Context.full_name();
			if (full_nameContext!=null){
				name.fullName = full_nameContext.NAME_VALUE().getText();
				DeLineParser.EvidenceContext evidence1 = full_nameContext.evidence();
				if (evidence1 != null) {
					List<TerminalNode> terminalNodes = evidence1.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), name.fullName, terminalNodes);
				}
			}

			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_1Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				String text = short_nameContext.NAME_VALUE().getText();
				name.shortNames.add(text);
				DeLineParser.EvidenceContext evidence1 = short_nameContext.evidence();
				if (evidence1 != null) {
					List<TerminalNode> terminalNodes = evidence1.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
			List<DeLineParser.EcContext> ec = alt_name_1Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				String text = ecContext.EC_NAME_VALUE().getText();
				name.ecs.add(text);
				DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
				if (evidence1 != null) {
					List<TerminalNode> terminalNodes = evidence1.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
		} else if (alt_name_2Context != null) {
			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_2Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				String text = short_nameContext.NAME_VALUE().getText();
				name.shortNames.add(text);
				DeLineParser.EvidenceContext evidence1 = short_nameContext.evidence();
				if (evidence1 != null) {
					List<TerminalNode> terminalNodes = evidence1.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
			List<DeLineParser.EcContext> ec = alt_name_2Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				String text = ecContext.EC_NAME_VALUE().getText();
				name.ecs.add(text);
				DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
				if (evidence1 != null) {
					List<TerminalNode> terminalNodes = evidence1.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
		} else if (alt_name_3Context != null) {
			List<DeLineParser.EcContext> ec = alt_name_3Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				String text = ecContext.EC_NAME_VALUE().getText();
				name.ecs.add(text);
				DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
				if (evidence1 != null) {
					List<TerminalNode> terminalNodes = evidence1.EV_TAG();
					EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
				}
			}
		}
		block.altName.add(name);
	}

	@Override
	public void exitSub_sub_name(@NotNull DeLineParser.Sub_sub_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();

		name.fullName = ctx.full_name().NAME_VALUE().getText();
		DeLineParser.EvidenceContext evidence = ctx.full_name().evidence();
		if (evidence!=null){
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), name.fullName, terminalNodes);
		}

		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			String text = ecContext.EC_NAME_VALUE().getText();
			name.ecs.add(text);
			DeLineParser.EvidenceContext evidence1 = ecContext.evidence();
			if (evidence1!=null){
				List<TerminalNode> terminalNodes = evidence1.EV_TAG();
				EvidenceInfo.processEvidence(object.getEvidenceInfo(), text, terminalNodes);
			}
		}

		block.subName.add(name);
	}


	@Override
	public void enterIncluded_de(@NotNull DeLineParser.Included_deContext ctx) {
		block = new DeLineObject.NameBlock();
	}

	@Override
	public void exitIncluded_de(@NotNull DeLineParser.Included_deContext ctx) {
		object.includedNames.add(block);
		block = null;
	}

	@Override
	public void enterContained_de(@NotNull DeLineParser.Contained_deContext ctx) {
		block = new DeLineObject.NameBlock();
	}

	@Override
	public void exitContained_de(@NotNull DeLineParser.Contained_deContext ctx) {
		object.containedNames.add(block);
		block = null;
	}

	@Override
	public void exitFlag_value(@NotNull DeLineParser.Flag_valueContext ctx) {

		DeLineObject.FlagType flag = null;

		if (ctx.FRAGMENT() != null) {
			 flag = DeLineObject.FlagType.Fragment;
		} else if (ctx.PRECURSOR() != null) {
			 flag = DeLineObject.FlagType.Precursor;
		} else if (ctx.FRAGMENTS() != null) {
			 flag = DeLineObject.FlagType.Fragments;
		}

		object.flags.add(flag);

		DeLineParser.EvidenceContext evidence = ctx.evidence();
		if (evidence!=null){
			List<TerminalNode> terminalNodes = evidence.EV_TAG();
			EvidenceInfo.processEvidence(object.getEvidenceInfo(), flag, terminalNodes);
		}
	}

}
