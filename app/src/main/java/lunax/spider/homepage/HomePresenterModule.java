package lunax.spider.homepage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bamboo on 5/31/2017.
 */

@Module
public class HomePresenterModule {
    private final HomeContract.View mView;

    public HomePresenterModule(HomeContract.View mView) {
        this.mView = mView;
    }

    @Provides
    HomeContract.View provideHomeContractView() {
        return mView;
    }
}
