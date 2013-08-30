package uk.ac.ebi.uniprot.parser.impl.de;

import org.antlr.v4.runtime.misc.NotNull;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.DeLineBaseListener;
import uk.ac.ebi.uniprot.parser.antlr.DeLineParser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class DeLineModelListener extends DeLineBaseListener implements ParseTreeObjectExtractor<DeLineObject> {

	private DeLineObject object = new DeLineObject();

	//temp object for the Include/Contain blocks.
	private DeLineObject.NameBlock block;

	public DeLineObject getObject() {
		return object;
	}

	@Override
	public void exitRec_name(@NotNull DeLineParser.Rec_nameContext ctx) {
		object.recName = new DeLineObject.Name();
		object.recName.fullName = treateNameValue(ctx.full_name().NAME_VALUE().getText());
		List<DeLineParser.Short_nameContext> short_nameContexts = ctx.short_name();
		for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
			object.recName.shortNames.add(treateNameValue(short_nameContext.NAME_VALUE().getText()));
		}
		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			object.recName.ecs.add(ecContext.EC_NAME_VALUE().getText());
		}
	}

	@Override
	public void exitAlt_name(@NotNull DeLineParser.Alt_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();
		DeLineParser.Alt_name_1Context alt_name_1Context = ctx.alt_name_1();
		DeLineParser.Alt_name_2Context alt_name_2Context = ctx.alt_name_2();
		DeLineParser.Alt_name_3Context alt_name_3Context = ctx.alt_name_3();

		if (alt_name_1Context != null) {
			name.fullName = treateNameValue(alt_name_1Context.full_name().NAME_VALUE().getText());
			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_1Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				name.shortNames.add(treateNameValue(short_nameContext.NAME_VALUE().getText()));
			}
			List<DeLineParser.EcContext> ec = alt_name_1Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				name.ecs.add(ecContext.EC_NAME_VALUE().getText());
			}
		} else if (alt_name_2Context != null) {
			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_2Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				name.shortNames.add(treateNameValue(short_nameContext.NAME_VALUE().getText()));
			}
			List<DeLineParser.EcContext> ec = alt_name_2Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				name.ecs.add(ecContext.EC_NAME_VALUE().getText());
			}
		} else if (alt_name_3Context != null) {
			List<DeLineParser.EcContext> ec = alt_name_3Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				name.ecs.add(ecContext.EC_NAME_VALUE().getText());
			}
		}
		object.altName.add(name);
	}

	@Override
	public void exitSub_name(@NotNull DeLineParser.Sub_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();

		name.fullName = treateNameValue(ctx.full_name().NAME_VALUE().getText());

		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			name.ecs.add(ecContext.EC_NAME_VALUE().getText());
		}

		object.subName.add(name);
	}


	@Override
	public void exitAlt_biotech(@NotNull DeLineParser.Alt_biotechContext ctx) {
		object.alt_Biotech = treateNameValue(ctx.NAME_VALUE().getText());
	}

	@Override
	public void exitAlt_inn(@NotNull DeLineParser.Alt_innContext ctx) {
		object.alt_INN.add(treateNameValue(ctx.NAME_VALUE().getText()));
	}

	@Override
	public void exitAlt_allergen(@NotNull DeLineParser.Alt_allergenContext ctx) {
		object.alt_Allergen = treateNameValue(ctx.NAME_VALUE().getText());
	}

	@Override
	public void exitAlt_cdantigen(@NotNull DeLineParser.Alt_cdantigenContext ctx) {
		object.alt_CD_antigen.add(treateNameValue(ctx.NAME_VALUE().getText()));
	}

	@Override
	public void exitSub_alt_biotech(@NotNull DeLineParser.Sub_alt_biotechContext ctx) {
		block.alt_Biotech = treateNameValue(ctx.NAME_VALUE().getText());
	}

	@Override
	public void exitSub_alt_inn(@NotNull DeLineParser.Sub_alt_innContext ctx) {
		block.alt_INN.add(treateNameValue(ctx.NAME_VALUE().getText()));
	}

	@Override
	public void exitSub_alt_allergen(@NotNull DeLineParser.Sub_alt_allergenContext ctx) {
		block.alt_Allergen = treateNameValue(ctx.NAME_VALUE().getText());
	}

	@Override
	public void exitSub_alt_cdantigen(@NotNull DeLineParser.Sub_alt_cdantigenContext ctx) {
		block.alt_CD_antigen.add(treateNameValue(ctx.NAME_VALUE().getText()));
	}

	@Override
	public void exitSub_rec_name(@NotNull DeLineParser.Sub_rec_nameContext ctx) {
		block.recName = new DeLineObject.Name();
		block.recName.fullName = treateNameValue(ctx.full_name().NAME_VALUE().getText());
		List<DeLineParser.Short_nameContext> short_nameContexts = ctx.short_name();
		for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
			block.recName.shortNames.add(treateNameValue(short_nameContext.NAME_VALUE().getText()));
		}
		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			block.recName.ecs.add(ecContext.EC_NAME_VALUE().getText());
		}
	}


	@Override
	public void exitSub_alt_name(@NotNull DeLineParser.Sub_alt_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();
		DeLineParser.Sub_alt_name_1Context alt_name_1Context = ctx.sub_alt_name_1();
		DeLineParser.Sub_alt_name_2Context alt_name_2Context = ctx.sub_alt_name_2();
		DeLineParser.Sub_alt_name_3Context alt_name_3Context = ctx.sub_alt_name_3();

		if (alt_name_1Context != null) {
			name.fullName = treateNameValue(alt_name_1Context.full_name().NAME_VALUE().getText());
			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_1Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				name.shortNames.add(treateNameValue(short_nameContext.NAME_VALUE().getText()));
			}
			List<DeLineParser.EcContext> ec = alt_name_1Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				name.ecs.add(ecContext.EC_NAME_VALUE().getText());
			}
		} else if (alt_name_2Context != null) {
			List<DeLineParser.Short_nameContext> short_nameContexts = alt_name_2Context.short_name();
			for (DeLineParser.Short_nameContext short_nameContext : short_nameContexts) {
				name.shortNames.add(treateNameValue(short_nameContext.NAME_VALUE().getText()));
			}
			List<DeLineParser.EcContext> ec = alt_name_2Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				name.ecs.add(ecContext.EC_NAME_VALUE().getText());
			}
		} else if (alt_name_3Context != null) {
			List<DeLineParser.EcContext> ec = alt_name_3Context.ec();
			for (DeLineParser.EcContext ecContext : ec) {
				name.ecs.add(ecContext.EC_NAME_VALUE().getText());
			}
		}
		block.altName.add(name);
	}

	@Override
	public void exitSub_sub_name(@NotNull DeLineParser.Sub_sub_nameContext ctx) {
		DeLineObject.Name name = new DeLineObject.Name();

		name.fullName = treateNameValue(ctx.full_name().NAME_VALUE().getText());

		List<DeLineParser.EcContext> ec = ctx.ec();
		for (DeLineParser.EcContext ecContext : ec) {
			name.ecs.add(ecContext.EC_NAME_VALUE().getText());
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
	public void exitFlags(@NotNull DeLineParser.FlagsContext ctx) {
		if (ctx.flag_value().FRAGMENT() != null) {
			object.flag = DeLineObject.FlagType.Fragment;
		} else if (ctx.flag_value().PRECURSOR() != null) {
			object.flag = DeLineObject.FlagType.Precursor;
		} else if (ctx.flag_value().PRECURSOR_FRAGMENT() != null) {
			object.flag = DeLineObject.FlagType.Precursor_Fragment;
		} else if (ctx.flag_value().FRAGMENTS() != null) {
			object.flag = DeLineObject.FlagType.Fragments;
		}
	}

	public String treateNameValue(String name) {
		return name.substring(1, name.length() - 2);
	}
}
