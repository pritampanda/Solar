package org.example;

public class WorkflowStep {
    String stepName;
    String approverRole;
    String condition;

    public WorkflowStep(String stepName, String approverRole, String condition) {
        this.stepName = stepName;
        this.approverRole = approverRole;
        this.condition = condition;
    }
}
