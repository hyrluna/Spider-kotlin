package lunax.spider.wallpaperlistpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import lunax.spider.BaseActivity;
import lunax.spider.R;

public class WallpaperListActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_LIST_URL = PACKAGE_NAME + ".WallpaperListActivity.img_list_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_list);

        String s = getIntent().getStringExtra(EXTRA_IMAGE_LIST_URL);
        ((TextView) findViewById(R.id.text)).setText(s);
    }
}
