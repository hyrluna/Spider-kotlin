package lunax.spider.wallpaperpage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bamboo on 3/13/2017.
 */

@Module
public class WallpaperPresenterModule {
    private final WallpaperContract.View mView;

    public WallpaperPresenterModule(WallpaperContract.View view) {
        mView = view;
    }

    @Provides
    WallpaperContract.View provideWallpaperContractView() {
        return mView;
    }
}
