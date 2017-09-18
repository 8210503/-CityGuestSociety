package www.cityguestsociety.com.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.secondFragmentActivity.SellerFragment;
import www.cityguestsociety.com.secondFragmentActivity.YouHuiJuanFragment;

/**
 * Created by LuoPan on 2017/9/4 9:27.
 */

public class SecondFragment extends BaseFragment implements View.OnClickListener {
    RadioButton fujin;
    RadioButton youhuijuan;
    private Fragment mFragment;
    private FragmentManager mMamager;
    private ArrayList<Fragment> Fragments;

    @Override
    protected void initView() {
        fujin = getView(R.id.fujin);
        youhuijuan = getView(R.id.youhuiquan);
    }

    @Override
    protected void initData() {
        mMamager = getChildFragmentManager();
        FragmentTransaction transaction = mMamager.beginTransaction();
        Fragments = new ArrayList<>();

        Fragments.add(new SellerFragment());
        Fragments.add(new YouHuiJuanFragment());

        mFragment = Fragments.get(0);
        transaction.replace(R.id.SellerRelative, mFragment);
        transaction.commit();
    }

    @Override
    protected void setListener() {
        fujin.setOnClickListener(this);
        youhuijuan.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_seller;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fujin:
                switchContent(mFragment, Fragments.get(0));
                break;
            case R.id.youhuiquan:
                switchContent(mFragment, new SellerFragment());
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
                transaction.hide(from).add(R.id.SellerRelative, to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }
}
