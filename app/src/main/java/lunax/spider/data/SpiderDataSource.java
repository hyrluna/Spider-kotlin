package lunax.spider.data;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Bamboo on 3/14/2017.
 */

public interface SpiderDataSource {
    Observable<Album> getAlbums(String type);
}
