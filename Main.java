package org.example;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RequestMetadata md = new RequestMetadata();
        md.requesterId = "user123";
        md.category = "Travel";
        md.tenantId = "TenantA";
        md.amount = 1500;
        md.description = "Flight to Bangalore";

        List<WorkflowStep> steps = new ArrayList<>();
        steps.add(new WorkflowStep("Manager Review", "Manager", null));
        steps.add(new WorkflowStep("Finance Review", "Finance", "amount > 1000"));

        WorkflowTemplate template = new WorkflowTemplate("templateA1", steps, "TenantA");

        Request request = new Request("req001", md, template);

        // Happy path
        request.actOnStep("manager1", "Manager", "TenantA", "Looks good", true);
        request.actOnStep("finance1", "Finance", "TenantA", "Budget ok", true);

        System.out.println(request.summary());


        Request request2 = new Request("req002", md, template);

        //Wrong tenant
        try {
            request2.actOnStep("manager2", "Manager", "TenantB", "Wrong tenant", true);
        } catch (SecurityException e) {
            System.out.println("Multi Tenant not allowed: " + e.getMessage());
        }
        Request request3 = new Request("req002", md, template);

        //Wrong role
        try {
            request3.actOnStep("financeX", "Legal", "TenantA", "Wrong role trying to act", true);
        } catch (SecurityException e) {
            System.out.println("Blocked invalid role: " + e.getMessage());
        }
    }
}