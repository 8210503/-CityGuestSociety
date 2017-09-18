package www.cityguestsociety.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

import www.cityguestsociety.com.entity.GuoJiangTitle;
import www.cityguestsociety.com.firstfragmentactivity.NewsFragment;
import www.cityguestsociety.com.ui.LazyFragmentPagerAdapter;

/**
 * Created by LuoPan on 2017/5/16 9:30.
 */
public class PagerAdapter extends LazyFragmentPagerAdapter {
    List<Fragment> fragments;
    List<GuoJiangTitle.DataBean> title;
    private FragmentManager fm;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments, List<GuoJiangTitle.DataBean> title) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.title = title;
    }

    private PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(ViewGroup container, int position) {
        return NewsFragment.newInstance(title.get(position).getId());
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position).getTitle();
    }
}
