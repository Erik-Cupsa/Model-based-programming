package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

import java.sql.Date;

public class AssetPlusFeatureSet4Controller {

    /**
     * Creates a new maintenance ticket and adds it to AssetPlus.
     *
     * @param id           The ID number of the new ticket, must be unique.
     * @param raisedOnDate Date the new ticket is raised on.
     * @param description  Description of the new ticket, cannot be empty.
     * @param email        Email of the user who raised the ticket.
     * @param assetNumber  ID number of the asset the ticket is about, -1 for no asset
     * @return String with error if there is an error, else empty string.
     * @author Anders Cairns Woodruff
     */
    public static String addMaintenanceTicket(
            int id, Date raisedOnDate, String description, String email, int assetNumber) {


        AssetPlus assetplus = AssetPlusApplication.getAssetPlus();
        User user = User.getWithEmail(email);

        if (user == null) {
            return "The ticket raiser does not exist";
        } else if (description == null || description.isEmpty()) {
            return "Ticket description cannot be empty";
        } else if (MaintenanceTicket.getWithId(id) != null) {
            return "Ticket id already exists";
        }

        MaintenanceTicket newticket =
                new MaintenanceTicket(id, raisedOnDate, description, assetplus, user);
        if (assetNumber != -1) {
            SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
            if (asset == null) {
                newticket.delete();
                return "The asset does not exist";
            }
            newticket.setAsset(asset);
        }
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * Edits the fields of an existing maintenance ticket.
     *
     * @param id              The ID of the ticket to edit.
     * @param newRaisedOnDate The new date the ticket is set to be raised on.
     * @param newDescription  The new description of the ticket, must not be empty.
     * @param newEmail        The email of the new user that is set to have raised the ticket.
     * @param newAssetNumber  The id number of the new asset that the ticket is set to be about, -1 for
     *                        none.
     * @return String with errors if there are any, else empty string.
     * @author Anders Cairns Woodruff
     */
    public static String updateMaintenanceTicket(
            int id, Date newRaisedOnDate, String newDescription, String newEmail, int newAssetNumber) {
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(id);
        if (ticket == null) {
            return "The ticket does not exist";
        }
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        User user = User.getWithEmail(newEmail); // temp fix to avoid controller errors

        if (user == null) {
            return "The ticket raiser does not exist";
        } else if (newDescription == null || newDescription.isEmpty()) {
            return "Ticket description cannot be empty";
        }

        if (newAssetNumber != -1) {
            SpecificAsset asset = SpecificAsset.getWithAssetNumber(newAssetNumber);
            if (asset == null) {
                return "The asset does not exist";
            }
            ticket.setAsset(asset);
        } else {
            ticket.setAsset(null);
        }

        ticket.setRaisedOnDate(newRaisedOnDate);
        ticket.setDescription(newDescription);
        ticket.setTicketRaiser(user);
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }

    /**
     * Deletes an existing maintenance ticket.
     *
     * @param id The ID number of the ticket to be deleted.
     * @author Anders Cairns Woodruff
     */
    public static void deleteMaintenanceTicket(int id) {

        MaintenanceTicket ticket = MaintenanceTicket.getWithId(id);
        if (ticket == null) {
            return;
        }
        ticket.delete();
        try {
            AssetPlusPersistence.save();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
