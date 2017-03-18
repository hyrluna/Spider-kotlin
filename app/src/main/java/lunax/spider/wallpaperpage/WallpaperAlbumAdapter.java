package lunax.spider.wallpaperpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import lunax.spider.data.Album;
import lunax.spider.R;
import lunax.spider.common.SUtil;

/**
 * Created by Bamboo on 3/12/2017.
 */

public class WallpaperAlbumAdapter extends RecyclerView.Adapter<WallpaperAlbumAdapter.AlbumViewHolder> {

    private List<Album> mAlbums;
    private Context mContext;

    public WallpaperAlbumAdapter(List<Album> albums, Context context) {
        mAlbums = albums;
        mContext = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.bind(mAlbums.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return mAlbums == null ? 0 : mAlbums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAlbum;
        TextView tvTitle;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            imgAlbum = (ImageView) itemView.findViewById(R.id.album);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }

        public void bind(final Album album, Context context) {
            Glide.with(context)
                    .load(album.getImgUrl())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(imgAlbum);
            tvTitle.setText(album.getTitle());
            imgAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SUtil.showToast(v.getContext(), "click "+album.getTypeUrl());
                }
            });
        }

    }
}
