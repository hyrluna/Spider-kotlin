package lunax.spider.homepage;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lunax.spider.BaseFragment;
import lunax.spider.R;
import lunax.spider.articlepage.ArticleActivity;
import lunax.spider.common.SUtil;
import lunax.spider.common.TransitionHelper;
import lunax.spider.data.dataitem.Article;
import lunax.spider.data.dataitem.ImageSrc;
import lunax.spider.data.remote.NetworkRequest;
import lunax.spider.wallpaperpage.WallpaperFragment;

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

    private Pair[] articleTypes = {
            Pair.create("全部小说", NetworkRequest.ARTICLE_TYPE_ALL_NOVEL),
            Pair.create("爱情小说", NetworkRequest.ARTICLE_TYPE_LOVE),
            Pair.create("青春小说", NetworkRequest.ARTICLE_TYPE_QINGCHUN),
            Pair.create("文艺小说", NetworkRequest.ARTICLE_TYPE_WENYI),
            Pair.create("都市小说", NetworkRequest.ARTICLE_TYPE_DUSHI),
            Pair.create("科幻小说", NetworkRequest.ARTICLE_TYPE_KEHUAN),
            Pair.create("幻想小说", NetworkRequest.ARTICLE_TYPE_HUANXIANG),
            Pair.create("武侠小说", NetworkRequest.ARTICLE_TYPE_WUXIA),
            Pair.create("悬疑小说", NetworkRequest.ARTICLE_TYPE_XUANYI),
            Pair.create("推理小说", NetworkRequest.ARTICLE_TYPE_TUILI),
            Pair.create("历史小说", NetworkRequest.ARTICLE_TYPE_HISTORY),
            Pair.create("故乡小说", NetworkRequest.ARTICLE_TYPE_GUXIANG),
            Pair.create("海外小说", NetworkRequest.ARTICLE_TYPE_HAIWAI),
            Pair.create("职业故事", NetworkRequest.ARTICLE_TYPE_ZHIYE),
            Pair.create("喜剧故事", NetworkRequest.ARTICLE_TYPE_XIJU),
            Pair.create("图画故事", NetworkRequest.ARTICLE_TYPE_TUHUA),
            Pair.create("非小说", NetworkRequest.ARTICLE_TYPE_ALL_NO_NOVEL),
            Pair.create("文艺散文", NetworkRequest.ARTICLE_TYPE_WENYI_SANWEN),
    };
    private String currentType;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HomeContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fabSelectType;
    private ArticleListAdapter mArticleListAdapter;
    private List<Article> mArticles = new ArrayList<>();
    private HomeFragmentListener listener;
    private AlertDialog mTypeDialog;

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
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isAnimEnd = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                ArticleListAdapter adapter = (ArticleListAdapter) recyclerView.getAdapter();
                //滚动到最底部
                if (layoutManager.findLastCompletelyVisibleItemPosition() == mArticles.size()) {
                    if (adapter.hasMore()) {
                        mPresenter.setRefresh(true);
                        mPresenter.loadArticles(currentType, adapter.getNextPage());
                    } else {
                        adapter.removeFooter();
                    }
                }

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
            currentType = NetworkRequest.ARTICLE_TYPE_WENYI;
            mPresenter.loadArticles(currentType, "0");
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
    public void onStop() {
        super.onStop();
        if (mTypeDialog != null) {
            mTypeDialog.cancel();
            mTypeDialog = null;
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
        mArticleListAdapter.recoverFooter();
        mArticleListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreArticles(List<Article> articles) {
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
        mPresenter.loadArticles(currentType, "0");
    }

    public void showArticleTypeList() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        View v = parentActivity.getLayoutInflater().inflate(R.layout.item_dialog_article_type_list, null);
        GridView gridView = (GridView) v.findViewById(R.id.article_type_list);
        gridView.setAdapter(new ArticleTypeAdapter(parentActivity, articleTypes));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, String> item = (Pair<String, String>) parent.getItemAtPosition(position);
                String type = item.second;
                currentType = type;
                mPresenter.setRefresh(true);
                mPresenter.loadArticles(type, "0");
                if (mTypeDialog.isShowing())
                    mTypeDialog.hide();
            }
        });
        builder.setView(v);
        mTypeDialog = builder.create();
        mTypeDialog.show();
    }

    private class ArticleTypeAdapter extends ArrayAdapter<Pair<String, String>> {

        public ArticleTypeAdapter(@NonNull Context context, Pair<String, String>[] types) {
            super(context, 0, types);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Pair<String, String> type = getItem(position);
            if (convertView == null) {
                convertView = parentActivity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            if (type != null)
                tv.setText(type.first);
            return convertView;
        }
    }

    public interface HomeFragmentListener {
    }
}
