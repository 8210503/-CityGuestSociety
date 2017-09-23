package www.cityguestsociety.com.fourthfragmentactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.activity.MainActivity;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;
import www.cityguestsociety.com.utils.L;
import www.cityguestsociety.com.utils.SPUtils;

import static www.cityguestsociety.com.utils.CacheUtils.deleteFolderFile;
import static www.cityguestsociety.com.utils.CacheUtils.getCacheSize;

public class SettingActivity extends BaseToolbarActivity {


    @BindView(R.id.changePassworldRelative)
    RelativeLayout mChangePassworldRelative;
    @BindView(R.id.tv_cacheSize)
    TextView mTvCacheSize;
    @BindView(R.id.ClearCacheRelative)
    RelativeLayout mClearCacheRelative;
    @BindView(R.id.switchButton)
    SwitchButton mSwitchButton;
    @BindView(R.id.linearlayout)
    LinearLayout mLinearlayout;
    @BindView(R.id.tv_loginOut)
    TextView mTvLoginOut;
    PopupWindow mPopWindow;
    public final String LOGINOUT = "loginOut";
    @BindView(R.id.blackListrRelative)
    RelativeLayout mBlackListrRelative;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToobar("设置中心");
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String cacheSize = getCacheSize(getExternalCacheDir());
                L.e(TAG, cacheSize);
                if (cacheSize.equals("0.0B")) {
                    mTvCacheSize.setText(getCacheSize(getCacheDir()));
                } else {
                    mTvCacheSize.setText(cacheSize);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setListener() {
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }


    @OnClick({R.id.changePassworldRelative, R.id.ClearCacheRelative, R.id.switchButton, R.id.tv_loginOut,R.id.blackListrRelative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changePassworldRelative:
                if (isLogined()) {
                    jumpToActivity(ChangePassworldActivity.class, false);
                }


                break;
            case R.id.ClearCacheRelative:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    deleteFolderFile(getExternalCacheDir().getAbsolutePath(), true);
                }
                deleteFolderFile(getCacheDir().getPath(), true);
                try {
                    mTvCacheSize.setText(getCacheSize(getCacheDir()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.switchButton:

                break;
            case R.id.tv_loginOut:
                showPopWindows(this, LOGINOUT);
                break;
            case R.id.blackListrRelative:

                break;
        }
    }

    private void showPopWindows(Activity activity, String message) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        if (message.equals(LOGINOUT)) {
            popView = View.inflate(this, R.layout.pop_login_out, null);
            TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
            TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dismiss();
                   /* Intent intent=new Intent(SettingActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                    startActivity(intent);*/
                    Bundle bundle = new Bundle();
                    int flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;

                    Constans.ID = "null";
                    SPUtils.setUnionid("null");
                    Constans.username = "";
                    Constans.nickName = "";
                    Constans.integral = "";
                    Constans.gender = "";
                    Constans.img = "";
                    Constans.birthday = "";
                    Constans.isLogined = false;
                    Constans.isBindHouse = false;
                    SPUtils.saveUserInfo("", "");
                    MainActivity.position = 5;


                    jumpToActivity(LoginActivity.class, bundle, flags, true);

                }
            });
            tv_cancle.setOnClickListener(new View.OnClickListener() {
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


}
