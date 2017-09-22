package www.cityguestsociety.com.fourthfragmentactivity;

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

import butterknife.BindView;
import www.cityguestsociety.com.ActivityInfo;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.JoinActivities;
import www.cityguestsociety.com.utils.Constans;

public class MyJoinActivity extends BaseToolbarActivity {


    @BindView(R.id.joinedActivityListView)
    LRecyclerView mJoinedActivityListView;
    @BindView(R.id.empty_view)
    View View;
    private List<JoinActivities.DataBean> mList;
    private List<JoinActivities.DataBean> mDataLists = new ArrayList<>();
    public boolean isRefresh = false;
    private BaseRecyclerAdapter mAdapter;
    private LRecyclerViewAdapter mAdapter1;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_join;
    }

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
    protected void initData() {
        mList = new ArrayList<>();
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        params.put("page", REQUEST_COUNT);
        params.put("next", mCurrentPage);
        getDataFromInternet(UrlFactory.miactivity, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        mList.clear();
        if (isRefresh) {
            mDataLists.clear();
        }
        JoinActivities joinActivities = gson.fromJson(object.toString(), JoinActivities.class);
        mList.addAll(joinActivities.getData());
        mDataLists.addAll(mList);
        TOTAL_COUNTER = Integer.parseInt(joinActivities.getPagecount());
        mCurrentCounter++;
        mCurrentCounter += mList.size();
        mAdapter.notifyDataSetChanged();
        mJoinedActivityListView.refreshComplete(REQUEST_COUNT);

    }

    private void setAdapter() {

        mAdapter = new BaseRecyclerAdapter<JoinActivities.DataBean>(this, mDataLists, R.layout.item_jfexchange) {
            @Override
            public void convert(BaseRecyclerHolder holder, JoinActivities.DataBean item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.projectShowImage, UrlFactory.imaPath + item.getImg());
                holder.setText(R.id.projectName, item.getTitle());
                holder.setText(R.id.projectAddress, item.getStart_time());
                holder.setText(R.id.baomingrenshuTextview, item.getEnd_time());
                holder.setText(R.id.count, "报名人数:" + item.getSign() + "/" + item.getMax_num());
                TextView bt_statue = holder.getView(R.id.CheckStatue);
                if (item.getState().equals("0")) {
                    bt_statue.setText("活动已结束");
                    bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_gray));
                }
                if (item.getState().equals("1")) {
                    bt_statue.setText("活动进行中");
                }
                if (item.getState().equals("2")) {
                    bt_statue.setText("活动未开始");
                }


            }
        };
        mAdapter1 = new LRecyclerViewAdapter(mAdapter);
        mJoinedActivityListView.setAdapter(mAdapter1);

    }

    @Override
    protected void setListener() {
      /*  mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        mAdapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInfo.TITLE, "活动详情");
                bundle.putInt(ActivityInfo.STATUE, 3);
                bundle.putInt(ActivityInfo.CAN, 3);
                bundle.putString(ActivityInfo.URL, UrlFactory.miactivity_cont + "/member_id/" + Constans.ID + "/id/" + mDataLists.get(position).getId());
                bundle.putBoolean(ActivityInfo.isShowing, true);
                bundle.putBoolean(ActivityInfo.isCheck,true);
                jumpToActivity(ActivityInfo.class, bundle, false);
            }
        });

        mJoinedActivityListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                isRefresh = true;
                getData();
            }
        });
        mJoinedActivityListView.refresh();
        mJoinedActivityListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    mJoinedActivityListView.setNoMore(true);
                }
            }
        });


    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar( "参加的活动");
        mJoinedActivityListView.setLayoutManager(new LinearLayoutManager(this));
        //设置底部加载颜色
        mJoinedActivityListView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        mJoinedActivityListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        mJoinedActivityListView.setEmptyView(View);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void RightOnClick() {

    }
}
