package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

/**
 * This class provides controller methods for managing specific assets in the AssetPlus system.
 */
public class AssetPlusFeatureSet3Controller {

  /**
   * Controller method which adds a new specific asset to the system.
   * 
   * @author Erik Cupsa 261016180
   * @param assetNumber The asset number of the specific asset.
   * @param floorNumber The floor number of the specific asset.
   * @param roomNumber The room number of the specific asset.
   * @param purchaseDate The purchase date of the specific asset.
   * @param assetTypeName The name of the asset type. 
   * @return String value of a message indicating the result of the add operation.
   */
  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {
        //calling private helper method to validate inputs based on constraints
        if(!inputValidation(assetNumber, floorNumber, roomNumber, assetTypeName)){
          return "Invalid input";
        }

        AssetType assetType = AssetType.getWithName(assetTypeName); //getting the asset type by name
        if(assetType == null){ //if the asset type does not exist in the Asset Plus system
          return "Asset Type provided does not exist";
        }
        else{
          SpecificAsset newSpecificAsset = assetType.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType.getAssetPlus()); // 
          if(assetType.addSpecificAsset(newSpecificAsset)){ //New Specific Asset successfully added
            return "Specific Asset added successfully!";
          }
          else{ // Error adding new specific asset to system
            return "Error adding Specific Asset";
          }
        }
  }

  /**
   * Updates an existing specific asset in the system. 
   * 
   * @author Erik Cupsa 261016180
   * @param assetNumber The asset number of the specific asset to update.
   * @param newFloorNumber The new floor number. 
   * @param newRoomNumber The new room number. 
   * @param newPurchaseDate The new purchase date. 
   * @param newAssetTypeName The new name of the asset type
   * @return String value of a message indicating the result of the update operation.  
   */
  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        if(!inputValidation(assetNumber, newFloorNumber, newRoomNumber, newAssetTypeName)){ //calling private helper method to validate inputs based on constraints
          return "Invalid input";
        }
        AssetType assetType = AssetType.getWithName(newAssetTypeName); //getting the asset type by name
        if (assetType == null){ //if the asset type does not exist in the system
          return "Asset Type provided does not exist";
        }
        else{
          SpecificAsset toUpdate = SpecificAsset.getWithAssetNumber(assetNumber); //getting specific asset that is going to be updated
          if(toUpdate == null){ //if no such specific asset exists
            return "Specific Asset does not exist";
          }
          else{
            //setting all attributes of new asset
            boolean updateFloor = toUpdate.setFloorNumber(newFloorNumber);
            boolean updateRoom = toUpdate.setRoomNumber(newRoomNumber);
            boolean updateDate = toUpdate.setPurchaseDate(newPurchaseDate);
            AssetType oldAssetType = toUpdate.getAssetType();
            
            if (!assetType.equals(oldAssetType)) { //if asset type was changed
              oldAssetType.removeSpecificAsset(toUpdate);
              assetType.addSpecificAsset(toUpdate);
            }
            if(updateFloor && updateRoom && updateDate){ //if all attributes were successfully changed
              return "Specific Asset successfully updated";
            }
            else{
              return "Error updating Specific Asset";
            }
          }
        }
  }

  /**
   * Deletes a specific asset from the system.
   * 
   * @author Erik Cupsa 261016180
   * @param assetNumber The asset number of the specific asset to delete from the system.
   */
  public static void deleteSpecificAsset(int assetNumber) {
    if(assetNumber >= 1){ //if valid asset number
      SpecificAsset toDelete = SpecificAsset.getWithAssetNumber(assetNumber); //getting the specific asset we want to delete
      if(toDelete != null){ //if the specific asset exists in the system
        //deleting specific asset
        AssetType assetType = toDelete.getAssetType(); 
        assetType.removeSpecificAsset(toDelete);
        toDelete.delete();
      }
    }
  }

  /**
   * Validates all input parameters for adding or updating a specific asset.
   * @author Erik Cupsa 261016180
   * @param assetNumber The asset number.
   * @param floorNumber The floor number. 
   * @param roomNumber The room number. 
   * @param assetTypeName The name of the asset type
   * @return True if the input is valid and false otherwise
   */
  private static boolean inputValidation(int assetNumber, int floorNumber, int roomNumber, String assetTypeName){
        if(assetTypeName == "" || assetTypeName == null){ //invalid asset type name
          return false; 
        }
        if(assetNumber < 1){ // asset number must be >= 1
          return false; 
        }
        if(floorNumber < 0){ // floor number must be >= 0
          return false; 
        }
        if(roomNumber < -2){ // room number must be >= -1
          return false;
        }
        return true; 
      }
}
