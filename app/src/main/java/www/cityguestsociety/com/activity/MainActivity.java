package www.cityguestsociety.com.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.fragments.FirstFragment;
import www.cityguestsociety.com.fragments.FourthFragemnt;
import www.cityguestsociety.com.fragments.SecondFragment;
import www.cityguestsociety.com.fragments.ThridFragment;

public class MainActivity extends BaseToolbarActivity {


    @BindView(R.id.R0)
    RadioButton mR0;
    @BindView(R.id.R1)
    RadioButton mR1;
    @BindView(R.id.R2)
    RadioButton mR2;
    @BindView(R.id.R3)
    RadioButton mR3;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    private Fragment mFragment;
    private FragmentManager mMamager;
    private List<Fragment> mFragments;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {


        /**自动登录使用广播更新ＵＩ*/
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("autoLogined");
        BroadcastReceiver br = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String key = intent.getStringExtra("name");
                //                fourthFragemnt.initData();
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);


        mMamager = getSupportFragmentManager();
        FragmentTransaction transaction = mMamager.beginTransaction();

        mFragments = new ArrayList<>();
        mFragments.add(FirstFragment.getInstance());
        mFragments.add(new SecondFragment());
        mFragments.add(new ThridFragment());
        mFragments.add(new FourthFragemnt());

        mFragment = mFragments.get(0);
        transaction.replace(R.id.content_relative, mFragments.get(0));
        transaction.commit();

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
       /* RequestParams params = new RequestParams();
        params.put("username", SPUtils.getCount());
        params.put("password", SPUtils.getPWD());
                getDataFromInternet(UrlFactory.logins, params, 0);*/
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
             /*   MyApplication.UserInfo user = MyApplication.getUser();
                user.setNickname(object.getJSONArray("data").getJSONObject(0).getString("nickname"));
                user.setUsername(object.getJSONArray("data").getJSONObject(0).getString("username"));
                user.setGender(object.getJSONArray("data").getJSONObject(0).getString("gender"));
                user.setIntegral(object.getJSONArray("data").getJSONObject(0).getString("integral"));
                user.setImg(object.getJSONArray("data").getJSONObject(0).getString("img"));
                user.setId(object.getJSONArray("data").getJSONObject(0).getString("id"));


                Constans.ID = object.getJSONArray("data").getJSONObject(0).getString("id");

                isLogined = true;
                Message msg = new Message();
                msg.what = 1;
                msg.obj = isLogined;
                LogUtils.e("自动登录成功");
                FirstFragment.getInstance().handler.sendMessage(msg);*/
                break;
        }
    }


    @Override
    protected void initBase() {
        isShowToolBar = false;
        isShowBackImage = false;
    }


    @OnClick({R.id.R0, R.id.R1, R.id.R2, R.id.R3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.R0:
                switchContent(mFragment, mFragments.get(0));
                FirstFragment fragment = (FirstFragment) mFragments.get(0);
                fragment.refresh();
                break;
            case R.id.R1:
                if(isLogined())
                switchContent(mFragment, mFragments.get(1));
                break;
            case R.id.R2:
                if(isLogined()&&isBindHouse())
                switchContent(mFragment, mFragments.get(2));
                break;
            case R.id.R3:
                if(isLogined()&&isBindHouse())
                switchContent(mFragment, mFragments.get(3));
                break;
        }
    }

    /**
     * 判断是否被add过
     * add过  隐藏当前的fragment，add下一个到Activity中
     * 否则   隐藏当前的fragment，显示下一个
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = mMamager.beginTransaction();
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(R.id.content_relative, to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

}
