package www.cityguestsociety.com.utils;

import android.util.Log;

import www.cityguestsociety.com.application.MyApplication;

/**
 * Created by LuoPan on 2017/9/4 12:25.
 */

public class L {
    public static void e(String tag,String msg) {
        if (!MyApplication.isRelase) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag,String msg) {
        if (!MyApplication.isRelase) {
            Log.e(tag, msg);
        }
    }
}
