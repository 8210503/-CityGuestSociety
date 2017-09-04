package www.cityguestsociety.com.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by LuoPan on 2017/9/4 12:28.
 */

public class MyApplication extends Application {
    private static MyApplication context;
    public static boolean isRelase = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //         解决7.0不能打开照相机的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    public static MyApplication getContext() {
        return context;
    }
}
