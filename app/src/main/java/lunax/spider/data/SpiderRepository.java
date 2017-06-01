package lunax.spider.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.local.SpiderLocalDataSource;
import lunax.spider.data.remote.SpiderRemoteDataSource;

/**
 * Created by Bamboo on 3/13/2017.
 */

@Singleton
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

    @Override
    public Observable<Article> getArticles(String fold, String subfold) {
        return mSpiderRemoteDataSource.getArticles(fold, subfold);
    }
}
