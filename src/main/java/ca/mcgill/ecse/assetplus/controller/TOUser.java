/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;

// line 36 "../../../../../../AssetPlusTransferObjects.ump"
public class TOUser {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //TOUser Attributes
    private String email;
    private String name;
    private String phoneNumber;
    private String password;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public TOUser(String aEmail, String aName, String aPhoneNumber, String aPassword) {
        email = aEmail;
        name = aName;
        phoneNumber = aPhoneNumber;
        password = aPassword;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void delete() {
    }


    public String toString() {
        return super.toString() + "[" +
                "email" + ":" + getEmail() + "," +
                "name" + ":" + getName() + "," +
                "phoneNumber" + ":" + getPhoneNumber() + "," +
                "password" + ":" + getPassword() + "]";
    }
}