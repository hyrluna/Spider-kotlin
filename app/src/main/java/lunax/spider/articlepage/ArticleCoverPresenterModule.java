package lunax.spider.articlepage;

import dagger.Module;
import dagger.Provides;

/**
 * Created by G1494458 on 2017/6/2.
 */

@Module
public class ArticleCoverPresenterModule {
    private final ArticleCoverContract.View mView;

    public ArticleCoverPresenterModule(ArticleCoverContract.View mView) {
        this.mView = mView;
    }

    @Provides
    ArticleCoverContract.View provideArticleCoverView() {
        return mView;
    }
}
