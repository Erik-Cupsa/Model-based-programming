package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

public class TOAssetTypeController{
    
    public static ArrayList<TOAssetType> getAssetTypes(){
        ArrayList<TOAssetType> assetTypes = new ArrayList<TOAssetType>();
        AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

        for(AssetType assetType : assetPlus.getAssetTypes()){
            String name = assetType.getName();
            Integer expectedLifeSpan = assetType.getExpectedLifeSpan();
            assetTypes.add(new TOAssetType(expectedLifeSpan, name));
        }

        return assetTypes;
    }

    public static void setName(TOAssetType assetType, String name){
        assetType = new TOAssetType(assetType.getExpectedLifeSpan(), name);
    }

    public static void setExpectedLifeSpan(TOAssetType assetType, Integer expectedLifeSpan){
        assetType = new TOAssetType(expectedLifeSpan, assetType.getName());
    }

}