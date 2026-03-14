package com.mateom.clinicalData;

import java.util.List;

public class MedicalInput {

    private PatientContext patientContext;
    private List<MedicalSources> sources;

    public PatientContext getPatientContext() {
        return patientContext;
    }

    public void setPatientContext(PatientContext patientContext) {
        this.patientContext = patientContext;
    }

    public List<MedicalSources> getSources() {
        return sources;
    }

    public void setSources(List<MedicalSources> sources) {
        this.sources = sources;
    }
}