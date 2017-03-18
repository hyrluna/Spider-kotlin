package lunax.spider.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Bamboo on 3/12/2017.
 */

public class SUtil {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
