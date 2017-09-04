package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.BaoXiuGridViewAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;

public class TSActivity extends BaseToolbarActivity {

    @BindView(R.id.houseRelative)
    RelativeLayout mHouseRelative;
    @BindView(R.id.iv_goodsPhoto)
    GridView mIvGoodsPhoto;
    @BindView(R.id.selectTimeRelative)
    RelativeLayout mSelectTimeRelative;
    @BindView(R.id.commit)
    Button mCommit;
    @BindView(R.id.tsContent)
    EditText tsContent;
    List<Bean> mlists;
    private final String TAKRPICTURE = "TAKEPICTURE";
    private static final int RESULT = 1;
    public static final int CAMERA_REQUEST_CODE_FRONT = 2;
    @BindView(R.id.textViewSelectTime)
    TextView mTextViewSelectTime;
    private File tempfile;
    private String mPicturePath;
    private BaoXiuGridViewAdapter mAdapter;
    PopupWindow mPopWindow;

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "建议", "历史");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ts;
    }

    @Override
    protected void initData() {
        mlists = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new BaoXiuGridViewAdapter(this, mlists);
        mIvGoodsPhoto.setAdapter(mAdapter);

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
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @OnClick({R.id.commit, R.id.selectTimeRelative})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:


                break;
            case R.id.selectTimeRelative:
                new MaterialDialog.Builder(this)
                        .title("建议类型")
                        .items(R.array.TSKind)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mTextViewSelectTime.setText(text);
                            }
                        })
                        .show();

                break;
        }
    }

    @Override
    public void RightOnClick() {
        jumpToActivity(TSRecoderActivity.class, false);
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
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
