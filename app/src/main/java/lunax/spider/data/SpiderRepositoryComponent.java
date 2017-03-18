package lunax.spider.data;

import javax.inject.Singleton;

import dagger.Component;
import lunax.spider.ApplicationModule;

/**
 * Created by Bamboo on 3/14/2017.
 */

@Singleton
@Component(modules = {SpiderRepositoryModule.class, ApplicationModule.class})
public interface SpiderRepositoryComponent {
    SpiderRepository getSpiderRepository();
}
