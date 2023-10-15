/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 22 "../../../../../AssetPlus.ump"
public abstract class HotelStaff extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HotelStaff Associations
  private List<MaintenanceNote> maintenanceNotes;
  private List<MaintenanceTicket> maintenanceTasks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public HotelStaff(String aEmail, String aName, String aPassword, String aPhoneNumber)
  {
    super(aEmail, aName, aPassword, aPhoneNumber);
    maintenanceNotes = new ArrayList<MaintenanceNote>();
    maintenanceTasks = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  public MaintenanceTicket getMaintenanceTask(int index)
  {
    MaintenanceTicket aMaintenanceTask = maintenanceTasks.get(index);
    return aMaintenanceTask;
  }

  public List<MaintenanceTicket> getMaintenanceTasks()
  {
    List<MaintenanceTicket> newMaintenanceTasks = Collections.unmodifiableList(maintenanceTasks);
    return newMaintenanceTasks;
  }

  public int numberOfMaintenanceTasks()
  {
    int number = maintenanceTasks.size();
    return number;
  }

  public boolean hasMaintenanceTasks()
  {
    boolean has = maintenanceTasks.size() > 0;
    return has;
  }

  public int indexOfMaintenanceTask(MaintenanceTicket aMaintenanceTask)
  {
    int index = maintenanceTasks.indexOf(aMaintenanceTask);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMaintenanceNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceNote addMaintenanceNote(Date aDate, String aDescription, MaintenanceTicket aTicket)
  {
    return new MaintenanceNote(aDate, aDescription, aTicket, this);
  }

  public boolean addMaintenanceNote(MaintenanceNote aMaintenanceNote)
  {
    boolean wasAdded = false;
    if (maintenanceNotes.contains(aMaintenanceNote)) { return false; }
    HotelStaff existingNoteTaker = aMaintenanceNote.getNoteTaker();
    boolean isNewNoteTaker = existingNoteTaker != null && !this.equals(existingNoteTaker);
    if (isNewNoteTaker)
    {
      aMaintenanceNote.setNoteTaker(this);
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
    //Unable to remove aMaintenanceNote, as it must always have a noteTaker
    if (!this.equals(aMaintenanceNote.getNoteTaker()))
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
  public static int minimumNumberOfMaintenanceTasks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addMaintenanceTask(MaintenanceTicket aMaintenanceTask)
  {
    boolean wasAdded = false;
    if (maintenanceTasks.contains(aMaintenanceTask)) { return false; }
    HotelStaff existingTicketFixer = aMaintenanceTask.getTicketFixer();
    if (existingTicketFixer == null)
    {
      aMaintenanceTask.setTicketFixer(this);
    }
    else if (!this.equals(existingTicketFixer))
    {
      existingTicketFixer.removeMaintenanceTask(aMaintenanceTask);
      addMaintenanceTask(aMaintenanceTask);
    }
    else
    {
      maintenanceTasks.add(aMaintenanceTask);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMaintenanceTask(MaintenanceTicket aMaintenanceTask)
  {
    boolean wasRemoved = false;
    if (maintenanceTasks.contains(aMaintenanceTask))
    {
      maintenanceTasks.remove(aMaintenanceTask);
      aMaintenanceTask.setTicketFixer(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMaintenanceTaskAt(MaintenanceTicket aMaintenanceTask, int index)
  {  
    boolean wasAdded = false;
    if(addMaintenanceTask(aMaintenanceTask))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTasks()) { index = numberOfMaintenanceTasks() - 1; }
      maintenanceTasks.remove(aMaintenanceTask);
      maintenanceTasks.add(index, aMaintenanceTask);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMaintenanceTaskAt(MaintenanceTicket aMaintenanceTask, int index)
  {
    boolean wasAdded = false;
    if(maintenanceTasks.contains(aMaintenanceTask))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMaintenanceTasks()) { index = numberOfMaintenanceTasks() - 1; }
      maintenanceTasks.remove(aMaintenanceTask);
      maintenanceTasks.add(index, aMaintenanceTask);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMaintenanceTaskAt(aMaintenanceTask, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=maintenanceNotes.size(); i > 0; i--)
    {
      MaintenanceNote aMaintenanceNote = maintenanceNotes.get(i - 1);
      aMaintenanceNote.delete();
    }
    while( !maintenanceTasks.isEmpty() )
    {
      maintenanceTasks.get(0).setTicketFixer(null);
    }
    super.delete();
  }

}