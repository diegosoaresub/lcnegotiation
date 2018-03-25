package com.ifes.lc.negotiation.agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ifes.lc.negotiation.attribute.Attribute;
import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.AttributeType;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.main.Application;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;
import com.ifes.lc.negotiation.util.Log;
import com.ifes.lc.negotiation.util.Util;

/**
 * Created by diegosoaresub on 28/05/17.
 */
public class Agent {

	protected NegotiationStrategie strategie;

	private String name;

	private Item obj;

	private Map<String, AttributeProposal> actualProposal;

	private Map<String, List<Double>> bidHistory;

	protected Map<String, Information> info;
	
	public Agent(String name, Item obj, NegotiationStrategie strategie) {
		this.name = name;
		this.obj = obj;
		this.bidHistory = new HashMap<String, List<Double>>();
		this.strategie = strategie;
		this.info = new HashMap<>();
	}

	public Agent(String name, Item obj, NegotiationStrategie strategie, Map<String, Information> info) {
		this.name = name;
		this.obj = obj;
		this.bidHistory = new HashMap<String, List<Double>>();
		this.strategie = strategie;
		this.info = info;
	}
	
	public void makeProposal(Map<String, AttributeProposal> actualProposal) {
		Map<String, AttributeProposal> proposal;

		if (Application.t == 1) {
			Log.println("\n\t[" + this.getName() + "] faz sua proposta inicial");

			proposal = this.strategie.initialProposal(this);
			saveInicialValues();
		} else {
			Log.println("\n\t[" + this.getName() + "] faz uma proposta");
			proposal = this.strategie.proposal(this, this.getActualProposal());
		}

		this.setActualProposal(proposal);
		this.getActualProposal().forEach((k, v) -> Log.println("\t\t" + k + " = " + v.getValue()));
	}

	public boolean evaluateProposal(Agent agentCounterProposal) {

		Log.println("\n\t[" + this.getName() + "] avalia a proposa de [" + agentCounterProposal.getName() + "]");

		Double proposalValue = this.strategie.computeProposalScore(this, this.getActualProposal());
		Log.println("\t\t" + this.getName() + ":" + proposalValue);

		Double counterProposalValue = this.strategie.computeCounterProposalScore(this,
				agentCounterProposal.getActualProposal());
		Log.println("\t\t" + agentCounterProposal.getName() + ":" + counterProposalValue);

		if (counterProposalValue >= proposalValue) {
			// Log.println("\n\n>>>>>>>>>>Proposal Accepted<<<<<<<<<<<<");
			return true;
		} else
			Log.println("\n\tContraproposta do [" + agentCounterProposal.getName() + "] nao aceita pelo ["
					+ this.getName() + "]\n");

		return false;
	}

	// Deve ser chamado a cada rodada para armazenar os valores ofertados
	protected void saveRound() {
		if (bidHistory.isEmpty())
			actualProposal.forEach((k, v) -> bidHistory.put(k, new ArrayList<>()));

		actualProposal.forEach((k, v) -> bidHistory.get(k).add(v.getValue()));
	}

	public Map<String, AttributeProposal> getActualProposal() {
		return actualProposal;
	}

	protected void saveInicialValues() {
		List<Attribute> attrs = obj.getAttrs();

		for (Attribute attribute : attrs) {
			Double vlr;
			if (attribute.getAttributeType().equals(AttributeType.BENEFIT))
				vlr = attribute.getMax();
			else
				vlr = attribute.getMin();

			bidHistory.put(attribute.getName(), new ArrayList<>());
			bidHistory.get(attribute.getName()).add(vlr);
		}
	}

	// Deve-se salvar a proposta atual apenas usando o set, para que o historico
	// possa ser salvo
	protected void setActualProposal(Map<String, AttributeProposal> actualProposal) {
		this.actualProposal = actualProposal;
		saveRound();
	}

	public void printHistory() {
		String str = "";
		String strCrescimento = "";
		String strCrescimentoTot = "\tTot.: ";

		Log.println(1, "\n\t" + this.getName());

		// Imprime o histórico
		for (Entry<String, List<Double>> entry : getBidHistory().entrySet()) {
			boolean first = true;

			str += "\t\t" + entry.getKey() + " = [ ";

			strCrescimento += "\t\t" + entry.getKey() + " = [ ";

			strCrescimentoTot += "\t" + entry.getKey() + " = ";

			// Calcula o crescimento
			Double[] vlrs = Arrays.copyOf(entry.getValue().toArray(), entry.getValue().toArray().length,
					Double[].class);
			for (int i = 1; i < vlrs.length; i++) {
				Double dValue = vlrs[i];
				Double dValuePrior = vlrs[i - 1];

				if (first) {
					first = false;
					str += "ini=[" + dValuePrior + "] " + dValue + " ";
				} else
					str += dValue + " ";

				strCrescimento += Util.round((((dValue - dValuePrior) / dValuePrior) * 100.0)) + " ";
			}
			str += "]";
			strCrescimento += "]";
			strCrescimentoTot += Util.round(vlrs[vlrs.length - 1] - vlrs[0]) + " => "
					+ Util.round((((vlrs[vlrs.length - 1] - vlrs[0]) / vlrs[0]) * 100.0)) + "% ";
		}
		Log.println(1, str);
		Log.println(1, "\n" + strCrescimento);
		Log.println(1, "\n\t" + strCrescimentoTot);
	}

	public void printProposal() {
		getActualProposal().forEach((k, v) -> {
			Log.print(1, k + ": " + v.getValue() + " ");
		});
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item getObj() {
		return obj;
	}

	public void setObj(Item obj) {
		this.obj = obj;
	}

	public Map<String, List<Double>> getBidHistory() {
		return bidHistory;
	}

	public void setBidHistory(Map<String, List<Double>> bidHistory) {
		this.bidHistory = bidHistory;
	}

	@Override
	public String toString() {
		return "Agent{" + "strategie=" + strategie + ", name='" + name + '\'' + ", obj=" + obj + ", actualProposal="
				+ actualProposal + '}';
	}
}
