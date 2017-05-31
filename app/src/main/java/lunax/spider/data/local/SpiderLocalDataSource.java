package lunax.spider.data.local;

import android.content.Context;

import javax.inject.Inject;

import io.reactivex.Observable;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.SpiderDataSource;
import lunax.spider.data.dataitem.Article;

/**
 * Created by Bamboo on 3/14/2017.
 */

public class SpiderLocalDataSource implements SpiderDataSource {


    @Inject
    public SpiderLocalDataSource(Context context) {
    }

    @Override
    public Observable<Album> getAlbums(String type) {
        return null;
    }

    @Override
    public Observable<Article> getArticles(String fold, String subfold) {
        return null;
    }
}
