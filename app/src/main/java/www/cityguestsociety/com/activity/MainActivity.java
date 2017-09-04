package www.cityguestsociety.com.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

        mMamager = getSupportFragmentManager();
        FragmentTransaction transaction = mMamager.beginTransaction();

        mFragments = new ArrayList<>();
        mFragments.add(new FirstFragment());
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
    protected void initBase() {
        isShowToolBar = false;
        isShowBackImage = false;
    }


    @OnClick({R.id.R0, R.id.R1, R.id.R2, R.id.R3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.R0:
                switchContent(mFragment, mFragments.get(0));
                break;
            case R.id.R1:
                switchContent(mFragment, mFragments.get(1));
                break;
            case R.id.R2:
                switchContent(mFragment, mFragments.get(2));
                break;
            case R.id.R3:
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
                transaction.hide(from).add(R.id.content_relative,to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

}
