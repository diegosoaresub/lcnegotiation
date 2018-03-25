package com.ifes.lc.negotiation.strategie;

import java.util.Map;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.attribute.AttributeProposal;

public class NegotiationStrategieAI extends NegotiationStrategie{

	@Override
    public Double computeProposalScore(Agent agent, Map<String, AttributeProposal> proposal){
        Double proposalValue = super.computeProposalScore(agent, proposal);
        return proposalValue;
    }	
}
