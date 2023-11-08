package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.PriorityLevel;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.Status;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket.TimeEstimate;

public class MaintenanceTicketWorkController {
  public static String assignStaffToTicket(int ticketId, String employeeEmail, TimeEstimate aTimeToResolve, PriorityLevel aPriority, Boolean requiresApproval) {
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
    if (status.equals(Status.InProgress)) {
      return "Cannot assign a maintenance ticket which is in progress.";
    }
   
    HotelStaff staff = (HotelStaff) HotelStaff.getWithEmail(employeeEmail);
    if (staff == null) {
      return "Staff to assign does not exist.";
    }

    ticket.assign(staff, aPriority, aTimeToResolve, requiresApproval);
    return "";
  }

  public static String startWorkOnTicket(int ticketId) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    if (ticket == null) {
      return "Maintenance ticket does not exist.";
    }

    Status status = ticket.getStatus();
    if (status.equals(Status.Open)) {
      return "Cannot start a maintenance ticket which is open.";
    }
    if (status.equals(Status.Resolved)) {
      return "Cannot start a maintenance ticket which is resolved.";
    }
    if (status.equals(Status.Closed)) {
      return "Cannot start a maintenance ticket which is closed.";
    }
    if (status.equals(Status.InProgress)) {
      return "The maintenance ticket is already in progress.";
    }

    ticket.startWork();
    return "";
  }

  public static String completeWorkOnTicket(int ticketId) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    if (ticket == null) {
      return "Maintenance ticket does not exist.";
    }

    Status status = ticket.getStatus();
    if (status.equals(Status.Open)) {
      return "Cannot complete a maintenance ticket which is open.";
    }
    if (status.equals(Status.Assigned)) {
      return "Cannot complete a maintenance ticket which is resolved.";
    }
    if (status.equals(Status.Closed)) {
      return "The maintenance ticket is already closed.";
    }
    if (status.equals(Status.Resolved)) {
      return "The maintenance ticket is already resolved.";
    }

    ticket.completeFix();
    return "TODO";
  }

  public static String approveWorkOnTicket(int ticketId) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    if (ticket == null) {
      return "Maintenance ticket does not exist.";
    }

    Status status = ticket.getStatus();
    if (status.equals(Status.Open)) {
      return "Cannot approve a maintenance ticket which is open.";
    }
    if (status.equals(Status.Resolved)) {
      return "Cannot disapprove a maintenance ticket which is resolved.";
    }
    if (status.equals(Status.Closed)) {
      return "The maintenance ticket is already closed.";
    }
    if (status.equals(Status.Assigned)) {
      return "Cannot disapprove a maintenance ticket which is assigned.";
    }

    ticket.acceptFix();
    return "";
  }

  public static String disapproveWorkOnTicket(int ticketId, Date date, String description) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketId);
    if (ticket == null) {
      return "Maintenance ticket does not exist.";
    }

    Status status = ticket.getStatus();

    if (status.equals(Status.Open)) {
      return "Cannot disapprove a maintenance ticket which is open.";
    }
    if (status.equals(Status.Assigned)) {
      return "Cannot disapprove a maintenance ticket which is assigned.";
    }
    if (status.equals(Status.Closed)) {
      return "Cannot disapprove a maintenance ticket which is closed.";
    }
    if (status.equals(Status.InProgress)) {
      return "Cannot disapprove a maintenance ticket which is in progress.";
    }

    ticket.rejectFix(date, description);
    return "";
  }
}
