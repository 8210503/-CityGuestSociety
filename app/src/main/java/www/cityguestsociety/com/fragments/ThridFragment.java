package www.cityguestsociety.com.fragments;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.hitomi.glideloader.GlideImageLoader;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.shared.CommentListTextView;
import www.cityguestsociety.com.shared.PraiseTextView;
import www.cityguestsociety.com.shared.SharedBean;
import www.cityguestsociety.com.ui.NineGridTestLayout;

/**
 * Created by LuoPan on 2017/9/4 9:29.
 */

public class ThridFragment extends BaseFragment {
    ImageView sendShared;
    LRecyclerView sharedRecylerView;
    List<SharedBean.DataBean> mdataLists = new ArrayList<>();

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
    private LRecyclerViewAdapter mRecylerAdapter;


    public ThridFragment() {

    }

    @Override
    protected void initView() {
        sendShared = getView(R.id.sendShared);
        sharedRecylerView = getView(R.id.sharedListView);
        sharedRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置底部加载颜色
        sharedRecylerView.setFooterViewColor(R.color.colorAccent, R.color.orange, android.R.color.white);
        //设置底部加载文字提示
        sharedRecylerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

    }

    @Override
    protected void initData() {
        setAdapter();
    }

    private void setAdapter() {
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<SharedBean.DataBean>(getActivity(), mdataLists, R.layout.item_sharedfragment) {

            PraiseTextView mPraiseTextView;
            CommentListTextView commentListTextView;

            private ImageView mIv_love;

            @Override
            public void convert(BaseRecyclerHolder holder, final SharedBean.DataBean item, int position, boolean isScrolling) {

                holder.setImageByUrl(R.id.userImage, item.getPub().getImg());
                holder.setText(R.id.userNickName, item.getPub().getNickname());
                //                holder.setText(R.id.sharedTime, item.getTime());
                final ImageView iv_love = holder.getView(R.id.compassion);
                ImageView comments = holder.getView(R.id.comments);
                comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                mPraiseTextView = holder.getView(R.id.LovePeople);
                mPraiseTextView.setData(item.getGive());
                mPraiseTextView.setNameTextColor(Color.parseColor("#7E8DAE"));
                mPraiseTextView.setMiddleStr("，");//设置分割文本
                mPraiseTextView.setTextColor(Color.parseColor("#7E8DAE"));
                holder.setText(R.id.sharedContent, item.getTitle());
                final NineGridTestLayout gridView = holder.getView(R.id.sharedGiridView);
                List<SharedBean.DataBean.ImgBean> img = item.getImg();
                final List<String> imageList = new ArrayList<>();
                imageList.clear();
                for (int i = 0; i < img.size(); i++) {
                    imageList.add(UrlFactory.imaPath + img.get(i).getImg());
                }
                gridView.setUrlList(imageList);
                gridView.setIsShowAll(false);
                gridView.setOnItemClickListener(new NineGridTestLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TransferConfig config = TransferConfig.build()
                                .setNowThumbnailIndex(position)
                                .setSourceImageList(imageList)
                                .setMissPlaceHolder(R.mipmap.ic_launcher)
                                .setOriginImageList(wrapOriginImageViewList(gridView))
                                .setProgressIndicator(new ProgressPieIndicator())
                                .setIndexIndicator(new NumberIndexIndicator())
                                .setJustLoadHitImage(true)
                                .setImageLoader(GlideImageLoader.with(getActivity()))
                                .create();
                        transferee.apply(config).show(new Transferee.OnTransfereeStateChangeListener() {
                            @Override
                            public void onShow() {
                                Glide.with(getActivity()).pauseRequests();
                            }

                            @Override
                            public void onDismiss() {
                                Glide.with(getActivity()).resumeRequests();
                            }
                        });


                    }
                });
                mIv_love = holder.getView(R.id.compassion);
                mPraiseTextView.setonPraiseListener(new PraiseTextView.onPraiseClickListener() {


                    @Override
                    public void onClick(int position, SharedBean.DataBean.GiveBean mPraiseInfo) {
                        ShowToast(mPraiseInfo.getNickname().toString());
                    }

                    @Override
                    public void onOtherClick() {
                        ShowToast("点击了其他的");
                    }
                });
                mIv_love.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                commentListTextView = holder.getView(R.id.commentsListView);
                commentListTextView.setData(item.getInformation());
                //                commentListTextView.setMaxlines(mCommentInfos.size());
                //                commentListTextView.setMoreStr ("查看更多");
                commentListTextView.setNameColor(Color.parseColor("#8694B3"));
                commentListTextView.setCommentColor(Color.parseColor("#242424"));
                commentListTextView.setTalkStr("回复");
                commentListTextView.setTalkColor(Color.parseColor("#242424"));
                commentListTextView.setonCommentListener(new CommentListTextView.onCommentListener() {


                    @Override
                    public void onNickNameClick(int position, SharedBean.DataBean.InformationBean mInfo) {

                    }

                    @Override
                    public void onToNickNameClick(int position, SharedBean.DataBean.InformationBean mInfo) {

                    }

                    @Override
                    public void onCommentItemClick(int position, SharedBean.DataBean.InformationBean mInfo) {

                    }

                    @Override
                    public void onOtherClick() {

                    }
                });


            }
        };
        mRecylerAdapter = new LRecyclerViewAdapter(adapter);
        sharedRecylerView.setAdapter(mRecylerAdapter);
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("next", mCurrentPage);
        params.put("page", REQUEST_COUNT);
        getDataFromInternet(UrlFactory.share, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        mdataLists.clear();
        SharedBean sharedBean = gson.fromJson(object.toString(), SharedBean.class);
        mdataLists.addAll(sharedBean.getData());

        TOTAL_COUNTER = Integer.parseInt(sharedBean.getPagecount());
        mCurrentCounter++;
        mCurrentCounter += mdataLists.size();
        mRecylerAdapter.notifyDataSetChanged();
        sharedRecylerView.refreshComplete(REQUEST_COUNT);
    }

    @Override
    protected void setListener() {
        sharedRecylerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                mCurrentPage = 1;
                getData();
            }
        });
        sharedRecylerView.refresh();
        sharedRecylerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    sharedRecylerView.setNoMore(true);
                }
            }
        });

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_shared;
    }

    /**
     * 包装缩略图 ImageView 集合
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
}
