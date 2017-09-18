package www.cityguestsociety.com.activity;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;
import www.cityguestsociety.com.utils.SPUtils;

public class SpanishActivity extends BaseToolbarActivity {
    // TODO: 2017/9/12 将自动登录写在这个里面 并且将user的信息保存下来

    @BindView(R.id.imageview)
    ImageView mImageview;

    @Override
    protected int getContentView() {
        return R.layout.activity_spanish;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void initView() {
        if (!SPUtils.getCount().isEmpty()) {
            RequestParams params = new RequestParams();
            params.put("username", SPUtils.getCount());
            params.put("password", SPUtils.getPWD());
            getDataFromInternet(UrlFactory.logins, params, 0);
        } else {
            mImageview.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SPUtils.getCount().isEmpty()) {
                        jumpToActivity(LoginActivity.class, true);
                    }
                }
            }, 1000);
        }
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        MyApplication.UserInfo user = MyApplication.getUser();
        user.setNickname(object.getJSONArray("data").getJSONObject(0).getString("nickname"));
        user.setUsername(object.getJSONArray("data").getJSONObject(0).getString("username"));
        user.setGender(object.getJSONArray("data").getJSONObject(0).getString("gender"));
        user.setIntegral(object.getJSONArray("data").getJSONObject(0).getString("integral"));
        user.setImg(object.getJSONArray("data").getJSONObject(0).getString("img"));
        user.setId(object.getJSONArray("data").getJSONObject(0).getString("id"));

        Constans.ID = object.getJSONArray("data").getJSONObject(0).getString("id");

        if (object.getJSONArray("data").getJSONObject(0).getInteger("house") == 1) {
            Constans.isBindHouse = true;
        } else {
            Constans.isBindHouse = false;
        }
        mImageview.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToActivity(MainActivity.class, true);
            }
        }, 1000);

    }


    @Override
    protected void initBase() {
        isShowToolBar = false;
        isShowBackImage = false;
    }

    @Override
    public void onClick(View v) {


    }
}
