package www.cityguestsociety.com.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.apkfuns.logutils.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by LuoPan on 2017/9/4 12:28.
 */

public class MyApplication extends Application {
    private static MyApplication context;
    public static boolean isRelase = false;
    public static UserInfo info;
    public static String thisCityName = "成都";
    public static String cityName = "成都";

    public static int getCount = 5;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        /**初始化ImageLoader*/
        initImageLoader();
        preinitX5WebCore();
        info = new UserInfo();

        //         解决7.0不能打开照相机的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("CityGuestSociety")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");

    }

    public static MyApplication getContext() {
        return context;
    }

    private void preinitX5WebCore() {
        LogUtils.d("preinitX5WebCore");
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
    }

    void initImageLoader() {
        LogUtils.d("preinitX5WebCore");
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(configuration);

    }

    public static UserInfo getUser() {
        return info;
    }

    public static class UserInfo {
        /* nickname	会员昵称
         integral	会员积分
         gender	性别   1:男  2：女  3：保密
         img	会员头像
         username	手机号*/
        public String nickname;
        public String integral;
        public String gender;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String img;
        public String username;
        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }



        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
