package com.ifes.lc.negotiation.strategie;

import java.util.HashMap;
import java.util.Map;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.attribute.Attribute;
import com.ifes.lc.negotiation.attribute.AttributeProposal;
import com.ifes.lc.negotiation.attribute.AttributeType;
import com.ifes.lc.negotiation.main.Application;
import com.ifes.lc.negotiation.util.Log;
import com.ifes.lc.negotiation.util.Util;

/**
 * Created by diegosoaresub on 28/05/17.
 */
public class NegotiationStrategie {

    public Map<String, AttributeProposal> initialProposal(Agent agent){

        Map<String, AttributeProposal> proposal = new HashMap<>();

        for (Attribute attr:
             agent.getObj().getAttrs()) {

            Double value = computeAttributeProposal(attr.getMax(), attr.getMin(),
            										   Application.t, Application.rounds,
                                                    attr.getAttributeType(), attr.getConcessionSpeed());

            AttributeProposal ap = new AttributeProposal(attr.getName(), value, attr.getWeight(), attr.getAttributeType());
            proposal.put(attr.getName(), ap);
        }

        return proposal;
    }

    public Map<String, AttributeProposal> proposal(Agent agent, Map<String, AttributeProposal> lastProposal){
        Map<String, AttributeProposal> proposal = new HashMap<>();

        for (Attribute attr:
                agent.getObj().getAttrs()) {

            if (Application.t == Application.rounds){
                applyPap(attr);
            }

            Double value;
            if (attr.getAttributeType().equals(AttributeType.BENEFIT)){
                value = computeAttributeProposal(lastProposal.get(attr.getName()).getValue(), attr.getMin(),
                        Application.t+1, Application.rounds,
                        attr.getAttributeType(), attr.getConcessionSpeed());
            }else{
                value = computeAttributeProposal(attr.getMax(), lastProposal.get(attr.getName()).getValue(),
                        Application.t+1, Application.rounds,
                        attr.getAttributeType(), attr.getConcessionSpeed());
            }
            proposal.put(attr.getName(), new AttributeProposal(attr.getName(), value, attr.getWeight(), attr.getAttributeType()));
        }

        return proposal;
    }

    public void applyPap(Attribute attr){
        Log.println("\n\tAplicando pap: ");

        if (attr.getAttributeType().equals(AttributeType.COST)){
//                    Updates the max value applying the pap constant
            Double newMax = Util.round2Places((1.0 + attr.getPap()) * attr.getMax());

            Log.println("\t\t[" + attr.getName() + "] Valor maximo [" + attr.getMax() + "] atualizado para [" + newMax + "]");

            attr.setMax(newMax);
        }
        else{
//                    Updates the min value applying the pap constant
            Double newMin = Util.round2Places((1.0 - attr.getPap()) * attr.getMin());

            Log.println("\t\t[" + attr.getName() + "] Valor minimo [" + attr.getMin() + "] atualizado para [" + newMin + "]");

            attr.setMin(newMin);
        }
    }

    public Double computeProposalScore(Agent agent, Map<String, AttributeProposal> proposal){
        Double proposalValue = 0.0;

        for (Attribute attr:
                agent.getObj().getAttrs()) {

            Double value = proposal.get(attr.getName()).getValue();
            if (value != null){
                proposalValue += computeAttributeProposalScore(attr.getMax(), attr.getMin(),
                        value, attr.getAttributeType(),
                        attr.getWeight());
            }

        }
        return Util.round2Places(proposalValue);


    }

    public Double computeCounterProposalScore(Agent agent, Map<String, AttributeProposal> counterProposal){
        Double counterProposalValue = 0.0;

        for (Attribute attr:
                agent.getObj().getAttrs()) {

            Double value = counterProposal.get(attr.getName()).getValue();
            if (value != null){
                counterProposalValue += computeAttributeProposalScore(attr.getMax(), attr.getMin(),
                        value, attr.getAttributeType(),
                        attr.getWeight());
            }

        }
        return Util.round2Places(counterProposalValue);
    }


    public Double computeAttributeProposalScore(Double upperBound, Double lowerBound,
                                                Double value, AttributeType type,
                                                Double weight){

        Double interval = upperBound - lowerBound;

        if (type.equals(AttributeType.BENEFIT)){
            return Util.round2Places(((value - lowerBound)/interval)*weight);
        }

        return  Util.round2Places(((upperBound - value)/interval)*weight);
    }

    /**
     * As the objective of the buyer is to minimize the cost and maximize the benefit
     * during the negotiation process, the offer values for the various negotiation issues
     * are computed depending upon their type in order to optimize the utility of the buyer.
     * @param upperBound
     * @param lowerBound
     * @param currentRound
     * @param rounds
     * @param type
     * @param concessionSpeed
     * @return proposal value
     */
    private Double computeAttributeProposal(Double upperBound, Double lowerBound,
                                            Integer currentRound, Integer rounds,
                                            AttributeType type, Double concessionSpeed){

        Double k = Math.pow(Double.valueOf(currentRound)/Double.valueOf(rounds), concessionSpeed);
        Double interval = upperBound - lowerBound;

        if (type.equals(AttributeType.BENEFIT)){
            return Util.round2Places(upperBound - k*interval);
        }

//      COST
        return Util.round2Places(lowerBound + k*interval);
    }


}
