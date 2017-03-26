package lunax.spider.wallpaperpage;

import java.util.List;

import lunax.spider.BasePresenter;
import lunax.spider.BaseView;
import lunax.spider.data.dataitem.Album;

/**
 * Created by Bamboo on 3/13/2017.
 */

public interface WallpaperContract {
    interface Presenter extends BasePresenter {
        void loadAlbums(String type);
        void downloadWallpaper(String url);
    }

    interface View extends BaseView<Presenter> {
        void showAlbumsView(List<Album> albums);
        void showWallpaperDownloadSelectView(String url);
    }
}