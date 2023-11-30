package ca.mcgill.ecse.assetplus.controller;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.application.*;

public class AssetPlusFeatureSet2Controller {

	/**
   * Adds asset type
   * @author David Marji.
	 * @param name name of the asset type to create.
	 * @param expectedLifeSpanInDays expected life span in days of the asset type to create.
   */

	public static String addAssetType(String name, int expectedLifeSpanInDays) {

		if(AssetPlusFeatureSet2Controller.validateInputs(name, expectedLifeSpanInDays).length()!=0) { //check if assetType already is in the system / wrong input formats
			return AssetPlusFeatureSet2Controller.validateInputs(name, expectedLifeSpanInDays); //if format is wrong/type already exists returns the corresponding error
		}
		AssetType assetType = new AssetType(name, expectedLifeSpanInDays, AssetPlusApplication.getAssetPlus());//instantiating the asset type
		
		//adding it the list of AssetTypes in the AssetPlus object
		try{
			AssetPlusPersistence.save();
		}catch(RuntimeException e){
			return e.getMessage();
		}
		return "";
	}

	/**
   * updates asset type
   * @author David Marji.
	 * @param oldName name of the old asset type
	 * @param newName newName after updating
	 * @param expectedLifeSpanInDays new expected life span of the type
   */

	public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {

		if (!newName.equals(oldName) && AssetPlusFeatureSet2Controller.validateInputs(newName, newExpectedLifeSpanInDays).length()!=0) { //check format and if assetType with newName already exists
			return AssetPlusFeatureSet2Controller.validateInputs(newName, newExpectedLifeSpanInDays); //return the corresponding error
		}
		else if (newName != null && newName.length()!=0 && newName.equals(oldName) && AssetType.hasWithName(oldName) && newExpectedLifeSpanInDays>0 ){ //if only want to update expectedLifeSpan and not the name
			AssetType.getWithName(newName).setExpectedLifeSpan(newExpectedLifeSpanInDays);
			return "";
		}

		if(oldName==null || oldName.length()==0) { //check if null first so that java doesn't give an error if null was passed in (.length() would give an error when called on null), then check if empty.
			return "The name must not be empty"; 
		}
		else if(!AssetType.hasWithName(oldName)) {//checks if the AssetPlus object with the name oldName exists

			return "The asset type does not exist"; //if it doesnt exist return an error
		}
		
		(AssetType.getWithName(oldName)).setName(newName);
		(AssetType.getWithName(newName)).setExpectedLifeSpan(newExpectedLifeSpanInDays);
		try{
			AssetPlusPersistence.save();
		}catch(RuntimeException e){
			return e.getMessage();
		}
	return "";
	}


	/**
   * deletes asset type
   * @author David Marji.
	* @param name name of the asset type to delete	
   */
	public static void deleteAssetType(String name) {

		if(AssetType.hasWithName(name)) {
			AssetPlusApplication.getAssetPlus().removeAssetType(AssetType.getWithName(name)); //removes from system
			AssetType.getWithName(name).delete(); //deletes
		}
		try{
			AssetPlusPersistence.save();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}
  

	/**
   * private helper method that validates inputs
   * @author David Marji.
	 * @param name name of the asset type to create/update to
	 * @param expectedLifeSpan expected life span of asset type about to be created or expected life span after updating.
	 * @return returns an error string that is empty if no errors were found.
   */
    private static String validateInputs(String name, int expectedLifeSpan) {
		if(name==null || name.length()==0){ // check if the name is empty
			return "The name must not be empty";
			}
			else if(expectedLifeSpan <= 0){ //check if the expected life span is more than 0
			return "The expected life span must be greater than 0 days";
			}
			else if(AssetType.hasWithName(name)){ //check if an AssetType with this name already exists
			return "The asset type already exists";
			}
		try{
			AssetPlusPersistence.save();
		}catch(RuntimeException e){
			return e.getMessage();
		}
		return "";
    }
}