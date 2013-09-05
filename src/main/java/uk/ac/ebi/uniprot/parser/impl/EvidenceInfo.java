package uk.ac.ebi.uniprot.parser.impl;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keep the evidence Info for the LineObject.
 */
public class EvidenceInfo {
	//put the evidence for a certain object.
	//object is the value, List<String> is the list of evidence it has.
	public Map<Object, List<String>> evidences = new HashMap<Object, List<String>>();


	public static void processEvidence(EvidenceInfo ev, Object key, List<TerminalNode> terminalNodes) {
		List<String> strings = new ArrayList<String>();
		for (TerminalNode terminalNode : terminalNodes) {
			strings.add(terminalNode.getText());
		}
		ev.evidences.put(key, strings);
	}
}
