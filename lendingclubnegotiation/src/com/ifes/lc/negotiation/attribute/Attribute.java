package com.ifes.lc.negotiation.attribute;

/**
 * Created by diegosoaresub on 28/05/17.
 */
public class Attribute {

    private String name;

    private AttributeType attributeType;

    private Double weight;

    private Double pap;

    private Double min;

    private Double max;

    private Double initialValue;
    
    private Double concessionSpeed;

    public Attribute(String name, AttributeType attributeType){
        this.name = name;
        this.attributeType = attributeType;
    }

    public Attribute(String name, Double weight, Double pap, Double min, Double max, AttributeType attributeType) {
        this.name = name;
        this.weight = weight;
        this.pap = pap;
        this.min = min;
        this.max = max;
        this.attributeType = attributeType;
        this.concessionSpeed = computeConcessionSpeed();
    }

    private Double computeConcessionSpeed(){
        return 1 + weight;
    }

	public void setConcessionSpeed(Double concessionSpeed) {
		this.concessionSpeed = concessionSpeed;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
        this.concessionSpeed = computeConcessionSpeed();
    }

    public Double getPap() {
        return pap;
    }

    public void setPap(Double pap) {
        this.pap = pap;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        if (min < 0.0)
        	this.min = 0.0;
        else        
        	this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        if (max < 0.0)
        	this.max = 0.0;
        else        
        	this.max = max;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public Double getConcessionSpeed() {
        return concessionSpeed;
    }

    public Double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(Double initialValue) {
		this.initialValue = initialValue;
	}

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", attributeType=" + attributeType + ", weight=" + weight + ", pap=" + pap
				+ ", min=" + min + ", max=" + max + ", initialValue=" + initialValue + ", concessionSpeed="
				+ concessionSpeed + "]";
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        return name != null ? name.equals(attribute.name) : attribute.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
