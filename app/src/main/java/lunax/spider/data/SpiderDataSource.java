package lunax.spider.data;

import java.util.List;

import io.reactivex.Observable;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ArticleCover;

/**
 * Created by Bamboo on 3/14/2017.
 */

public interface SpiderDataSource {
    Observable<Album> getAlbums(String type);
    Observable<Article> getArticles(String kind, String start);
    Observable<ArticleCover> getArticleCover(String url);

    void saveAlbums(List<Album> alba, String type);
    void saveArticles(List<Article> articles, String kind);
    void saveArticleCovers(ArticleCover articleCover, String articleTitle);

    Observable<String> getTestData();
}
