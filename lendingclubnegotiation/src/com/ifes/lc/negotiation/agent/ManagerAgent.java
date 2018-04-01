package com.ifes.lc.negotiation.agent;

import static com.ifes.lc.negotiation.main.Application.AMOUNT_REQUIRED;
import static com.ifes.lc.negotiation.main.Application.JUROS;

import java.util.Map;

import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.prediction.DefaultPredictionParams;
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

		Log.println(1, "\n\t\tEvaluateProposalInfo: ");

		Log.println(1, "\t\t\tint_rate    = " + proposal.get(JUROS).getValue());
		Log.println(1, "\t\t\ttot_cur_bal = " + opponentInfo.get("tot_cur_bal").getValue());
		Log.println(1, "\t\t\tgrade       = " + opponentInfo.get("grade").getValue());
		Log.println(1, "\t\t\tdti         = " + opponentInfo.get("dti").getValue());
		Log.println(1, "\t\t\trevol_bal   = " + opponentInfo.get("revol_bal").getValue());
		Log.println(1, "\t\t\trevol_util  = " + opponentInfo.get("revol_util").getValue());
		Log.println(1, "\t\t\tannual_inc  = " + opponentInfo.get("annual_inc").getValue());
		Log.println(1, "\t\t\ttotal_acc   = " + opponentInfo.get("total_acc").getValue());
		Log.println(1, "\t\t\tloan_amnt   = " + AMOUNT_REQUIRED);

		DefaultPredictionParams params = new DefaultPredictionParams(proposal.get(JUROS).getValue(),
																	opponentInfo.get("tot_cur_bal").getDoubleValue(), 
																	opponentInfo.get("grade").getDoubleValue(),
																	opponentInfo.get("dti").getDoubleValue(), 
																	opponentInfo.get("revol_bal").getDoubleValue(),
																	opponentInfo.get("revol_util").getDoubleValue(), 
																	opponentInfo.get("annual_inc").getDoubleValue(),
																	opponentInfo.get("total_acc").getDoubleValue(), 
																	AMOUNT_REQUIRED);
		
		DefaultPredictionService service = new DefaultPredictionService();
		service.predict(params);
		
		return proposalValue;
	}

}
