package com.mateom.clinicalData;

import java.util.*;

public class MedicalSources {
	private String system;
	private String medication;
	private String lastUpdated;
	private String sourceReliability;

	public MedicalSources() {

	}

	public MedicalSources(String system, String medication, String lastUpdated, String sourceReliability) {
		this.system = system;
		this.medication = medication;
		this.lastUpdated = lastUpdated;
		this.sourceReliability = sourceReliability;
	}

	public String getSystem() {
		return system;
	}

	public String getMedication() {
		return medication;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public String getSourceReliability() {
		return sourceReliability;
	}

}
