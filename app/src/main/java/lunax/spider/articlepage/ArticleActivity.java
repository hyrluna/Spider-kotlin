package lunax.spider.articlepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lunax.spider.BaseActivity;
import lunax.spider.R;
import lunax.spider.SpiderApplication;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.remote.NetworkRequest;

public class ArticleActivity extends BaseActivity {

    public static final String EXTRA_ARTICLE = PACKAGE_NAME + ".ArticleActivity.extra_article_url";

    private static final String TAG_ARTICLE_COVER_FRAGMENT = "tag_article_cover_fragment";
    private static final String TAG_ARTICLE_FRAGMENT = "tag_article_fragment";
    private List<Fragment> fragments = new ArrayList<>();

    //注入对象必须是具体类
    @Inject
    ArticleCoverPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        getWindow().setStatusBarColor(getResources().getColor(R.color.article_card_background));

        Article article = getIntent().getParcelableExtra(EXTRA_ARTICLE);

        ArticleCoverFragment articleCoverFragment = ArticleCoverFragment.newInstance(article, "p2");
        ArticleFragment articleFragment = ArticleFragment.newInstance(article.getArticleUrl(), "p2");
        fragments.add(articleCoverFragment);
        fragments.add(articleFragment);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content, articleFragment, TAG_ARTICLE_FRAGMENT)
                .hide(articleFragment)
                .add(R.id.content, articleCoverFragment, TAG_ARTICLE_COVER_FRAGMENT)
                .show(articleCoverFragment)
                .commit();

        DaggerArticleComponent.builder()
                .spiderRepositoryComponent(mRepositoryComponent)
                .articleCoverPresenterModule(new ArticleCoverPresenterModule(articleCoverFragment))
                .build()
                .inject(this);

        mPresenter.setRefresh(true);

    }

//    @Override
    public void toReadArticle(View view) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment coverFrag = fm.findFragmentByTag(TAG_ARTICLE_COVER_FRAGMENT);
        Fragment articleFrag = fm.findFragmentByTag(TAG_ARTICLE_FRAGMENT);

        fm.beginTransaction()
                .hide(coverFrag)
                .show(articleFrag)
                .addToBackStack(null)
                .commit();
    }

    public void back(View v) {
        onBackPressed();
    }
}
