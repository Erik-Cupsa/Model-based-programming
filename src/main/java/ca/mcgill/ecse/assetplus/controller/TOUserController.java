package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class TOUserController {
  public static List<TOUser> getUsers(){
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    List<TOUser> users = new ArrayList<TOUser>();

    for (Employee employee : assetPlus.getEmployees()){
      String name = employee.getName();
      String email = employee.getEmail();
      String phoneNumber = employee.getPhoneNumber();
      String password = employee.getPassword();
      users.add(new TOUser(email, name, phoneNumber, password));
    }

    for (Guest guest : assetPlus.getGuests()){
      String name = guest.getName();
      String email = guest.getEmail();
      String phoneNumber = guest.getPhoneNumber();
      String password = guest.getPassword();
      users.add(new TOUser(email, name, phoneNumber, password));
    }
    Manager manager = assetPlus.getManager();
    users.add(new TOUser(manager.getEmail(), manager.getName(), manager.getPhoneNumber(), manager.getPassword()));

    return users;
  }
}
