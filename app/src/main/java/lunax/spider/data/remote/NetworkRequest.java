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
import lunax.spider.data.dataitem.Wallpaper;

/**
 * Created by Bamboo on 3/18/2017.
 */

public class NetworkRequest {

    private static final String TAG = "NetworkRequest";
    private static final String WALLPAPER_BASE_URL = "http://download.pchome.net/wallpaper/";
    public static final String QUERY_TYPE_GIRL = "meinv/";

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
                        Elements uls = doc.select("ul");
                        return Observable.fromIterable(uls);
                    }
                })
                .filter(new Predicate<Element>() {
                    @Override
                    public boolean test(Element element) throws Exception {
                        return element.attr("class").equals("pic-list2 clearfix");
                    }
                })
                .flatMap(new Function<Element, ObservableSource<Element>>() {
                    @Override
                    public ObservableSource<Element> apply(Element element) throws Exception {
                        return Observable.fromIterable(element.select("li"));
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
                        List<Wallpaper> wallpapers = new ArrayList<>();
                        List<String> bigImgHrefs = getBigImgUrls(typeUrl);
                        for (String href : bigImgHrefs) {
                            wallpapers.add(new Wallpaper(href, getAlbumImage(href)));
                        }
                        Log.d("test", "bigImgUrls.size: "+wallpapers.size());
                        return Observable.just(new Album(title, wallpapers));
                    }
                });
    }

    private List<String> getBigImgUrls(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
        List<String> hrefs = new ArrayList<>();
        Elements uls = doc.select("ul");
        for (Element ul : uls) {
            if (ul.attr("id").equals("showImg")) {
                Elements imgELs = ul.select("a");
                for (Element imgEl : imgELs) {
                    String href = imgEl.absUrl("href");
                    Log.d("test", "img url: "+href);
                    hrefs.add(href);
                }
            }
        }
        return hrefs;
    }

    private String getAlbumImage(String href) throws IOException {
        Document doc = Jsoup.connect(href)
                .userAgent("Mozilla")
                .get();
        Elements photos = doc.select("div");
        for (Element p : photos) {
            if (p.attr("class").equals("photo")) {
                //当前显示图片
                String imageUrl = p.select("img").first().attr("src");
                Log.d("test", "get big img");
                return imageUrl;
            }
        }
        return "";
    }
}
