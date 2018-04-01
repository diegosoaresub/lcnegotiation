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
          |   int_rate |  tot_cur_bal |      grade |        dti |    revol_bal |  revol_util |   annual_inc |  loan_amnt |  total_acc
	count | 372.980,00 |   372.980,00 | 372.980,00 | 372.980,00 |   372.980,00 |  372.980,00 |   372.980,00 | 372.980,00 | 372.980,00 
	mean  |      14,49 |   104.195,71 |       3,02 |      18,07 |    15.609,95 |       56,06 |    71.113,56 |  14.405,37 |      24,66 
	std   |       4,59 |   137.669,19 |       1,37 |       8,63 |    19.034,14 |       23,99 |    62.375,44 |   8.337,23 |      11,68 
	min   |       5,32 |         0,00 |       1,00 |       0,00 |         0,00 |        0,00 |       690,00 |     500,00 |       2,00 
	25%   |      11,14 |    12.344,00 |       2,00 |      11,86 |     6.270,00 |       39,00 |    44.000,00 |   8.000,00 |      16,00 
	50%   |      14,09 |    44.139,50 |       3,00 |      17,67 |    11.441,00 |       57,60 |    60.000,00 |  12.225,00 |      23,00 
	75%   |      17,57 |   161.113,50 |       4,00 |      23,88 |    19.658,00 |       74,90 |    85.000,00 |  20.000,00 |      31,00 
	max   |      30,99 | 8.000.078,00 |       7,00 |     999,00 | 2.568.995,00 |      366,60 | 9.500.000,00 |  40.000,00 |     176,00          
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
		double minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(1.2, 7.0));
		double maxInterest = Util.round2Places(
				minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101)) / 100.0));

		double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
		double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Item productSeller = new Item("Emprestimo");
		productSeller.addAttribute(
				new Attribute(Application.JUROS, 0.6, 0.2, minInterest, maxInterest, AttributeType.BENEFIT));
		productSeller.addAttribute(
				new Attribute(Application.PARCELA, 0.4, 0.2, minInstallments, maxInstallments, AttributeType.COST));

		agentCounterProposal = new ManagerAgent("Gerente", productSeller, new NegotiationStrategieAI());		
	}
	
	static void loadCustomer() {
		Double minInterest = Util.round2Places(ThreadLocalRandom.current().nextDouble(1.2, 7.0));
		Double maxInterest = Util.round2Places(
				minInterest + minInterest * ((30 + ThreadLocalRandom.current().nextDouble(0, 101)) / 100.0));

		Double minInstallments = Double.valueOf(ThreadLocalRandom.current().nextInt(1, 60));
		Double maxInstallments = Math.ceil(
				minInstallments + minInstallments * ((20 + ThreadLocalRandom.current().nextDouble(1, 101)) / 100));

		Item productBuyer = new Item("Emprestimo");
		productBuyer.addAttribute(Application.JUROS, 0.6, 0.2, minInterest, maxInterest, AttributeType.COST);
		productBuyer.addAttribute(Application.PARCELA, 0.4, 0.2, minInstallments, maxInstallments,
				AttributeType.BENEFIT);

		Map<String, Information> info = new HashMap<>();
		info.put("tot_cur_bal", new Information("tot_cur_bal", 137_669.19));
		info.put("grade", new Information("grade", 1));
		info.put("dti", new Information("dti", 18.07));
		info.put("revol_bal", new Information("revol_bal", 15_609.95));
		info.put("revol_util", new Information("revol_util", 56.06));
		info.put("annual_inc", new Information("annual_inc", 71_113.56));
		info.put("total_acc", new Information("total_acc", 24.66));

		agentProposal = new CustomerAgent("Cliente", productBuyer, new NegotiationStrategie(), info);
	}
}
