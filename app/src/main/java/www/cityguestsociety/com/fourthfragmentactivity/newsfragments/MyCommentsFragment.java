package www.cityguestsociety.com.fourthfragmentactivity.newsfragments;

import android.support.v7.widget.LinearLayoutManager;

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
import www.cityguestsociety.com.entity.Comments;
import www.cityguestsociety.com.utils.Constans;

/**
 * Created by LuoPan on 2017/9/5 15:15.
 */

public class MyCommentsFragment extends BaseFragment {
    private LRecyclerView mMyCommentsListView;
    private List<Comments.DataBean> mLists;
    private List<Comments.DataBean> mdataLists = new ArrayList<>();
    public boolean isRefresh;
    private BaseRecyclerAdapter mAdapter;

    @Override
    protected void initView() {
        mMyCommentsListView = getView(R.id.notifyListView);
        mMyCommentsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置底部加载颜色
        mMyCommentsListView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        mMyCommentsListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
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
        mLists = new ArrayList<>();
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        params.put("page", REQUEST_COUNT);
        params.put("next", mCurrentPage);
        getDataFromInternet(UrlFactory.praise, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        LogUtils.e(object.toString());
        mLists.clear();
        if (isRefresh) {
            mdataLists.clear();
        }
        Gson gson = new Gson();
        Comments comments = gson.fromJson(object.toString(), Comments.class);
        mLists.addAll(comments.getData());
        mdataLists.addAll(mLists);


        TOTAL_COUNTER = Integer.parseInt(comments.getPagecount());
        mCurrentPage++;
        mCurrentCounter += mLists.size();
        mAdapter.notifyDataSetChanged();
        mMyCommentsListView.refreshComplete(REQUEST_COUNT);
    }

    @Override
    public void NoData(JSONObject object, int what) {
        super.NoData(object, what);
        mMyCommentsListView.refreshComplete(REQUEST_COUNT);
    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<Comments.DataBean>(getActivity(), mdataLists, R.layout.item_comments) {

            @Override
            public void convert(BaseRecyclerHolder holder, Comments.DataBean item, int position, boolean isScrolling) {
               /* holder.setText(R.id.conmentsType, item.getContent());
                holder.setText(R.id.commentContent, item.getTextInfo());
                holder.setImageByUrl(R.id.userPhoto, item.getMember().getImg());*/

                if (item.getType().equals("1")) {
                    /**点赞*/
                    holder.setText(R.id.conmentsType, item.getMember().getNickname() + "  赞了你的分享");
                    holder.setText(R.id.commentContent, item.getShare().getTitle());
                }
                if (item.getType().equals("2")) {
                    /**回复*/
                    holder.setText(R.id.conmentsType, item.getMember().getNickname() + "  回复了你的分享");
                    holder.setText(R.id.commentContent, item.getShare().getTitle());
                }
                if (item.getType().equals("3")) {
                    /**评论*/
                    holder.setText(R.id.conmentsType, item.getMember().getNickname() + "  评论了你的分享");
                    holder.setText(R.id.commentContent, item.getShare().getTitle());
                }
                holder.setImageByUrl(R.id.userPhoto, item.getMember().getImg());
            }
        };
        LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(mAdapter);
        mMyCommentsListView.setAdapter(adapter);

    }

    @Override
    protected void setListener() {

        mMyCommentsListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                isRefresh = true;
                getData();
            }
        });
        mMyCommentsListView.refresh();

        mMyCommentsListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh=false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    mMyCommentsListView.setNoMore(true);
                }
            }
        });

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mynotify;
    }
}
