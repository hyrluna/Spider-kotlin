package lunax.spider.data;

import javax.inject.Inject;

import lunax.spider.data.local.SpiderLocalDataSource;
import lunax.spider.data.remote.SpiderRemoteDataSource;

/**
 * Created by Bamboo on 3/13/2017.
 */

public class SpiderRepository implements SpiderDataSource {

    private SpiderLocalDataSource mSpiderLocalDataSource;
    private SpiderRemoteDataSource mSpiderRemoteDataSource;

    @Inject
    public SpiderRepository(SpiderLocalDataSource spiderLocalDataSource,
                            SpiderRemoteDataSource spiderRemoteDataSource) {
        mSpiderLocalDataSource = spiderLocalDataSource;
        mSpiderRemoteDataSource = spiderRemoteDataSource;
    }
}
