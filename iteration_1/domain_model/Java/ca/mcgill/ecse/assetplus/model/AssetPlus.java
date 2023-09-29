/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 3 "../../../../../../classes.ump"
public class AssetPlus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AssetPlus Associations
  private List<Location> locations;
  private List<AssetType> assetTypes;
  private List<User> users;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AssetPlus()
  {
    locations = new ArrayList<Location>();
    assetTypes = new ArrayList<AssetType>();
    users = new ArrayList<User>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Location getLocation(int index)
  {
    Location aLocation = locations.get(index);
    return aLocation;
  }

  public List<Location> getLocations()
  {
    List<Location> newLocations = Collections.unmodifiableList(locations);
    return newLocations;
  }

  public int numberOfLocations()
  {
    int number = locations.size();
    return number;
  }

  public boolean hasLocations()
  {
    boolean has = locations.size() > 0;
    return has;
  }

  public int indexOfLocation(Location aLocation)
  {
    int index = locations.indexOf(aLocation);
    return index;
  }
  /* Code from template association_GetMany */
  public AssetType getAssetType(int index)
  {
    AssetType aAssetType = assetTypes.get(index);
    return aAssetType;
  }

  public List<AssetType> getAssetTypes()
  {
    List<AssetType> newAssetTypes = Collections.unmodifiableList(assetTypes);
    return newAssetTypes;
  }

  public int numberOfAssetTypes()
  {
    int number = assetTypes.size();
    return number;
  }

  public boolean hasAssetTypes()
  {
    boolean has = assetTypes.size() > 0;
    return has;
  }

  public int indexOfAssetType(AssetType aAssetType)
  {
    int index = assetTypes.indexOf(aAssetType);
    return index;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLocations()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addLocation(Location aLocation)
  {
    boolean wasAdded = false;
    if (locations.contains(aLocation)) { return false; }
    AssetPlus existingAssetPlus = aLocation.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aLocation.setAssetPlus(this);
    }
    else
    {
      locations.add(aLocation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLocation(Location aLocation)
  {
    boolean wasRemoved = false;
    //Unable to remove aLocation, as it must always have a assetPlus
    if (!this.equals(aLocation.getAssetPlus()))
    {
      locations.remove(aLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLocationAt(Location aLocation, int index)
  {  
    boolean wasAdded = false;
    if(addLocation(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLocationAt(Location aLocation, int index)
  {
    boolean wasAdded = false;
    if(locations.contains(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLocationAt(aLocation, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssetTypes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public AssetType addAssetType(String aCategory, int aExpectedLifespan)
  {
    return new AssetType(aCategory, aExpectedLifespan, this);
  }

  public boolean addAssetType(AssetType aAssetType)
  {
    boolean wasAdded = false;
    if (assetTypes.contains(aAssetType)) { return false; }
    AssetPlus existingAssetPlus = aAssetType.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aAssetType.setAssetPlus(this);
    }
    else
    {
      assetTypes.add(aAssetType);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssetType(AssetType aAssetType)
  {
    boolean wasRemoved = false;
    //Unable to remove aAssetType, as it must always have a assetPlus
    if (!this.equals(aAssetType.getAssetPlus()))
    {
      assetTypes.remove(aAssetType);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssetTypeAt(AssetType aAssetType, int index)
  {  
    boolean wasAdded = false;
    if(addAssetType(aAssetType))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssetTypes()) { index = numberOfAssetTypes() - 1; }
      assetTypes.remove(aAssetType);
      assetTypes.add(index, aAssetType);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssetTypeAt(AssetType aAssetType, int index)
  {
    boolean wasAdded = false;
    if(assetTypes.contains(aAssetType))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssetTypes()) { index = numberOfAssetTypes() - 1; }
      assetTypes.remove(aAssetType);
      assetTypes.add(index, aAssetType);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssetTypeAt(aAssetType, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    AssetPlus existingAssetPlus = aUser.getAssetPlus();
    boolean isNewAssetPlus = existingAssetPlus != null && !this.equals(existingAssetPlus);
    if (isNewAssetPlus)
    {
      aUser.setAssetPlus(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a assetPlus
    if (!this.equals(aUser.getAssetPlus()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (locations.size() > 0)
    {
      Location aLocation = locations.get(locations.size() - 1);
      aLocation.delete();
      locations.remove(aLocation);
    }
    
    while (assetTypes.size() > 0)
    {
      AssetType aAssetType = assetTypes.get(assetTypes.size() - 1);
      aAssetType.delete();
      assetTypes.remove(aAssetType);
    }
    
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
  }

}