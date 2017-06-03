package lunax.spider.articlepage;

import lunax.spider.BasePresenter;
import lunax.spider.BaseView;
import lunax.spider.data.dataitem.ArticleCover;

/**
 * Created by G1494458 on 2017/6/2.
 */

public interface ArticleCoverContract {

    interface Presenter extends BasePresenter {
        void loadArticleCover(String url);
    }

    interface View extends BaseView<Presenter> {
        void showArticleCoverView(ArticleCover cover);
    }

}
