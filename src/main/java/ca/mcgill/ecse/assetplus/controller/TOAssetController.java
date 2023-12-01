package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;

import java.sql.Date;
import java.util.ArrayList;

public class TOAssetController {

    public static ArrayList<TOAsset> getAssets() {
        ArrayList<TOAsset> assets = new ArrayList<TOAsset>();
        AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

        for (SpecificAsset asset : assetPlus.getSpecificAssets()) {
            Integer assetNumber = asset.getAssetNumber();
            Integer floorNumber = asset.getFloorNumber();
            Integer roomNumber = asset.getRoomNumber();
            Date purchaseDate = asset.getPurchaseDate();

            String typeName = asset.getAssetType().getName();

            assets.add(new TOAsset(assetNumber, floorNumber, roomNumber, purchaseDate, typeName));
        }

        return assets;
    }

}
