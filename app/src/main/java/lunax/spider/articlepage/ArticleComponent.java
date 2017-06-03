package lunax.spider.articlepage;

import dagger.Component;
import lunax.spider.FragmentScoped;
import lunax.spider.data.SpiderRepositoryComponent;
import lunax.spider.data.dataitem.Article;

/**
 * Created by G1494458 on 2017/6/2.
 */

@FragmentScoped
@Component(dependencies = SpiderRepositoryComponent.class, modules = ArticleCoverPresenterModule.class)
public interface ArticleComponent {
    void inject(ArticleActivity articleActivity);
}
