package lunax.spider.data.remote;

import javax.inject.Inject;

import io.reactivex.Observable;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.SpiderDataSource;

/**
 * Created by Bamboo on 3/14/2017.
 */

public class SpiderRemoteDataSource implements SpiderDataSource {

    @Inject
    NetworkRequest mNetworkRequest;

    @Inject
    public SpiderRemoteDataSource() {
        DaggerNetworkComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public Observable<Album> getAlbums(String type) {
        return mNetworkRequest.getWallpaperTypeUrls(type);
    }
}
