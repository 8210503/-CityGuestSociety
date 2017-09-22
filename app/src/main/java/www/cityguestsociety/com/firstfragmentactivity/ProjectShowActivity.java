package www.cityguestsociety.com.firstfragmentactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
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
import www.cityguestsociety.com.entity.Project;

import static www.cityguestsociety.com.UrlFactory.project;

public class ProjectShowActivity extends BaseToolbarActivity {


    @BindView(R.id.projectShowListView)
    LRecyclerView mProjectShowListView;
    List<Project.DataBean> mLists = new ArrayList<>();
    List<Project.DataBean> dataLists = new ArrayList<>();
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
    private BaseRecyclerAdapter mAdapter;
    public static boolean isRefresh = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_project_show;
    }

    @Override
    protected void initData() {

        setAdapter();


    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("next", mCurrentPage);
        params.put("page", REQUEST_COUNT);
        getDataFromInternet(project, params, 0);
    }


    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        if (isRefresh) {
            dataLists.clear();
        }
        Project project = gson.fromJson(object.toString(), Project.class);
        mLists = project.getData();
        dataLists.addAll(mLists);

        TOTAL_COUNTER = Integer.parseInt(project.getPagecount());
        mCurrentPage++;
        mCurrentCounter += mLists.size();
        mAdapter.notifyDataSetChanged();
        mProjectShowListView.refreshComplete(REQUEST_COUNT);

    }

    @Override
    protected void initView() {
        initToobar("项目展示");
        mProjectShowListView.setLayoutManager(new LinearLayoutManager(this));
        //设置底部加载颜色
        mProjectShowListView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        mProjectShowListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<Project.DataBean>(this, dataLists, R.layout.item_projectshow) {

            @Override
            public void convert(BaseRecyclerHolder holder, Project.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.projectName, item.getTitle());
                holder.setText(R.id.projectAddress, item.getAddress());
                if (item.getImg() != null) {
                    holder.setImageByUrl(R.id.projectShowImage, UrlFactory.imaPath + item.getImg());
                } else {
                    holder.setImageResource(R.id.projectShowImage, R.drawable.sysyhui11);
                }
            }
        };
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mProjectShowListView.setAdapter(lRecyclerViewAdapter);
    }

    @Override
    protected void setListener() {


        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ProjectShowWebViewActivity.ID, dataLists.get(position - 1).getId());
                jumpToActivity(ProjectShowWebViewActivity.class, bundle, false);
            }
        });

        mProjectShowListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    mProjectShowListView.setNoMore(true);
                }
            }
        });

        mProjectShowListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                isRefresh = true;
                getData();
            }
        });

        mProjectShowListView.refresh();


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
