package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args){
        Schema schema = new Schema(1, "com.blz.gundam_database.entities");
        schema.setDefaultJavaPackageDao("com.blz.gundam_database.dao");

        //闪屏页图图片
        Entity splashScreenEntity = schema.addEntity("SplashScreenEntity");
        splashScreenEntity.addStringProperty("imgUrl");

        Entity mainListByWorkEntity = schema.addEntity("MainListByWorkEntity");
        mainListByWorkEntity.addStringProperty("workId");
        mainListByWorkEntity.addStringProperty("name");
        mainListByWorkEntity.addStringProperty("originalName");
        mainListByWorkEntity.addStringProperty("englishName");
        mainListByWorkEntity.addStringProperty("storyYear");
        mainListByWorkEntity.addStringProperty("icon");

        Entity mobileSuitEntity = schema.addEntity("MobileSuitEntity");
        mobileSuitEntity.addStringProperty("objectId").primaryKey();
        mobileSuitEntity.addStringProperty("workId");
        mobileSuitEntity.addStringProperty("originalName");
        mobileSuitEntity.addStringProperty("modelSeries");
        mobileSuitEntity.addStringProperty("scale");
        mobileSuitEntity.addStringProperty("itemNo");
        mobileSuitEntity.addStringProperty("launchDate");
        mobileSuitEntity.addStringProperty("price");
        mobileSuitEntity.addStringProperty("images");

        try {
            new DaoGenerator().generateAll(schema,"lib/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
