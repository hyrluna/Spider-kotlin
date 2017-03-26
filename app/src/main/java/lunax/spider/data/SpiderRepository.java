package lunax.spider.data;

import javax.inject.Inject;

import io.reactivex.Observable;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.local.SpiderLocalDataSource;
import lunax.spider.data.remote.SpiderRemoteDataSource;

/**
 * Created by Bamboo on 3/13/2017.
 */

public class SpiderRepository implements SpiderDataSource {

    private SpiderLocalDataSource mSpiderLocalDataSource;
    private SpiderRemoteDataSource mSpiderRemoteDataSource;

    @Inject
    public SpiderRepository(SpiderLocalDataSource spiderLocalDataSource,
                            SpiderRemoteDataSource spiderRemoteDataSource) {
        mSpiderLocalDataSource = spiderLocalDataSource;
        mSpiderRemoteDataSource = spiderRemoteDataSource;
    }

    @Override
    public Observable<Album> getAlbums(String type) {
        return mSpiderRemoteDataSource.getAlbums(type);
    }
}
