package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet4Controller{
  // assetNumber -1 means that no asset is specified
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
    String email, int assetNumber) {
    AssetPlus assetplus = AssetPlusApplication.getAssetPlus();

    User user = User.getWithEmail(email);

    if (user == null){
      return "The ticket raiser does not exist";
    } else if (description == "" || description == null){
      return "Ticket description cannot be empty";
    }
    
    MaintenanceTicket newticket = new MaintenanceTicket(id, raisedOnDate, description, assetplus, user);

    if (assetNumber != -1){
      SpecificAsset asset = SpecificAsset.getWithAssetNumber(assetNumber);
      if (asset == null){
        return "The asset does not exist";
      }
      newticket.setAsset(asset);
    }


    return "";
  }

  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(id);
    if (ticket == null){
      return "The ticket does not exist";
    }

    User user = User.getWithEmail(newEmail); // temp fix to avoid controller errors

    if (user == null){
      return "The ticket raiser does not exist";
    } else if (newDescription == "" || newDescription == null){
      return "Ticket description cannot be empty";
    }

    if (newAssetNumber != -1){
      SpecificAsset asset = SpecificAsset.getWithAssetNumber(newAssetNumber);
      if (asset == null){
        return "The asset does not exist";
      }
      ticket.setAsset(asset);
    }

    ticket.setRaisedOnDate(newRaisedOnDate);
    ticket.setDescription(newDescription);
    ticket.setTicketRaiser(user);

    return "";
  }

  public static void deleteMaintenanceTicket(int id) {
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(id);
    ticket.delete();
  }

}
