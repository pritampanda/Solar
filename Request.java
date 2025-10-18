package org.example;

import java.util.ArrayList;
import java.util.List;

public class Request {
    String requestId;
    RequestMetadata metadata;
    WorkflowTemplate template;
    List<ApprovalTask> approvalTasks;
    int currentStepIndex;
    WorkflowStatus status;

    public Request(String requestId, RequestMetadata metadata, WorkflowTemplate template) {
        this.requestId = requestId;
        this.metadata = metadata;
        this.template = template;
        this.approvalTasks = new ArrayList<>();
        this.currentStepIndex = 0;
        this.status = WorkflowStatus.PENDING;
        for(WorkflowStep step : template.steps){
            approvalTasks.add(new ApprovalTask(step));
        }
    }

    public boolean actOnStep(String approverId, String approverRole, String tenantId, String comments, boolean approve){
        //No action needed
        if(status == WorkflowStatus.APPROVED || status == WorkflowStatus.REJECTED){
            return false;
        }
        if(!this.metadata.tenantId.equals(tenantId)){
            throw new SecurityException("Multiple Tenant not allowed");
        }

        ApprovalTask task = approvalTasks.get(currentStepIndex);

        if (!task.step.approverRole.equalsIgnoreCase(approverRole)) {
            throw new SecurityException("Approver role mismatch");
        }

        task.approverId = approverId;
        task.comments = comments;
        task.approved = approve;
        status = WorkflowStatus.IN_REVIEW;

        //If rejected then
        if(!approve){
            status = WorkflowStatus.REJECTED;
            return true;
        }
        //move to next step
        currentStepIndex++;
        if(currentStepIndex >= approvalTasks.size()){
            status = WorkflowStatus.APPROVED;
        }
        return true;
    }

    public WorkflowStatus getStatus(){
        return status;
    }

    public String summary(){
        StringBuilder sb = new StringBuilder();
        sb.append("Request ").append(requestId).append(" is ").append(status).append("\n");
        for (ApprovalTask task : approvalTasks){
            sb.append("Step : ").append(task.step.stepName)
                    .append(" by ").append(task.approverId == null ? "N/A" : task.approverId)
                    .append(" approved : ").append(task.approved)
                    .append(" comments : ").append(task.comments == null ? "" : task.comments).append("\n");
        }

        return sb.toString();
    }
}
