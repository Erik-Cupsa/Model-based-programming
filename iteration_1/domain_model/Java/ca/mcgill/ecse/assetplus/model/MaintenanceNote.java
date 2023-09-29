/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 75 "../../../../../../classes.ump"
public class MaintenanceNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceNote Attributes
  private boolean resolved;
  private boolean hasManagerApproval;
  private String description;
  private Date creationDate;

  //MaintenanceNote Associations
  private MaintenanceTicket maintenanceTicket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceNote(boolean aResolved, String aDescription, Date aCreationDate, MaintenanceTicket aMaintenanceTicket)
  {
    resolved = aResolved;
    hasManagerApproval = false;
    description = aDescription;
    creationDate = aCreationDate;
    boolean didAddMaintenanceTicket = setMaintenanceTicket(aMaintenanceTicket);
    if (!didAddMaintenanceTicket)
    {
      throw new RuntimeException("Unable to create maintenanceNote due to maintenanceTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setResolved(boolean aResolved)
  {
    boolean wasSet = false;
    resolved = aResolved;
    wasSet = true;
    return wasSet;
  }

  public boolean setHasManagerApproval(boolean aHasManagerApproval)
  {
    boolean wasSet = false;
    hasManagerApproval = aHasManagerApproval;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreationDate(Date aCreationDate)
  {
    boolean wasSet = false;
    creationDate = aCreationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean getResolved()
  {
    return resolved;
  }

  public boolean getHasManagerApproval()
  {
    return hasManagerApproval;
  }

  public String getDescription()
  {
    return description;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isResolved()
  {
    return resolved;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHasManagerApproval()
  {
    return hasManagerApproval;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getMaintenanceTicket()
  {
    return maintenanceTicket;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMaintenanceTicket(MaintenanceTicket aMaintenanceTicket)
  {
    boolean wasSet = false;
    if (aMaintenanceTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingMaintenanceTicket = maintenanceTicket;
    maintenanceTicket = aMaintenanceTicket;
    if (existingMaintenanceTicket != null && !existingMaintenanceTicket.equals(aMaintenanceTicket))
    {
      existingMaintenanceTicket.removeMaintenanceNote(this);
    }
    maintenanceTicket.addMaintenanceNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderMaintenanceTicket = maintenanceTicket;
    this.maintenanceTicket = null;
    if(placeholderMaintenanceTicket != null)
    {
      placeholderMaintenanceTicket.removeMaintenanceNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "resolved" + ":" + getResolved()+ "," +
            "hasManagerApproval" + ":" + getHasManagerApproval()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "creationDate" + "=" + (getCreationDate() != null ? !getCreationDate().equals(this)  ? getCreationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "maintenanceTicket = "+(getMaintenanceTicket()!=null?Integer.toHexString(System.identityHashCode(getMaintenanceTicket())):"null");
  }
}