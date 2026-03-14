package com.mateom.clinicalData;

public class IssueDetected {

    private String field;
    private String issue;
    private String severity;

    public IssueDetected(String field, String issue, String severity){
        this.field = field;
        this.issue = issue;
        this.severity = severity;
    }

    public String getField(){
        return field;
    }

    public String getIssue(){
        return issue;
    }

    public String getSeverity(){
        return severity;
    }
}