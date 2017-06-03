package lunax.spider.articlepage;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import lunax.spider.BaseFragment;
import lunax.spider.R;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ArticleCover;
import lunax.spider.data.remote.NetworkRequest;
import lunax.spider.databinding.FragmentArticleCoverBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleCoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleCoverFragment extends BaseFragment implements ArticleCoverContract.View {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ARTICLE = "arg_article";
    private static final String ARG_PARAM2 = "param2";

    private Article mArticle;
    private String mParam2;
    private NetworkRequest request = new NetworkRequest();

    TextView wordCount;
    TextView hotMarking;
    private ArticleCoverContract.Presenter mPresenter;
//    private CoverFragmentListener mListener;
    FragmentArticleCoverBinding mBinding;

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
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof CoverFragmentListener) {
//            mListener = (CoverFragmentListener) context;
//        } else {
//            throw new IllegalArgumentException("CoverFragmentListener Exception");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        if (mListener != null) {
//            mListener = null;
//        }
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
        mBinding = FragmentArticleCoverBinding.inflate(inflater, container, false);
//        View v = inflater.inflate(R.layout.fragment_article_cover, container, false);
//        ImageView avatar = (ImageView) v.findViewById(R.id.avatar);
//        TextView title = (TextView) v.findViewById(R.id.title);
//        TextView subtitle = (TextView) v.findViewById(R.id.subtitle);
//        TextView author = (TextView) v.findViewById(R.id.author);
//        TextView type = (TextView) v.findViewById(R.id.type);
//        Button btnReadArticle = (Button) v.findViewById(R.id.read_article_button);
        mBinding.setArticle(mArticle);
        mBinding.readArticleButton.setScaleX(0);
        mBinding.readArticleButton.setScaleY(0);
//        btnReadArticle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.toReadArticle();
//            }
//        });

//        wordCount = (TextView) v.findViewById(R.id.word_count);
//        hotMarking = (TextView) v.findViewById(R.id.hot_marking);

        Glide.with(parentActivity)
                .load(mArticle.getAvatar())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(mBinding.avatar);

//        title.setText(mArticle.getTitle());
//        subtitle.setText(mArticle.getSubtitle());
//        author.setText(mArticle.getAuthor());
//        type.setText(mArticle.getType());

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadArticleCover(mArticle.getArticleUrl());
    }

    @Override
    public void showArticleCoverView(ArticleCover cover) {
        mBinding.setCover(cover);
        mBinding.hotMarking.setText(Html.fromHtml(cover.getHotMark()));
        mBinding.readArticleButton.animate()
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(500)
                .start();
        mBinding.hotMarking.setAlpha(0);
        mBinding.hotMarking.setTranslationY(200);
        mBinding.hotMarking.animate()
                .alpha(1)
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(500)
                .start();

    }

    @Override
    public void setPresenter(ArticleCoverContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
    }

    //    public interface CoverFragmentListener {
//        void toReadArticle();
//    }
}
