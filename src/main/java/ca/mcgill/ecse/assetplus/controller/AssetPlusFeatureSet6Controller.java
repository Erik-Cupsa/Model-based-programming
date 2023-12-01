package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

import java.util.ArrayList;
import java.util.List;

public class AssetPlusFeatureSet6Controller {

    /**
     * Delete an employee or a guest with provided email
     *
     * @param email
     * @author Ming Xuan Yue
     */
    public static void deleteEmployeeOrGuest(String email) {

        try {

            if (User.getWithEmail(email) != null) {                 // if the user is not found in the system, then do not delete
                if (!"manager@ap.com".equals(User.getWithEmail(email).getEmail())) {     // if the email belongs to manager, then do not delete
                    User.getWithEmail(email).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // trace all exceptions
        }
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


    /**
     * Create a list of all maintenance tickets with information specified by the transfer objects of maintenance tickets
     *
     * @return a list of transfer objects of MaintenanceTicket
     * @author Ming Xuan Yue
     */

    public static List<TOMaintenanceTicket> getTickets() {


        List<TOMaintenanceTicket> tickets = new ArrayList<TOMaintenanceTicket>();

        AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
        for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()) {
            int id = ticket.getId();
            String description = ticket.getDescription();
            String raisedByEmail = ticket.getTicketRaiser().getEmail();
            List<TicketImage> ticketImages = ticket.getTicketImages();
            List<String> imageURLs = new ArrayList<String>();
            TOMaintenanceNote[] notes = new TOMaintenanceNote[ticket.getTicketNotes().size()];
            String assetname = null;
            int expectedLifeSpanInDays = -1;
            int floorNumber = -1;
            int roomNumber = -1;
            String aStatus = ticket.getStatusFullName();
            String aFixedbyEmail = null;
            String aTimeToResolve = null;
            String aPriority = null;
            boolean aApprovalRequired = false;


            if (!ticketImages.isEmpty()) {                       // hasImages/not
                for (TicketImage ticketimage : ticketImages) {
                    imageURLs.add(ticketimage.getImageURL());
                }
            }

            if (!ticket.getTicketNotes().isEmpty()) {            // hasMaintenanceNotes/not
                for (int i = 0; i < ticket.getTicketNotes().size(); i++) {
                    notes[i] = new TOMaintenanceNote(ticket.getTicketNote(i).getDate(), ticket.getTicketNote(i).getDescription(), ticket.getTicketNote(i).getNoteTaker().getEmail());
                } // create TOMaintenanceNote then add to an array
            }
            if (ticket.hasAsset()) {
                assetname = ticket.getAsset().getAssetType().getName();
                expectedLifeSpanInDays = ticket.getAsset().getAssetType().getExpectedLifeSpan();
                floorNumber = ticket.getAsset().getFloorNumber();
                roomNumber = ticket.getAsset().getRoomNumber();                             // hasAsset/not
                if (ticket.hasTicketFixer()) {       // if TicketFixer exists
                    aFixedbyEmail = ticket.getTicketFixer().getEmail();
                    aTimeToResolve = ticket.getTimeToResolve().toString();
                    aPriority = ticket.getPriority().toString();
                    aApprovalRequired = ticket.hasFixApprover();
                    tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, aStatus, aFixedbyEmail, aTimeToResolve, aPriority, aApprovalRequired, assetname, expectedLifeSpanInDays, ticket.getAsset().getPurchaseDate(), floorNumber, roomNumber, imageURLs, notes));
                } else {
                    tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, aStatus, aFixedbyEmail, aTimeToResolve, aPriority, aApprovalRequired, assetname, expectedLifeSpanInDays, ticket.getAsset().getPurchaseDate(), floorNumber, roomNumber, imageURLs, notes));
                }
            } else {      // if asset does not exist for a ticket, then
                if (ticket.hasTicketFixer()) {
                    aFixedbyEmail = ticket.getTicketFixer().getEmail();
                    aTimeToResolve = ticket.getTimeToResolve().toString();
                    aPriority = ticket.getPriority().toString();
                    aApprovalRequired = ticket.hasFixApprover();
                    tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, aStatus, aFixedbyEmail, aTimeToResolve, aPriority, aApprovalRequired, assetname, expectedLifeSpanInDays, null, floorNumber, roomNumber, imageURLs, notes));
                } else {
                    tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, aStatus, aFixedbyEmail, aTimeToResolve, aPriority, aApprovalRequired, assetname, expectedLifeSpanInDays, null, floorNumber, roomNumber, imageURLs, notes));
                }
            }
        }
        return tickets;         // returns all tickets
    }
}