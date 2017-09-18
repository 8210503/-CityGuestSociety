package www.cityguestsociety.com.fourthfragmentactivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.ui.CircleImageView;
import www.cityguestsociety.com.utils.CompressImageUtils;
import www.cityguestsociety.com.utils.Constans;

import static www.cityguestsociety.com.utils.Constans.birthday;
import static www.cityguestsociety.com.utils.Constans.nickName;

public class MineDataActivity extends BaseToolbarActivity {


    @BindView(R.id.userPhoto)
    CircleImageView mUserPhoto;
    @BindView(R.id.userPhotoRelative)
    RelativeLayout mUserPhotoRelative;
    @BindView(R.id.userNickName)
    TextView mUserNickName;
    @BindView(R.id.userNickNameRelative)
    RelativeLayout mUserNickNameRelative;
    @BindView(R.id.userNumber)
    TextView mUserNumber;
    @BindView(R.id.userPhoneNumberRelative)
    RelativeLayout mUserPhoneNumberRelative;
    @BindView(R.id.userSex)
    TextView mUserSex;
    @BindView(R.id.userSexRelative)
    RelativeLayout mUserSexRelative;
    @BindView(R.id.userBorth)
    TextView mUserBorth;
    @BindView(R.id.userBorthRelative)
    RelativeLayout mUserBorthRelative;
    private ByteArrayInputStream header;

    public final String USERPHOTO = "userPhoto";
    public final String USERNICKNAME = "userNickName";
    public final String USERSEX = "userSex";
    public final String USERBORTH = "userborth";
    PopupWindow mPopWindow;
    private File tempfile;
    // 拍照成功，读取相册成功，裁减成功
    private final int ALBUM_OK = 1, CAMERA_OK = 2, CUT_OK = 3;
    private int Method = 0;
    private Uri mSelectedImage;
    private String mPhotoPath;
    int gender = 0;
    private String mNickName;
    private String mBorthDay;
    private String mBorthDayone;


    @Override
    protected int getContentView() {
        return R.layout.activity_mine_data;
    }

    @Override
    protected void initData() {
        Glide.with(this).load(Constans.img).asBitmap().error(R.mipmap.icon_head_portrait).placeholder(R.mipmap.icon_head_portrait).into(mUserPhoto);
        mUserNickName.setText(nickName);
        mUserNumber.setText(Constans.username);
        mUserBorth.setText(birthday);

        if (Constans.gender.equals("1")) {
            mUserSex.setText("男");
        } else if (Constans.gender.equals("2")) {
            mUserSex.setText("女");
        } else if (Constans.gender.equals("3")) {
            mUserSex.setText("保密");
        } else if (Constans.gender.equals("0")) {
            mUserSex.setText("请选择性别");
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("我的资料");
    }

    @OnClick({R.id.userPhotoRelative, R.id.userNickNameRelative, R.id.userPhoneNumberRelative, R.id.userSexRelative, R.id.userBorthRelative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userPhotoRelative:
                showPopWindows(this, USERPHOTO);
                break;
            case R.id.userNickNameRelative:
                showPopWindows(this, USERNICKNAME);
                break;
            case R.id.userPhoneNumberRelative:
                break;
            case R.id.userSexRelative:
                showPopWindows(this, USERSEX);
                break;
            case R.id.userBorthRelative:
                showPopWindows(this, USERBORTH);
                break;
        }
    }

    public void showPopWindows(Activity activity, String message) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        if (message.equals(USERPHOTO)) {
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
                    startActivityForResult(intent, ALBUM_OK);
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPopWindow.dismiss();
                }
            });
        } else if (message.equals(USERNICKNAME)) {

            popView = View.inflate(this, R.layout.pop_nick_name, null);
            final EditText et_nickName = (EditText) popView.findViewById(R.id.editTextNickName);
            TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
            TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
            et_nickName.setText(mUserNickName.getText());
            et_nickName.setSelection(mUserNickName.length());// 光标移动到最后
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNickName = et_nickName.getText().toString().trim();
                    if (mNickName.isEmpty()) {
                        ShowToast("昵称不能为空");
                    } else {
                        RequestParams params = new RequestParams();
                        params.put("member_id", Constans.ID);
                        params.put("nickname", mNickName);
                        getDataFromInternet(UrlFactory.update_data, params, 2);
                        mPopWindow.dismiss();
                    }
                }
            });
            tv_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dismiss();
                }
            });
        } else if (message.equals(USERSEX)) {

            popView = View.inflate(this, R.layout.pop_select_sex, null);
            RelativeLayout manRelative = (RelativeLayout) popView.findViewById(R.id.manRelative);
            RelativeLayout femalRelative = (RelativeLayout) popView.findViewById(R.id.femalRelative);
            RelativeLayout keeSercetRelative = (RelativeLayout) popView.findViewById(R.id.keepSecrecyRelative);
            manRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserSex.setText("男");
                    gender = 1;
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("gender", gender);
                    getDataFromInternet(UrlFactory.update_data, params, 1);
                    mPopWindow.dismiss();

                }
            });
            femalRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserSex.setText("女");
                    gender = 2;
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("gender", gender);
                    getDataFromInternet(UrlFactory.update_data, params, 1);
                    mPopWindow.dismiss();
                }
            });
            keeSercetRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserSex.setText("保密");
                    gender = 3;
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("gender", gender);
                    getDataFromInternet(UrlFactory.update_data, params, 1);
                    mPopWindow.dismiss();
                }
            });
            /**性别*/

            // TODO: 2017/9/12

        } else if (message.equals(USERBORTH)) {

            popView = View.inflate(this, R.layout.pop_select_birth, null);

            //获取一个日历对象
            Calendar cal = Calendar.getInstance();
            //获取年月日时分秒的信息
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            setTitle(year + "-" + month + "-" + day + "  " + hour + ":" + minute);
            DatePicker datePicker = (DatePicker) popView.findViewById(R.id.datePicker);
            TextView tv_comfirm = (TextView) popView.findViewById(R.id.tv_borth_confirm);
            datePicker.setMaxDate(System.currentTimeMillis());
            datePicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {

                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub
                    setTitle(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mBorthDay = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    mBorthDayone = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);

                }
            });
            tv_comfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("birthday",mBorthDay);
                    getDataFromInternet(UrlFactory.update_data, params, 3);
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

    private void takePictureByCarema() {
        File DatalDir = Environment.getExternalStorageDirectory();
        File myDir = new File(DatalDir, "/DCIM/Camera");
        myDir.mkdirs();
        String mDirectoryname = DatalDir.toString() + "/DCIM/Camera";
        tempfile = new File(mDirectoryname, "temp" + ".jpg");
        if (tempfile.isFile())
            tempfile.delete();
        Uri Imagefile = Uri.fromFile(tempfile);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
        startActivityForResult(cameraIntent, CAMERA_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPopWindow.dismiss();
        if (resultCode == RESULT_OK) {
            if (requestCode == ALBUM_OK) {

                mSelectedImage = data.getData();
                Method = 1;
                clipPhoto(mSelectedImage);
            } else if (requestCode == CAMERA_OK) {
                clipPhoto(Uri.fromFile(tempfile));
                Method = 2;
            } else if (requestCode == CUT_OK) {
                setPicToView(data, mUserPhoto);

            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_OK);
    }

    /**
     * 保存裁剪之后的图片数据 将图片设置到imageview中
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata, ImageView imageView) {
        Bundle extras = picdata.getExtras();
        mPhotoPath = null;
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            //            Glide.with(MineDataActivity.this).load(photo).asBitmap().into(imageView);

            if (Method == 1) {


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(mSelectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPhotoPath = cursor.getString(columnIndex);
                //                Glide.with(this).load(mPicturePath).asBitmap().into(mIv_goodsPhoto);
                // 相册
                LogUtils.e(mPhotoPath);
            } else if (Method == 2) {
                mPhotoPath = tempfile.getAbsolutePath();
                LogUtils.e(mPhotoPath);
            }


            /**上传头像*/
            header = CompressImageUtils.CompressImageUtils(mPhotoPath);
            RequestParams params = new RequestParams();
            params.put("member_id", Constans.ID);
            params.put("img", header, "head.png", "image/png");
            getDataFromInternet(UrlFactory.headerimg, params, 0);
            showLoadingDialog();

            //上传到服务器

        }
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            /**上传头像*/
            case 0:
                ShowToast(object.getString("info"));
                Glide.with(this).load(mPhotoPath).asBitmap().into(mUserPhoto);
                Constans.img = mPhotoPath;
                break;
            /**性别*/
            case 1:
                ShowToast(object.getString("info"));
                Constans.gender = String.valueOf(gender);
                if (Constans.gender.equals("1")) {
                    mUserSex.setText("男");
                } else if (Constans.gender.equals("2")) {
                    mUserSex.setText("女");
                } else if (Constans.gender.equals("3")) {
                    mUserSex.setText("保密");
                } else if (Constans.gender.equals("0")) {
                    mUserSex.setText("请选择性别");
                }
                break;
            case 2:
                /**昵称*/
                ShowToast(object.getString("info"));
                mUserNickName.setText(mNickName);
                Constans.nickName = mNickName;
                break;
            case 3:
                /**出生日期*/
                ShowToast(object.getString("info"));
                mUserBorth.setText(mBorthDayone);
                Constans.birthday = mBorthDayone;
                break;
        }

    }
}
