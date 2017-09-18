package www.cityguestsociety.com.fourthfragmentactivity.newsfragments;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
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
import www.cityguestsociety.com.entity.Notification;
import www.cityguestsociety.com.utils.Constans;

/**
 * Created by LuoPan on 2017/9/5 15:08.
 */

public class MyNotifyFragment extends BaseFragment {
    LRecyclerView notifyListView;
    private List<Notification.DataBean> mLists;
    private BaseRecyclerAdapter mAdapter;
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

    @Override
    protected void initView() {
        notifyListView = getView(R.id.notifyListView);
        notifyListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //设置底部加载颜色
        notifyListView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        notifyListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        params.put("page", REQUEST_COUNT);
        params.put("next", mCurrentPage);
        getDataFromInternet(UrlFactory.notice, params, 0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        mLists.clear();
        LogUtils.e(object.toString());
        Gson gson = new Gson();
        Notification notification = gson.fromJson(object.toString(), Notification.class);

        TOTAL_COUNTER = Integer.parseInt(notification.getPagecount());
        mCurrentCounter++;
        mCurrentCounter += mLists.size();
        mLists.addAll(notification.getData());
        mAdapter.notifyDataSetChanged();
        notifyListView.refreshComplete(REQUEST_COUNT);
    }

    @Override
    public void NoData(JSONObject object, int what) {
        super.NoData(object, what);
        notifyListView.refreshComplete(REQUEST_COUNT);
    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<Notification.DataBean>(getActivity(), mLists, R.layout.item_notify) {
            @Override
            public void convert(BaseRecyclerHolder holder, Notification.DataBean item, int position, boolean isScrolling) {
                if (item.getNotice_type().equals("1")) {
                    holder.setText(R.id.notifyKind, "活动通知");
                } else if (item.getNotice_type().equals("2")) {
                    holder.setText(R.id.notifyKind, "积分通知");
                } else if (item.getNotice_type().equals("3")) {
                    holder.setText(R.id.notifyKind, "缴费通知");
                }
                holder.setText(R.id.notifyContent, item.getContent());
                if (item.getState().equals("0")) {

                    TextView tv_statue = holder.getView(R.id.statue);
                    tv_statue.setTextColor(Color.parseColor("#FA646F"));
                    holder.setText(R.id.statue, "未读");
                } else if (item.getState().equals("1")) {
                    holder.setText(R.id.statue, "已读");
                    TextView tv_statue = holder.getView(R.id.statue);
                    tv_statue.setTextColor(Color.parseColor("#8C9397"));
                }
            }
        };


        LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(mAdapter);
        notifyListView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        notifyListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                getData();
            }
        });
        notifyListView.refresh();
        notifyListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    notifyListView.setNoMore(true);
                }
            }
        });

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mynotify;
    }
}
