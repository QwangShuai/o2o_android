package utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 创建日期：2017/7/28 on 13:38
 * 作者:孙明明
 * 描述:工具类
 */

public class Utils {

    public static void toast(Context c, String s) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }

    public static void log(Context c, String s) {
        Log.e(c.getClass().getSimpleName(), s);
    }

}
