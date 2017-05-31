package lunax.spider.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import lunax.spider.data.local.SpiderLocalDataSource;
import lunax.spider.data.remote.SpiderRemoteDataSource;

/**
 * Created by Bamboo on 3/14/2017.
 */

@Module
abstract class SpiderRepositoryModule {

    @Singleton
    @Binds
    abstract SpiderDataSource providerSpiderLocalData(SpiderLocalDataSource localDataSource);

    @Singleton
    @Binds
    abstract SpiderDataSource providerSpiderRemoteData(SpiderRemoteDataSource remoteDataSource);

}
