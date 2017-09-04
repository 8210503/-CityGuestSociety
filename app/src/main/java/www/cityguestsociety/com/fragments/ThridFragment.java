package www.cityguestsociety.com.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseFragment;

/**
 * Created by LuoPan on 2017/9/4 9:29.
 */

public class ThridFragment extends BaseFragment {

    private ViewHolder mHolder;
    private int mHeight;
    private int mScorllPosition;
    /**
     * item的高度
     */
    private int viewHeight;

    public ThridFragment() {
    }

    @Override
    protected void initView() {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_shared,null);
        mHolder = new ViewHolder(view);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_shared;
    }

    static class ViewHolder {
        @BindView(R.id.titleRelative)
        RelativeLayout mTitleRelative;
        @BindView(R.id.sharedListView)
        RecyclerView mSharedListView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
