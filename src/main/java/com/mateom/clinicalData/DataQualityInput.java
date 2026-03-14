package com.mateom.clinicalData;

import java.util.*;

public class DataQualityInput {

    private Demographics demographics;
    private ArrayList<String> medications;
    private ArrayList<String> allergies;
    private ArrayList<String> conditions;
    private HashMap<String,String> vitalSigns;
    private String lastUpdated;

    public Demographics getDemographics() {
        return demographics;
    }

    public ArrayList<String> getMedications() {
        return medications;
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public HashMap<String,String> getVitalSigns() {
        return vitalSigns;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
