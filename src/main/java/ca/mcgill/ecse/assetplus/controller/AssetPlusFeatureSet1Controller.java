package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.*;

/*
 * Provides controller methods for updating the manager password, and adding and updating guests and employees
 */

public class AssetPlusFeatureSet1Controller {
    private static AssetPlus app = AssetPlusApplication.getAssetPlus();

    /**
     * Validates a given email address.
     * @author Manuel Hanna
     * @param email Email to validate
     * @return String value, if non-empty then indicates an error in validation
     */
    private static String checkValidEmail(String email) {
        // email must not null
        if (email == null || email.length() == 0) {
            return "Email cannot be empty";
        }
        // email must not contain any spaces
        if (email.contains(" ")) {
            return "Email must not contain any spaces";
        }
        // email must contain some characters (anything is allowed except @), a @, some characters, a dot, and some characters
        if (!(email.indexOf("@") > 0
                && email.indexOf("@") == email.lastIndexOf("@")
                && email.indexOf("@") < email.lastIndexOf(".") - 1
                && email.lastIndexOf(".") < email.length() - 1)) {
            return "Invalid email";
        }
        // email cannot be manager@ap.com unless it is for the manager
        else if (email.equals("manager@ap.com")) {
            return "Email cannot be manager@ap.com";
        } else {
            return "";
        }
    }

    /**
     * Validates a given password.
     * @author Manuel Hanna
     * @param password password to validate
     * @param isManager boolean indicating whether password should be validated
     *  according to manager's password constraints
     * @return String value, if non-empty then indicates an error in validation
     */
    private static String checkValidPassword(String password, boolean isManager) {
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                containsUpperCase = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                containsLowerCase = true;
            }
        }

        // password must not be empty or null
        if ((password == null) || (password.length() == 0)) {
            return "Password cannot be empty";
        }
        // password must be at least four characters long
        else if (!(password.length() >= 4)) {
            return "Password must be at least four characters long";
        }
        // for each Manager:
        else if (isManager) {
            // password must contain a special character out of !#$
            if (!(
                    password.contains("!")
                            || password.contains("#")
                            || password.contains("$")
            )) {
                return "Password must contain one character out of !#$";
            }
            // password must contain an upper case character
            if (!containsUpperCase) {
                return "Password must contain one upper-case character";
            }
            // password must contain a lower case character
            if (!containsLowerCase) {
                return "Password must contain one lower-case character";
            }
        }

        return "";
    }

    /**
     * Checks whether an email is a valid employee email.
     * @author Manuel Hanna
     * @param email Email to be validated as employee email
     * @return Boolean, true if email is valid employee email
     */
    private static boolean isValidEmployeeEmail(String email) {
        return email.substring(email.length() - 7).equals("@ap.com");
    }

    /**
     * Validates a given name and phone number.
     * @author Manuel Hanna
     * @param name name to validate
     * @param phoneNumber phone number to validate
     * @return String value, if non-empty then indicates an error in validation
     */
    private static String checkValidIdentification(String name, String phoneNumber) {
        if (name == null) {
            return "Name must not null";
        } else if (phoneNumber == null) {
            return "Phone number must not null";
        } else {
            return "";
        }
    }

    /**
     * @param password New password of the manager
     * @return String value, if non-empty then indicates an error in the operation
     * @author Manuel Hanna
     */
    public static String updateManager(String password) {
        // for each Manager: email has to be manager@ap.com
        Manager manager = app.getManager();
        String passCheck = checkValidPassword(password, true);
        if (!(passCheck.equals(""))) {
            return passCheck;
        }
        manager.setPassword(password);
        return "";
    }

    /**
     * @param email       Email of the guest/employee to be added
     * @param password    Password of the guest/employee to be added
     * @param name        Name of the guest/employee to be added
     * @param phoneNumber Phone number of the guest/employee to be added
     * @param isEmployee  Boolean indicating if the new user is an employee
     * @return String value, if non-empty then indicates an error in the operation
     * @author Manuel Hanna
     */
    public static String addEmployeeOrGuest(String email, String password, String name, String phoneNumber,
                                            boolean isEmployee) {

        // check email, password, name, phone
        String emailCheck = checkValidEmail(email);
        String passCheck = checkValidPassword(password, false);
        String idCheck = checkValidIdentification(name, phoneNumber);
        if (!(emailCheck.equals(""))) {
            return emailCheck;
        } else if (!(passCheck.equals(""))) {
            return passCheck;
        } else if (!(idCheck.equals(""))) {
            return idCheck;
        }
        // Check if a guest already has this email
        if (User.getWithEmail(email) instanceof Guest) {
            return "Email already linked to an guest account";
        }
        // check if an employee already has this email
        else if (User.getWithEmail(email) instanceof Employee) {
            return "Email already linked to an employee account";
        }

        if (isEmployee) {
            // for each Employee: email domain has to be @ap.com
            if (!isValidEmployeeEmail(email)) {
                return "Email domain must be @ap.com";
            }
            app.addEmployee(email, name, password, phoneNumber);
        } else {
            // for each Guest: email domain cannot be @ap.com
            if (isValidEmployeeEmail(email)) {
                return "Email domain cannot be @ap.com";
            }
            app.addGuest(email, name, password, phoneNumber);
        }
        return "";
    }

    /**
     * @param email          The email of the employee or guest to be updated
     * @param newPassword    The new password of the employee/guest
     * @param newName        The new name of the employee/guest
     * @param newPhoneNumber The new phone number of the employee/guest
     * @return String value, if non-empty then indicates an error in the operation
     * @author Manuel Hanna
     */
    public static String updateEmployeeOrGuest(String email, String newPassword, String newName, String newPhoneNumber) {
        User user = User.getWithEmail(email);
        // check if user with this email actually exists...
        if (user == null) {
            return "User does not exist";
        }

        // general constraints...
        String passCheck = checkValidPassword(newPassword, false);
        if (!(passCheck.equals(""))) {
            return passCheck;
        }
        String idCheck = checkValidIdentification(newName, newPhoneNumber);
        if (!(idCheck.equals(""))) {
            return idCheck;
        }
        user.setPassword(newPassword);
        user.setName(newName);
        user.setPhoneNumber(newPhoneNumber);

        return "";
    }

}
