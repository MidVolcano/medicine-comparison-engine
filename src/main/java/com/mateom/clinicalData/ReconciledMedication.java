package com.mateom.clinicalData;

import java.util.List;

public class ReconciledMedication {

	private String reconciledMedication;
	private double confidenceScore;
	private String reasoning;
	private List<String> recommendedActions;
	private String clinicalSafetyCheck;

	public ReconciledMedication() {

	}

	public ReconciledMedication(String reconciledMedication, double confidenceScore, String reasoning,
			List<String> recommendedActionsString, String clinicalSafetyCheck) {

		this.reconciledMedication = reconciledMedication;
		this.confidenceScore = confidenceScore;
		this.reasoning = reasoning;
		this.recommendedActions = recommendedActionsString;
		this.clinicalSafetyCheck = clinicalSafetyCheck;
	}

	public String getReconciledMedication() {
		return reconciledMedication;
	}

	public double getConfidenceScore() {
		return confidenceScore;
	}

	public String getReasoning() {
		return reasoning;
	}

	public List<String> getRecommendedActions() {
		return recommendedActions;
	}

	public String getClinicalSafetyCheck() {
		return clinicalSafetyCheck;
	}
}