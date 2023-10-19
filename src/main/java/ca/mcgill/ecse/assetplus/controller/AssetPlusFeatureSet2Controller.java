package ca.mcgill.ecse.assetplus.controller;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.*;

public class AssetPlusFeatureSet2Controller {

	/**
   * Adds asset type
   * @author David Marji.
   */

  public static String addAssetType(String name, int expectedLifeSpanInDays) {
    
	if(AssetPlusFeatureSet2Controller.validateInputs(name, expectedLifeSpanInDays).length()!=0) {
		return AssetPlusFeatureSet2Controller.validateInputs(name, expectedLifeSpanInDays);
	}
    AssetType assetType = new AssetType(name, expectedLifeSpanInDays, AssetPlusApplication.getAssetPlus());
    boolean placeHolderBool = ((AssetPlusApplication.getAssetPlus()).addAssetType(assetType));// IMPORTANT NOTE im not sure if this actually modifies the list of asset types since the method is a boolean one, but I think it should
    return "";
  }

	/**
   * updates asset type
   * @author David Marji.
   */

  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
	if(AssetPlusFeatureSet2Controller.validateInputs(newName, newExpectedLifeSpanInDays).length()!=0) {
		return AssetPlusFeatureSet2Controller.validateInputs(newName, newExpectedLifeSpanInDays);
	}
	
	if(oldName==null || oldName.length()==0) { //check if null first so that java doesn't give an error if null was passed in (.length() would give an error when called on null), then check if empty.
		return "The name must not be empty"; 
	}
	else if(AssetType.getWithName(oldName)==null) { //check if old name exists in the system
		return "The asset type does not exist";
	}
	
	
	(AssetType.getWithName(oldName)).setName(newName);
	(AssetType.getWithName(oldName)).setExpectedLifeSpan(newExpectedLifeSpanInDays);
    return "";
  }


	/**
   * deletes asset type
   * @author David Marji.
   */
  public static void deleteAssetType(String name) {
    throw new UnsupportedOperationException("Not Implemented!");
  }
  

	/**
   * validates inputs
   * @author David Marji.
   */

  private static String validateInputs(String name, int expectedLifeSpan) {
	  if(name==null || name.length()==0){
	      return "The name must not be empty";
	    }
	    else if(expectedLifeSpan <= 0){
	      return "The expected life span must be greater than 0 days";
	    }
	    else if(AssetType.getWithName("name")!=null){
	      return "The asset type already exists";
	    }
	  return "";
  }
}