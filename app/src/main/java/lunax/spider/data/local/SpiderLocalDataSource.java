package lunax.spider.data.local;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import lunax.spider.SpiderApplication;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.SpiderDataSource;
import lunax.spider.data.dataitem.AlbumDao;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ArticleCover;
import lunax.spider.data.dataitem.ArticleCoverDao;
import lunax.spider.data.dataitem.ArticleDao;
import lunax.spider.data.dataitem.DaoSession;
import lunax.spider.data.dataitem.ImageSrc;
import lunax.spider.data.dataitem.ImageSrcDao;
import lunax.spider.data.dataitem.Wallpaper;
import lunax.spider.data.dataitem.WallpaperDao;

/**
 * Created by Bamboo on 3/14/2017.
 */
@Singleton
public class SpiderLocalDataSource implements SpiderDataSource {

    private DaoSession mDaoSession;

    @Inject
    public SpiderLocalDataSource(Context context) {
        mDaoSession = ((SpiderApplication) context).getDaoSession();
    }

    @Override
    public Observable<Album> getAlbums(String type) {
        AlbumDao albumDao = mDaoSession.getAlbumDao();
        WallpaperDao wallpaperDao = mDaoSession.getWallpaperDao();
        ImageSrcDao imageSrcDao = mDaoSession.getImageSrcDao();

        List<Album> albums = albumDao.queryBuilder().where(AlbumDao.Properties.Type.eq(type)).list();
        for (Album album: albums) {
            QueryBuilder<Wallpaper> queryWallpaper = wallpaperDao.queryBuilder();
            queryWallpaper.where(WallpaperDao.Properties.AlbumId.eq(album.getId()));
            List<Wallpaper> wps = queryWallpaper.list();
            for (Wallpaper wp : wps) {
                QueryBuilder<ImageSrc> queryImageSrc = imageSrcDao.queryBuilder();
                queryImageSrc.where(ImageSrcDao.Properties.WallpaperId.eq(wp.getId()));
                List<ImageSrc> imgs = queryImageSrc.list();
                wp.setDownloadUrls(imgs);
            }
            album.setWallpapers(wps);
        }
        return Observable.fromIterable(albums);
    }

    @Override
    public Observable<Article> getArticles(String kind, String start) {
        ArticleDao articleDao = mDaoSession.getArticleDao();
        List<Article> articles = articleDao.queryBuilder().where(ArticleDao.Properties.Kind.eq(kind)).list();
        return Observable.fromIterable(articles);
    }

    @Override
    public Observable<ArticleCover> getArticleCover(String url) {
        ArticleCoverDao dao = mDaoSession.getArticleCoverDao();
        List<ArticleCover> covers = dao.queryBuilder().where(ArticleCoverDao.Properties.Title.eq(url)).list();
        return Observable.fromIterable(covers);
    }

    @Override
    public void saveAlbums(List<Album> alba, String type) {
        AlbumDao albumDao = mDaoSession.getAlbumDao();
        WallpaperDao wallpaperDao = mDaoSession.getWallpaperDao();
        ImageSrcDao imageSrcDao = mDaoSession.getImageSrcDao();
        albumDao.deleteAll();
        wallpaperDao.deleteAll();
        imageSrcDao.deleteAll();
        int i = 0;
        int j = 0;
        int k = 0;
        for (Album album: alba) {
            album.setType(type);
            album.setId(i++);
            long albumId = albumDao.insert(album);
            List<Wallpaper> wallpapers = album.getWallpapers();
            for (Wallpaper wallpaper: wallpapers) {
                wallpaper.setAlbumId(albumId);
                wallpaper.setId(j++);
                long wallpaperId = wallpaperDao.insert(wallpaper);
                List<ImageSrc> imageSrcs = wallpaper.getDownloadUrls();
                for (ImageSrc imageSrc: imageSrcs) {
                    imageSrc.setId(k++);
                    imageSrc.setWallpaperId(wallpaperId);
                    imageSrcDao.insert(imageSrc);
                }
            }
        }
    }

    @Override
    public void saveArticles(List<Article> articles, String kind) {
        ArticleDao articleDao = mDaoSession.getArticleDao();
        articleDao.deleteAll();
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            article.setKind(kind);
            articleDao.insert(article);
        }
    }

    @Override
    public void saveArticleCovers(ArticleCover cover, String articleTitle) {
        ArticleCoverDao articleCoverDao = mDaoSession.getArticleCoverDao();
        List coverTmp = articleCoverDao.queryBuilder().where(ArticleCoverDao.Properties.Title.eq(articleTitle)).list();
        if (coverTmp != null && coverTmp.size() > 0) {
            articleCoverDao.update(cover);
        } else {
            cover.setTitle(articleTitle);
            articleCoverDao.insert(cover);
        }
    }
}
