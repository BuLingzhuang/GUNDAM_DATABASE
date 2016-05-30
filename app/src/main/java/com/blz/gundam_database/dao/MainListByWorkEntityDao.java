package com.blz.gundam_database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.blz.gundam_database.entities.MainListByWorkEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MAIN_LIST_BY_WORK_ENTITY".
*/
public class MainListByWorkEntityDao extends AbstractDao<MainListByWorkEntity, Void> {

    public static final String TABLENAME = "MAIN_LIST_BY_WORK_ENTITY";

    /**
     * Properties of entity MainListByWorkEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property WorkId = new Property(0, String.class, "workId", false, "WORK_ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property OriginalName = new Property(2, String.class, "originalName", false, "ORIGINAL_NAME");
        public final static Property EnglishName = new Property(3, String.class, "englishName", false, "ENGLISH_NAME");
        public final static Property StoryYear = new Property(4, String.class, "storyYear", false, "STORY_YEAR");
        public final static Property Icon = new Property(5, String.class, "icon", false, "ICON");
    };


    public MainListByWorkEntityDao(DaoConfig config) {
        super(config);
    }
    
    public MainListByWorkEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MAIN_LIST_BY_WORK_ENTITY\" (" + //
                "\"WORK_ID\" TEXT," + // 0: workId
                "\"NAME\" TEXT," + // 1: name
                "\"ORIGINAL_NAME\" TEXT," + // 2: originalName
                "\"ENGLISH_NAME\" TEXT," + // 3: englishName
                "\"STORY_YEAR\" TEXT," + // 4: storyYear
                "\"ICON\" TEXT);"); // 5: icon
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MAIN_LIST_BY_WORK_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MainListByWorkEntity entity) {
        stmt.clearBindings();
 
        String workId = entity.getWorkId();
        if (workId != null) {
            stmt.bindString(1, workId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String originalName = entity.getOriginalName();
        if (originalName != null) {
            stmt.bindString(3, originalName);
        }
 
        String englishName = entity.getEnglishName();
        if (englishName != null) {
            stmt.bindString(4, englishName);
        }
 
        String storyYear = entity.getStoryYear();
        if (storyYear != null) {
            stmt.bindString(5, storyYear);
        }
 
        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(6, icon);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public MainListByWorkEntity readEntity(Cursor cursor, int offset) {
        MainListByWorkEntity entity = new MainListByWorkEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // workId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // originalName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // englishName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // storyYear
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // icon
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MainListByWorkEntity entity, int offset) {
        entity.setWorkId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOriginalName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEnglishName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStoryYear(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIcon(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(MainListByWorkEntity entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(MainListByWorkEntity entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}