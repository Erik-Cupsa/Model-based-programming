package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

public class TOAssetType
{

 
    //------------------------
    // MEMBER VARIABLES
    //------------------------
    //TOAssetType Attributes

    private int expectedLifeSpan;

    private String name;

    
    //------------------------
    // CONSTRUCTOR
    //------------------------
    

    public TOAssetType(int aExpectedLifeSpan, String aName){
        expectedLifeSpan = aExpectedLifeSpan;
        name = aName;
        try{
            AssetPlusPersistence.save();
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    
    //------------------------

    // INTERFACE

    //------------------------

    

    public int getExpectedLifeSpan()

    {

    return expectedLifeSpan;

    }

    

    public String getName()

    {

    return name;

    }

    public void delete()
    {}
    
    public String toString()
    {
    return super.toString() + "["+
    "expectedLifeSpan" + ":" + getExpectedLifeSpan()+ "," +
    "name" + ":" + getName()+ "]";

    }
}