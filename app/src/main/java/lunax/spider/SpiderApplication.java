package lunax.spider;

import android.app.Application;

import lunax.spider.data.DaggerSpiderRepositoryComponent;
import lunax.spider.data.SpiderRepositoryComponent;

/**
 * Created by Bamboo on 3/13/2017.
 */

public class SpiderApplication extends Application {
    private SpiderRepositoryComponent mSpiderRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mSpiderRepositoryComponent = DaggerSpiderRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();

    }

    public SpiderRepositoryComponent getSpiderRepositoryComponent() {
        return mSpiderRepositoryComponent;
    }
}
