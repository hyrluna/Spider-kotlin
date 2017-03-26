package lunax.spider.wallpaperpage;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import lunax.spider.data.dataitem.Album;
import lunax.spider.R;
import lunax.spider.common.SUtil;
import lunax.spider.data.dataitem.Wallpaper;

/**
 * Created by Bamboo on 3/12/2017.
 */

public class WallpaperAlbumAdapter extends RecyclerView.Adapter<WallpaperAlbumAdapter.AlbumViewHolder> {

    private List<Album> mAlbums;
    private Context mContext;
    private WallpaperContract.Presenter mPresenter;

    public WallpaperAlbumAdapter(List<Album> albums, Context context, WallpaperContract.Presenter presenter) {
        mAlbums = albums;
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_album_container, parent, false);
        return new AlbumViewHolder(v, mContext, mPresenter);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.bind(mAlbums.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbums == null ? 0 : mAlbums.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvAlbumContainer;
        WallpaperListAdapter adapter;
        List<Wallpaper> items;

        public AlbumViewHolder(View itemView, Context context, WallpaperContract.Presenter presenter) {
            super(itemView);
//            imgAlbum = (ImageView) itemView.findViewById(R.id.album);
//            tvTitle = (TextView) itemView.findViewById(R.id.title);
            rvAlbumContainer = (RecyclerView) itemView.findViewById(R.id.album_container);
            items = new ArrayList<>();
            adapter = new WallpaperListAdapter(items, context, presenter);
            rvAlbumContainer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            rvAlbumContainer.setAdapter(adapter);
        }

        public void bind(Album album) {
//
//            List<String> ts = new ArrayList<>();
//            ts.add("one");
//            ts.add("two");
//            ts.add("three");
//            ts.add("four");

            items.clear();
            items.addAll(album.getWallpapers());
            adapter.notifyDataSetChanged();
        }

    }
}
