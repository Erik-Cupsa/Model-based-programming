package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

import java.sql.Date;

public class AssetPlusFeatureSet7Controller {

    /**
     * adds a maintenance note to a specified maintenance ticket
     *
     * @param date        the maintenance note's date
     * @param description the maintenance note's description
     * @param ticketID    the id of the ticket to add the note to
     * @param email       the email of the note taker
     * @return returns a string explaining the errors if encountered, empty string if successful
     * @author Philippe Aprahamian
     */
    public static String addMaintenanceNote(Date date, String description, int ticketID,
                                            String email) {

        if (description == null || description.equals("")) {
            return "Ticket description cannot be empty";
        }
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
        if (ticket == null) {
            return "Ticket does not exist";
        }
        HotelStaff noteTaker = (HotelStaff) HotelStaff.getWithEmail(email);
        if (noteTaker == null) {
            return "Hotel staff does not exist";
        }
        MaintenanceNote newNote = new MaintenanceNote(date, description, ticket, noteTaker);
        ticket.addTicketNote(newNote);
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * updates a specified maintenance note
     *
     * @param ticketID       the id of the ticket that the target note is attached to
     * @param index          the index of the maintenance note inside the ticket's notes list
     * @param newDate        the new date of the maintenance note
     * @param newDescription the new description of the maintenance note
     * @param newEmail       the email of the new note taker
     * @return returns a string explaining the errors if encountered, empty string if successful
     * @author Philippe Aprahamian
     */
    // index starts at 0
    public static String updateMaintenanceNote(int ticketID, int index, Date newDate,
                                               String newDescription, String newEmail) {
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        if (newDescription == null || newDescription.equals("")) {
            return "Ticket description cannot be empty";
        }
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
        if (ticket == null) {
            return "Ticket does not exist";
        }
        MaintenanceNote targetNote;
        try {
            targetNote = ticket.getTicketNote(index);
        } catch (Exception e) {
            return "Note does not exist";
        }
        if (targetNote == null) {
            return "Note does not exist";
        }
        HotelStaff newNoteTaker = (HotelStaff) HotelStaff.getWithEmail(newEmail);
        if (newNoteTaker == null) {
            return "Hotel staff does not exist";
        }
        targetNote.setDate(newDate);
        targetNote.setDescription(newDescription);
        targetNote.setNoteTaker(newNoteTaker);
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * deletes a specified maintenance note
     *
     * @param ticketID the id of the ticket that the note to be deleted is attached to
     * @param index    the index of the maintenance note to be deleted inside the corresponding ticket's notes list
     * @author Philippe Aprahamian
     */
    // index starts at 0
    public static void deleteMaintenanceNote(int ticketID, int index) {
        try {
            MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
            MaintenanceNote targetNote = ticket.getTicketNote(index);
            targetNote.delete();
            AssetPlusPersistence.save();
        } catch (Exception e) {

        }
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
