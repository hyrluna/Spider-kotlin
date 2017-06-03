package lunax.spider.homepage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import lunax.spider.BaseFragment;
import lunax.spider.R;
import lunax.spider.articlepage.ArticleActivity;
import lunax.spider.common.TransitionHelper;
import lunax.spider.data.dataitem.Article;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements HomeContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HomeContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private ArticleListAdapter mArticleListAdapter;
    private List<Article> mArticles = new ArrayList<>();
    private HomeFragmentListener listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(parentActivity));
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listener.onScroll(event);
                return false;
            }
        });

        mArticleListAdapter = new ArticleListAdapter(mArticles, parentActivity, mPresenter);
        mRecyclerView.setAdapter(mArticleListAdapter);
        return v;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.loadArticles("", "");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (listener != null) {
            listener = null;
        }
    }

    @Override
    public void showArticlesView(List<Article> articles) {
        swipeRefreshLayout.setRefreshing(false);
        mArticles.clear();
        mArticles.addAll(articles);
        mArticleListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showArticleDetail(Article article, ImageView v) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(parentActivity, false,
                new Pair<>(v, getString(R.string.transition_avatar)));
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(parentActivity, pairs);
        Intent intent = new Intent(parentActivity, ArticleActivity.class);
        intent.putExtra(ArticleActivity.EXTRA_ARTICLE, article);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mPresenter.setRefresh(true);
        mPresenter.loadArticles("", "");
    }

    public interface HomeFragmentListener {
        void onScroll(MotionEvent event);
    }
}
