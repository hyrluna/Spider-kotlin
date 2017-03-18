package lunax.spider.data.remote;

import dagger.Component;

/**
 * Created by Bamboo on 3/18/2017.
 */

@Component
public interface NetworkComponent {
    void inject(SpiderRemoteDataSource spiderRemoteDataSource);
}
