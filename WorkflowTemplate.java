package org.example;
import java.util.List;

public class WorkflowTemplate {
    String templateId;
    List<WorkflowStep> steps;
    String tenantId;

    public boolean validate(){
        if(steps == null || steps.size() == 0) return false;

        for(WorkflowStep step : steps){
            if(step.approverRole == null || step.stepName == null) return false;
        }
        return true;
    }

    public WorkflowTemplate(String templateId, List<WorkflowStep> steps, String tenantId) {
        this.templateId = templateId;
        this.steps = steps;
        this.tenantId = tenantId;
    }
}
