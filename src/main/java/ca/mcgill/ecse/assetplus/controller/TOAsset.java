package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;

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
  private String typeName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, String aTypeName)
  {
    assetNumber = aAssetNumber;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    purchaseDate = aPurchaseDate;
    typeName = aTypeName;
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

  public String getTypeName()
  {
    return typeName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "," +
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "," +
            "typeName" + ":" + getTypeName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}

