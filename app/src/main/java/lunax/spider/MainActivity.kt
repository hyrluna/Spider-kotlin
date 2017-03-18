package lunax.spider

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import java.util.ArrayList

import javax.inject.Inject

import lunax.spider.homepage.HomeFragment
import lunax.spider.wallpaperpage.DaggerWallpaperComponent
import lunax.spider.wallpaperpage.WallpaperFragment
import lunax.spider.wallpaperpage.WallpaperPresenter
import lunax.spider.wallpaperpage.WallpaperPresenterModule

class MainActivity : AppCompatActivity() {

    private val fragments = ArrayList<Fragment>()

    @Inject
    internal var mWallpaperPresenter: WallpaperPresenter? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_home -> selectedFragment = fm.findFragmentByTag(TAG_HOME_FRAGMENT)
            R.id.navigation_dashboard -> selectedFragment = fm.findFragmentByTag(TAG_WALLPAPER_FRAGMENT)
            R.id.navigation_notifications -> {
            }
        }
        for (f in fragments) {
            transaction.hide(f)
        }
        if (selectedFragment != null) {
            transaction.show(selectedFragment)
            transaction.commit()
            return@OnNavigationItemSelectedListener true
        }
        transaction.commit()
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment.newInstance("p1", "p2")
        val wpFragment = WallpaperFragment.newInstance("p1", "p2")
        fragments.add(homeFragment)
        fragments.add(wpFragment)
        val fm = supportFragmentManager
        fm.beginTransaction()
                .add(R.id.content, homeFragment, TAG_HOME_FRAGMENT)
                .show(homeFragment)
                .add(R.id.content, wpFragment, TAG_WALLPAPER_FRAGMENT)
                .hide(wpFragment)
                .commit()

        DaggerWallpaperComponent.builder()
                .spiderRepositoryComponent((application as SpiderApplication).spiderRepositoryComponent)
                .wallpaperPresenterModule(WallpaperPresenterModule(wpFragment)).build()
                .inject(this)

        mWallpaperPresenter!!.test()

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_WALLPAPER_FRAGMENT) as WallpaperFragment
        if (fragment.isSelectorShow) {
            fragment.closePopSelector()
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        private val TAG_HOME_FRAGMENT = "tag_home_fragment"
        private val TAG_WALLPAPER_FRAGMENT = "tag_wallpaper_fragment"
    }

}
