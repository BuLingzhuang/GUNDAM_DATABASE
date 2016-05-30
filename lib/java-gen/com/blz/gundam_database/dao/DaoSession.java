package com.blz.gundam_database.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.blz.gundam_database.entities.SplashScreenEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;
import com.blz.gundam_database.entities.MobileSuitEntity;

import com.blz.gundam_database.dao.SplashScreenEntityDao;
import com.blz.gundam_database.dao.MainListByWorkEntityDao;
import com.blz.gundam_database.dao.MobileSuitEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig splashScreenEntityDaoConfig;
    private final DaoConfig mainListByWorkEntityDaoConfig;
    private final DaoConfig mobileSuitEntityDaoConfig;

    private final SplashScreenEntityDao splashScreenEntityDao;
    private final MainListByWorkEntityDao mainListByWorkEntityDao;
    private final MobileSuitEntityDao mobileSuitEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        splashScreenEntityDaoConfig = daoConfigMap.get(SplashScreenEntityDao.class).clone();
        splashScreenEntityDaoConfig.initIdentityScope(type);

        mainListByWorkEntityDaoConfig = daoConfigMap.get(MainListByWorkEntityDao.class).clone();
        mainListByWorkEntityDaoConfig.initIdentityScope(type);

        mobileSuitEntityDaoConfig = daoConfigMap.get(MobileSuitEntityDao.class).clone();
        mobileSuitEntityDaoConfig.initIdentityScope(type);

        splashScreenEntityDao = new SplashScreenEntityDao(splashScreenEntityDaoConfig, this);
        mainListByWorkEntityDao = new MainListByWorkEntityDao(mainListByWorkEntityDaoConfig, this);
        mobileSuitEntityDao = new MobileSuitEntityDao(mobileSuitEntityDaoConfig, this);

        registerDao(SplashScreenEntity.class, splashScreenEntityDao);
        registerDao(MainListByWorkEntity.class, mainListByWorkEntityDao);
        registerDao(MobileSuitEntity.class, mobileSuitEntityDao);
    }
    
    public void clear() {
        splashScreenEntityDaoConfig.getIdentityScope().clear();
        mainListByWorkEntityDaoConfig.getIdentityScope().clear();
        mobileSuitEntityDaoConfig.getIdentityScope().clear();
    }

    public SplashScreenEntityDao getSplashScreenEntityDao() {
        return splashScreenEntityDao;
    }

    public MainListByWorkEntityDao getMainListByWorkEntityDao() {
        return mainListByWorkEntityDao;
    }

    public MobileSuitEntityDao getMobileSuitEntityDao() {
        return mobileSuitEntityDao;
    }

}