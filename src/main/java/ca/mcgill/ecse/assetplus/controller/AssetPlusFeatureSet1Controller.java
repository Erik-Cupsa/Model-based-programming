package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.*;

// TODO: Add JavaDocs for each function
// TODO: Make sure that AssetPlus is accessed through getAssetPlus()

public class AssetPlusFeatureSet1Controller {
  private static Boolean isValidEmail(String email) {
    return (
   // email must not contain any spaces
      !email.contains(" ")
   // email must contain some characters (anything is allowed except @), a @, some characters, a dot, and some characters
      && email.indexOf("@") > 0
      && email.indexOf("@") == email.lastIndexOf("@")
      && email.indexOf("@") < email.lastIndexOf(".") - 1
      && email.lastIndexOf(".") < email.length() - 1
    );
  }

  private static Boolean isValidPassword(String password) {
    return (
   // password must not be empty or null
      !(password == null) && !(password.length() == 0)
   // password must be at least four characters long
      && password.length() >= 4
   // password must contain a special character out of !#$
      && (
        password.contains("!")
        || password.contains("#")
        || password.contains("$")
      )
   // password must contain an upper case character
      && !password.toUpperCase().equals(password)
   // password must contain a lower case character
      && !password.toLowerCase().equals(password)
    );
  }

  private static boolean isValidIdentification(String name, String phoneNumber) {
    return (
   // name must not null
      !(name == null)
   // phoneNumber must not null
      && !(phoneNumber == null)
    );
  }

  private static boolean isValidEmployeeEmail(String email) {
    return email.substring(email.length() - 7).equals("@ap.com"); 
  }

  public static String updateManager(String password) {
   // for each Manager: email has to be manager@ap.com
    Manager manager = (Manager) User.getWithEmail("manager@ap.com");
    if (!isValidPassword(password)) {
      return "invalid password";
    }
    manager.setPassword(password);
    return "successful password change";
  }

  public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
        boolean isEmployee) {
    AssetPlus app = AssetPlusApplication.getAssetPlus();
    if (!(isValidEmail(email) && isValidPassword(password) && isValidIdentification(name, phoneNumber))) {
      return "Invalid credentials";
    }
    // Check if someone already has this email
    if (User.getWithEmail(email) != null) {
      return "A user already has this email!";
    }
   // email cannot be manager@ap.com unless it is for the manager
    if (email.equals("manager@ap.com")) {
      return "Email cannot be set to manager's email";
    }
    if (isEmployee) {
     // for each Employee: email domain has to be @ap.com
      if (!isValidEmployeeEmail(email)) {
        return "Invalid employee email";
      }
      app.addEmployee(email, name, password, phoneNumber);
    }
    else {
   // for each Guest: email domain cannot be @ap.com
      if (isValidEmployeeEmail(email)) {
        return "Guest email cannot be set to an employee email";
      }
      app.addGuest(email, name, password, phoneNumber);
    }
    return "account added successfully";
  }

  public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
    User user = User.getWithEmail(email);
    // check if user with this email actually exists...
    if (user == null) {
      return "This user doesn't exist!";
    }

    // general constraints...
    if (!isValidPassword(newPassword)) {
      return "New password is invalid";
    }
    else if (!isValidIdentification(newName, newPhoneNumber)) {
      return "New name or phone number is invalid";
    }
    if (user instanceof Employee) {
     // for each Employee: email domain has to be @ap.com
      if (!isValidEmployeeEmail(email)) {
        return "Invalid employee email. If you get this message then that means you already have an employee"
        + " that does not have a valid employee email!";
      }
    }
    else if (user instanceof Guest) {
   // for each Guest: email domain cannot be @ap.com
      if (isValidEmployeeEmail(email)) {
        return "Guest email cannot be sent to an employee email. If you get this message then that means" 
        + " you already have a guest that has a valid employee email!";
      }
    }
    user.setPassword(newPassword);
    user.setName(newName);
    user.setPhoneNumber(newPhoneNumber);

    return "user updated successfully";
  }

}
