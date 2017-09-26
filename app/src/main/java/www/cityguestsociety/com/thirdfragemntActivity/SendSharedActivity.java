package www.cityguestsociety.com.thirdfragemntActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.SendSharedGirdAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.shared.GlideLoader;
import www.cityguestsociety.com.shared.UpLoadServices;
import www.cityguestsociety.com.utils.CacheUtils;

public class SendSharedActivity extends BaseToolbarActivity {


    @BindView(R.id.sendContent)
    EditText mSendContent;
    @BindView(R.id.sendImages)
    GridView mSendImages;
    private static final int IMAGE_PICKER = 1;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> imageList = new ArrayList<>();
    private SendSharedGirdAdapter mAdapter;
    public static String IMAGELISTS = "imageLists";
    public static String CONTENT = "content";
    public String message;
    public static final String mAction = "com.cityGuestsociety.com.sendSuccess";
    public static final String mFialed = "com.cityGuestsociety.com.sendFailed";
    PopupWindow mPopWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_send_shared;
    }

    @Override
    protected void initData() {
        setAdapter();
    }


    @Override
    public void RightOnClick() {

        if (mSendContent.getText().toString().trim().isEmpty() || imageList.size() == 0) {
            ShowToast("请输入内容并选择发送的图片");
        } else {
            Intent intent = new Intent(SendSharedActivity.this, UpLoadServices.class);
            intent.putExtra(IMAGELISTS, imageList);
            intent.putExtra(CONTENT, mSendContent.getText().toString());
            startService(intent);
            showLoadingDialog();
        }

    }


    @Override
    protected void setListener() {
        mSendImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == imageList.size()) {
                    Intent intent = new Intent(SendSharedActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, imageList);
                    startActivityForResult(intent, IMAGE_PICKER);
                }
            }
        });
    }

    public void setAdapter() {
        mAdapter = new SendSharedGirdAdapter(this, imageList);
        mSendImages.setAdapter(mAdapter);
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            imageList = (ArrayList<ImageItem>) intent.getSerializableExtra(IMAGELISTS);
            message = intent.getStringExtra(CONTENT);
            mSendContent.setText(message);
        }

        if (imageList == null) {
            imageList = new ArrayList<>();
        }


        initToobar(R.mipmap.fanhui, "发动态", "发送");
        initImagePicker();


        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(mAction);
        intentFilter.addAction(mFialed);
        BroadcastReceiver br = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.e("onReceive");
                cancleLoadingDialog();

                if (intent.getAction().equals(mAction)) {
                    /**上传成功后删除压缩过后的图片*/
                    File DatalDir = Environment.getExternalStorageDirectory();
                    File myDir = new File(DatalDir, "/DCIM/北京城建");
                    CacheUtils.deleteFolderFile(myDir.getPath(), true);
                    finish();
                } else if (intent.getAction().equals(mFialed)) {

                }


            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);

    }

    public void onClick(View v) {

    }

    /**
     * 第三方拍照和裁剪
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();

        imagePicker.setImageLoader(new GlideLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

    }

    /*
         * 相机拍照得到的图片设置到ImageView上面去
         */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {

            if (data != null && requestCode == IMAGE_PICKER) {

                imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

                mAdapter.notifyDataChanged(imageList);


            } else {
                //                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                ShowToastLong("沒有数据");
            }
        }
    }

    private void showPopWindows() {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.pop_exit_edit, null);

        TextView cancle = (TextView) popView.findViewById(R.id.cancle);
        TextView comfirm = (TextView) popView.findViewById(R.id.comfirm);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
                finish();
            }
        });
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    public void LeftOnClick() {
        showPopWindows();
    }

    @Override
    public void onBackPressed() {
        if (mPopWindow == null) {
            showPopWindows();
        } else if (mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        } else {
            showPopWindows();
        }
    }
}
