package lunax.spider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import lunax.spider.data.SpiderRepository;
import lunax.spider.data.SpiderRepositoryComponent;

/**
 * Created by Bamboo on 3/18/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected static final String PACKAGE_NAME = "lunax.spider";
    protected SpiderRepositoryComponent mRepositoryComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepositoryComponent = ((SpiderApplication) getApplication()).getSpiderRepositoryComponent();
    }
}
