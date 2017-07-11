package lunax.spider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lunax.spider.homepage.HomeFragment;
import lunax.spider.homepage.HomePresenter;
import lunax.spider.homepage.HomePresenterModule;
import lunax.spider.wallpaperpage.WallpaperFragment;
import lunax.spider.wallpaperpage.WallpaperPresenter;
import lunax.spider.wallpaperpage.WallpaperPresenterModule;

public class MainActivity extends BaseActivity
        implements HomeFragment.HomeFragmentListener {

    private static final String TAG_HOME_FRAGMENT = "tag_home_fragment";
    private static final String TAG_WALLPAPER_FRAGMENT = "tag_wallpaper_fragment";

    private List<Fragment> fragments = new ArrayList<>();

    @Inject
    HomePresenter mHomePresenter;

    @Inject
    WallpaperPresenter mWallpaperPresenter;

    private BottomNavigationView navigation;
    protected GestureDetectorCompat gestureDetector;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = fm.findFragmentByTag(TAG_HOME_FRAGMENT);
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = fm.findFragmentByTag(TAG_WALLPAPER_FRAGMENT);
                    break;
                case R.id.navigation_notifications:
                    break;
                default:
                    selectedFragment = fm.findFragmentByTag(TAG_HOME_FRAGMENT);
            }
            for (Fragment f : fragments) {
                transaction.hide(f);
            }
            if (selectedFragment != null) {
                transaction.show(selectedFragment);
                transaction.commit();
                return true;
            }
            transaction.commit();
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Spider");

        gestureDetector = new GestureDetectorCompat(this, new ScrollDetector());

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HomeFragment homeFragment = HomeFragment.newInstance("p1", "p2");
        WallpaperFragment wpFragment = WallpaperFragment.newInstance("p1", "p2");
        fragments.add(homeFragment);
        fragments.add(wpFragment);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content, homeFragment, TAG_HOME_FRAGMENT)
                .hide(homeFragment)
                .add(R.id.content, wpFragment,TAG_WALLPAPER_FRAGMENT)
                .hide(wpFragment)
                .show(homeFragment)
                .commit();

        DaggerMainComponent.builder()
                .spiderRepositoryComponent(mRepositoryComponent)
                .wallpaperPresenterModule(new WallpaperPresenterModule(wpFragment))
                .homePresenterModule(new HomePresenterModule(homeFragment))
                .build()
                .inject(this);

        mHomePresenter.setRefresh(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClick(View v) {
        HomeFragment f = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME_FRAGMENT);
        if (f != null && !f.isHidden()) {
            f.showArticleTypeList();
        }
    }

    private class ScrollDetector extends GestureDetector.SimpleOnGestureListener {

        final int GESTURE_THRESHOLD_DP = ViewConfiguration.get(getApplication()).getScaledTouchSlop();

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("test", "y = "+distanceY);
            Log.d("test", "e1y = "+e1.getY());
            Log.d("test", "e2y = "+e2.getY());
            return true;
        }
    }

}
