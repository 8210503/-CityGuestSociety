package www.cityguestsociety.com.application;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.netease.scan.QrScan;
import com.netease.scan.QrScanConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.smtt.sdk.QbSdk;

import okhttp3.OkHttpClient;
import www.cityguestsociety.com.R;

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


        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        OkGo.getInstance().init(this)
                .setOkHttpClient(okBuilder.build())   //必须调用初始化//建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);
        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0


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

        /**二维码扫描*/
        // 自定义配置
        QrScanConfiguration configuration = new QrScanConfiguration.Builder(this)
                .setTitleHeight(53)
                .setTitleText("签到")
                .setTitleTextSize(18)
                .setTitleTextColor(R.color.white)
                .setTipText("将二维码放入框内扫描~")
                .setTipTextSize(14)
                .setTipMarginTop(40)
                .setTipTextColor(R.color.white)
                .setSlideIcon(R.mipmap.capture_add_scanning)
                .setAngleColor(R.color.white)
                .setMaskColor(R.color.balck)
                .setScanFrameRectRate((float) 0.8)
                .build();
        QrScan.getInstance().init(configuration);


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
