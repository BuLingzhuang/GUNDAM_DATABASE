package com.blz.gundam_database.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.blz.gundam_database.entities.SplashScreenEntity;
import com.blz.gundam_database.entities.MainListByWorkEntity;

import com.blz.gundam_database.dao.SplashScreenEntityDao;
import com.blz.gundam_database.dao.MainListByWorkEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig splashScreenEntityDaoConfig;
    private final DaoConfig mainListByWorkEntityDaoConfig;

    private final SplashScreenEntityDao splashScreenEntityDao;
    private final MainListByWorkEntityDao mainListByWorkEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        splashScreenEntityDaoConfig = daoConfigMap.get(SplashScreenEntityDao.class).clone();
        splashScreenEntityDaoConfig.initIdentityScope(type);

        mainListByWorkEntityDaoConfig = daoConfigMap.get(MainListByWorkEntityDao.class).clone();
        mainListByWorkEntityDaoConfig.initIdentityScope(type);

        splashScreenEntityDao = new SplashScreenEntityDao(splashScreenEntityDaoConfig, this);
        mainListByWorkEntityDao = new MainListByWorkEntityDao(mainListByWorkEntityDaoConfig, this);

        registerDao(SplashScreenEntity.class, splashScreenEntityDao);
        registerDao(MainListByWorkEntity.class, mainListByWorkEntityDao);
    }
    
    public void clear() {
        splashScreenEntityDaoConfig.getIdentityScope().clear();
        mainListByWorkEntityDaoConfig.getIdentityScope().clear();
    }

    public SplashScreenEntityDao getSplashScreenEntityDao() {
        return splashScreenEntityDao;
    }

    public MainListByWorkEntityDao getMainListByWorkEntityDao() {
        return mainListByWorkEntityDao;
    }

}
