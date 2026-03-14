package com.mateom.clinicalData;

import java.util.*;

public class DataQualityResponse {

	private Integer overallScore;
	private HashMap<String, Integer> breakdown;
	private ArrayList<IssueDetected> issuesDetected;

	public DataQualityResponse(int overallScore, HashMap<String, Integer> breakdown,
			ArrayList<IssueDetected> issuesDetected) {

		this.overallScore = overallScore;
		this.breakdown = breakdown;
		this.issuesDetected = issuesDetected;
	}

	public int getOverallScore() {
		return overallScore;
	}

	public HashMap<String, Integer> getBreakdown() {
		return breakdown;
	}

	public ArrayList<IssueDetected> getIssuesDetected() {
		return issuesDetected;
	}
}
