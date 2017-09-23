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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.bindhouse.SelectHouseInfoActivity;
import www.cityguestsociety.com.fragments.FirstFragment;
import www.cityguestsociety.com.fragments.FourthFragemnt;
import www.cityguestsociety.com.fragments.SecondFragment;
import www.cityguestsociety.com.fragments.ThridFragment;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;

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
    private int clickPosition = 0;
    public static int position = 0;

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

    }


    @Override
    protected void initBase() {
        isShowToolBar = false;
        isShowBackImage = false;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mFragment != null) {
            switchContent(mFragment, mFragments.get(clickPosition));
            if (clickPosition == 0) {
                mR0.setChecked(true);
            } else if (clickPosition == 1) {
                mR1.setChecked(true);
            } else if (clickPosition == 2) {
                mR2.setChecked(true);
            } else if (clickPosition == 3) {
                mR3.setChecked(true);
            }
        }

        if (position != 0 && mFragment != null) {

            if (position == 2) {
                switchContent(mFragment, mFragments.get(1));
                mR1.setChecked(true);
            } else if (position == 3) {
                switchContent(mFragment, mFragments.get(2));
                mR2.setChecked(true);
            } else if (position == 4) {
                switchContent(mFragment, mFragments.get(3));
                mR3.setChecked(true);
            } else {
                switchContent(mFragment, mFragments.get(0));
                mR0.setChecked(true);
            }
        } else if (Constans.ID.equals("null")) {
            switchContent(mFragment, mFragments.get(0));
            mR0.setChecked(true);
        }


    }

    @OnClick({R.id.R0, R.id.R1, R.id.R2, R.id.R3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.R0:
                clickPosition = 2;
                position = 5;
                switchContent(mFragment, mFragments.get(0));
                FirstFragment fragment = (FirstFragment) mFragments.get(0);
                fragment.refresh();
                break;
            case R.id.R1:
                if (Constans.ID.equals("null")) {
                    mR0.setChecked(true);
                    clickPosition = 0;
                    jumpToActivity(LoginActivity.class, false);
                } else {
                    switchContent(mFragment, mFragments.get(1));
                    clickPosition = 1;
                    position = 2;
                }
                break;
            case R.id.R2:
                if (Constans.ID.equals("null")) {
                    mR0.setChecked(true);
                    clickPosition = 0;
                    jumpToActivity(LoginActivity.class, false);
                } else if (!(Constans.ID.equals("null")) && Constans.isBindHouse) {

                    switchContent(mFragment, mFragments.get(2));
                    clickPosition = 2;
                    position = 3;
                } else if (!(Constans.ID.equals("null")) && !Constans.isBindHouse) {
                    jumpToActivity(SelectHouseInfoActivity.class, false);
                }

                break;
            case R.id.R3:
                if (Constans.ID.equals("null")) {
                    mR0.setChecked(true);
                    clickPosition = 0;
                    jumpToActivity(LoginActivity.class, false);
                } else if (!(Constans.ID.equals("null")) && Constans.isBindHouse) {
                    switchContent(mFragment, mFragments.get(3));
                    clickPosition = 3;
                    position = 4;
                } else if (!(Constans.ID.equals("null")) && !Constans.isBindHouse) {
                    jumpToActivity(SelectHouseInfoActivity.class, false);
                }
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
