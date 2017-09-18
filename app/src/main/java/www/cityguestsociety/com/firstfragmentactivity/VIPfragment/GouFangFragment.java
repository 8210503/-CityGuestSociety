package www.cityguestsociety.com.firstfragmentactivity.VIPfragment;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.adapter.ViewHolder;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.Bean;

/**
 * Created by LuoPan on 2017/9/4 17:58.
 */

public class GouFangFragment extends BaseFragment {

    private View mView;
    private ListView mJifenInfolistView;
    private RelativeLayout mTextViewRelative;
    private List<Bean> mLists;

    @Override
    protected void initView() {

        mJifenInfolistView = getView(R.id.jifenInfoListview);
        mTextViewRelative = getView(R.id.textviewRemaltive);
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        if (mLists.size() == 0) {
            mTextViewRelative.setVisibility(View.GONE);
        }
        setAdapter();
    }

    private void setAdapter() {
       LogUtils.e("集合大小",mLists.size());
        mJifenInfolistView.setAdapter(new CommonAdaper<Bean>(getActivity(), mLists, R.layout.item_joinactivty_fragment) {

            @Override
            public void convert(ViewHolder holder, Bean item, int p) {
                holder.setText(R.id.joinActivityTime, item.getText());
                holder.setText(R.id.joinActityContent, item.getTextInfo());
                holder.setText(R.id.joinActityJiFen, item.getIamge());

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_joinactivity;
    }
}
