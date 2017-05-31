package lunax.spider;

import dagger.Component;
import lunax.spider.data.SpiderRepositoryComponent;
import lunax.spider.homepage.HomePresenterModule;
import lunax.spider.wallpaperpage.WallpaperPresenterModule;

/**
 * Created by Bamboo on 5/31/2017.
 */

@FragmentScoped
@Component(dependencies = SpiderRepositoryComponent.class,
        modules = {WallpaperPresenterModule.class, HomePresenterModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
