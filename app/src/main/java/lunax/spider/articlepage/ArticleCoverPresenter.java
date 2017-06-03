package lunax.spider.articlepage;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lunax.spider.data.SpiderRepository;
import lunax.spider.data.dataitem.ArticleCover;

/**
 * Created by G1494458 on 2017/6/2.
 */

public class ArticleCoverPresenter implements ArticleCoverContract.Presenter {

    private SpiderRepository mRepository;
    private ArticleCoverContract.View mView;

    @Inject
    public ArticleCoverPresenter(SpiderRepository repository, ArticleCoverContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadArticleCover(String url) {
        mRepository.getArticleCover(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleCover>() {
                    @Override
                    public void accept(ArticleCover cover) throws Exception {
                        mView.showArticleCoverView(cover);
                    }
                });
    }

    @Override
    public void setRefresh(boolean isRefresh) {
        mRepository.setNeedRefresh(isRefresh);
    }
}
