package lunax.spider.wallpaperpage;

import javax.inject.Scope;

import dagger.Component;
import lunax.spider.FragmentScoped;
import lunax.spider.MainActivity;
import lunax.spider.data.SpiderRepositoryComponent;

/**
 * Created by Bamboo on 3/13/2017.
 */

@FragmentScoped
@Component(dependencies = SpiderRepositoryComponent.class, modules = WallpaperPresenterModule.class)
public interface WallpaperComponent {
    void inject(MainActivity activity);
}
