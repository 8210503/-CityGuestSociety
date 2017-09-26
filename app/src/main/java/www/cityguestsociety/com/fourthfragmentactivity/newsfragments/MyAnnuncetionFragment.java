package www.cityguestsociety.com.fourthfragmentactivity.newsfragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
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
import www.cityguestsociety.com.entity.Annuncetion;
import www.cityguestsociety.com.utils.Constans;

/**
 * Created by LuoPan on 2017/9/5 15:08.
 */

public class MyAnnuncetionFragment extends BaseFragment {
    LRecyclerView notifyListView;
    private List<Annuncetion.DataBean> mLists = new ArrayList<>();
    private List<Annuncetion.DataBean> mdataLists = new ArrayList<>();
    private BaseRecyclerAdapter mAdapter;
    private LRecyclerViewAdapter mAdapter1;
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

    @Override
    protected void initView() {
        notifyListView = getView(R.id.notifyListView);
        notifyListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置底部加载颜色
        notifyListView.setFooterViewColor(R.color.colorAccent, R.color.white, android.R.color.white);
        //设置底部加载文字提示
        notifyListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("num", 20);
        params.put("member_id", Constans.ID);
        params.put("next", mCurrentPage);
        params.put("page", REQUEST_COUNT);
        getDataFromInternet(UrlFactory.system, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        mLists.clear();
        if (isRefresh) {
            mdataLists.clear();
        }
        super.getSuccess(object, what);
        Gson gson = new Gson();
        Annuncetion annuncetion = gson.fromJson(object.toString(), Annuncetion.class);
        mLists.addAll(annuncetion.getData());

        mdataLists.addAll(mLists);

        notifyListView.refreshComplete(20);

        TOTAL_COUNTER = annuncetion.getPagecount();
        mCurrentPage++;
        mCurrentCounter += mLists.size();
        mAdapter.notifyDataSetChanged();
        notifyListView.refreshComplete(REQUEST_COUNT);
        mAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<Annuncetion.DataBean>(getActivity(), mdataLists, R.layout.item_announcement) {
            @Override
            public void convert(BaseRecyclerHolder holder, Annuncetion.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.notifyContent, item.getTitle());
                holder.setText(R.id.time, item.getCreation_time());
                TextView statue = holder.getView(R.id.statue);
                if (item.getState() == 0) {
                    statue.setText("未读");
                    statue.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    statue.setText("已读");
                    statue.setTextColor(getResources().getColor(R.color.gray));
                }
            }
        };

        mAdapter1 = new LRecyclerViewAdapter(mAdapter);
        notifyListView.setAdapter(mAdapter1);
    }

    @Override
    protected void setListener() {
        notifyListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                isRefresh = true;
                getData();
            }
        });
        notifyListView.refresh();
        notifyListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    notifyListView.setNoMore(true);
                }
            }
        });


        mAdapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(SystemAnnuncationWebView.ID, mdataLists.get(position).getId());
                jumpToActivity(SystemAnnuncationWebView.class, bundle, false);
            }
        });
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mynotify;
    }
}
