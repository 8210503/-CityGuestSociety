package www.cityguestsociety.com.shared;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Formatter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;
import com.wenming.library.NotifyUtil;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import top.zibin.luban.OnCompressListener;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.fragments.ThridFragment;
import www.cityguestsociety.com.thirdfragemntActivity.SendSharedActivity;
import www.cityguestsociety.com.utils.Constans;


/**
 * Created by LuoPan on 2017/7/4.
 */

public class UpLoadServices extends IntentService {
    private static final int NOTIFICATION_ID = 100;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png/gif");
    private NotifyUtil currentNotify;
    public static String content;
    ArrayList<ImageItem> imageItemList;
    private boolean isFinish = false;
    int imageItemListSize = 0;


    public UpLoadServices() {
        super("UpLoad");

    }

    public UpLoadServices(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        imageItemList = (ArrayList<ImageItem>) intent.getSerializableExtra(SendSharedActivity.IMAGELISTS);
        content = intent.getStringExtra(SendSharedActivity.CONTENT);
        if (imageItemList.size() == 0) {


        } else {
            final List<File> fileList = new ArrayList<>();
            final List<File> gifLists = new ArrayList<>();
            imageItemListSize = imageItemList.size();

            List<String> imageLists = new ArrayList<>();
            for (int i = 0; i < imageItemList.size(); i++) {
               /* if (imageItemList.get(i).name.endsWith("gif")) {
                    *//**如果是以gif结尾的动图 就不去压缩*//*
                    fileList.add(new File(imageItemList.get(i).path));
                } else {*/
                imageLists.add(imageItemList.get(i).path);
                //                }
            }
            File DatalDir = Environment.getExternalStorageDirectory();
            File myDir = new File(DatalDir, "/DCIM/北京城建");
            myDir.mkdirs();
            Luban_Self.with(this)
                    .load(imageLists)                                   // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    .setTargetDir(myDir.getPath())                        // 设置压缩后文件存储位置
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            fileList.add(file);

                            LogUtils.e(fileList.size() + "-----" + imageItemList.size());

                            if (fileList.size() == imageItemList.size()) {
                                sendShared(fileList);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            Intent intent = new Intent();
                            intent.setAction(SendSharedActivity.mFialed);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            Toast.makeText(getApplicationContext(), "图片路径有误", Toast.LENGTH_SHORT).show();

                        }
                    }).launch();    //启动压缩

        }

    }

    public void sendShared(List<File> fileList) {
        final NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        OkGo.<String>post(UrlFactory.subShare)
                .tag(this)//
                .params("member_id", Constans.ID)
                .params("content", content)
                .addFileParams("file[]", fileList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        JSONObject object = JSON.parseObject(response.body());
                        LogUtils.e(object.toString());
                        if (object.getInteger("code") == 1) {
                            Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_SHORT).show();
                            sendNotification("发送成功", "", false);

                            Intent intent = new Intent();
                            intent.setAction(ThridFragment.mAction);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                            intent = new Intent();
                            intent.setAction(SendSharedActivity.mAction);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), object.getString(object.getString("info")), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        sendNotification("发布中", "上传进度：" + numberFormat.format(progress.fraction) + "      " +
                                String.format("%s/s", Formatter.formatFileSize(getApplicationContext(), progress.speed)), false);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        Toast.makeText(getApplicationContext(), "上传中", Toast.LENGTH_SHORT).show();
                        LogUtils.e(request.getParams().toString());
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {

                        LogUtils.e(response.body());

                        Intent intent = new Intent();
                        intent.setAction(SendSharedActivity.mFialed);
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                        Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_SHORT).show();
                        cancelNotification();
                        sendNotification("发布失败", "点击重新发送", true);

                    }

                    @Override
                    public void onFinish() {


                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFinish = false;

    }

    /**
     * 发通知
     */
    public void sendNotification(String message, String text, boolean isVibrate) {

        Intent intent = new Intent(MyApplication.getContext(), SendSharedActivity.class);
        intent.putExtra(SendSharedActivity.IMAGELISTS, imageItemList);
        intent.putExtra(SendSharedActivity.CONTENT, content);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(MyApplication.getContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.mipmap.login_bg;
        String ticker = "您有一条新通知";
        String title = message;
        String content = text;

        //实例化工具类，并且调用接口
        NotifyUtil notify1 = new NotifyUtil(getApplicationContext(), NOTIFICATION_ID);
        if (isVibrate) {
            notify1.notify_normal_singline(pIntent, smallIcon, ticker, title, content, true, isVibrate, false);
        } else {
            notify1.notify_normal_singline(null, smallIcon, ticker, title, content, false, isVibrate, false);
        }


        currentNotify = notify1;

    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        if (currentNotify != null) {
            currentNotify.clear();
        }

    }


}
