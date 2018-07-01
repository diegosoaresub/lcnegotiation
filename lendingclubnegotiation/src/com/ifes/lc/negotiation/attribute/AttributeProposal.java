package com.ifes.lc.negotiation.attribute;

/**
 * Created by diegosoaresub on 03/06/17.
 */
public class AttributeProposal {

    private String name;

    private String nameOnModel;
    
    private Double value;

    private Double weight;

    private AttributeType type;

    public AttributeProposal(String name, String nameModel, Double value, Double weight, AttributeType type) {
        this.name = name;
        this.value = value;
        this.weight = weight;
        this.type = type;
        this.nameOnModel = nameModel;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

	public String getNameOnModel() {
		return nameOnModel;
	}

	public void setNameOnModel(String nameOnModel) {
		this.nameOnModel = nameOnModel;
	}
}
