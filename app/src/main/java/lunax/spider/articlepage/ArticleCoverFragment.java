package lunax.spider.articlepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lunax.spider.BaseFragment;
import lunax.spider.R;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ArticleCover;
import lunax.spider.data.remote.NetworkRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleCoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleCoverFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ARTICLE = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Article mArticle;
    private String mParam2;
    private NetworkRequest request = new NetworkRequest();

    TextView wordCount;
    TextView hotMarking;

    public ArticleCoverFragment() {
        // Required empty public constructor
    }

    public static ArticleCoverFragment newInstance(Article article, String param2) {
        ArticleCoverFragment fragment = new ArticleCoverFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTICLE, article);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = getArguments().getParcelable(ARG_ARTICLE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article_cover, container, false);
        ImageView avatar = (ImageView) v.findViewById(R.id.avatar);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView subtitle = (TextView) v.findViewById(R.id.subtitle);
        TextView author = (TextView) v.findViewById(R.id.author);
        TextView type = (TextView) v.findViewById(R.id.type);
        wordCount = (TextView) v.findViewById(R.id.word_count);
        hotMarking = (TextView) v.findViewById(R.id.hot_marking);

        Glide.with(parentActivity)
                .load(mArticle.getAvatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(avatar);

        title.setText(mArticle.getTitle());
        subtitle.setText(mArticle.getSubtitle());
        author.setText(mArticle.getAuthor());
        type.setText(mArticle.getType());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        request.getArticleCover(mArticle.getArticleUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleCover>() {
                    @Override
                    public void accept(ArticleCover cover) throws Exception {
                        wordCount.setText("字数: " + cover.getWordCount());
                        hotMarking.setMovementMethod(ScrollingMovementMethod.getInstance());
                        hotMarking.setText(Html.fromHtml(cover.getHotMarking()));
                    }
                });
    }
}
