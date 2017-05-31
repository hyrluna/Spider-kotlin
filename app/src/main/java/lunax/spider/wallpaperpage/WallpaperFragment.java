package lunax.spider.wallpaperpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lunax.spider.common.SUtil;
import lunax.spider.data.dataitem.Album;
import lunax.spider.BaseFragment;
import lunax.spider.R;
import lunax.spider.data.dataitem.ImageSrc;
import lunax.spider.data.remote.NetworkRequest;
import lunax.spider.wallpaperlistpage.WallpaperListActivity;
import lunax.spider.widget.Fab;
import lunax.spider.widget.MaterialSheetFab;
import lunax.spider.widget.MaterialSheetFabEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WallpaperFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WallpaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WallpaperFragment extends BaseFragment
        implements WallpaperContract.View, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Album> mAlbums;
    boolean isClicked = true;
    private int statusBarColor;

    private PopupWindow popUpWindow;
    private MaterialSheetFab materialSheetFab;
//    private FloatingActionButton btnImgSerch;
    private RecyclerView recyclerView;
    private WallpaperAlbumAdapter mAlbumAdapter;

    private WallpaperContract.Presenter mPresenter;

//    private OnFragmentInteractionListener mListener;

    public WallpaperFragment() {
        // Required empty public constructor
    }

    public static WallpaperFragment newInstance(String param1, String param2) {
        WallpaperFragment fragment = new WallpaperFragment();
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
//        testJsoup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        final View mainLayout = v.findViewById(R.id.wallpaper_fragment);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleView);
//        btnImgSerch = (FloatingActionButton) v.findViewById(R.id.image_search_button);
        popUpWindow = new PopupWindow(parentActivity);

        View popSelectorView = inflater.inflate(R.layout.selector_tag_cloud, null);
        popUpWindow.setContentView(popSelectorView);

        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity));
        mAlbums = new ArrayList<>();
        mAlbumAdapter = new WallpaperAlbumAdapter(mAlbums, parentActivity, mPresenter);
        recyclerView.setAdapter(mAlbumAdapter);

        Fab fab = (Fab) v.findViewById(R.id.fab);
        View sheetView = v.findViewById(R.id.fab_sheet);
        View overlay = v.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.theme_accent);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners
        v.findViewById(R.id.fab_sheet_item_recording).setOnClickListener(this);
        v.findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this);
        v.findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        v.findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);

//        btnImgSerch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
//                if (isClicked) {
//                    isClicked = false;
////                    popUpWindow.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
//                    popUpWindow.showAtLocation(parentActivity.findViewById(R.id.container), Gravity.CENTER, 0, 0);
////                    popUpWindow.update(50, 50, 320, 90);
//                } else {
//                    isClicked = true;
//                    popUpWindow.dismiss();
//                }
//            }
//        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.loadAlbums(NetworkRequest.QUERY_TYPE_GIRL);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(parentActivity, R.string.sheet_item_pressed, Toast.LENGTH_SHORT).show();
        materialSheetFab.hideSheet();
    }

    @Override
    public void setPresenter(WallpaperContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAlbumsView(List<Album> albums) {
        mAlbums.clear();
        mAlbums.addAll(albums);
        mAlbumAdapter.notifyDataSetChanged();
    }

    @Override
    public void showWallpaperDownloadSelectView(List<ImageSrc> urls) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        View v = parentActivity.getLayoutInflater().inflate(R.layout.item_dialog_img_src_list, null);
        ListView listView = (ListView) v.findViewById(R.id.img_src_list);
        listView.setAdapter(new ImageSrcAdapter(parentActivity, urls));
        builder.setView(v);
        builder.show();
    }

    @Override
    public void showLoadingIndicator(boolean isShow) {
        showProgressIndicator(isShow);
    }

    public void closePopSelector() {
        popUpWindow.dismiss();
    }

    public boolean isSelectorShow() {
        return popUpWindow.isShowing();
    }

    private class ImageSrcAdapter extends ArrayAdapter<ImageSrc> {
        public ImageSrcAdapter(@NonNull Context context, List<ImageSrc> imageSrcs) {
            super(context, 0, imageSrcs);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ImageSrc imageSrc = getItem(position);
            if (convertView == null) {
                convertView = parentActivity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            tv.setText(imageSrc.getSize());
            return convertView;
        }
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return parentActivity.getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            parentActivity.getWindow().setStatusBarColor(color);
        }
    }

    private void testJsoup() {

        Observable.just("http://download.pchome.net/wallpaper/meinv/")
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        crawlTypePage(s);
                        return Observable.just(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("test", "accept: "+s);
                    }
                });

    }

    public void crawlTypePage(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
        Log.d("test", "connected");
        Elements uls = doc.select("ul");
        for (Element e : uls) {
            if (e.attr("class").equals("pic-list2 clearfix")) {
                Elements links = e.select("a");
                for (Element link : links) {
                    String typeUrl = link.absUrl("href");
                    Log.d("test", "banner url: "+link.absUrl("href"));
                    crawlImagePage(typeUrl);
                }
            }
        }
    }

    public void crawlImagePage(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
        Elements photos = doc.select("div");
        for (Element p : photos) {
            if (p.attr("class").equals("photo")) {
                //当前显示图片
                String imageUrl = p.select("img").first().attr("src");
//                Log.d("test", "url: "+imageUrl+", "+p.select("img").first().attr("alt"));
            }
        }
        Elements eImgs = doc.select("dd");
        for (Element eImg : eImgs) {
            if (eImg.attr("id").equals("tagfbl")) {
                Elements imgSizeEls = eImg.select("a");
                for (Element imgSizeEl : imgSizeEls) {
                    //当前图片可选的尺寸列表
                    String imageSizeUrl = imgSizeEl.absUrl("href");
                    String sizeTxt = imgSizeEl.attr("id");
//                    Log.d("test", "url: "+imageSizeUrl+", "+sizeTxt);
                }
            }
        }
        //抓取一组图片的列表
        List<String> hrefs = new ArrayList<>();
        Elements uls = doc.select("ul");
        for (Element ul : uls) {
            if (ul.attr("id").equals("showImg")) {
                Elements imgELs = ul.select("a");
                for (Element imgEl : imgELs) {
                    hrefs.add(imgEl.absUrl("href"));
                    Log.d("test", "img url: "+imgEl.absUrl("href"));
                }
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
