package lunax.spider.homepage;

import android.widget.ImageView;

import java.util.List;

import lunax.spider.BasePresenter;
import lunax.spider.BaseView;
import lunax.spider.data.dataitem.Article;

/**
 * Created by Bamboo on 5/31/2017.
 */

public interface HomeContract {
    interface Presenter extends BasePresenter {
        void loadArticles(String fold, String subfold);
        void loadArticleDetail(Article articleUrl, ImageView avatar);
    }

    interface View extends BaseView<Presenter> {
        void showArticlesView(List<Article> articles);
        void showArticleDetail(Article article, ImageView avatar);
    }
}
