package www.cityguestsociety.com.fourthfragmentactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.fourthfragmentactivity.newsfragments.MyAnnuncetionFragment;
import www.cityguestsociety.com.fourthfragmentactivity.newsfragments.MyCommentsFragment;
import www.cityguestsociety.com.fourthfragmentactivity.newsfragments.MyNotifyFragment;

public class MyNewsActivity extends BaseToolbarActivity {


    @BindView(R.id.backRelative)
    RelativeLayout mBackRelative;
    @BindView(R.id.announcement)
    RadioButton mAnnouncement;
    @BindView(R.id.notice)
    RadioButton mNotice;
    @BindView(R.id.comments)
    RadioButton mComments;
    @BindView(R.id.titleLinear)
    LinearLayout mTitleLinear;
    @BindView(R.id.contentRealative)
    ViewPager mViewPager;

    private Fragment mFragment;
    private FragmentManager mMamager;
    private List<Fragment> mFragments;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_news;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        mFragments.add(new MyAnnuncetionFragment());
        mFragments.add(new MyNotifyFragment());
        mFragments.add(new MyCommentsFragment());
        mFragment = mFragments.get(0);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(manager, mFragments);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected void setListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mAnnouncement.setChecked(true);
                } else if (position == 1) {
                    mNotice.setChecked(true);
                } else if (position == 2) {
                    mComments.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void initBase() {
        isShowToolBar = false;
        isShowBackImage = false;
    }

    @OnClick({R.id.backRelative, R.id.announcement, R.id.notice, R.id.comments})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.announcement:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.notice:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.comments:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.backRelative:
                finish();
                break;
        }
    }

    class MainPagerAdapter extends FragmentPagerAdapter {
        FragmentManager fm;
        List<Fragment> fragments;

        public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
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
