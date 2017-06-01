package lunax.spider.homepage;

import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lunax.spider.data.SpiderRepository;
import lunax.spider.data.dataitem.Article;

/**
 * Created by Bamboo on 5/31/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    private SpiderRepository mRepository;
    private HomeContract.View mView;

    @Inject
    public HomePresenter(SpiderRepository mRepository, HomeContract.View mView) {
        this.mRepository = mRepository;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadArticles(String fold, String subfold) {
        mRepository.getArticles(fold, subfold)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(List<Article> articles) throws Exception {
                        mView.showArticlesView(articles);
                    }
                });
    }

    @Override
    public void start() {

    }

    @Override
    public void loadArticleDetail(Article article, ImageView v) {
        mView.showArticleDetail(article, v);
    }
}
