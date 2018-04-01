package com.ifes.lc.negotiation.prediction;

public class DefaultPredictionParams {

	private Double int_rate;
	private Double tot_cur_bal;
	private Double grade;
	private Double dti;
	private Double revol_bal;
	private Double revol_util;
	private Double annual_inc;
	private Double total_acc;
	private Double loan_amnt;
	
	public DefaultPredictionParams(Double int_rate, Double tot_cur_bal, Double grade, Double dti, Double revol_bal,
			Double revol_util, Double annual_inc, Double total_acc, Double loan_amnt) {
		this.int_rate = int_rate;
		this.tot_cur_bal = tot_cur_bal;
		this.grade = grade;
		this.dti = dti;
		this.revol_bal = revol_bal;
		this.revol_util = revol_util;
		this.annual_inc = annual_inc;
		this.total_acc = total_acc;
		this.loan_amnt = loan_amnt;
	}
	public Double getInt_rate() {
		return int_rate;
	}
	public void setInt_rate(Double int_rate) {
		this.int_rate = int_rate;
	}
	public Double getTot_cur_bal() {
		return tot_cur_bal;
	}
	public void setTot_cur_bal(Double tot_cur_bal) {
		this.tot_cur_bal = tot_cur_bal;
	}
	public Double getGrade() {
		return grade;
	}
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	public Double getDti() {
		return dti;
	}
	public void setDti(Double dti) {
		this.dti = dti;
	}
	public Double getRevol_bal() {
		return revol_bal;
	}
	public void setRevol_bal(Double revol_bal) {
		this.revol_bal = revol_bal;
	}
	public Double getRevol_util() {
		return revol_util;
	}
	public void setRevol_util(Double revol_util) {
		this.revol_util = revol_util;
	}
	public Double getAnnual_inc() {
		return annual_inc;
	}
	public void setAnnual_inc(Double annual_inc) {
		this.annual_inc = annual_inc;
	}
	public Double getTotal_acc() {
		return total_acc;
	}
	public void setTotal_acc(Double total_acc) {
		this.total_acc = total_acc;
	}
	public Double getLoan_amnt() {
		return loan_amnt;
	}
	public void setLoan_amnt(Double loan_amnt) {
		this.loan_amnt = loan_amnt;
	}
}
