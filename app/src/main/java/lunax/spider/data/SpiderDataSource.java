package lunax.spider.data;

import io.reactivex.Observable;
import lunax.spider.data.dataitem.Album;

/**
 * Created by Bamboo on 3/14/2017.
 */

public interface SpiderDataSource {
    Observable<Album> getAlbums(String type);
}
