package com.blz.gundam_database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.blz.gundam_database.entities.SplashScreenEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SPLASH_SCREEN_ENTITY".
*/
public class SplashScreenEntityDao extends AbstractDao<SplashScreenEntity, Void> {

    public static final String TABLENAME = "SPLASH_SCREEN_ENTITY";

    /**
     * Properties of entity SplashScreenEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ImgUrl = new Property(0, String.class, "imgUrl", false, "IMG_URL");
    };


    public SplashScreenEntityDao(DaoConfig config) {
        super(config);
    }
    
    public SplashScreenEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SPLASH_SCREEN_ENTITY\" (" + //
                "\"IMG_URL\" TEXT);"); // 0: imgUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SPLASH_SCREEN_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SplashScreenEntity entity) {
        stmt.clearBindings();
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(1, imgUrl);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SplashScreenEntity readEntity(Cursor cursor, int offset) {
        SplashScreenEntity entity = new SplashScreenEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0) // imgUrl
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SplashScreenEntity entity, int offset) {
        entity.setImgUrl(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(SplashScreenEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(SplashScreenEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}