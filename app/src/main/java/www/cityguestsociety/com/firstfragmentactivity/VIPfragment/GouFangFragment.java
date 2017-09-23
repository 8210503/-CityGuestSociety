package www.cityguestsociety.com.firstfragmentactivity.VIPfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.VIPintegral;
import www.cityguestsociety.com.utils.Constans;

/**
 * Created by LuoPan on 2017/9/4 17:58.
 */

public class GouFangFragment extends BaseFragment {

    private View mView;
    private LRecyclerView mJifenInfolistView;
    private RelativeLayout mTextViewRelative;
    private List<VIPintegral.DataBean> mLists;
    private List<VIPintegral.DataBean> mDataLists;
    private boolean isRefresh = false;

    /**
     * 服务器端一共多少条数据
     */
    private static int TOTAL_COUNTER = 0;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = MyApplication.getCount;

    /**
     * 已经获取到多少条数据了
     */
    private int mCurrentCounter = 0;

    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mAdapter;

    @Override
    protected void initView() {

        mJifenInfolistView = getView(R.id.jifenInfoListview);
        mTextViewRelative = getView(R.id.textviewRemaltive);
        mJifenInfolistView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置底部加载颜色
        mJifenInfolistView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        mJifenInfolistView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        mDataLists = new ArrayList<>();
        setAdapter();
        getData();
    }

    void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        params.put("next", mCurrentPage);
        params.put("type", 1);
        getDataFromInternet(UrlFactory.integral, params, 0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        mLists.clear();
        if (isRefresh) {
            mDataLists.clear();
        }
        VIPintegral VIPintegra = gson.fromJson(object.toString(), VIPintegral.class);

        mLists.addAll(VIPintegra.getData());
        mDataLists.addAll(mLists);

        TOTAL_COUNTER = Integer.parseInt(VIPintegra.getPagecount());
        mCurrentPage++;
        mCurrentCounter += mLists.size();


        mAdapter.notifyDataSetChanged();

        mJifenInfolistView.refreshComplete(20);
        if (mLists.size() == 0) {
            mTextViewRelative.setVisibility(View.GONE);
        }
    }

    private void setAdapter() {

        BaseRecyclerAdapter baseRecyclerAdapter = new BaseRecyclerAdapter<VIPintegral.DataBean>(getActivity(), mDataLists, R.layout.item_joinactivty_fragment) {
            @Override
            public void convert(BaseRecyclerHolder holder, VIPintegral.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.joinActivityTime, item.getTime());
                holder.setText(R.id.joinActityContent, item.getTitle());
                holder.setText(R.id.joinActityJiFen, item.getIntegral());
            }
        };
        mAdapter = new LRecyclerViewAdapter(baseRecyclerAdapter);
        mJifenInfolistView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mJifenInfolistView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    isRefresh = false;
                    // loading more
                    getData();
                } else {
                    //the end
                    mJifenInfolistView.setNoMore(true);
                }
            }
        });
        mJifenInfolistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                mCurrentCounter = 0;
                isRefresh = true;
                getData();

            }
        });
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_joinactivity;
    }
}
