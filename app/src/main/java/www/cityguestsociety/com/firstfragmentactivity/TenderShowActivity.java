package www.cityguestsociety.com.firstfragmentactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Tender;

public class TenderShowActivity extends BaseToolbarActivity {
    private List<Tender.DataBean> mLists;

    @BindView(R.id.tenderShowListView)
    LRecyclerView mTenderShowListView;
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
    private List<Tender.DataBean> mDataList;
    private BaseRecyclerAdapter mAdapter;
    public boolean isRefresh = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_tender_show;
    }

    @Override
    protected void initView() {
        initToobar("招商展示");
        mTenderShowListView.setLayoutManager(new LinearLayoutManager(this));
        //设置底部加载颜色
        mTenderShowListView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        mTenderShowListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        mDataList = new ArrayList<>();
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("next", mCurrentPage);
        params.put("page", REQUEST_COUNT);
        getDataFromInternet(UrlFactory.attract, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        mLists.clear();
        if (isRefresh) {
            mDataList.clear();
        }
        Tender tender = gson.fromJson(object.toString(), Tender.class);
        mLists = tender.getData();
        mDataList.addAll(mLists);
        TOTAL_COUNTER = Integer.parseInt(tender.getPagecount());
        mCurrentPage++;
        mCurrentCounter += mLists.size();

        LogUtils.e(mCurrentCounter + "____" + TOTAL_COUNTER);

        mAdapter.notifyDataSetChanged();

        mTenderShowListView.refreshComplete(REQUEST_COUNT);
    }

    @Override
    public void getFailed(JSONObject jsonObject, int what) {
        mTenderShowListView.refreshComplete(REQUEST_COUNT);

    }

    private void setAdapter() {

        mAdapter = new BaseRecyclerAdapter<Tender.DataBean>(this, mDataList, R.layout.item_tendershow) {

            @Override
            public void convert(BaseRecyclerHolder holder, Tender.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tenderThem, item.getTitle());
                holder.setText(R.id.tenderInfo, item.getBrief());
                holder.setText(R.id.time, item.getCreation_time());
                holder.setImageByUrl(R.id.tenderImage, UrlFactory.imaPath + item.getImg());
            }
        };
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mTenderShowListView.setAdapter(lRecyclerViewAdapter);

    }

    @Override
    protected void setListener() {

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString(TenderShowWebViewActivity.ID, mDataList.get(position - 1).getId());
                jumpToActivity(TenderShowWebViewActivity.class, bundle, false);
            }
        });

        mTenderShowListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    mTenderShowListView.setNoMore(true);
                }
            }
        });

        mTenderShowListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                isRefresh = true;
                getData();
            }
        });
        mTenderShowListView.refresh();
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {

    }


}
