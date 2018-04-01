package com.ifes.lc.negotiation.prediction;

public class DefaultPredictionResponse {
	
	private String predicted_class;
	private Double delinquent_prob;
	public String getPredicted_class() {
		return predicted_class;
	}
	public void setPredicted_class(String predicted_class) {
		this.predicted_class = predicted_class;
	}
	public Double getDelinquent_prob() {
		return delinquent_prob;
	}
	public void setDelinquent_prob(Double delinquent_prob) {
		this.delinquent_prob = delinquent_prob;
	}
}
