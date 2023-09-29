/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;
import java.util.*;

// line 36 "../../../../../../classes.ump"
public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Urgency { Urgent, Normal, Low }
  public enum TimeEstimate { LessThanADay, OneToThreeDays, ThreeToSevenDays, OneToThreeWeeks, ThreeOrMoreWeeks }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextTicketNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceTicket Attributes
  private Urgency urgency;
  private boolean requiresManagerApproval;
  private TimeEstimate timeEstimate;
  private Date dateOpened;

  //Autounique Attributes
  private int ticketNumber;

  //MaintenanceTicket Associations
  private User ticketInitiator;
  private Manager reviewer;
  private List<Employee> maintenanceStaff;
  private Asset asset;
  private List<MaintenanceNote> maintenanceNotes;
  private List<Image> images;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceTicket(Date aDateOpened, User aTicketInitiator, Manager aReviewer, Asset aAsset)
  {
    requiresManagerApproval = false;
    dateOpened = aDateOpened;
    ticketNumber = nextTicketNumber++;
    boolean didAddTicketInitiator = setTicketInitiator(aTicketInitiator);
    if (!didAddTicketInitiator)
    {
      throw new RuntimeException("Unable to create ticketsOpened due to ticketInitiator. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddReviewer = setReviewer(aReviewer);
    if (!didAddReviewer)
    {
      throw new RuntimeException("Unable to create ticketsToApprove due to reviewer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    maintenanceStaff = new ArrayList<Employee>();
    boolean didAddAsset = setAsset(aAsset);
    if (!didAddAsset)
    {
      throw new RuntimeException("Unable to create maintenanceTicket due to asset. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    maintenanceNotes = new ArrayList<MaintenanceNote>();
    images = new ArrayList<Image>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUrgency(Urgency aUrgency)
  {
    boolean wasSet = false;
    urgency = aUrgency;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequiresManagerApproval(boolean aRequiresManagerApproval)
  {
    boolean wasSet = false;
    requiresManagerApproval = aRequiresManagerApproval;
    wasSet = true;
    return wasSet;
  }

  public boolean setTimeEstimate(TimeEstimate aTimeEstimate)
  {
    boolean wasSet = false;
    timeEstimate = aTimeEstimate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDateOpened(Date aDateOpened)
  {
    boolean wasSet = false;
    dateOpened = aDateOpened;
    wasSet = true;
    return wasSet;
  }

  public Urgency getUrgency()
  {
    return urgency;
  }

  public boolean getRequiresManagerApproval()
  {
    return requiresManagerApproval;
  }

  public TimeEstimate getTimeEstimate()
  {
    return timeEstimate;
  }

  public Date getDateOpened()
  {
    return dateOpened;
  }

  public int getTicketNumber()
  {
    return ticketNumber;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isRequiresManagerApproval()
  {
    return requiresManagerApproval;
  }
  /* Code from template association_GetOne */
  public User getTicketInitiator()
  {
    return ticketInitiator;
  }
  /* Code from template association_GetOne */
  public Manager getReviewer()
  {
    return reviewer;
  }
  /* Code from template association_GetMany */
  public Employee getMaintenanceStaff(int index)
  {
    Employee aMaintenanceStaff = maintenanceStaff.get(index);
    return aMaintenanceStaff;
  }

  public List<Employee> getMaintenanceStaff()
  {
    List<Employee> newMaintenanceStaff = Collections.unmodifiableList(maintenanceStaff);
    return newMaintenanceStaff;
  }

  public int numberOfMaintenanceStaff()
  {
    int number = maintenanceStaff.size();
    return number;
  }

  public boolean hasMaintenanceStaff()
  {
    boolean has = maintenanceStaff.size() > 0;
    return has;
  }

  public int indexOfMaintenanceStaff(Employee aMaintenanceStaff)
  {
    int index = maintenanceStaff.indexOf(aMaintenanceStaff);
    return index;
  }
  /* Code from template association_GetOne */
  public Asset getAsset()
  {
    return asset;
  }
  /* Code from template association_GetMany */
  public MaintenanceNote getMaintenanceNote(int index)
  {
    MaintenanceNote aMaintenanceNote = maintenanceNotes.get(index);
    return aMaintenanceNote;
  }

  public List<MaintenanceNote> getMaintenanceNotes()
  {
    List<MaintenanceNote> newMaintenanceNotes = Collections.unmodifiableList(maintenanceNotes);
    return newMaintenanceNotes;
  }

  public int numberOfMaintenanceNotes()
  {
    int number = maintenanceNotes.size();
    return number;
  }

  public boolean hasMaintenanceNotes()
  {
    boolean has = maintenanceNotes.size() > 0;
    return has;
  }

  public int indexOfMaintenanceNote(MaintenanceNote aMaintenanceNote)
  {
    int index = maintenanceNotes.indexOf(aMaintenanceNote);
    return index;
  }
  /* Code from template association_GetMany */
  public Image getImage(int index)
  {
    Image aImage = images.get(index);
    return aImage;
  }

  public List<Image> getImages()
  {
    List<Image> newImages = Collections.unmodifiableList(images);
    return newImages;
  }

  public int numberOfImages()
  {
    int number = images.size();
    return number;
  }

  public boolean hasImages()
  {
    boolean has = images.size() > 0;
    return has;
  }

  public int indexOfImage(Image aImage)
  {
    int index = images.indexOf(aImage);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTicketInitiator(User aTicketInitiator)
  {
    boolean wasSet = false;
    if (aTicketInitiator == null)
    {
      return wasSet;
    }

    User existingTicketInitiator = ticketInitiator;
    ticketInitiator = aTicketInitiator;
    if (existingTicketInitiator != null && !existingTicketInitiator.equals(aTicketInitiator))
    {
      existingTicketInitiator.removeTicketsOpened(this);
    }
    ticketInitiator.addTicketsOpened(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setReviewer(Manager aReviewer)
  {
    boolean wasSet = false;
    if (aReviewer == null)
    {
      return wasSet;
    }

    Manager existingReviewer = reviewer;
    reviewer = aReviewer;
    if (existingReviewer != null && !existingReviewer.equals(aReviewer))
    {
      existingReviewer.removeTicketsToApprove(this);
    }
    reviewer.addTicketsToApprove(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceStaff()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addMaintenanceStaff(Employee aMaintenanceStaff)
  {
    boolean wasAdded = false;
    if (maintenanceStaff.contains(aMaintenanceStaff)) { return false; }
    maintenanceStaff.add(aMaintenanceStaff);
    if (aMaintenanceStaff.indexOfMaintenanceTask(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMaintenanceStaff.addMaintenanceTask(this);
      if (!wasAdded)
      {
        maintenanceStaff.remove(aMaintenanceStaff);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeMaintenanceStaff(Employee aMaintenanceStaff)
  {
    boolean wasRemoved = false;
    if (!maintenanceStaff.contains(aMaintenanceStaff))
    {
      return wasRemoved;
    }

    int oldIndex = maintenanceStaff.indexOf(aMaintenanceStaff);
    maintenanceStaff.remove(oldIndex);
    if (aMaintenanceStaff.indexOfMaintenanceTask(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMaintenanceStaff.removeMaintenanceTask(this);
      if (!wasRemoved)
      {
        maintenanceStaff.add(oldIndex,aMaintenanceStaff);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceStaffAt(Employee aMaintenanceStaff, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceStaff(aMaintenanceStaff))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceStaff()) { index = numberOfMaintenanceStaff() - 1; }
      maintenanceStaff.remove(aMaintenanceStaff);
      maintenanceStaff.add(index, aMaintenanceStaff);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceStaffAt(Employee aMaintenanceStaff, int index)
  {
    boolean wasAdded = false;
    if(maintenanceStaff.contains(aMaintenanceStaff))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceStaff()) { index = numberOfMaintenanceStaff() - 1; }
      maintenanceStaff.remove(aMaintenanceStaff);
      maintenanceStaff.add(index, aMaintenanceStaff);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceStaffAt(aMaintenanceStaff, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAsset(Asset aAsset)
  {
    boolean wasSet = false;
    if (aAsset == null)
    {
      return wasSet;
    }

    Asset existingAsset = asset;
    asset = aAsset;
    if (existingAsset != null && !existingAsset.equals(aAsset))
    {
      existingAsset.removeMaintenanceTicket(this);
    }
    asset.addMaintenanceTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceNote addMaintenanceNote(boolean aResolved, String aDescription, Date aCreationDate)
  {
    return new MaintenanceNote(aResolved, aDescription, aCreationDate, this);
  }

  public boolean addMaintenanceNote(MaintenanceNote aMaintenanceNote)
  {
    boolean wasAdded = false;
    if (maintenanceNotes.contains(aMaintenanceNote)) { return false; }
    MaintenanceTicket existingMaintenanceTicket = aMaintenanceNote.getMaintenanceTicket();
    boolean isNewMaintenanceTicket = existingMaintenanceTicket != null && !this.equals(existingMaintenanceTicket);
    if (isNewMaintenanceTicket)
    {
      aMaintenanceNote.setMaintenanceTicket(this);
    }
    else
    {
      maintenanceNotes.add(aMaintenanceNote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMaintenanceNote(MaintenanceNote aMaintenanceNote)
  {
    boolean wasRemoved = false;
    //Unable to remove aMaintenanceNote, as it must always have a maintenanceTicket
    if (!this.equals(aMaintenanceNote.getMaintenanceTicket()))
    {
      maintenanceNotes.remove(aMaintenanceNote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceNoteAt(MaintenanceNote aMaintenanceNote, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceNote(aMaintenanceNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceNotes()) { index = numberOfMaintenanceNotes() - 1; }
      maintenanceNotes.remove(aMaintenanceNote);
      maintenanceNotes.add(index, aMaintenanceNote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceNoteAt(MaintenanceNote aMaintenanceNote, int index)
  {
    boolean wasAdded = false;
    if(maintenanceNotes.contains(aMaintenanceNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceNotes()) { index = numberOfMaintenanceNotes() - 1; }
      maintenanceNotes.remove(aMaintenanceNote);
      maintenanceNotes.add(index, aMaintenanceNote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceNoteAt(aMaintenanceNote, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfImages()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Image addImage(String aUrl)
  {
    return new Image(aUrl, this);
  }

  public boolean addImage(Image aImage)
  {
    boolean wasAdded = false;
    if (images.contains(aImage)) { return false; }
    MaintenanceTicket existingMaintenanceTicket = aImage.getMaintenanceTicket();
    boolean isNewMaintenanceTicket = existingMaintenanceTicket != null && !this.equals(existingMaintenanceTicket);
    if (isNewMaintenanceTicket)
    {
      aImage.setMaintenanceTicket(this);
    }
    else
    {
      images.add(aImage);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeImage(Image aImage)
  {
    boolean wasRemoved = false;
    //Unable to remove aImage, as it must always have a maintenanceTicket
    if (!this.equals(aImage.getMaintenanceTicket()))
    {
      images.remove(aImage);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addImageAt(Image aImage, int index)
  {  
    boolean wasAdded = false;
    if(addImage(aImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfImages()) { index = numberOfImages() - 1; }
      images.remove(aImage);
      images.add(index, aImage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveImageAt(Image aImage, int index)
  {
    boolean wasAdded = false;
    if(images.contains(aImage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfImages()) { index = numberOfImages() - 1; }
      images.remove(aImage);
      images.add(index, aImage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addImageAt(aImage, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    User placeholderTicketInitiator = ticketInitiator;
    this.ticketInitiator = null;
    if(placeholderTicketInitiator != null)
    {
      placeholderTicketInitiator.removeTicketsOpened(this);
    }
    Manager placeholderReviewer = reviewer;
    this.reviewer = null;
    if(placeholderReviewer != null)
    {
      placeholderReviewer.removeTicketsToApprove(this);
    }
    ArrayList<Employee> copyOfMaintenanceStaff = new ArrayList<Employee>(maintenanceStaff);
    maintenanceStaff.clear();
    for(Employee aMaintenanceStaff : copyOfMaintenanceStaff)
    {
      aMaintenanceStaff.removeMaintenanceTask(this);
    }
    Asset placeholderAsset = asset;
    this.asset = null;
    if(placeholderAsset != null)
    {
      placeholderAsset.removeMaintenanceTicket(this);
    }
    while (maintenanceNotes.size() > 0)
    {
      MaintenanceNote aMaintenanceNote = maintenanceNotes.get(maintenanceNotes.size() - 1);
      aMaintenanceNote.delete();
      maintenanceNotes.remove(aMaintenanceNote);
    }
    
    while (images.size() > 0)
    {
      Image aImage = images.get(images.size() - 1);
      aImage.delete();
      images.remove(aImage);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "ticketNumber" + ":" + getTicketNumber()+ "," +
            "requiresManagerApproval" + ":" + getRequiresManagerApproval()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "urgency" + "=" + (getUrgency() != null ? !getUrgency().equals(this)  ? getUrgency().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeEstimate" + "=" + (getTimeEstimate() != null ? !getTimeEstimate().equals(this)  ? getTimeEstimate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dateOpened" + "=" + (getDateOpened() != null ? !getDateOpened().equals(this)  ? getDateOpened().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticketInitiator = "+(getTicketInitiator()!=null?Integer.toHexString(System.identityHashCode(getTicketInitiator())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "reviewer = "+(getReviewer()!=null?Integer.toHexString(System.identityHashCode(getReviewer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "asset = "+(getAsset()!=null?Integer.toHexString(System.identityHashCode(getAsset())):"null");
  }
}