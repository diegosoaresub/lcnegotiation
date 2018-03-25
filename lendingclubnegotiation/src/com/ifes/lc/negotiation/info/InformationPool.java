package com.ifes.lc.negotiation.info;

import java.util.HashMap;
import java.util.Map;

public class InformationPool {
	
	private Map<String, Information> info = new HashMap<>();

	public Map<String, Information> getInfo() {
		return info;
	}
	
	public void add(String name, Information inf) {
		info.put(name, inf);
	}
	
	public Information get(String name) {
		return info.get(name);
	}
}
