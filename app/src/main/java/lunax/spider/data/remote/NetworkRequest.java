package lunax.spider.data.remote;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ImageSrc;
import lunax.spider.data.dataitem.Wallpaper;

/**
 * Created by Bamboo on 3/18/2017.
 */

public class NetworkRequest {

    private static final String TAG = "NetworkRequest";
    private static final String WALLPAPER_BASE_URL = "http://download.pchome.net/wallpaper/";
    public static final String QUERY_TYPE_GIRL = "meinv/";
    public static final String ARTICLE_URL = "https://read.douban.com/kind/501?sort=hot&promotion_only=False&min_price=0&works_type=None&max_price=0";

    @Inject
    public NetworkRequest() {

    }

    public Observable<Album> getWallpaperTypeUrls(String type) {
        return Observable.just(WALLPAPER_BASE_URL + type)
                .flatMap(new Function<String, ObservableSource<Element>>() {
                    @Override
                    public ObservableSource<Element> apply(String url) throws Exception {
                        Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla")
                                .get();
                        Elements uls = doc.select("li.photo-list-padding");
                        return Observable.fromIterable(uls);
                    }
                })
                .flatMap(new Function<Element, ObservableSource<Album>>() {
                    @Override
                    public ObservableSource<Album> apply(Element element) throws Exception {
                        Element a = element.select("a").first();
                        Element img = element.select("img").first();
                        //第一张大图
                        String typeUrl = a.absUrl("href");
                        String title = element.select("span").first().text();
                        //获取每种类型图集的大图
                        List<Wallpaper> wallpapers = getBigImgUrls(typeUrl);
//                        List<String> bigImgHrefs =
//                        for (String href : bigImgHrefs) {
//                            wallpapers.add(new Wallpaper(href, getAlbumImage(href)));
//                        }
                        Log.d("test", "bigImgUrls.size: "+wallpapers.size());
                        return Observable.just(new Album(title, wallpapers));
                    }
                });
    }

    private List<Wallpaper> getBigImgUrls(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();

        List<ImageSrc> imageSrcs = new ArrayList<>();
        Log.d("test", "url = "+url);
        Elements downloadEls = doc.select("dd#tagfbl").first().select("a");
        for (Element e : downloadEls) {
            imageSrcs.add(new ImageSrc(e.text(), e.absUrl("href")));
        }

        List<Wallpaper> wallpapers = new ArrayList<>();
        Elements els = doc.select("ul[id=showImg]").first().select("a");
        for (Element e : els) {
            String href = e.absUrl("href");
            String bigImgUrl = getAlbumImage(href);
            wallpapers.add(new Wallpaper(href, bigImgUrl, imageSrcs));
        }
        return wallpapers;
    }

    private String getAlbumImage(String href) throws IOException {
        Document doc = Jsoup.connect(href)
                .userAgent("Mozilla")
                .get();

        return doc.select("img[id=bigImg]").first().attr("src");
    }

    public Observable<Article> getArticles(String fold, String subfold) {
        return Observable.just(ARTICLE_URL)
                .flatMap(new Function<String, ObservableSource<Element>>() {
                    @Override
                    public ObservableSource<Element> apply(String url) throws Exception {
                        Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla")
                                .get();
                        Elements uls = doc.select("li.store-item");
                        return Observable.fromIterable(uls);
                    }
                })
                .flatMap(new Function<Element, ObservableSource<Article>>() {
                    @Override
                    public ObservableSource<Article> apply(Element element) throws Exception {
                        Element imgEl = element.select("img").first();
                        Element titleEl = element.select("div.title").first();
                        if (titleEl == null) {
                            titleEl = element.select("h4.title").first();
                        }
                        Element authorEl = element.select("a.author-item").first();
                        if (authorEl == null) {
                            authorEl = element.select("div.author").first().select("a").first();
                        }
                        Element typeEl = element.select("span.category").first();
                        String type;
                        if (typeEl == null) {
                            typeEl = element.select("div.category").first();
                            type = typeEl.text();
                        } else {
                            type = typeEl.select("span.labeled-text").first().text();
                        }
//                        Element typeE = element.select("div.category").first().select("span.labeled-text").first();
//                        if (typeE == null) {
//                            typeE = element.select("div.category").first();
//                        }
                        Element descEl = element.select("div.article-desc-brief").first();
                        if (descEl == null) {
                            descEl = element.select("div.intro").first();
                        }

                        String avatar = imgEl.attr("src");
                        String title = titleEl.select("a").first().text();
                        String articleUrl = titleEl.select("a").first().absUrl("href");
                        String subTitle = titleEl.select("p.subtitle").text();
                        if (subTitle == null) {
                            subTitle = element.select("h5.subtitle").text();
                        }
                        String author = authorEl.text();
                        String authorUrl = authorEl.absUrl("href");
                        String rating = element.select("span.rating-average").first().text();
                        String desc = descEl.text();
                        Log.d("test", "get article: "+title+", "+articleUrl);
                        return Observable.just(new Article(avatar, title, subTitle, author, type, rating, desc, articleUrl));
                    }
                });
    }
}
