package lunax.spider;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lunax.spider.homepage.HomeFragment;
import lunax.spider.wallpaperpage.DaggerWallpaperComponent;
import lunax.spider.wallpaperpage.WallpaperFragment;
import lunax.spider.wallpaperpage.WallpaperPresenter;
import lunax.spider.wallpaperpage.WallpaperPresenterModule;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_HOME_FRAGMENT = "tag_home_fragment";
    private static final String TAG_WALLPAPER_FRAGMENT = "tag_wallpaper_fragment";

    private List<Fragment> fragments = new ArrayList<>();

    @Inject
    WallpaperPresenter mWallpaperPresenter;

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

        HomeFragment homeFragment = HomeFragment.newInstance("p1", "p2");
        WallpaperFragment wpFragment = WallpaperFragment.newInstance("p1", "p2");
        fragments.add(homeFragment);
        fragments.add(wpFragment);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content, homeFragment, TAG_HOME_FRAGMENT)
                .show(homeFragment)
                .add(R.id.content, wpFragment,TAG_WALLPAPER_FRAGMENT)
                .hide(wpFragment)
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DaggerWallpaperComponent.builder()
                .spiderRepositoryComponent(((SpiderApplication) getApplication()).getSpiderRepositoryComponent())
                .wallpaperPresenterModule(new WallpaperPresenterModule(wpFragment))
                .build()
                .inject(this);

    }

    @Override
    public void onBackPressed() {
        WallpaperFragment fragment = (WallpaperFragment)
                getSupportFragmentManager().findFragmentByTag(TAG_WALLPAPER_FRAGMENT);
        if (fragment.isSelectorShow()) {
            fragment.closePopSelector();
        } else {
            super.onBackPressed();
        }
    }

}
