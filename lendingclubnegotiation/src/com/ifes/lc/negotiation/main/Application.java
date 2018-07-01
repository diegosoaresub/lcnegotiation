package com.ifes.lc.negotiation.main;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.ifes.lc.negotiation.agent.Agent;
import com.ifes.lc.negotiation.agent.CustomerAgent;
import com.ifes.lc.negotiation.agent.ManagerAgent;
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

/*
			  ----- GOOD ----- 
      int_rate      grade   annual_inc  loan_amnt
mean     13,34       2,57    72.626,62  13.360,64
std       4,26       1,25    56.405,65   7.933,94
min       5,42       1,00     4.000,00     500,00
25%      10,16       2,00    45.000,00   7.200,00
50%      13,11       2,00    62.000,00  12.000,00
75%      16,20       3,00    88.000,00  18.000,00
max      26,06       7,00 7.141.778,00  35.000,00

*/

/*
			----- DELINQUENT ----- 
      int_rate      grade   annual_inc  loan_amnt
mean     15,63       3,47    69.600,50  15.450,10
std       4,62       1,34    67.788,17   8.595,87
min       5,32       1,00       690,00     900,00
25%      12,42       3,00    43.000,00   9.000,00
50%      15,31       3,00    60.000,00  14.175,00
75%      18,49       4,00    83.000,00  20.000,00
max      30,99       7,00 9.500.000,00  40.000,00

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

	public static void negociacoesFixas() {
		NegotiationHost host = new NegotiationHost();

		for (int test = 0; test < testTotal; test++) {
			for (int i = 0; i < negotiationsTotal; i++) {
				t = 1;

				Log.print(1, (i + 1) + ": ");

				carregaDadosFixos();

				host.negotiation(agentProposal, agentCounterProposal, AMOUNT_REQUIRED);
			}
		}
	}

	public static void carregaDadosFixos() {
		loadCustomer();
		
		Log.println(1, "");
		Log.print(1, "\tDados " + agentProposal.getName() + " -> ");

		agentProposal.getObj().getAttrs().forEach(a -> {
			Log.print(1, a.getName() + ": " + a.getMin() + " - " + a.getMax() + " ");
		});
		Log.println(1, "");

		loadManager();
	}

	static void loadManager() {
		double minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(15.2, 16.0));
		double maxInterest = Util.round2Places(
				minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101)) / 100.0));

		double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
		double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Item productSeller = new Item("Emprestimo");
		productSeller.addAttribute(
				new Attribute(Application.JUROS, "int_rate", 0.6, 0.2, minInterest, maxInterest, AttributeType.BENEFIT));
		productSeller.addAttribute(
				new Attribute(Application.PARCELA, "installments", 0.4, 0.2, minInstallments, maxInstallments, AttributeType.COST));

		agentCounterProposal = new ManagerAgent("Gerente", productSeller, new NegotiationStrategieAI());		
	}
	
	static void loadCustomer() {
		Double minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(15.2, 20.0));
		Double maxInterest = Util.round2Places(
				minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101)) / 100.0));

		Double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
		Double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Item productBuyer = new Item("Emprestimo");
		productBuyer.addAttribute(Application.JUROS, "int_rate", 0.6, 0.2, minInterest, maxInterest, AttributeType.COST);
		productBuyer.addAttribute(Application.PARCELA, "installments", 0.4, 0.2, minInstallments, maxInstallments,
				AttributeType.BENEFIT);

		Map<String, Information> info = new HashMap<>();
		info.put("grade", new Information("grade", 1));
		info.put("annual_inc", new Information("annual_inc", 71_113.56));

		
//		info.put("tot_cur_bal", new Information("tot_cur_bal", 137_669.19));
//		info.put("dti", new Information("dti", 18.07));
//		info.put("revol_bal", new Information("revol_bal", 15_609.95));
//		info.put("revol_util", new Information("revol_util", 56.06));
//		info.put("total_acc", new Information("total_acc", 24.66));

		agentProposal = new CustomerAgent("Cliente", productBuyer, new NegotiationStrategie(), info);
	}
}
