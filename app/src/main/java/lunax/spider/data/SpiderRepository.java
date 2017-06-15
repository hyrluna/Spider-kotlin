package lunax.spider.data;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ArticleCover;
import lunax.spider.data.local.SpiderLocalDataSource;
import lunax.spider.data.remote.SpiderRemoteDataSource;

/**
 * Created by Bamboo on 3/13/2017.
 */

@Singleton
public class SpiderRepository implements SpiderDataSource {

    private SpiderLocalDataSource mSpiderLocalDataSource;
    private SpiderRemoteDataSource mSpiderRemoteDataSource;
    private boolean needRefresh = false;

    @Inject
    public SpiderRepository(SpiderLocalDataSource spiderLocalDataSource,
                            SpiderRemoteDataSource spiderRemoteDataSource) {
        mSpiderLocalDataSource = spiderLocalDataSource;
        mSpiderRemoteDataSource = spiderRemoteDataSource;
    }

    @Override
    public Observable<Album> getAlbums(String type) {
        Observable<Album> remote = getAndSaveAlbumsRemote(type);
        Observable<Album> local = mSpiderLocalDataSource.getAlbums(type);
        if (needRefresh) {
            return remote;
        } else {
            return local;
        }
    }

    public Observable<Album> getAndSaveAlbumsRemote(final String type) {
        return mSpiderRemoteDataSource.getAlbums(type)
                .toList()
                .flatMapObservable(new Function<List<Album>, ObservableSource<Album>>() {
                    @Override
                    public ObservableSource<Album> apply(List<Album> alba) throws Exception {
                        saveAlbums(alba, type);
                        return Observable.fromIterable(alba);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        needRefresh = false;
                    }
                });
    }

    @Override
    public Observable<Article> getArticles(final String kind, String start) {
        Observable<Article> remote = getAndSaveArticlesRemote(kind, start);
        Observable<Article> local = mSpiderLocalDataSource.getArticles(kind, start);

        if (needRefresh) {
            return remote;
        } else {
            return local;
        }
    }

    public Observable<Article> getAndSaveArticlesRemote(final String kind, String start) {
        return mSpiderRemoteDataSource.getArticles(kind, start)
                .toList()
                .flatMapObservable(new Function<List<Article>, ObservableSource<Article>>() {
                    @Override
                    public ObservableSource<Article> apply(List<Article> articles) throws Exception {
                        saveArticles(articles, kind);
                        return Observable.fromIterable(articles);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        needRefresh = false;
                    }
                });
    }

    @Override
    public Observable<ArticleCover> getArticleCover(String url) {
        Observable<ArticleCover> remote = getAndSaveArticleCoverRemote(url);
        Observable<ArticleCover> local = mSpiderLocalDataSource.getArticleCover(url);

        if (needRefresh) {
            return remote;
        } else {
            return local;
        }
    }

    public Observable<ArticleCover> getAndSaveArticleCoverRemote(final String url) {
        return mSpiderRemoteDataSource.getArticleCover(url)
                .flatMap(new Function<ArticleCover, ObservableSource<ArticleCover>>() {
                    @Override
                    public ObservableSource<ArticleCover> apply(ArticleCover cover) throws Exception {
                        saveArticleCovers(cover, url);
                        return Observable.just(cover);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        needRefresh = false;
                    }
                });
    }

    @Override
    public void saveAlbums(List<Album> alba, String type) {
        mSpiderLocalDataSource.saveAlbums(alba, type);
    }

    @Override
    public void saveArticles(List<Article> articles, String kind) {
        mSpiderLocalDataSource.saveArticles(articles, kind);
    }

    @Override
    public void saveArticleCovers(ArticleCover articleCover, String articleTitle) {

    }

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<String> getTestData() {
        Repository.Builder<String> builder = Repository.builder();

        return builder
                .local(mSpiderLocalDataSource)
                .remote(mSpiderRemoteDataSource)
                .methodArguments(null)
                .method("getTestData", null)
                .loadFromLocal(true)
                .build();
    }
}
