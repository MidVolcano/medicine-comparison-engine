package com.mateom.clinicalData;

import org.springframework.web.bind.annotation.*;
import java.util.*;

import jakarta.validation.Valid;

@RestController
public class ClinicalDataController {

	private final ClinicalDataService clinicalDataService;

	public ClinicalDataController(ClinicalDataService clinicalDataService) {
		this.clinicalDataService = clinicalDataService;

	}

	@PostMapping("/api/reconcile/medication")
	public ReconciledMedication reconcile(@RequestHeader("x-api-key") String apiKey,
			@Valid @RequestBody MedicalInput input) {

		if (!apiKey.equals("mateom-secret")) {
			throw new RuntimeException("Invalid API Key");
		}

		return clinicalDataService.calculatePreffered(new ArrayList<>(input.getSources()), input.getPatientContext());
	}

	@PostMapping("/api/validate/data-quality")
	public DataQualityResponse validate(@RequestHeader("x-api-key") String apiKey,
			@RequestBody DataQualityInput input) {

		if (!apiKey.equals("mateom-secret")) {
			throw new RuntimeException("Invalid API Key");
		}

		return clinicalDataService.validateDataQuality(input);
	}

//	@PostMapping("/api/validate/data-quality")
//	public DataQualityResponse validate(@RequestBody DataQualityInput input){
//	    return clinicalDataService.validateDataQuality(input);
//	}
}
