package com.mateom.clinicalData;

import java.util.*;

public class PatientContext {

	private Integer age;
	private ArrayList<String> conditions;
	private HashMap<String, Integer> recentLabs;

	public PatientContext() {

	}

	public PatientContext(Integer age, ArrayList<String> conditions, HashMap<String, Integer> recent_labs) {
		this.age = age;
		this.conditions = conditions;
		this.recentLabs = recent_labs;
	}

	public Integer getAge() {
		return age;
	}

	public ArrayList<String> getConditions() {
		return conditions;
	}

	public HashMap<String, Integer> getRecentLabs() {
		return recentLabs;
	}

}
