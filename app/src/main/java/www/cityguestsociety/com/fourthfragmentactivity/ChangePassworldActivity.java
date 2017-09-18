package www.cityguestsociety.com.fourthfragmentactivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;
import www.cityguestsociety.com.utils.SPUtils;

public class ChangePassworldActivity extends BaseToolbarActivity {


    @BindView(R.id.oldPwd)
    EditText mOldPwd;
    @BindView(R.id.iv_seePassworld1)
    CheckBox mIvSeePassworld1;
    @BindView(R.id.newPwd)
    EditText mNewPwd;
    @BindView(R.id.iv_seePassworld2)
    CheckBox mIvSeePassworld2;
    @BindView(R.id.newPwdAgain)
    EditText mNewPwdAgain;
    @BindView(R.id.iv_seePassworld)
    CheckBox mIvSeePassworld;
    @BindView(R.id.complete)
    Button complete;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_passworld;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {

    }

    @OnClick({R.id.iv_seePassworld1, R.id.iv_seePassworld2, R.id.iv_seePassworld, R.id.complete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_seePassworld1:
                if (mIvSeePassworld1.isChecked()) {
                    String content = mOldPwd.getText().toString().trim();
                    mOldPwd.setText(content);
                    mOldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mOldPwd.setSelection(content.length());
                } else {
                    String content = mOldPwd.getText().toString().trim();
                    mOldPwd.setText(content);
                    mOldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mOldPwd.setSelection(content.length());
                }
                break;

            case R.id.iv_seePassworld2:
                if (mIvSeePassworld2.isChecked()) {
                    String content = mNewPwd.getText().toString().trim();
                    mNewPwd.setText(content);
                    mNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mNewPwd.setSelection(content.length());
                } else {
                    String content = mNewPwd.getText().toString().trim();
                    mNewPwd.setText(content);
                    mNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mNewPwd.setSelection(content.length());
                }

                break;
            case R.id.iv_seePassworld:
                if (mIvSeePassworld.isChecked()) {
                    String content = mNewPwdAgain.getText().toString().trim();
                    mNewPwdAgain.setText(content);
                    mNewPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mNewPwdAgain.setSelection(content.length());
                } else {
                    String content = mNewPwdAgain.getText().toString().trim();
                    mNewPwdAgain.setText(content);
                    mNewPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mNewPwdAgain.setSelection(content.length());
                }


                break;
            case R.id.complete:
                if (mOldPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入旧密码");
                } else if (mNewPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入新密码");
                } else if (!mNewPwd.getText().toString().trim().equals(mNewPwdAgain.getText().toString().trim())) {
                    ShowToast("两次输入的密码不一致");
                } else if (isCorrentPasswrold(mNewPwd)) {
                    ShowToast("密码长度为6~18位");
                } else {
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("password", mOldPwd.getText().toString().trim());
                    params.put("xinpassword", mNewPwd.getText().toString().trim());
                    params.put("repassword", mNewPwdAgain.getText().toString().trim());
                    getDataFromInternet(UrlFactory.xiupassword, params, 0);
                }
                break;
        }

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        ShowToast(object.getString("info"));


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
        SPUtils.saveUserInfo(SPUtils.getCount(),mNewPwd.getText().toString().trim());
        jumpToActivity(LoginActivity.class, true);
    }
}
