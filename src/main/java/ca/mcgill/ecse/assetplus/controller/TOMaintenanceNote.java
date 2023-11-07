/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;

// line 29 "../../../../../AssetPlusTransferObjects.ump"
public class TOMaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOMaintenanceNote Attributes
  private Date date;
  private String description;
  private String noteTakerEmail;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOMaintenanceNote(Date aDate, String aDescription, String aNoteTakerEmail)
  {
    date = aDate;
    description = aDescription;
    noteTakerEmail = aNoteTakerEmail;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }

  public String getNoteTakerEmail()
  {
    return noteTakerEmail;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "noteTakerEmail" + ":" + getNoteTakerEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}