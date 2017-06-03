package lunax.spider;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import lunax.spider.data.DaggerSpiderRepositoryComponent;
import lunax.spider.data.SpiderRepositoryComponent;
import lunax.spider.data.dataitem.DaoMaster;
import lunax.spider.data.dataitem.DaoSession;

/**
 * Created by Bamboo on 3/13/2017.
 */

public class SpiderApplication extends Application {
    public static final boolean ENCRYPTED = false;
    private DaoSession daoSession;

    private SpiderRepositoryComponent mSpiderRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mSpiderRepositoryComponent = DaggerSpiderRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public SpiderRepositoryComponent getSpiderRepositoryComponent() {
        return mSpiderRepositoryComponent;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
