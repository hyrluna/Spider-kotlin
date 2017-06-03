package lunax.spider.data.remote;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.SpiderDataSource;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ArticleCover;

/**
 * Created by Bamboo on 3/14/2017.
 */
@Singleton
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

    @Override
    public Observable<Article> getArticles(String fold, String subfold) {
        return mNetworkRequest.getArticles(fold, subfold);
    }

    @Override
    public Observable<ArticleCover> getArticleCover(String url) {
        return mNetworkRequest.getArticleCover(url);
    }

    @Override
    public void saveAlbums(List<Album> alba, String type) {

    }

    @Override
    public void saveArticles(List<Article> articles, String kind) {

    }

    @Override
    public void saveArticleCovers(ArticleCover articleCover, String articleTitle) {

    }
}
