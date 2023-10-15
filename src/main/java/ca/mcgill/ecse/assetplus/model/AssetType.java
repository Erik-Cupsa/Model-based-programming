/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 78 "../../../../../AssetPlus.ump"
public class AssetType
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, AssetType> assettypesByName = new HashMap<String, AssetType>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AssetType Attributes
  private String name;
  private int expectedLifeSpan;

  //AssetType Associations
  private AssetPlus assetPlus;
  private List<SpecificAsset> specificAssets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AssetType(String aName, int aExpectedLifeSpan, AssetPlus aAssetPlus)
  {
    expectedLifeSpan = aExpectedLifeSpan;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create assetType due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    specificAssets = new ArrayList<SpecificAsset>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      assettypesByName.remove(anOldName);
    }
    assettypesByName.put(aName, this);
    return wasSet;
  }

  public boolean setExpectedLifeSpan(int aExpectedLifeSpan)
  {
    boolean wasSet = false;
    expectedLifeSpan = aExpectedLifeSpan;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static AssetType getWithName(String aName)
  {
    return assettypesByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getExpectedLifeSpan()
  {
    return expectedLifeSpan;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetMany */
  public SpecificAsset getSpecificAsset(int index)
  {
    SpecificAsset aSpecificAsset = specificAssets.get(index);
    return aSpecificAsset;
  }

  public List<SpecificAsset> getSpecificAssets()
  {
    List<SpecificAsset> newSpecificAssets = Collections.unmodifiableList(specificAssets);
    return newSpecificAssets;
  }

  public int numberOfSpecificAssets()
  {
    int number = specificAssets.size();
    return number;
  }

  public boolean hasSpecificAssets()
  {
    boolean has = specificAssets.size() > 0;
    return has;
  }

  public int indexOfSpecificAsset(SpecificAsset aSpecificAsset)
  {
    int index = specificAssets.indexOf(aSpecificAsset);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetPlus(AssetPlus aAssetPlus)
  {
    boolean wasSet = false;
    if (aAssetPlus == null)
    {
      return wasSet;
    }

    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = aAssetPlus;
    if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
    {
      existingAssetPlus.removeAssetType(this);
    }
    assetPlus.addAssetType(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSpecificAssets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public SpecificAsset addSpecificAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, AssetPlus aAssetPlus)
  {
    return new SpecificAsset(aAssetNumber, aFloorNumber, aRoomNumber, aPurchaseDate, aAssetPlus, this);
  }

  public boolean addSpecificAsset(SpecificAsset aSpecificAsset)
  {
    boolean wasAdded = false;
    if (specificAssets.contains(aSpecificAsset)) { return false; }
    AssetType existingAssetType = aSpecificAsset.getAssetType();
    boolean isNewAssetType = existingAssetType != null && !this.equals(existingAssetType);
    if (isNewAssetType)
    {
      aSpecificAsset.setAssetType(this);
    }
    else
    {
      specificAssets.add(aSpecificAsset);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSpecificAsset(SpecificAsset aSpecificAsset)
  {
    boolean wasRemoved = false;
    //Unable to remove aSpecificAsset, as it must always have a assetType
    if (!this.equals(aSpecificAsset.getAssetType()))
    {
      specificAssets.remove(aSpecificAsset);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSpecificAssetAt(SpecificAsset aSpecificAsset, int index)
  {  
    boolean wasAdded = false;
    if(addSpecificAsset(aSpecificAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificAssets()) { index = numberOfSpecificAssets() - 1; }
      specificAssets.remove(aSpecificAsset);
      specificAssets.add(index, aSpecificAsset);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSpecificAssetAt(SpecificAsset aSpecificAsset, int index)
  {
    boolean wasAdded = false;
    if(specificAssets.contains(aSpecificAsset))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificAssets()) { index = numberOfSpecificAssets() - 1; }
      specificAssets.remove(aSpecificAsset);
      specificAssets.add(index, aSpecificAsset);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSpecificAssetAt(aSpecificAsset, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    assettypesByName.remove(getName());
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeAssetType(this);
    }
    for(int i=specificAssets.size(); i > 0; i--)
    {
      SpecificAsset aSpecificAsset = specificAssets.get(i - 1);
      aSpecificAsset.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "expectedLifeSpan" + ":" + getExpectedLifeSpan()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null");
  }
}