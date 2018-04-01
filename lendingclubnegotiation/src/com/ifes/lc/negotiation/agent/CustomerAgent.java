package com.ifes.lc.negotiation.agent;

import java.util.Map;

import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;

public class CustomerAgent extends Agent{

	public CustomerAgent(String name, Item obj, NegotiationStrategie strategie) {
		super(name, obj, strategie);
	}

	public CustomerAgent(String name, Item obj, NegotiationStrategie strategie, Map<String, Information> info) {
		super(name, obj, strategie, info);
	}

	@Override
	public Double evaluateProposalInfo(Double proposalValue, Map<String, AttributeProposal> proposal, Map<String, Information> info2) {
		return null;
	}

}
