package www.cityguestsociety.com.login;

import android.content.Intent;
import android.os.Handler;
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

import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.utils.L;

public class RegiestActivity extends BaseToolbarActivity {

    @BindView(R.id.textView1)
    TextView mTextView1;
    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.phoneNumberHengxian)
    TextView mPhoneNumberHengxian;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.phoneNumberRelative)
    RelativeLayout mPhoneNumberRelative;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.checkCode)
    EditText mCheckCode;
    @BindView(R.id.checkCodeHengxian)
    TextView mCheckCodeHengxian;
    @BindView(R.id.sendCheckCode)
    TextView mSendCheckCode;
    @BindView(R.id.checkCodeRelative)
    RelativeLayout mCheckCodeRelative;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.pwd)
    EditText mPwd;
    @BindView(R.id.passworldHengxian)
    TextView mPassworldHengxian;
    @BindView(R.id.iv_seePassworld)
    CheckBox mIvSeePassworld;
    @BindView(R.id.passworldRelative)
    RelativeLayout mPassworldRelative;
    @BindView(R.id.textViewinfo)
    TextView mTextViewinfo;
    @BindView(R.id.aggrement)
    TextView mAggrement;
    @BindView(R.id.regiset_complete)
    Button mRegisetComplete;
    int i = 60;
    private TimerTask mTimerTask;
    private String mExtra;
    private String mCode;


    @Override
    protected int getContentView() {
        return R.layout.activity_regiest;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

        mPhoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPhoneNumberHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.orange));
                mCheckCodeHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.deep_back));
                mPassworldHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.deep_back));
                return false;
            }
        });
        mCheckCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mCheckCodeHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.orange));
                mPhoneNumberHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.deep_back));
                mPassworldHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.deep_back));
                return false;
            }
        });
        mPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPassworldHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.orange));
                mPhoneNumberHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.deep_back));
                mCheckCodeHengxian.setBackgroundColor(RegiestActivity.this.getResources().getColor(R.color.deep_back));
                return false;
            }
        });


    }

    @Override
    protected void initView() {
        initToobar("注册");
        Intent intent = getIntent();
        mExtra = intent.getStringExtra(LoginActivity.FROM);

        if (mExtra.equals(LoginActivity.FORGETPASSWORLD)) {
            mRegisetComplete.setText(R.string.confirm);
            initToobar("忘记密码");
            mTextViewinfo.setVisibility(View.INVISIBLE);
            mAggrement.setVisibility(View.INVISIBLE);
        } else if (mExtra.equals(LoginActivity.REGIEST)) {
            mRegisetComplete.setText(R.string.completeRigest);
            initToobar("注册");
            mTextViewinfo.setVisibility(View.VISIBLE);
            mAggrement.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }


    @OnClick({R.id.iv_delete, R.id.checkCodeHengxian, R.id.sendCheckCode, R.id.pwd, R.id.iv_seePassworld, R.id.regiset_complete})
    public void onClick(View view) {
        String content = "";
        switch (view.getId()) {
            case R.id.iv_delete:
                mPhoneNumber.setText("");
                break;
            case R.id.checkCodeHengxian:
                break;
            case R.id.sendCheckCode:
                L.e(TAG, "sendCheckCode");
                if (!mPhoneNumber.getText().toString().trim().isEmpty() || (mPhoneNumber.getText().toString().trim().length() != 11)) {
                    if (mExtra.equals(LoginActivity.REGIEST)) {
                        RequestParams params = new RequestParams();
                        params.put("username", mPhoneNumber.getText().toString().trim());
                        getDataFromInternet(UrlFactory.yzm, params, 0);
                        showLoadingDialog();
                    } else if (mExtra.equals(LoginActivity.FORGETPASSWORLD)) {
                        RequestParams params = new RequestParams();
                        params.put("username", mPhoneNumber.getText().toString().trim());
                        getDataFromInternet(UrlFactory.forgot_yzm, params, 0);
                        showLoadingDialog();
                    }

                } else {
                    ShowToast("请输入正确的电话号码");
                }
                break;
            case R.id.pwd:
                break;
            case R.id.iv_seePassworld:
                if (mIvSeePassworld.isChecked()) {
                    content = mPwd.getText().toString().trim();
                    if (!content.isEmpty()) {
                        mPwd.setText(content);
                        mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mPwd.setSelection(content.length());
                    }
                } else {
                    content = mPwd.getText().toString().trim();
                    mPwd.setText(content);
                    mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPwd.setSelection(content.length());
                }
                break;
            case R.id.regiset_complete:

                if (!mCheckCode.getText().toString().trim().equals(mCode)) {
                    ShowToast("验证码有误");
                } else if (mPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入密码");
                } else if (isCorrentPasswrold(mPwd)) {
                    ShowToast("密码长度为6~18位");
                } else if (mExtra.equals(LoginActivity.REGIEST)) {
                    RequestParams params = new RequestParams();
                    params.put("username", mPhoneNumber.getText().toString().trim());
                    params.put("yzm", mCheckCode.getText().toString().trim());
                    params.put("password", mPwd.getText().toString().trim());
                    getDataFromInternet(UrlFactory.register, params, 1);
                    showLoadingDialog();
                } else if (mExtra.equals(LoginActivity.FORGETPASSWORLD)) {
                    RequestParams params = new RequestParams();
                    params.put("username", mPhoneNumber.getText().toString().trim());
                    params.put("yzm", mCheckCode.getText().toString().trim());
                    params.put("password", mPwd.getText().toString().trim());
                    getDataFromInternet(UrlFactory.forgot_password, params, 2);
                    showLoadingDialog();
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
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i--;
                        mSendCheckCode.setText(i + "S");
                        mSendCheckCode.setEnabled(false);
                        if (i > 0) {
                            handler.postDelayed(this, 1000);
                        } else {
                            i = 60;
                            mSendCheckCode.setText(R.string.sendCheckCode);
                            mSendCheckCode.setEnabled(true);
                        }

                    }
                }, 200);
                mCode = object.getJSONArray("data").getJSONObject(0).getString("yzm");
                break;
            case 1:
                finish();
                ShowToast(object.getString("info"));
                break;
            case 2:
                ShowToast(object.getString("info"));
                break;
        }

    }
}
