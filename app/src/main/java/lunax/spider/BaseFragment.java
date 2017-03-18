package lunax.spider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Bamboo on 3/12/2017.
 */

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity parentActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity) context;
    }
}
