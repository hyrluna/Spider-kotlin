package lunax.spider.wallpaperpage;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lunax.spider.data.dataitem.Album;
import lunax.spider.data.SpiderRepository;
import lunax.spider.data.dataitem.ImageSrc;

/**
 * Created by Bamboo on 3/13/2017.
 */

public class WallpaperPresenter implements WallpaperContract.Presenter {

    SpiderRepository mRepository;
    WallpaperContract.View mView;

    @Inject
    public WallpaperPresenter(SpiderRepository repository, WallpaperContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadAlbums(String type) {
        mView.showLoadingIndicator(true);
        mRepository.getAlbums(type)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Album>>() {
                    @Override
                    public void accept(List<Album> alba) throws Exception {
                        mView.showLoadingIndicator(false);
                        mView.showAlbumsView(alba);
                    }
                });
    }

    @Override
    public void downloadWallpaper(List<ImageSrc> imageSrcs) {
        mView.showWallpaperDownloadSelectView(imageSrcs);
    }

    @Override
    public void start() {

    }

    public void test() {
        Log.d("test", "hello dagger");
    }
}
