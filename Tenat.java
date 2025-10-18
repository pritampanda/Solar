package org.example;

import java.util.ArrayList;
import java.util.List;

public class Tenat {
     String tenatId;
     List<WorkflowTemplate> templates;

    public Tenat(String tenatId) {
        this.tenatId = tenatId;
        this.templates = new ArrayList<>();
    }
    public void addTemplate(WorkflowTemplate template){
        templates.add(template);
    }
}
