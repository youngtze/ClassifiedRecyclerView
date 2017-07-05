package young.tze.appstat;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Yangzhi on 2017-06-26.
 */

public class Utilities {
    private static DisplayMetrics mDM;

    public static String trimLeading(String str) {
        int start = 0;
        for (int i = 0; i < str.length(); i++) {
            //裁剪掉字符串开头不是英文或者数字的字符
            if (!(Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i)))) {
                start++;
                continue;
            }
            break;
        }
        return str.substring(start, str.length());
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (mDM == null) {
            mDM = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(mDM);
        }
        return mDM;
    }
}
