package www.cityguestsociety.com.fourthfragmentactivity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.hitomi.glideloader.GlideImageLoader;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
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
import www.cityguestsociety.com.entity.PostBean;
import www.cityguestsociety.com.ui.NineGridTestLayout;
import www.cityguestsociety.com.utils.Constans;

public class MyPostActivity extends BaseToolbarActivity {


    @BindView(R.id.imagevi)
    ImageView mImagevi;
    @BindView(R.id.noPostRelative)
    RelativeLayout mNoPostRelative;
    @BindView(R.id.myPostListView)
    LRecyclerView mMyPostListView;
    @BindView(R.id.hasPostRelative)
    RelativeLayout mHasPostRelative;
    private List<PostBean.DataBean> mLists = new ArrayList<>();
    private List<PostBean.DataBean> mDataLists = new ArrayList<>();
    PopupWindow mPopWindow;
    private BaseRecyclerAdapter mAdapter;

    @BindView(R.id.noPost)
    View noPost;
    public final String DELETE = "delete";
    int delPosition;

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
    public boolean isRefresh = false;


    @Override
    protected int getContentView() {
        return R.layout.activity_my_post;
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        params.put("page", REQUEST_COUNT);
        params.put("next", mCurrentPage);
        getDataFromInternet(UrlFactory.sharetie, params, 0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                mLists.clear();
                if (isRefresh) {
                    mDataLists.clear();
                }
                PostBean joinActivities = gson.fromJson(object.toString(), PostBean.class);
                mLists = joinActivities.getData();

                mDataLists.addAll(mLists);

                TOTAL_COUNTER = Integer.parseInt(joinActivities.getPagecount());
                mCurrentPage++;
                mCurrentCounter += mLists.size();
                mAdapter.notifyDataSetChanged();
                mMyPostListView.refreshComplete(REQUEST_COUNT);
                break;

            case 1:
                mDataLists.remove(delPosition);
                mAdapter.notifyDataSetChanged();
                mPopWindow.dismiss();
                ShowToast(object.getString("info"));
                break;
        }

    }

    /**
     * 包装缩略图  集合
     * <p>
     * 注意：此方法只是为了收集 Activity 列表中所有可见 ImageView 好传递给 transferee。
     * 如果你添加了一些图片路径，扩展了列表图片个数，让列表超出屏幕，导致一些 ImageViwe 不
     * 可见，那么有可能这个方法会报错。这种情况，可以自己根据实际情况，来设置 transferee 的
     * originImageList 属性值
     *
     * @return
     */
    @NonNull
    protected List<ImageView> wrapOriginImageViewList(NineGridTestLayout nineGridTestLayout) {
        List<ImageView> originImgList = new ArrayList<>();
        originImgList = nineGridTestLayout.getImageViewList();
       /* for (int i = 0; i < size; i++) {
            ImageView thumImg = (ImageView) ((LinearLayout) gvImages.getChildAt(i)).getChildAt(0);
            originImgList.add(thumImg);
        }*/
        return originImgList;
    }

    private void setAdapter() {

        mAdapter = new BaseRecyclerAdapter<PostBean.DataBean>(this, mDataLists, R.layout.item_mypost_listview) {
            @Override
            public void convert(BaseRecyclerHolder holder, final PostBean.DataBean item, final int position, boolean isScrolling) {


                holder.setText(R.id.userNickName, item.getMember().getNickname());
                holder.setText(R.id.content, item.getTitle());
                holder.setText(R.id.tv_love, item.getCollection_num());
                holder.setText(R.id.tv_comments, item.getComment());
                holder.setImageByUrl(R.id.userCorn, item.getMember().getImg());
                holder.setText(R.id.sendTime, item.getRelease_time());
                final List<String> imageList = new ArrayList<>();
                imageList.clear();
                for (int i = 0; i < item.getImg().size(); i++) {
                    imageList.add(UrlFactory.imaPath + item.getImg().get(i).getImg());
                }
                final NineGridTestLayout gridView = holder.getView(R.id.userPhotoGridView);
                gridView.setUrlList(imageList);
                gridView.setIsShowAll(true);
                holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopWindows(MyPostActivity.this, DELETE, position, item.getId());

                    }
                });
                gridView.setOnItemClickListener(new NineGridTestLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TransferConfig config = TransferConfig.build()
                                .setNowThumbnailIndex(position)
                                .setSourceImageList(imageList)
                                .setMissPlaceHolder(R.mipmap.ic_launcher)
                                .setOriginImageList(wrapOriginImageViewList(gridView))
                                .setProgressIndicator(new ProgressPieIndicator())
                                .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                                .create();
                        transferee.apply(config).show(new Transferee.OnTransfereeStateChangeListener() {
                            @Override
                            public void onShow() {
                                Glide.with(MyPostActivity.this).pauseRequests();
                            }

                            @Override
                            public void onDismiss() {
                                Glide.with(MyPostActivity.this).resumeRequests();
                            }
                        });
                    }
                });

            }

        };
        LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(mAdapter);
        mMyPostListView.setAdapter(adapter);


    }

    private void showPopWindows(Activity activity, String message, final int position, final String id) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        if (message.equals(DELETE)) {
            popView = View.inflate(this, R.layout.pop_delete, null);
            TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
            TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    delPosition = position;
                    //                    mLists.remove(obj);
                 /*   mAdapter.delete(position);
                    mPopWindow.dismiss();
                    mAdapter.notifyDataSetChanged();
                    if (mLists.size() == 0) {
                        mNoPostRelative.setVisibility(View.VISIBLE);
                        mHasPostRelative.setVisibility(View.INVISIBLE);
                    }*/
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("id", id);
                    getDataFromInternet(UrlFactory.del_share, params, 1);


                }
            });
            tv_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopWindow.dismiss();
                }
            });
        }
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    protected void setListener() {
        mMyPostListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                isRefresh = true;
                getData();
            }
        });
        mMyPostListView.refresh();
        mMyPostListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh=false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    mMyPostListView.setNoMore(true);
                }
            }
        });


    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar("我的贴子");
        mMyPostListView.setLayoutManager(new LinearLayoutManager(this));
        mMyPostListView.setEmptyView(noPost);
        //设置底部加载颜色
        mMyPostListView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        mMyPostListView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
    }

    @Override
    public void onClick(View v) {

    }

}
