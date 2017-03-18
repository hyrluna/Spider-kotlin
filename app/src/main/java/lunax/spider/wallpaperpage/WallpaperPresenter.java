package lunax.spider.wallpaperpage;

import android.util.Log;

import javax.inject.Inject;

import lunax.spider.data.SpiderRepository;

/**
 * Created by Bamboo on 3/13/2017.
 */

public class WallpaperPresenter {

    SpiderRepository mRepository;
    WallpaperContract.View mView;

    @Inject
    public WallpaperPresenter(SpiderRepository repository, WallpaperContract.View view) {
        mRepository = repository;
        mView = view;
    }

    public void test() {
        Log.d("test", "hello dagger");
    }
}
