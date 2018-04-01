package com.ifes.lc.negotiation.info;


public class Information {
	
	private String name;
	
	private Object value;
	
	public Information(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public Double getDoubleValue() {
		if (value instanceof Number)
			return ((Number)value).doubleValue();
		
		if(value instanceof String)
			return Double.valueOf((String)value);
		
		throw new IllegalArgumentException(value + " nao pode ser Double");
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
