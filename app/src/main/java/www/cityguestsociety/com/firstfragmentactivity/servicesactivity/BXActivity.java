package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.BaoXiuGridViewAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;
import www.cityguestsociety.com.ui.CustomDatePicker;

public class BXActivity extends BaseToolbarActivity {


    @BindView(R.id.houseRelative)
    RelativeLayout mHouseRelative;
    @BindView(R.id.BXDescriptionET)
    EditText mBXDescriptionET;
    @BindView(R.id.selectTimeRelative)
    RelativeLayout mSelectTimeRelative;
    @BindView(R.id.commit)
    Button mCommit;
    @BindView(R.id.iv_goodsPhoto)
    GridView mIvGoodsPhoto;
    private BaoXiuGridViewAdapter mAdapter;
    List<Bean> mlists;
    PopupWindow mPopWindow;
    private final String TAKRPICTURE = "TAKEPICTURE";
    private static final int RESULT = 1;
    public static final int CAMERA_REQUEST_CODE_FRONT = 2;
    private File tempfile;
    private String mPicturePath;
    private CustomDatePicker timePicker;
    private String time;
    private String date;


    private void initPicker() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];


        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {

            }
        }, time, "2027-12-31 23:59");//"2027-12-31 23:59"
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_bx2;
    }

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "我要报修", "记录");
        initPicker();
    }

    @Override
    public void RightOnClick() {
        jumpToActivity(BXRecoderActivity.class,false);
    }

    @Override
    protected void initData() {
        mlists = new ArrayList<>();

        setAdapter();
    }

    @Override
    protected void setListener() {
        mIvGoodsPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mlists.size()) {
                    showPopWindows(TAKRPICTURE);
                } else {
                    mlists.remove(position);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    /**
     * 设置报修照片 的Adapter
     * 最多为3张
     */
    private void setAdapter() {
        mIvGoodsPhoto.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new BaoXiuGridViewAdapter(this, mlists);
        mIvGoodsPhoto.setAdapter(mAdapter);

    }

    private void showPopWindows(String message) {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        if (message.equals(TAKRPICTURE)) {
            popView = View.inflate(this, R.layout.pop_window_camera, null);

            Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
            Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_album);
            Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);
            btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePictureByCarema();

                }
            });

            btnAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPopWindow.dismiss();
                }
            });
        }
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


    @OnClick({R.id.houseRelative, R.id.selectTimeRelative, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.houseRelative:
                break;
            case R.id.selectTimeRelative:
                timePicker.show(time);
                break;
            case R.id.commit:

                break;
        }
    }

    private void takePictureByCarema() {
        File DatalDir = Environment.getExternalStorageDirectory();
        File myDir = new File(DatalDir, "/DCIM/Camera");
        myDir.mkdirs();
        String mDirectoryname = DatalDir.toString() + "/DCIM/Camera";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmmss", Locale.SIMPLIFIED_CHINESE);
        tempfile = new File(mDirectoryname, System.currentTimeMillis()
                + ".jpg");
        if (tempfile.isFile())
            tempfile.delete();
        Uri Imagefile = Uri.fromFile(tempfile);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE_FRONT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPopWindow.dismiss();
        Bean info = new Bean();
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPicturePath = cursor.getString(columnIndex);
                //                Glide.with(this).load(mPicturePath).asBitmap().into(mIv_goodsPhoto);
                info.setIamge(mPicturePath);
                mlists.add(info);
                mAdapter.notifyDataSetChanged();
                cursor.close();

            } else if (requestCode == CAMERA_REQUEST_CODE_FRONT) {

                //Glide.with(this).load(tempfile.getPath()).asBitmap().into(mIv_goodsPhoto);
                info.setIamge(tempfile.getPath());
                mlists.add(info);
                mAdapter.notifyDataSetChanged();
            }


        }
    }

}
