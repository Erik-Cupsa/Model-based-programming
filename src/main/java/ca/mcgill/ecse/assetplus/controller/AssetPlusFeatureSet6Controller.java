package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.*;


import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
public class AssetPlusFeatureSet6Controller {

    /**
     * Delete an employee or a guest with provided email
     *
     * @author Ming Xuan Yue
     * @param email
     */
    public static void deleteEmployeeOrGuest(String email) {

        try{
            if(User.getWithEmail(email) != null){                 // if the user is not found in the system, then do not delete
                if (!"manager@ap.com".equals(User.getWithEmail(email).getEmail())){     // if the email belongs to manager, then do not delete
                    User.getWithEmail(email).delete();
                }
            }
        } catch(Exception e){
            e.printStackTrace(); // trace all exceptions
        }
    }


    /**
     * Create a list of all maintenance tickets with information specified by the transfer objects of maintenance tickets
     *
     * @author Ming Xuan Yue
     * @return a list of transfer objects of MaintenanceTicket
     */

    public static List<TOMaintenanceTicket> getTickets() {


        List<TOMaintenanceTicket> tickets = new ArrayList<TOMaintenanceTicket>();

        AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
        for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()){
            int id = ticket.getId();
            String description = ticket.getDescription();
            String raisedByEmail = ticket.getTicketRaiser().getEmail();
            List<TicketImage> ticketImages = ticket.getTicketImages();
            List<String> imageURLs = new ArrayList<String>();
            TOMaintenanceNote[] notes = new TOMaintenanceNote[ticket.getTicketImages().size()];
            String assetname = null;
            int expectedLifeSpanInDays = -1;
            int floorNumber = -1;
            int roomNumber = -1;



            if (!ticketImages.isEmpty()){                       // hasImages/not
                for (TicketImage ticketimage: ticketImages){
                    imageURLs.add(ticketimage.getImageURL());
                }
            }

            if (!ticket.getTicketNotes().isEmpty()){            // hasMaintenanceNotes/not
                for (int i = 0; i <ticket.getTicketImages().size(); i++){
                    notes[i] = new TOMaintenanceNote(ticket.getTicketNote(i).getDate(), ticket.getTicketNote(i).getDescription(), ticket.getTicketNote(i).getNoteTaker().getEmail());
                } // create TOMaintenanceNote then add to an array
            }
            if (ticket.hasAsset()){                             // hasAsset/not
                assetname = ticket.getAsset().getAssetType().getName();
                expectedLifeSpanInDays = ticket.getAsset().getAssetType().getExpectedLifeSpan();
                floorNumber = ticket.getAsset().getFloorNumber();
                roomNumber = ticket.getAsset().getRoomNumber();
                tickets.add(new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, assetname, expectedLifeSpanInDays, ticket.getAsset().getPurchaseDate(), floorNumber, roomNumber, imageURLs, notes));
            }else{            // if asset does not exist for a ticket, then
                TOMaintenanceTicket toTicket = new TOMaintenanceTicket(id, ticket.getRaisedOnDate(), description, raisedByEmail, assetname, expectedLifeSpanInDays, null, floorNumber, roomNumber, imageURLs, notes);
                tickets.add(toTicket);
            }
        }
        return tickets;         // returns all tickets
    }
}