package lunax.spider.data.local;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import lunax.spider.data.Album;
import lunax.spider.data.SpiderDataSource;

/**
 * Created by Bamboo on 3/14/2017.
 */

public class SpiderLocalDataSource implements SpiderDataSource {


    @Inject
    public SpiderLocalDataSource(Context context) {
    }

    @Override
    public Observable<Album> getAlbums(String type) {
        return null;
    }
}
