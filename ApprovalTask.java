package org.example;

public class ApprovalTask {
    WorkflowStep step;
    String approverId;
    String comments;
    boolean approved;

    public ApprovalTask(WorkflowStep step) {
        this.step = step;
        this.approved = false;
    }
}
