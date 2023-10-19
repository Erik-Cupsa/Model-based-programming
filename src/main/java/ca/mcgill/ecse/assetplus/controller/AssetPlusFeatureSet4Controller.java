package ca.mcgill.ecse.assetplus.controller;

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
    // if 
    User user = User.getWithEmail(email);
    // TODO: Check feature for possible errors raised
    List<User> userList = new ArrayList<User>(assetplus.getEmployees());
    userList.addAll(assetplus.getGuests());
    userList.add(assetplus.getManager());

    for (User u: userList){
      if (u.getEmail() == email){
        user = u;
        break;
      }
    }
    
    MaintenanceTicket newticket = new MaintenanceTicket(id, raisedOnDate, description, assetplus, user);

    if (assetNumber != -1){
      SpecificAsset asset = assetplus.getSpecificAsset(assetNumber);
      newticket.setAsset(asset);
    }

    return "";
  }

  // newAssetNumber -1 means that no asset is specified
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
    AssetPlus assetplus = AssetPlusApplication.getAssetPlus();

    MaintenanceTicket ticket = assetplus.getMaintenanceTicket(id);

    User user = User.getWithEmail(email);
    List<User> userList = new ArrayList<User>(assetplus.getEmployees());
    userList.addAll(assetplus.getGuests());
    userList.add(assetplus.getManager());

    for (User u: userList){
      if (u.getEmail() == newEmail){
        user = u;
        break;
      }
    }

    ticket.setRaisedOnDate(newRaisedOnDate);
    ticket.setDescription(newDescription);
    ticket.setTicketRaiser(user);
    if(newAssetNumber != -1){
    ticket.setAsset(assetplus.getSpecificAsset(newAssetNumber));}

    return "";
  }

  public static void deleteMaintenanceTicket(int id) {
    AssetPlus assetplus = new AssetPlus();
    MaintenanceTicket ticket = assetplus.getMaintenanceTicket(id);
    ticket.delete();
  }

}