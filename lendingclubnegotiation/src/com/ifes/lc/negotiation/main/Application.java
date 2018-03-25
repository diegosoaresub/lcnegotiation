package com.ifes.lc.negotiation.main;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.agent.AgentAI;
import com.ifes.lc.negotiation.attribute.Attribute;
import com.ifes.lc.negotiation.attribute.AttributeType;
import com.ifes.lc.negotiation.attribute.Item;
import com.ifes.lc.negotiation.info.Information;
import com.ifes.lc.negotiation.strategie.NegotiationStrategie;
import com.ifes.lc.negotiation.strategie.NegotiationStrategieAI;
import com.ifes.lc.negotiation.util.Log;
import com.ifes.lc.negotiation.util.Util;

/**
 * Created by diegosoaresub on 28/05/17.
 */
public class Application {

    static Agent agentProposal;

    static Agent agentCounterProposal;
    
    static long negotiationsTotal = 1;

    static int testTotal = 1;

    public static Double AMOUNT_REQUIRED = 20_000.00;

    public static String PARCELA = "parcela";

    public static String JUROS = "juros";
    
    public static int rounds = 10;

    public static int t = 1;
    
    
    public static void main(String[] args) {
        Log.level = 1;

        negociacoesFixas();
    }

    public static void negociacoesFixas(){
    		NegotiationHost host = new NegotiationHost();
    	
    		for (int test = 0; test < testTotal; test++){
	        for (int i = 0; i < negotiationsTotal; i++) {
	            t = 1;
	
	            Log.print(1, (i + 1)  + ": " );
	
	            carregaDadosFixos();
	
	            host.negotiation(agentProposal, agentCounterProposal, AMOUNT_REQUIRED);
	        }
    		}
    }
    

    public static void carregaDadosFixos(){

    	    Double minInterest = com.ifes.lc.negotiation.util.Util.round2Places(ThreadLocalRandom.current().nextDouble(1.2, 7.0));
    	    Double maxInterest = Util.round2Places(minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101))/100.0));
	
    	    Double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
    	    Double maxInstallments = Math.ceil(minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101))/100));
    	
        	
    	   Item productBuyer = new Item("Emprestimo");
       productBuyer.addAttribute(Application.JUROS, 0.6, 0.2, minInterest, maxInterest, AttributeType.COST);
       productBuyer.addAttribute(Application.PARCELA, 0.4, 0.2, minInstallments, maxInstallments, AttributeType.BENEFIT);
       
       Map<String, Information> info = new HashMap<>();
	   info.put("tot_cur_bal", new Information("tot_cur_bal", 1));
	   info.put("grade", new Information("grade", 1));
	   info.put("dti", new Information("dti", 1));
	   info.put("revol_bal", new Information("revol_bal", 1));
	   info.put("revol_util", new Information("revol_util", 1));
	   info.put("annual_inc", new Information("annual_inc", 1));
	   info.put("loan_amnt", new Information("loan_amnt", 1));
	   info.put("total_acc", new Information("total_acc", 1));       
       
       agentProposal =  new Agent("Cliente", productBuyer, new NegotiationStrategie(), info);
	
       Log.println(1, "");
       Log.print(1, "\tDados " + agentProposal.getName() + " -> ");

       agentProposal.getObj().getAttrs().forEach(a -> {
           Log.print(1, a.getName() + ": " + a.getMin() + " - " + a.getMax() + " ");
       });
       Log.println(1, "");

       
   	    minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(1.2, 7.0));
   	    maxInterest = Util.round2Places(minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101))/100.0));
	
   	    minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
   	    maxInstallments = Math.ceil(minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101))/100));
       

       Item productSeller = new Item("Emprestimo");
       productSeller.addAttribute(new Attribute(Application.JUROS, 0.6, 0.2, minInterest, maxInterest, AttributeType.BENEFIT));
       productSeller.addAttribute(new Attribute(Application.PARCELA, 0.4, 0.2, minInstallments, maxInstallments, AttributeType.COST));
       
       agentCounterProposal =  new AgentAI("Gerente", productSeller, new NegotiationStrategieAI());
    	
    }
}
