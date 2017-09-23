package www.cityguestsociety.com.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.activity.MainActivity;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.utils.Constans;
import www.cityguestsociety.com.utils.SPUtils;

public class LoginActivity extends BaseToolbarActivity {


    @BindView(R.id.companyCorn)
    RelativeLayout mCompanyCorn;
    @BindView(R.id.textView1)
    TextView mTextView1;
    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.phoneNumberHengxian)
    TextView mPhoneNumberHengxian;
    @BindView(R.id.phoneNumberRelative)
    RelativeLayout mPhoneNumberRelative;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.pwd)
    EditText mPwd;
    @BindView(R.id.passworldHengxian)
    TextView mPassworldHengxian;
    @BindView(R.id.iv_seePassworld)
    CheckBox mIvSeePassworld;
    @BindView(R.id.passworldRelative)
    RelativeLayout mPassworldRelative;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.bt_regiset)
    Button mBtRegiset;
    @BindView(R.id.forgetPassworld)
    TextView mForgetPassworld;
    @BindView(R.id.imageview)
    ImageView mImageview;

    public static final String FROM = "From";
    public static final String FORGETPASSWORLD = "forgetPassworld";
    public static final String REGIEST = "regiest";
    public static final String TITLE = "title";

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mPhoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPhoneNumberHengxian.setBackgroundColor(LoginActivity.this.getResources().getColor(R.color.orange));
                mPassworldHengxian.setBackgroundColor(LoginActivity.this.getResources().getColor(R.color.deep_back));
                return false;
            }
        });
        mPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPhoneNumberHengxian.setBackgroundColor(LoginActivity.this.getResources().getColor(R.color.deep_back));
                mPassworldHengxian.setBackgroundColor(LoginActivity.this.getResources().getColor(R.color.orange));
                return false;
            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.bt_login, R.id.bt_regiset, R.id.forgetPassworld, R.id.imageview, R.id.iv_seePassworld})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if (mPhoneNumber.getText().toString().trim().isEmpty() || mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号");
                } else if (mPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入密码");
                } else {
                    RequestParams params = new RequestParams();
                    params.put("username", mPhoneNumber.getText().toString().trim());
                    params.put("password", mPwd.getText().toString().trim());
                    getDataFromInternet(UrlFactory.logins, params, 0);
                    showLoadingDialog();
                }


                break;
            case R.id.bt_regiset:
                Bundle bundle = new Bundle();
                bundle.putString(FROM, REGIEST);
                jumpToActivity(RegiestActivity.class, bundle, false);
                break;
            case R.id.forgetPassworld:
                bundle = new Bundle();
                bundle.putString(FROM, FORGETPASSWORLD);
                jumpToActivity(RegiestActivity.class, bundle, false);
                break;
            case R.id.imageview:
                moveTaskToBack(true);
                break;
            case R.id.iv_seePassworld:
                if (mIvSeePassworld.isChecked()) {
                    String content = mPwd.getText().toString().trim();
                    mPwd.setText(content);
                    mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mPwd.setSelection(content.length());
                } else {
                    String content = mPwd.getText().toString().trim();
                    mPwd.setText(content);
                    mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPwd.setSelection(content.length());
                }
                break;
        }
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                ShowToast(object.getString("info"));
//                MyApplication.UserInfo user = MyApplication.getUser();
                Constans.nickName=object.getJSONArray("data").getJSONObject(0).getString("nickname");
                Constans.username=object.getJSONArray("data").getJSONObject(0).getString("username");
                Constans.gender=object.getJSONArray("data").getJSONObject(0).getString("gender");
                Constans.integral=object.getJSONArray("data").getJSONObject(0).getString("integral");
                Constans.img=object.getJSONArray("data").getJSONObject(0).getString("img");

                /*user.setNickname(object.getJSONArray("data").getJSONObject(0).getString("nickname"));
                user.setUsername(object.getJSONArray("data").getJSONObject(0).getString("username"));
                user.setGender(object.getJSONArray("data").getJSONObject(0).getString("gender"));
                user.setIntegral(object.getJSONArray("data").getJSONObject(0).getString("integral"));
                user.setImg(object.getJSONArray("data").getJSONObject(0).getString("img"));
                user.setId(object.getJSONArray("data").getJSONObject(0).getString("id"));*/

                Constans.ID = object.getJSONArray("data").getJSONObject(0).getString("id");

                SPUtils.setUnionid(object.getJSONArray("data").getJSONObject(0).getString("id"));

                SPUtils.saveUserInfo(mPwd.getText().toString().trim(), mPhoneNumber.getText().toString().trim());

                if (object.getJSONArray("data").getJSONObject(0).getInteger("house") == 1) {
                    Constans.isBindHouse = true;
                } else {
                    Constans.isBindHouse = false;
                }
                jumpToActivity(MainActivity.class, true);
                break;
        }
    }
}
