package www.cityguestsociety.com.thirdfragemntActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.SendSharedGirdAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.shared.GlideLoader;
import www.cityguestsociety.com.shared.UpLoadServices;

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
            finish();
        }
    }


    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        ShowToast(object.getString("info"));
    }

    @Override
    protected void setListener() {
        mSendImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == imageList.size()) {
                    Intent intent = new Intent(SendSharedActivity.this, ImageGridActivity.class);
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

        if(imageList==null){
            imageList=new ArrayList<>();
        }



        initToobar(R.mipmap.fanhui, "发动态", "发送");
        initImagePicker();
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
                if (imageList == null) {
                    imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                } else {
                    imageList.addAll((ArrayList<ImageItem>) data.getSerializableExtra(imagePicker.EXTRA_RESULT_ITEMS));
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                //                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
                ShowToastLong("沒有数据");
            }
        }
    }


}
