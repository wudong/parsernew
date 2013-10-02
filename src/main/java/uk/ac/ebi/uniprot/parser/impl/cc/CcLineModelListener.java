package uk.ac.ebi.uniprot.parser.impl.cc;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;
import uk.ac.ebi.uniprot.antlr.TextHelper;
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
	public void exitCc_sequence_caution(@NotNull CcLineParser.Cc_sequence_cautionContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;

		CcLineObject.SequenceCaution sc = new CcLineObject.SequenceCaution();
		cc.object = sc;
		object.ccs.add(cc);

		List<CcLineParser.Cc_sequence_caution_lineContext>
				cc_sequence_caution_lineContexts = ctx.cc_sequence_caution_line();

		for (CcLineParser.Cc_sequence_caution_lineContext lineContext : cc_sequence_caution_lineContexts) {
			CcLineObject.SequenceCautionObject object1 = new CcLineObject.SequenceCautionObject();
			sc.sequenceCautionObjects.add(object1);
			CcLineParser.Cc_sequence_caution_sequenceContext cc_sequence_caution_sequenceContext = lineContext.cc_sequence_caution_sequence();
			object1.sequence = cc_sequence_caution_sequenceContext.cc_sequence_caution_value().getText();

			CcLineParser.Cc_sequence_caution_typeContext cc_sequence_caution_typeContext = lineContext.cc_sequence_caution_type();
			object1.type = CcLineObject.SequenceCautionType.fromSting(cc_sequence_caution_typeContext.CC_SC_TYPE_VALUE().getText());

			CcLineParser.Cc_sequence_caution_positionContext cc_sequence_caution_positionContext = lineContext.cc_sequence_caution_position();
			if (cc_sequence_caution_positionContext != null) {
				CcLineParser.Cc_sequence_caution_position_valueContext ccc = cc_sequence_caution_positionContext.cc_sequence_caution_position_value();

				List<TerminalNode> integer = ccc.INTEGER();
				for (TerminalNode terminalNode : integer) {
					object1.positions.add(Integer.parseInt(terminalNode.getText()));
				}

				TerminalNode terminalNode = ccc.CC_SC_P_VALUE();
				if (terminalNode !=null){
					object1.positionValue = terminalNode.getText();
				};
			}

			CcLineParser.Cc_sequence_caution_noteContext cc_sequence_caution_noteContext = lineContext.cc_sequence_caution_note();
			if (cc_sequence_caution_noteContext != null) {
				object1.note = cc_sequence_caution_noteContext.CC_SC_NOTE_TEXT().getText();
			}
		}

	}

	@Override
	public void exitCc_mass_spectrometry(@NotNull CcLineParser.Cc_mass_spectrometryContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.MASS_SPECTROMETRY;

		CcLineObject.MassSpectrometry ms = new CcLineObject.MassSpectrometry();
		cc.object = ms;
		object.ccs.add(cc);

		CcLineParser.Cc_mass_spectrometry_massContext cc_mass_spectrometry_massContext = ctx.cc_mass_spectrometry_mass();
		String text = cc_mass_spectrometry_massContext.CC_MS_V_NUMBER().getText();
		ms.mass = Float.parseFloat(text);

		CcLineParser.Cc_mass_spectrometry_mass_errorContext cc_mass_spectrometry_mass_errorContext = ctx.cc_mass_spectrometry_mass_error();
		if (cc_mass_spectrometry_mass_errorContext != null) {
			String text1 = cc_mass_spectrometry_mass_errorContext.CC_MS_V_NUMBER().getText();
			ms.mass_error = Float.parseFloat(text1);
		}

		CcLineParser.Cc_mass_spectrometry_mass_methodContext cc_mass_spectrometry_mass_methodContext = ctx.cc_mass_spectrometry_mass_method();
		String text1 = cc_mass_spectrometry_mass_methodContext.cc_mass_spectrometry_value().getText();
		ms.method = text1;

		CcLineParser.Cc_mass_spectrometry_mass_rangeContext rangeContext = ctx.cc_mass_spectrometry_mass_range();

		List<CcLineParser.Cc_mass_spectrometry_mass_range_valueContext> range_valueContexts = rangeContext.cc_mass_spectrometry_mass_range_value();
		for (CcLineParser.Cc_mass_spectrometry_mass_range_valueContext range_valueContext : range_valueContexts) {
			CcLineParser.Cc_mass_spectrometry_mass_range_value_valueContext vv1 =
					range_valueContext.cc_mass_spectrometry_mass_range_value_value(0);

			CcLineParser.Cc_mass_spectrometry_mass_range_value_valueContext vv2 =
					range_valueContext.cc_mass_spectrometry_mass_range_value_value(1);

			CcLineObject.MassSpectrometryRange range = new CcLineObject.MassSpectrometryRange();

			if (vv1.INTEGER()!=null){
				range.start = Integer.parseInt(vv1.INTEGER().getText());
			}else{
				range.start_unknown = true;
			}

			if (vv2.INTEGER()!=null){
				range.end = Integer.parseInt(vv2.INTEGER().getText());
			}else{
				range.end_unknown = true;
			}
			ms.ranges.add(range);
		}

		TerminalNode iso = rangeContext.CC_MS_R_V_ISO();
		if (iso != null) {
			//there are some wrapping problem here.
			//TODO should be checked. could the wrapping really happen here.
			ms.range_isoform = iso.getText().replace("\nCC       ", "");
		}

		CcLineParser.Cc_mass_spectrometry_mass_noteContext noteContext = ctx.cc_mass_spectrometry_mass_note();
		if (noteContext != null) {
			ms.note = noteContext.cc_properties_text().getText();
		}

		CcLineParser.Cc_mass_spectrometry_mass_sourceContext sourceContext = ctx.cc_mass_spectrometry_mass_source();
		ms.source = sourceContext.cc_mass_spectrometry_value().getText();

	}

	@Override
	public void exitCc_web_resource(@NotNull CcLineParser.Cc_web_resourceContext ctx) {
		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.WEB_RESOURCE;

		CcLineObject.WebResource wr = new CcLineObject.WebResource();
		cc.object = wr;
		object.ccs.add(cc);

		CcLineParser.Cc_web_resource_nameContext cc_web_resource_nameContext = ctx.cc_web_resource_name();
		wr.name = cc_web_resource_nameContext.cc_properties_text().getText();

		CcLineParser.Cc_web_resource_urlContext cc_web_resource_urlContext = ctx.cc_web_resource_url();
		if (cc_web_resource_urlContext != null) {
			String text = cc_web_resource_urlContext.cc_properties_text().getText();
			//url is in the format of "http://..."
			wr.url = text.substring(1, text.length() - 1);
		}

		CcLineParser.Cc_web_resource_noteContext cc_web_resource_noteContext = ctx.cc_web_resource_note();
		if (cc_web_resource_noteContext != null) {
			wr.note = cc_web_resource_noteContext.cc_properties_text().getText();
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
		for (CcLineParser.Cc_interaction_lineContext io : cc_interaction_lineContexts) {
			CcLineObject.InteractionObject interactionObject = new CcLineObject.InteractionObject();
			CcLineParser.Cc_interaction_intactContext cc_interaction_intactContext = io.cc_interaction_intact();
			interactionObject.firstId = cc_interaction_intactContext.CC_IR_AC(0).getText();
			interactionObject.secondId = cc_interaction_intactContext.CC_IR_AC(1).getText();

			TerminalNode integer = io.cc_interaction_nbexp().INTEGER();
			interactionObject.nbexp = Integer.parseInt(integer.getText());

			CcLineParser.Cc_interaction_spContext cc_interaction_spContext = io.cc_interaction_sp();
			if (cc_interaction_spContext.CC_IR_SELF() != null) {
				interactionObject.isSelf = true;
			} else {
				interactionObject.spAc = cc_interaction_spContext.CC_IR_AC().getText();
				if (cc_interaction_spContext.DASH() != null) {
					interactionObject.gene = "-";
				} else {
					interactionObject.gene = cc_interaction_spContext.CC_IR_GENENAME().getText();
				}

				if (cc_interaction_spContext.CC_IR_XENO() != null) {
					interactionObject.xeno = true;
				}
			}

			ir.interactions.add(interactionObject);
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

			if (ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_note()!=null){
				bp.bsorption_note = ctx.cc_biophyiochemical_absorption().cc_biophyiochemical_absorption_note().
						cc_properties_text_level2().getText();
			}
		}

		CcLineParser.Cc_biophyiochemical_kineticContext kineticContext = ctx.cc_biophyiochemical_kinetic();
		if (kineticContext != null) {
			List<CcLineParser.Cc_biophyiochemical_kinetic_kmContext> cc_biophyiochemical_kinetic_kmContexts = kineticContext.cc_biophyiochemical_kinetic_km();
			for (CcLineParser.Cc_biophyiochemical_kinetic_kmContext km : cc_biophyiochemical_kinetic_kmContexts) {
				bp.kms.add(km.cc_properties_text_level2().getText());
			}

			List<CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext> maxcontext = kineticContext.cc_biophyiochemical_kinetic_bpmax();
			for (CcLineParser.Cc_biophyiochemical_kinetic_bpmaxContext c : maxcontext) {
				bp.vmaxs.add(c.cc_properties_text_level2().getText());
			}

			CcLineParser.Cc_biophyiochemical_kinetic_noteContext noteContext = kineticContext.cc_biophyiochemical_kinetic_note();
			if (noteContext != null) {
				bp.kp_note = noteContext.cc_properties_text_level2().getText();
			}
		}

		if (ctx.cc_biophyiochemical_redox() != null) {
			bp.rdox_potential = ctx.cc_biophyiochemical_redox().cc_properties_text_level2().getText().trim();
		}

		if (ctx.cc_biophyiochemical_ph() != null) {
			bp.ph_dependence = ctx.cc_biophyiochemical_ph().cc_properties_text_level2().getText().trim();
		}

		if (ctx.cc_biophyiochemical_temperature() != null) {
			bp.temperature_dependence = ctx.cc_biophyiochemical_temperature().cc_properties_text_level2().getText().trim();
		}
	}

	@Override
	public void exitCc_rna_editing(@NotNull CcLineParser.Cc_rna_editingContext ctx) {

		CcLineObject.CC cc = new CcLineObject.CC();

		cc.topic = CcLineObject.CCTopicEnum.RNA_EDITING;
		CcLineObject.RnaEditing re = new CcLineObject.RnaEditing();
		cc.object = re;

		CcLineParser.Cc_rna_editing_positionContext positionContext = ctx.cc_rna_edigint_modified_position().cc_rna_editing_position();
		if (positionContext!=null){
			List<TerminalNode> integer = positionContext.INTEGER();
			for (TerminalNode terminalNode : integer) {
				String text = terminalNode.getText();
				re.locations.add(Integer.parseInt(text));
			}
		} if (ctx.cc_rna_edigint_modified_position()
					.CC_RE_MODIFIED_POSITION_UNDETERMINED()!=null){
			re.locationEnum = CcLineObject.RnaEditingLocationEnum.Undetermined;
		}

		if (ctx.cc_rna_edigint_note()!=null){
			String text = ctx.cc_rna_edigint_note().cc_re_note_value().getText();
			re.note = text;
		}

		object.ccs.add(cc);
	}

	@Override
	public void exitCc_subcellular_location(@NotNull CcLineParser.Cc_subcellular_locationContext ctx) {

		CcLineObject.CC cc = new CcLineObject.CC();

		cc.topic = CcLineObject.CCTopicEnum.SUBCELLULAR_LOCATION;
		CcLineObject.SubcullarLocation sl = new CcLineObject.SubcullarLocation();
		cc.object = sl;

		CcLineParser.Cc_subcellular_location_moleculeContext cc_subcellular_location_moleculeContext = ctx.cc_subcellular_location_molecule();
		if (cc_subcellular_location_moleculeContext!=null){
			String word = cc_subcellular_location_moleculeContext.cc_subcellular_words().getText();
			sl.molecule=word;
		}

		CcLineParser.Cc_subcellular_noteContext cc_subcellular_noteContext = ctx.cc_subcellular_note();
		if (cc_subcellular_noteContext!=null){
			List<CcLineParser.Cc_subcellular_note_valueContext> cc_subcellular_note_valueContexts = cc_subcellular_noteContext.cc_subcellular_note_value();
			for (CcLineParser.Cc_subcellular_note_valueContext nnContext : cc_subcellular_note_valueContexts) {
				CcLineObject.SubcullarLocationNote note = new CcLineObject.SubcullarLocationNote();
				note.note = nnContext.cc_subcellular_words().getText();
				if (nnContext.cc_subcellular_location_flag()!=null){
					String text = nnContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
					note.noteFlag = CcLineObject.LocationFlagEnum.fromSting(text.substring(1, text.length()-1));
				}
				sl.notes.add(note);
			}
		}

		CcLineParser.Cc_subcellular_location_sectionContext cc_subcellular_location_sectionContext = ctx.cc_subcellular_location_section();
		if (cc_subcellular_location_sectionContext!=null){
			List<CcLineParser.Cc_subcellular_location_locationContext> cc_subcellular_location_locationContexts = cc_subcellular_location_sectionContext.cc_subcellular_location_location();
			for (CcLineParser.Cc_subcellular_location_locationContext locationContext : cc_subcellular_location_locationContexts) {
				CcLineObject.LocationObject locationObject = new CcLineObject.LocationObject();

				List<CcLineParser.Cc_subcellular_location_valueContext> valueContexts = locationContext.cc_subcellular_location_value();
				int size = valueContexts.size();
				if (size>=1){
					CcLineParser.Cc_subcellular_location_valueContext locationValueContext = valueContexts.get(0);
					locationObject.subcellular_location = locationValueContext.cc_subcellular_words().getText();
					if (locationValueContext.cc_subcellular_location_flag()!=null){
						String text = locationValueContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
						locationObject.subcellular_location_flag = CcLineObject.LocationFlagEnum.fromSting(text.substring(1, text.length()-1));
					}
				}
				if (size>=2){
					CcLineParser.Cc_subcellular_location_valueContext locationValueContext = valueContexts.get(1);
					locationObject.topology = locationValueContext.cc_subcellular_words().getText();
					if (locationValueContext.cc_subcellular_location_flag()!=null){
						String text = locationValueContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
						locationObject.topology_flag = CcLineObject.LocationFlagEnum.fromSting(text.substring(1, text.length()-1));
					}
				}
				if (size>=3){
					CcLineParser.Cc_subcellular_location_valueContext locationValueContext = valueContexts.get(2);
					locationObject.orientation = locationValueContext.cc_subcellular_words().getText();
					if (locationValueContext.cc_subcellular_location_flag()!=null){
						String text = locationValueContext.cc_subcellular_location_flag().CC_SL_FLAG().getText();
						locationObject.orientation_flag = CcLineObject.LocationFlagEnum.fromSting(text.substring(1, text.length()-1));
					}
				}

				sl.locations.add(locationObject);
			}
		}

		object.ccs.add(cc);
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
			ap.comment = cc_alternative_products_eventContext.cc_alternative_products_event_comment().cc_properties_text_level2().getText();
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
			}else if (seqvalueContext.CC_AP_VALUE_UNSURE() != null) {
				name.sequence_enum = CcLineObject.AlternativeNameSequenceEnum.Unsure;
			}else if (seqvalueContext.cc_alternative_products_sequence_value_identifiers() != null) {
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
				String value = cc_alternative_products_noteContext.cc_properties_text_level2().getText();
				name.note = value;
			}
			ap.names.add(name);
		}

		object.ccs.add(cc);
	}


	@Override
	public void exitCc_disease(@NotNull CcLineParser.Cc_diseaseContext ctx) {

		CcLineObject.CC cc = new CcLineObject.CC();
		cc.topic = CcLineObject.CCTopicEnum.DISEASE;
		CcLineObject.Disease dd = new CcLineObject.Disease();
		cc.object = dd;

		if (ctx.cc_disease_name()!=null){
			String text = ctx.cc_disease_name().getText();
			dd.name = text;

			CcLineParser.Cc_disease_abbr_minContext cc_disease_abbr_minContext = ctx.cc_disease_abbr_min();
			String text1 = cc_disease_abbr_minContext.getText();

			String[] objects = TextHelper.parseCCDiseaseAbbrMim(text1);
			dd.abbr = objects[0];
			dd.mim = objects[1];
		}

		if (ctx.cc_disease_description()!=null){
			List<CcLineParser.Cc_disease_textContext> cc_disease_textContexts = ctx.cc_disease_description().cc_disease_text();
			for (CcLineParser.Cc_disease_textContext textContext : cc_disease_textContexts) {

			}
		}

		object.ccs.add(cc);
	}

	public CcLineObject getObject() {
		return object;
	}

}
