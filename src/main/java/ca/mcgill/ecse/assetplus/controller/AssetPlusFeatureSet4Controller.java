package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

public class AssetPlusFeatureSet4Controller{

  // assetNumber -1 means that no asset is specified
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
    String email, int assetNumber) {
    AssetPlus assetplus = AssetPlusApplication.getAssetPlus();

    User user = User.getWithEmail(email);

    List<User> userList = new ArrayList<User>(assetplus.getEmployees());
    userList.addAll(assetplus.getGuests());
    userList.add(assetplus.getManager());

    for (User u: userList){
      if (u.getEmail() == email){
        user = u;
        break;
      }
    }

    if (user == null){
      return "The ticket raiser does not exist";
    } else if (description == ""){
      return "Ticket description cannot be empty";
    }

    
    MaintenanceTicket newticket = new MaintenanceTicket(id, raisedOnDate, description, assetplus, user);


    if (assetNumber != -1){
      boolean assetFound = false;
      for (SpecificAsset k: assetplus.getSpecificAssets()){
        if (k.getAssetNumber() == assetNumber){
          newticket.setAsset(k);
          assetFound = true;
          break;
        }
      }
      if (!assetFound){
        return "The asset does not exist";
      }
    }


    return "";
  }

  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    AssetPlus assetplus = AssetPlusApplication.getAssetPlus();

    MaintenanceTicket ticket;

    boolean ticketFound = false;
    for (MaintenanceTicket l: assetplus.getMaintenanceTickets()){
      if (l.getId() == id){
        ticket = l;
        ticketFound = true;
        break;
      }
    }
    if (!ticketFound){
      return "The ticket does not exist";
    }

    User user = User.getWithEmail(newEmail); // temp fix to avoid controller errors
    List<User> userList = new ArrayList<User>(assetplus.getEmployees());
    userList.addAll(assetplus.getGuests());
    userList.add(assetplus.getManager());

    for (User u: userList){
      if (u.getEmail() == newEmail){
        user = u;
        break;
      }
    }

    if (user == null){
      return "The ticket raiser does not exist";
    } else if (newDescription == ""){
      return "Ticket description cannot be empty";
    }

    ticket.setRaisedOnDate(newRaisedOnDate);
    ticket.setDescription(newDescription);
    ticket.setTicketRaiser(user);

    if (newAssetNumber != -1){
      boolean assetFound = false;
      for (SpecificAsset k: assetplus.getSpecificAssets()){
        if (k.getAssetNumber() == newAssetNumber){
          ticket.setAsset(k);
          assetFound = true;
          break;
        }
      }
      if (!assetFound){
        return "The asset does not exist";
      }
    }

    return "";
  }

  public static void deleteMaintenanceTicket(int id) {
    AssetPlus assetplus = AssetPlusApplication.getAssetPlus();

    MaintenanceTicket ticket;

    boolean ticketFound = false;
    for (MaintenanceTicket l: assetplus.getMaintenanceTickets()){
      if (l.getId() == id){
        l.delete();
        break;
      }
    }
  }

}
