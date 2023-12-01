package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;

public class TOMaintenanceTicketController {
    public static PriorityLevel stringPriority(String s) {
        PriorityLevel priority = PriorityLevel.valueOf(s);
        return priority;
    }

    public static TimeEstimate stringTimeEstimate(String str) {
        TimeEstimate timeEstimate = TimeEstimate.valueOf(str);
        return timeEstimate;
    }
}

