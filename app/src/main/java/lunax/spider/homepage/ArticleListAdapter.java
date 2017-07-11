package lunax.spider.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import lunax.spider.R;
import lunax.spider.data.dataitem.Article;

/**
 * Created by G1494458 on 2017/5/31.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL =  0;
    private static final int TYPE_LOAD_MORE =  1;

    private int currentPage;
    private int itemCount;
    private int pageMax;
    private boolean hasFooter = true;

    private List<Article> mArticles;
    private Context mContext;
    private HomeContract.Presenter mPresenter;

    public ArticleListAdapter(List<Article> articles, Context context, HomeContract.Presenter presenter) {
        this.mArticles = articles;
//        if (mArticles != null) {
//            itemCount = mArticles.size() + 1;
//            if (mArticles.size() > 0) {
//                pageMax = mArticles.get(0).getPageCount();
//            }
//        } else {
//            itemCount = 0;
//        }
        mContext = context;
        mPresenter = presenter;
        currentPage = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = null;
        switch (viewType) {
            case TYPE_NORMAL:
                v = inflater.inflate(R.layout.item_article, parent, false);
                return new ArticleViewHolder(v);
            case TYPE_LOAD_MORE:
                v = inflater.inflate(R.layout.foot_recycler_view, parent, false);
                return new FooterViewHolder(v);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL:
                ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
                if (position < mArticles.size()) {
                    articleViewHolder.bind(mArticles.get(position), mContext, mPresenter);
                }
                break;
            case TYPE_LOAD_MORE:

                break;
        }

    }

    @Override
    public int getItemCount() {
        itemCount = 0;
        if (mArticles != null) {
            if (mArticles.size() > 0) {
                pageMax = mArticles.get(0).getPageCount() - 1;
            }

            if (hasFooter) {
                itemCount = mArticles.size() + 1;
            } else {
                itemCount = mArticles.size();
            }
        }
        return itemCount;
//        return mArticles != null ? mArticles.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mArticles.size()) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_NORMAL;
        }
    }

    public boolean hasMore() {
        if (mArticles != null && mArticles.size() > 0 && currentPage < pageMax) {
            return true;
        } else {
            return false;
        }
    }

    public String getNextPage() {
        if (currentPage < pageMax) {
            return (++ currentPage)*20 + "";
        } else {
            return "";
        }
    }

    public void recoverFooter() {
        hasFooter = true;
    }

    public void removeFooter() {
        //Adapter数据为空时不能删除脚标
        if (mArticles != null && mArticles.size() > 0 ) {
            hasFooter = false;
            notifyDataSetChanged();
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        ImageView imgvAvatar;
        TextView tvTitle;
        TextView tvSubtitle;
        TextView tvDesc;
        TextView tvAuthor;
        RatingBar ratingBar;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            imgvAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            tvTitle = (TextView) itemView.findViewById(R.id.article_title);
            tvSubtitle = (TextView) itemView.findViewById(R.id.subtitle);
            tvDesc = (TextView) itemView.findViewById(R.id.desc);
            tvAuthor = (TextView) itemView.findViewById(R.id.author);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        public void bind(final Article article, Context context, final HomeContract.Presenter presenter) {
            Glide.with(context)
                    .load(article.getAvatar())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(imgvAvatar);
            tvTitle.setText(article.getTitle());
            tvSubtitle.setText(article.getSubtitle());
            tvTitle.setText(article.getTitle());
            tvDesc.setText(article.getDescription());
            tvAuthor.setText(article.getAuthor());
            ratingBar.setRating(Float.parseFloat(article.getRating()) / 2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.loadArticleDetail(article, imgvAvatar);
                }
            });
        }
    }
}
