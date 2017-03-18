package lunax.spider;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bamboo on 3/13/2017.
 */

@Module
public final class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
