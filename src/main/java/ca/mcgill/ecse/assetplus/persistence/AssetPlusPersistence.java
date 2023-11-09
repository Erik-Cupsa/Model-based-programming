package ca.mcgill.ecse.assetplus.persistence;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

/*
 * @Ming Xuan Yue
 */
public class AssetPlusPersistence {
  
  private static String filename = "ap.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.assetplus");

  public void setFilename(String filename){
    AssetPlusPersistence.filename = filename;
  }

  public static void save(){
    save(AssetPlusApplication.getAssetPlus());
  }

  public static void save(AssetPlus assetPlus){
    serializer.serialize(assetPlus, filename);
  }

  public static AssetPlus load(){
    var assetPlus = (AssetPlus) serializer.deserialize(filename);
    
    if (assetPlus == null) {
      assetPlus = new AssetPlus();
    } else {
      assetPlus.reinitialize();
    }
    return assetPlus;
  }

}