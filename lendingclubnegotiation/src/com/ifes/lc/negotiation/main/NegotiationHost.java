package com.ifes.lc.negotiation.main;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.util.Log;

public class NegotiationHost {

	public static int negotiationAcceptedCounter = 0;
	
	public boolean negotiation(Agent agentProposal,
								Agent agentCounterProposal,
								Double amountRequired){
        int i = 1;
        boolean initialProposal = true;
        boolean negotiationAccepted = false;


        Log.println("Inicio da negociacao");


        while (Application.t <= Application.rounds)
        {
            if (i == 1)
                Log.println("\n----------------------- " + Application.t + " -----------------------");

            Log.println(agentProposal.getName() +  ":");
            
            agentProposal.makeProposal(agentCounterProposal.getActualProposal());

            if (initialProposal){
                initialProposal = false;
            }
            else
            if (agentProposal.evaluateProposal(agentCounterProposal)){
                negotiationAccepted = true;
                break;
            }

            Agent temp = agentCounterProposal;
            agentCounterProposal = agentProposal;
            agentProposal = temp;


            if (i == 2) {
                i = 1;
                Application.t++;;
            }else
                i++;
        }

        Log.println(1, "\n--------------------------------------------------------");
        Log.print(1, "\tRound [" +Application.t + "] ");
        if (negotiationAccepted){
        	negotiationAcceptedCounter++;
            Log.print(1, "OK     -> ");
        }else{
            Log.print(1, "NOT OK -> ");
        }
        
        Log.print(1, agentProposal.getName() + " -> ");
        agentProposal.printProposal();
        Log.println(1, "");
        
        
//      ---------------HISTORICO--------------
        
        Log.println("\nHistorico: ");

        Log.println("\t" + agentProposal.getName());
        agentProposal.printHistory();
        
        Log.println("\n");

        Log.println("\t" + agentCounterProposal.getName());
        agentCounterProposal.printHistory();
        
//      --------------------------------------        
        
        Log.println();

        Log.println("--------------------------------------------------------");
        
        return negotiationAccepted;
    }
}
