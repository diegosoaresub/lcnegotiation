package com.ifes.lc.negotiation.agent;

import static com.ifes.lc.negotiation.main.Application.AMOUNT_REQUIRED;
import static com.ifes.lc.negotiation.main.Application.JUROS;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.prediction.DefaultPredictionService;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;
import com.ifes.lc.negotiation.util.Log;

public class ManagerAgent extends Agent {

	public ManagerAgent(String name, Item obj, NegotiationStrategie strategie) {
		super(name, obj, strategie);
	}

	@Override
	public Double evaluateProposalInfo(Double proposalValue, Map<String, AttributeProposal> proposal,
			Map<String, Information> opponentInfo) {
		
		Map<String, Double> request = new HashMap<>();

		Log.println(1, "\n\t\tEvaluateProposalInfo: ");
		Log.println(1, "\t\t\tint_rate    = " + proposal.get(JUROS).getValue());
		Log.println(1, "\t\t\tloan_amnt   = " + AMOUNT_REQUIRED);

		for (Entry<String, Information> entry : opponentInfo.entrySet()) {
			Log.println(1, "\t\t" + entry.getKey() + " = " + entry.getValue().getDoubleValue());
			request.put(entry.getKey(), entry.getValue().getDoubleValue());
		} 

		request.put(proposal.get(JUROS).getNameOnModel(), proposal.get(JUROS).getValue());
		request.put("loan_amnt", AMOUNT_REQUIRED);

		
		DefaultPredictionService service = new DefaultPredictionService();
		service.predict(request);

		return proposalValue;
	}

}
