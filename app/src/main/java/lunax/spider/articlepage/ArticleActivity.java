package lunax.spider.articlepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lunax.spider.BaseActivity;
import lunax.spider.R;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.remote.NetworkRequest;

public class ArticleActivity extends BaseActivity {

    public static final String EXTRA_ARTICLE = PACKAGE_NAME + ".ArticleActivity.extra_article_url";

    private static final String TAG_ARTICLE_COVER_FRAGMENT = "tag_article_cover_fragment";
    private static final String TAG_ARTICLE_FRAGMENT = "tag_article_fragment";
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Article article = getIntent().getParcelableExtra(EXTRA_ARTICLE);

        ArticleCoverFragment articleCoverFragment = ArticleCoverFragment.newInstance(article, "p2");
        ArticleFragment articleFragment = ArticleFragment.newInstance(article.getArticleUrl(), "p2");
        fragments.add(articleCoverFragment);
        fragments.add(articleFragment);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content, articleCoverFragment, TAG_ARTICLE_COVER_FRAGMENT)
                .show(articleCoverFragment)
                .add(R.id.content, articleFragment, TAG_ARTICLE_FRAGMENT)
                .hide(articleFragment)
                .commit();

    }
}
