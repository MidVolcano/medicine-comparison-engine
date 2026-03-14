package com.mateom.clinicalData;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ClinicalDataService {
	private final AiHelp aiHelp;

	public ClinicalDataService(AiHelp aiHelp) {
		this.aiHelp = aiHelp;
	}

	public ReconciledMedication calculatePreffered(ArrayList<MedicalSources> sources, PatientContext context) {

		HashMap<String, Integer> reliabilityScores = new HashMap<>();
		reliabilityScores.put("high", 6);
		reliabilityScores.put("medium", 4);
		reliabilityScores.put("low", 2);

		int reliability = 0; // 8 points max, 6 for reliability, 4 for date
		int usedIndex = 0;

		for (int i = 0; i < sources.size(); i++) {
			String lastUpdated = sources.get(i).getLastUpdated();

			if (lastUpdated == null) {
				continue;
			}

			String[] compareDates = lastUpdated.split("-");
			int dates = 0;

			if (Integer.valueOf(compareDates[0]) >= 2026) {
				dates = 4;
			} else if (Integer.valueOf(compareDates[0]) > 2025) {
				dates = 3;
			} else if (Integer.valueOf(compareDates[0]) > 2024) {
				dates = 2;
			} else {
				dates = 1;
			}

			String currentReliability = sources.get(i).getSourceReliability();
			int reliabilityCalculation = reliabilityScores.get(currentReliability);

			if (reliabilityCalculation + dates > reliability) {
				reliability = reliabilityCalculation + dates;
				usedIndex = i;
			}

		}
		String recommendedMedication = sources.get(usedIndex).getMedication();
		double confidenceScore = reliability / 10.0;

		String labName = context.getRecentLabs().keySet().toArray()[0].toString();
		Integer labValue = context.getRecentLabs().get(labName);

		String reasoning = aiHelp.generateReasoning(recommendedMedication, context.getConditions().get(0), labName,
				labValue);

		List<String> recommendedActions = new ArrayList<>();
		recommendedActions.add(
				"Update " + sources.get(usedIndex).getSystem() + " to " + sources.get(usedIndex).getMedication() + ".");
		recommendedActions.add("Verify with pharmacist that correct dose is being filled");

		String clinicalSafetyCheck = "PASSED";
		if (reliability < 5) {
			clinicalSafetyCheck = "FAILED";
		}

		ReconciledMedication myMedication = new ReconciledMedication(recommendedMedication, confidenceScore, reasoning,
				recommendedActions, clinicalSafetyCheck);

		return myMedication;

	}

	public DataQualityResponse validateDataQuality(DataQualityInput input) {

		int completeness = 0;
		int accuracy = 100;
		int timeliness = 100;
		int clinicalPlausibility = 100;

		ArrayList<IssueDetected> issues = new ArrayList<>();

		// COMPLETENESS CHECK

		int filledFields = 0;
		int totalFields = 5;

		if (input.getDemographics() != null) {
			filledFields++;
		}

		if (input.getMedications() != null && input.getMedications().size() > 0) {
			filledFields++;
		}

		if (input.getConditions() != null && input.getConditions().size() > 0) {
			filledFields++;
		}

		if (input.getVitalSigns() != null) {
			filledFields++;
		}

		if (input.getAllergies() != null) {
			filledFields++;
		}

		completeness = (filledFields * 100) / totalFields;

		if (input.getAllergies() != null && input.getAllergies().size() == 0) {
			issues.add(new IssueDetected("allergies", "No allergies documented - likely incomplete", "medium"));
		}

		// CLINICAL PLAUSIBILITY

		if (input.getVitalSigns() != null) {

			String bp = input.getVitalSigns().get("blood_pressure");

			if (bp != null) {

				String[] values = bp.split("/");

				int systolic = Integer.valueOf(values[0]);
				int diastolic = Integer.valueOf(values[1]);

				if (systolic > 300 || diastolic > 200) {

					clinicalPlausibility = 40;

					issues.add(new IssueDetected("vital_signs.blood_pressure",
							"Blood pressure " + bp + " is physiologically implausible", "high"));
				}
			}
		}

		// TIMELINESS

		String lastUpdated = input.getLastUpdated();

		if (lastUpdated != null) {

			String[] parts = lastUpdated.split("-");

			int year = Integer.valueOf(parts[0]);

			if (year < 2025) {

				timeliness = 70;

				issues.add(new IssueDetected("last_updated", "Data is 7+ months old", "medium"));
			}
		}

		// ACCURACY

		if (input.getDemographics() != null) {

			String gender = input.getDemographics().getGender();

			if (!(gender.equals("M") || gender.equals("F"))) {
				accuracy = 50;
			}
		}

		// OVERALL SCORE

		int overallScore = (completeness + accuracy + timeliness + clinicalPlausibility) / 4;

		HashMap<String, Integer> breakdown = new HashMap<>();
		breakdown.put("completeness", completeness);
		breakdown.put("accuracy", accuracy);
		breakdown.put("timeliness", timeliness);
		breakdown.put("clinical_plausibility", clinicalPlausibility);

		DataQualityResponse response = new DataQualityResponse(overallScore, breakdown, issues);

		return response;
	}

}
