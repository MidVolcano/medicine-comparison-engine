package com.mateom.clinicalData;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ClinicalDataApplicationTests {

    @Test
    void testSelectMostReliableSource() {

        ClinicalDataService service = new ClinicalDataService(null);

        ArrayList<MedicalSources> sources = new ArrayList<>();

        sources.add(new MedicalSources(
                "Hospital",
                "Metformin 1000mg",
                "2024-01-01",
                "low"
        ));

        sources.add(new MedicalSources(
                "Primary Care",
                "Metformin 500mg",
                "2026-01-01",
                "high"
        ));

        HashMap<String,Integer> labs = new HashMap<>();
        labs.put("eGFR",45);

        PatientContext context =
                new PatientContext(67,new ArrayList<>(List.of("Diabetes")),labs);

        ReconciledMedication result =
                service.calculatePreffered(sources,context);

        assertEquals("Metformin 500mg", result.getReconciledMedication());
    }

    @Test
    void testConfidenceScoreCalculation(){

        ClinicalDataService service = new ClinicalDataService(null);

        ArrayList<MedicalSources> sources = new ArrayList<>();

        sources.add(new MedicalSources(
                "Pharmacy",
                "Metformin 500mg",
                "2026-01-01",
                "high"
        ));

        HashMap<String,Integer> labs = new HashMap<>();
        labs.put("eGFR",45);

        PatientContext context =
                new PatientContext(60,new ArrayList<>(List.of("Diabetes")),labs);

        ReconciledMedication result =
                service.calculatePreffered(sources,context);

        assertTrue(result.getConfidenceScore() > 0);
    }

    @Test
    void testClinicalSafetyChec(){

        ClinicalDataService service = new ClinicalDataService(null);

        ArrayList<MedicalSources> sources = new ArrayList<>();

        sources.add(new MedicalSources(
                "Hospital",
                "Metformin 500mg",
                "2026-01-01",
                "high"
        ));

        HashMap<String,Integer> labs = new HashMap<>();
        labs.put("eGFR",45);

        PatientContext context =
                new PatientContext(60,new ArrayList<>(List.of("Diabetes")),labs);

        ReconciledMedication result =
                service.calculatePreffered(sources,context);

        assertEquals("PASSED", result.getClinicalSafetyCheck());
    }

    @Test
    void testImplausibleBloodPressure(){

        ClinicalDataService service = new ClinicalDataService(null);

        DataQualityInput input = new DataQualityInput();

        HashMap<String,String> vitals = new HashMap<>();
        vitals.put("blood_pressure","340/180");

        input.setVitalSigns(vitals);

        DataQualityResponse response =
                service.validateDataQuality(input);

        assertTrue(response.getOverallScore() < 100);
    }

    @Test
    void testCompletenessCalculation(){

        ClinicalDataService service = new ClinicalDataService(null);

        DataQualityInput input = new DataQualityInput();

        input.setConditions(new ArrayList<>(List.of("Diabetes")));

        DataQualityResponse response =
                service.validateDataQuality(input);

        assertTrue(response.getOverallScore() < 100);
    }
}