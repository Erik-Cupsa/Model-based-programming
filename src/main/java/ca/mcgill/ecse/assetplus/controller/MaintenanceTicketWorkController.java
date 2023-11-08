package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.Status;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;

public class MaintenanceTicketWorkController {
  public static String assignStaffToTicket(int ticketId, HotelStaff staff, PriorityLevel aPriority,TimeEstimate aTimeToResolve, Boolean requiresApproval) {
    // constraints (look at feature file)
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    if (ticket == null) {
      return "Maintenance ticket does not exist.";
    }

    Status status = ticket.getStatus();

    if (status.equals(Status.Assigned)) {
      return "The maintenance ticket is already assigned.";
    }
    if (status.equals(Status.Resolved)) {
      return "Cannot assign a maintenance ticket which is resolved.";
    }
    if (status.equals(Status.Closed)) {
      return "Cannot assign a maintenance ticket which is closed.";
    }
    if (status.equals(Status.Assigned)) {
      return "Cannot assign a maintenance ticket which is in progress.";
    }
   
    ticket.assign(staff, aPriority, aTimeToResolve, requiresApproval);
    return "";
  }

  public static String startWorkOnTicket(/*  */) {
    return "TODO";
  }

  public static String completeWorkOnTicket(/*  */) {
    return "TODO";
  }

  public static String approveWorkOnTicket(/*  */) {
    return "TODO";
  }

  public static String disapproveWorkOnTicket(int ticketId, Date date, String reason) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    if (ticket == null) {
      return "Maintenance ticket does not exist.";
    }

    Status status = ticket.getStatus();

    if (status.equals(Status.Open)) {
      return "Cannot disapprove a maintenance ticket which is open. ";
    }
    if (status.equals(Status.Assigned)) {
      return "Cannot disapprove a maintenance ticket which is resolved.";
    }
    if (status.equals(Status.Closed)) {
      return "Cannot disapprove a maintenance ticket which is closed.";
    }
    if (status.equals(Status.Assigned)) {
      return "Cannot disapprove a maintenance ticket which is in progress.";
    }

    ticket.rejectFix(date, reason);
    return "";
  }
}
