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

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private List<Article> mArticles;
    private Context mContext;
    private HomeContract.Presenter mPresenter;

    public ArticleListAdapter(List<Article> articles, Context context, HomeContract.Presenter presenter) {
        this.mArticles = articles;
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(mArticles.get(position), mContext, mPresenter);
    }

    @Override
    public int getItemCount() {
        return mArticles != null ? mArticles.size() : 0;
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
