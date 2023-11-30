package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import java.sql.Date;

/**
 * This class provides controller methods for managing specific assets in the AssetPlus system.
 */
public class AssetPlusFeatureSet3Controller {

    /**
     * Controller method which adds a new specific asset to the system.
     *
     * @param assetNumber   The asset number of the specific asset.
     * @param floorNumber   The floor number of the specific asset.
     * @param roomNumber    The room number of the specific asset.
     * @param purchaseDate  The purchase date of the specific asset.
     * @param assetTypeName The name of the asset type.
     * @return String value of a message indicating the result of the add operation.
     * @author Erik Cupsa (@Erik-Cupsa)
     */
    public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
                                          Date purchaseDate, String assetTypeName) {
        //calling private helper method to validate inputs based on constraints
        String testing = inputValidation(assetNumber, floorNumber, roomNumber, assetTypeName);
        if(!testing.equals("Correct")) { //if error found
            return testing; // return error
        }

        AssetType assetType = AssetType.getWithName(assetTypeName); //getting the asset type by name

        if (assetType == null) { //if the asset type does not exist in the Asset Plus system
            return "The asset type does not exist";
        } else {
            SpecificAsset newSpecificAsset = assetType.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType.getAssetPlus()); //
            if (newSpecificAsset!=null) { //New Specific Asset successfully added
                try{
                    AssetPlusPersistence.save();
                }catch(RuntimeException e){
                    return e.getMessage();
                }
                return "";
            } else { // Error adding new specific asset to system
                return "Error adding Specific Asset";
            }
        }

    }

    /**
     * Updates an existing specific asset in the system.
     *
     * @param assetNumber      The asset number of the specific asset to update.
     * @param newFloorNumber   The new floor number.
     * @param newRoomNumber    The new room number.
     * @param newPurchaseDate  The new purchase date.
     * @param newAssetTypeName The new name of the asset type
     * @return String value of a message indicating the result of the update operation.
     * @author Erik Cupsa (@Erik-Cupsa)
     */
    public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
                                             Date newPurchaseDate, String newAssetTypeName) {
        String testing = inputValidation(assetNumber, newFloorNumber, newRoomNumber, newAssetTypeName); //calling private helper method to validate inputs based on constraints

        if(!testing.equals("Correct")) { // if error found
            return testing; //return String error
        }

        AssetType assetType = AssetType.getWithName(newAssetTypeName); //getting the asset type by name

        if (assetType == null) { //if the asset type does not exist in the system
            return "The asset type does not exist";
        } else {
            SpecificAsset toUpdate = SpecificAsset.getWithAssetNumber(assetNumber); //getting specific asset that is going to be updated
            if (toUpdate == null) { //if no such specific asset exists
                return "Specific Asset does not exist";
            } else {
                //setting all attributes of new asset
                boolean updateFloor = toUpdate.setFloorNumber(newFloorNumber);
                boolean updateRoom = toUpdate.setRoomNumber(newRoomNumber);
                boolean updateDate = toUpdate.setPurchaseDate(newPurchaseDate);
                AssetType oldAssetType = toUpdate.getAssetType();

                if (updateFloor && updateRoom && updateDate) { //if all attributes were successfully changed

                    if (!assetType.equals(oldAssetType)) { //if asset type was changed
                        oldAssetType.removeSpecificAsset(toUpdate);
                        assetType.addSpecificAsset(toUpdate);
                    }

                    try{
                        AssetPlusPersistence.save();
                    }catch(RuntimeException e){
                        return e.getMessage();
                    }
                    return "";
                } else {
                    return "Error updating Specific Asset";
                }

            }
        }
    }

    /**
     * Deletes a specific asset from the system.
     *
     * @param assetNumber The asset number of the specific asset to delete from the system.
     * @author Erik Cupsa (@Erik-Cupsa)
     */
    public static void deleteSpecificAsset(int assetNumber) {

        if (assetNumber >= 1) { //if valid asset number
            SpecificAsset toDelete = SpecificAsset.getWithAssetNumber(assetNumber); //getting the specific asset we want to delete
            if (toDelete != null) { //if the specific asset exists in the system
                //deleting specific asset
                AssetType assetType = toDelete.getAssetType();
                assetType.removeSpecificAsset(toDelete);
                toDelete.delete();
            }
        }
        try{
            AssetPlusPersistence.save();
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

  /**
   * Validates all input parameters for adding or updating a specific asset.
   * @author Erik Cupsa (@Erik-Cupsa)
   * @param assetNumber The asset number.
   * @param floorNumber The floor number. 
   * @param roomNumber The room number. 
   * @param assetTypeName The name of the asset type
   * @return True if the input is valid and false otherwise
   */
  private static String inputValidation(int assetNumber, int floorNumber, int roomNumber, String assetTypeName){
        if(assetTypeName == null || assetTypeName.length() == 0){ //invalid asset type name
          return "The asset type does not exist";
        }
        if(assetNumber < 1){ // asset number must be >= 1
          return "The asset number shall not be less than 1";
        }
        if(floorNumber < 0){ // floor number must be >= 0
          return "The floor number shall not be less than 0";
        }
        if(roomNumber < -1){ // room number must be >= -1
          return "The room number shall not be less than -1";
        }

        return "Correct";
      }
}
