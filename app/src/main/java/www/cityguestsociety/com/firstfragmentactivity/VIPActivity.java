package www.cityguestsociety.com.firstfragmentactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.VIPintegral;
import www.cityguestsociety.com.firstfragmentactivity.VIPfragment.GouFangFragment;
import www.cityguestsociety.com.firstfragmentactivity.VIPfragment.JiFenExchangeActivity;
import www.cityguestsociety.com.firstfragmentactivity.VIPfragment.JoinActivityFragment;
import www.cityguestsociety.com.firstfragmentactivity.VIPfragment.OldWithYoungFragment;
import www.cityguestsociety.com.utils.Constans;

public class VIPActivity extends BaseToolbarActivity {


    @BindView(R.id.presentJiFen)
    TextView mPresentJiFen;
    @BindView(R.id.r1)
    RadioButton mR1;
    @BindView(R.id.r2)
    RadioButton mR2;
    @BindView(R.id.r3)
    RadioButton mR3;
    @BindView(R.id.jifenmingxiRealative)
    RelativeLayout mJifenmingxiRealative;
    private JoinActivityFragment mJoinFragment;
    private FragmentManager mManager;
    private GouFangFragment mGouFangFragment;
    private OldWithYoungFragment mOldWithYoungFragment;
    Fragment mFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_vip;
    }

    @Override
    protected void initView() {
        initToobar("会员积分");

        {
            mManager = getSupportFragmentManager();
            FragmentTransaction transaction = mManager.beginTransaction();
            if (mJoinFragment == null) {
                mJoinFragment = new JoinActivityFragment();
                transaction.add(R.id.jifenmingxiRealative, mJoinFragment);
            } else {
                transaction.show(mJoinFragment);
            }
            mFragment = mJoinFragment;
            transaction.commit();
        }


    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        params.put("next", 1);
        params.put("type", 3);
        getDataFromInternet(UrlFactory.integral, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        VIPintegral VIPintegra = gson.fromJson(object.toString(), VIPintegral.class);
        mPresentJiFen.setText(VIPintegra.getZongintegral());
    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @OnClick({R.id.r1, R.id.r2, R.id.r3})
    public void onClick(View v) {
        FragmentTransaction transaction = mManager.beginTransaction();
        hideFragments(transaction);
        switch (v.getId()) {
            case R.id.r1:
                if (mJoinFragment == null) {
                    mJoinFragment = new JoinActivityFragment();
                    transaction.add(R.id.jifenmingxiRealative, mJoinFragment);
                } else {
                    transaction.show(mJoinFragment);
                }
                mFragment = mJoinFragment;
                break;
            case R.id.r2:
                if (mGouFangFragment == null) {
                    mGouFangFragment = new GouFangFragment();
                    transaction.add(R.id.jifenmingxiRealative, mGouFangFragment);
                } else {
                    transaction.show(mGouFangFragment);
                }
                mFragment = mGouFangFragment;
                break;
            case R.id.r3:

                if (mOldWithYoungFragment == null) {
                    mOldWithYoungFragment = new OldWithYoungFragment();
                    transaction.add(R.id.jifenmingxiRealative, mOldWithYoungFragment);
                } else {
                    transaction.show(mOldWithYoungFragment);
                }
                mFragment = mOldWithYoungFragment;
                break;
        }
        transaction.commit();
    }


    @Override
    public void RightOnClick() {

        Bundle bundle = new Bundle();
        bundle.putString(JiFenExchangeActivity.TITLE, "兑换活动");
        jumpToActivity(JiFenExchangeActivity.class, bundle, false);

    }

    public void hideFragments(FragmentTransaction transaction) {
        if (mJoinFragment != null) {
            transaction.hide(mJoinFragment);
        }
        if (mGouFangFragment != null) {
            transaction.hide(mGouFangFragment);
        }
        if (mOldWithYoungFragment != null) {
            transaction.hide(mOldWithYoungFragment);
        }

    }

}
