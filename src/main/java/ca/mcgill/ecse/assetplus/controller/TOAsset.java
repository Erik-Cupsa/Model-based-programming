package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;

import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

// line 43 "../../../../../../model.ump"
// line 71 "../../../../../../model.ump"
public class TOAsset
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAsset Attributes
  private int assetNumber;
  private int floorNumber;
  private int roomNumber;
  private Date purchaseDate;
  private TOAssetType assetType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, TOAssetType aAssetType)
  {
    assetNumber = aAssetNumber;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    purchaseDate = aPurchaseDate;
    assetType = aAssetType;
    try{
      AssetPlusPersistence.save();
    }catch(RuntimeException e){
       e.printStackTrace();
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getAssetNumber()
  {
    return assetNumber;
  }

  public int getFloorNumber()
  {
    return floorNumber;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  public TOAssetType getAssetType()
  {
    return assetType;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "," +
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetType" + "=" + (getAssetType() != null ? !getAssetType().equals(this)  ? getAssetType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}

