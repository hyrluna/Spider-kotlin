package lunax.spider.wallpaperpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import lunax.spider.R;
import lunax.spider.data.dataitem.Wallpaper;

/**
 * Created by Bamboo on 3/12/2017.
 */

public class WallpaperListAdapter extends RecyclerView.Adapter<WallpaperListAdapter.AlbumItemViewHolder> {

    private List<Wallpaper> mUrls;
    private Context mContext;
    private WallpaperContract.Presenter mPresenter;

    public WallpaperListAdapter(List<Wallpaper> urls, Context context, WallpaperContract.Presenter presenter) {
        mUrls = urls;
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public AlbumItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_album, parent, false);
        return new AlbumItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumItemViewHolder holder, int position) {
        holder.bind(mUrls.get(position), mContext, mPresenter);
    }

    @Override
    public int getItemCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    public static class AlbumItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAlbum;
        TextView tvTitle;
        ImageButton imgBtnDownload;

        public AlbumItemViewHolder(View itemView) {
            super(itemView);
            imgAlbum = (ImageView) itemView.findViewById(R.id.album);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            imgBtnDownload = (ImageButton) itemView.findViewById(R.id.download_button);
        }

        public void bind(final Wallpaper wallpaper, Context context, final WallpaperContract.Presenter presenter) {
            Glide.with(context)
                    .load(wallpaper.getImgUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(imgAlbum);
            tvTitle.setText("title");

            imgBtnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.downloadWallpaper(wallpaper.getHref());
                }
            });
        }

    }
}
