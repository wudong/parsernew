package uk.ac.ebi.uniprot.parser.impl.cc;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.parser.ParseTreeObjectExtractor;
import uk.ac.ebi.uniprot.parser.antlr.CcLineParser;
import uk.ac.ebi.uniprot.parser.antlr.CcLineParserBaseListener;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wudong
 * Date: 08/08/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class CcLineModelListener extends CcLineParserBaseListener implements ParseTreeObjectExtractor<CcLineObject> {

	private CcLineObject object = new CcLineObject();

	@Override
	public void exitCc_common(@NotNull CcLineParser.Cc_commonContext ctx) {
		CcLineObject.CC ccCommon = new CcLineObject.CC();
		TerminalNode terminalNode = ctx.CC_TOPIC_COMMON();
		ccCommon.topic = CcLineObject.CCTopicEnum.fromSting(terminalNode.getText());
		ccCommon.object = ctx.cc_common_text().getText();
		object.ccs.add(ccCommon);
	}

	@Override
	public void exitCc_web_resource(@NotNull CcLineParser.Cc_web_resourceContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.WEB_RESOURCE;

		CcLineObject.WebResource wr = new CcLineObject.WebResource();
		cc.object = wr;
		object.ccs.add(cc);

		CcLineParser.Cc_web_resource_nameContext cc_web_resource_nameContext = ctx.cc_web_resource_name();
		wr.name = cc_web_resource_nameContext.CC_WR_TEXT().getText();

		CcLineParser.Cc_web_resource_urlContext cc_web_resource_urlContext = ctx.cc_web_resource_url();
		if (cc_web_resource_urlContext!=null){
			String text = cc_web_resource_urlContext.CC_WR_URL().getText();
			wr.url = text.substring(1, text.length()-1);
		}

		CcLineParser.Cc_web_resource_noteContext cc_web_resource_noteContext = ctx.cc_web_resource_note();
		if (cc_web_resource_noteContext != null) {
			wr.note = cc_web_resource_noteContext.CC_WR_TEXT().getText();
		}
	}

	@Override
	public void exitCc_interaction(@NotNull CcLineParser.Cc_interactionContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.INTERACTION;

		CcLineObject.Interaction ir = new CcLineObject.Interaction();
		cc.object = ir;
		object.ccs.add(cc);

		//CC       {{SP_Ac:identifier[ (xeno)]}|Self}; NbExp=n; IntAct=IntAct_Protein_Ac, IntAct_Protein_Ac;
		List<CcLineParser.Cc_interaction_lineContext> cc_interaction_lineContexts = ctx.cc_interaction_line();
		for (CcLineParser.Cc_interaction_lineContext cc_interaction_lineContext : cc_interaction_lineContexts) {

		}
	}

	@Override
	public void exitCc_biophyiochemical(@NotNull CcLineParser.Cc_biophyiochemicalContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.BIOPHYSICOCHEMICAL_PROPERTIES;
		CcLineObject.BiophysicochemicalProperties bp = new CcLineObject.BiophysicochemicalProperties();
		cc.object = bp;
		object.ccs.add(cc);

		List<CcLineParser.Cc_biophyiochemical_propertiesContext> ctx2 = ctx.cc_biophyiochemical_properties();
		for (CcLineParser.Cc_biophyiochemical_propertiesContext cc_biophyiochemical_propertiesContext : ctx2) {
			processBiophysicalProperties(cc_biophyiochemical_propertiesContext, bp);
		}
	}

	private void processBiophysicalProperties(@NotNull CcLineParser.Cc_biophyiochemical_propertiesContext ctx, CcLineObject.BiophysicochemicalProperties bp) {
		if (ctx.cc_biophyiochemical_absorption() != null) {
			TerminalNode terminalNode = ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_bas().CC_BP_DIGIT();
			bp.bsorption_abs = Integer.parseInt(terminalNode.getText());
			bp.bsorption_note = ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_note().cc_properties_text().getText();
		}

		CcLineParser.Cc_biophyiochemical_kineticContext kineticContext = ctx.cc_biophyiochemical_kinetic();
		if (kineticContext != null) {
			List<CcLineParser.Cc_biophyiochemical_kinetic_kmContext> cc_biophyiochemical_kinetic_kmContexts = kineticContext.cc_biophyiochemical_kinetic_km();
			for (CcLineParser.Cc_biophyiochemical_kinetic_kmContext km : cc_biophyiochemical_kinetic_kmContexts) {
				bp.kms.add(km.cc_properties_text().getText());
			}

			List<CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext> maxcontext = kineticContext.cc_biophyiochemical_kinetic_bpmax();
			for (CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext c : maxcontext) {
				bp.vmaxs.add(c.cc_properties_text().getText());
			}

			CcLineParser.Cc_biophyiochemical_kinetic_noteContext noteContext = kineticContext.cc_biophyiochemical_kinetic_note();
			if (noteContext != null) {
				bp.kp_note = noteContext.cc_properties_text().getText();
			}
		}

		if (ctx.cc_biophyiochemical_redox() != null) {
			bp.rdox_potential = ctx.cc_biophyiochemical_redox().cc_properties_text().getText().trim();
		}

		if (ctx.cc_biophyiochemical_ph() != null) {
			bp.ph_dependence = ctx.cc_biophyiochemical_ph().cc_properties_text().getText().trim();
		}

		if (ctx.cc_biophyiochemical_temperature() != null) {
			bp.temperature_dependence = ctx.cc_biophyiochemical_temperature().cc_properties_text().getText().trim();
		}
	}

	@Override
	public void exitCc_alternative_products(@NotNull CcLineParser.Cc_alternative_productsContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.ALTERNATIVE_PRODUCTS;
		CcLineObject.AlternativeProducts ap = new CcLineObject.AlternativeProducts();
		cc.object = ap;

		CcLineParser.Cc_alternative_products_eventContext cc_alternative_products_eventContext = ctx.cc_alternative_products_event();
		List<CcLineParser.Cc_alternative_valueContext> cc_alternative_valueContexts1 =
				cc_alternative_products_eventContext.cc_alternative_products_event_event().cc_alternative_value();

		for (CcLineParser.Cc_alternative_valueContext cc_alternative_valueContext : cc_alternative_valueContexts1) {
			String text = cc_alternative_valueContext.getText();
			ap.events.add(text);
		}

		ap.namedIsoforms = cc_alternative_products_eventContext.cc_alternative_products_event_namedisoforms().cc_alternative_value().getText();
		if (cc_alternative_products_eventContext.cc_alternative_products_event_comment() != null) {
			ap.comment = cc_alternative_products_eventContext.cc_alternative_products_event_comment().cc_properties_text().getText();
		}

		List<CcLineParser.Cc_alternative_products_nameContext> cc_alternative_products_nameContexts = ctx.cc_alternative_products_name();
		for (CcLineParser.Cc_alternative_products_nameContext nameContext : cc_alternative_products_nameContexts) {
			CcLineObject.AlternativeProductName name = new CcLineObject.AlternativeProductName();
			name.name = nameContext.cc_alternative_value().getText();

			CcLineParser.Cc_alternative_products_isoidContext cc_alternative_products_isoidContext = nameContext.cc_alternative_products_isoid();
			List<CcLineParser.Cc_alternative_valueContext> isos = cc_alternative_products_isoidContext.cc_alternative_value();
			for (CcLineParser.Cc_alternative_valueContext cc_alternative_valueContext : isos) {
				name.isoId.add(cc_alternative_valueContext.getText());
			}

			CcLineParser.Cc_alternative_products_sequence_valueContext seqvalueContext =
					nameContext.cc_alternative_products_sequence().cc_alternative_products_sequence_value();
			if (seqvalueContext.CC_AP_DISPLAYED() != null) {
				name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Displayed;
			} else if (seqvalueContext.CC_AP_EXTERNAL() != null) {
				name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.External;
			} else if (seqvalueContext.CC_AP_NOT_DESCRIBED() != null) {
				name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Not_described;
			} else if (seqvalueContext.cc_alternative_products_sequence_value_identifiers() != null) {
				List<TerminalNode> terminalNodes = seqvalueContext.cc_alternative_products_sequence_value_identifiers().CC_AP_FEATURE_IDENTIFIER();
				for (TerminalNode terminalNode : terminalNodes) {
					String text = terminalNode.getText();
					name.sequence_FTId.add(text);
				}
			}

			CcLineParser.Cc_alternative_products_synonymsContext synContext = nameContext.cc_alternative_products_synonyms();
			if (synContext != null) {
				List<CcLineParser.Cc_alternative_valueContext> cc_alternative_valueContexts = synContext.cc_alternative_value();
				for (CcLineParser.Cc_alternative_valueContext cc_alternative_valueContext : cc_alternative_valueContexts) {
					name.synNames.add(cc_alternative_valueContext.getText());
				}
			}

			CcLineParser.Cc_alternative_products_noteContext cc_alternative_products_noteContext
					= nameContext.cc_alternative_products_note();
			if (cc_alternative_products_noteContext != null) {
				String value = cc_alternative_products_noteContext.cc_properties_text().getText();
				name.note = value;
			}
			ap.names.add(name);
		}

		object.ccs.add(cc);
	}

	public CcLineObject getObject() {
		return object;
	}
}
