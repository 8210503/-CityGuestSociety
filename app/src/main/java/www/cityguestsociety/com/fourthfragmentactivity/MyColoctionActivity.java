package www.cityguestsociety.com.fourthfragmentactivity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
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
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Colocation;
import www.cityguestsociety.com.ui.NineGridTestLayout;
import www.cityguestsociety.com.utils.Constans;

public class MyColoctionActivity extends BaseToolbarActivity {


    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.noColocation)
    View noColocation;
    @BindView(R.id.MyColoctionListView)
    LRecyclerView mMyColoctionListView;
    @BindView(R.id.hasColoctionRelative)
    RelativeLayout mHasColoctionRelative;
    PopupWindow mPopWindow;
    public final String DELETE = "delete";
    protected Transferee transferee;

    List<Colocation.DataBean> mDatalists = new ArrayList<>();

    private BaseRecyclerAdapter mAdapter1;
    Colocation.DataBean data = new Colocation.DataBean();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_coloction;
    }

    @Override
    protected void initView() {
        initToobar("我的收藏");
        transferee = Transferee.getDefault(this);
        mMyColoctionListView.setLayoutManager(new LinearLayoutManager(this));
        mMyColoctionListView.setEmptyView(noColocation);

    }

    @Override
    protected void initData() {
        setAdapter();


    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.collection, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        switch (what) {
            case 0:
                Gson gson = new Gson();
                mDatalists.clear();
                mMyColoctionListView.refreshComplete(50);
                Colocation colocation = gson.fromJson(object.toString(), Colocation.class);
                mDatalists.addAll(colocation.getData());
                mAdapter1.notifyDataSetChanged();
                break;
            case 1:
                mDatalists.remove(data);
                mPopWindow.dismiss();
                mAdapter1.notifyDataSetChanged();
                ShowToast(object.getString("info"));
                break;
        }

    }

   /* @Override
    protected void noData(JSONObject jsonObject, int what) {
        LogUtils.e("noData");
        Gson gson = new Gson();
        mDatalists.clear();
        mMyColoctionListView.refreshComplete(50);
        Colocation colocation = gson.fromJson(jsonObject.toString(), Colocation.class);
        mDatalists.addAll(colocation.getData());
        mAdapter1.notifyDataSetChanged();
    }*/

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


    private void setAdapter() {
        mAdapter1 = new BaseRecyclerAdapter<Colocation.DataBean>(this, mDatalists, R.layout.item_mypost_listview) {

            @Override
            public void convert(final BaseRecyclerHolder holder, final Colocation.DataBean item, int position, final boolean isScrolling) {
                holder.setText(R.id.userNickName, item.getMember().getNickname());
                holder.setText(R.id.content, item.getShare().getTitle());
                holder.setText(R.id.sendTime, item.getTime());
                holder.setText(R.id.tv_love, item.getShare().getCollection_num());
                holder.setText(R.id.tv_comments, item.getShare().getComment());
                holder.setImageByUrl(R.id.userCorn, item.getMember().getImg());

                ImageView zan = holder.getView(R.id.imageview);
                if (Integer.parseInt(mDatalists.get(position).getShare().getCollection_num()) > 0) {
                    Glide.with(getApplicationContext()).load(R.mipmap.youse).asBitmap().into(zan);
                } else {
                    Glide.with(getApplicationContext()).load(R.mipmap.zanwuse).asBitmap().into(zan);

                }

                final NineGridTestLayout gridView = holder.getView(R.id.userPhotoGridView);
                final List<String> imageList = new ArrayList<>();
                imageList.clear();
                for (int i = 0; i < item.getShare_img().size(); i++) {
                    imageList.add(UrlFactory.imaPath + item.getShare_img().get(i).getImg());
                }

                LogUtils.e(item.getId(), imageList.toString());
                gridView.setUrlList(imageList);
                gridView.setIsShowAll(true);
                holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopWindows(MyColoctionActivity.this, DELETE, item);

                    }
                });
                gridView.setOnItemClickListener(new NineGridTestLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        LogUtils.e(imageList.get(position));
                        TransferConfig config = TransferConfig.build()
                                .setNowThumbnailIndex(position)
                                .setSourceImageList(imageList)
                                .setMissPlaceHolder(R.drawable.wrong_image)
                                .setErrorPlaceHolder(R.drawable.wrong_image)
                                .setOriginImageList(wrapOriginImageViewList(gridView))
                                .setProgressIndicator(new ProgressPieIndicator())
                                .setImageLoader(GlideImageLoader.with(getApplicationContext()))
                                .create();
                        transferee.apply(config).show(new Transferee.OnTransfereeStateChangeListener() {
                            @Override
                            public void onShow() {
                                Glide.with(MyColoctionActivity.this).pauseRequests();
                            }

                            @Override
                            public void onDismiss() {
                                Glide.with(MyColoctionActivity.this).resumeRequests();
                            }
                        });
                    }
                });
            }
        };
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter1);
        mMyColoctionListView.setAdapter(mLRecyclerViewAdapter);

    }

    private void showPopWindows(Activity activity, String message, final Colocation.DataBean obj) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        if (message.equals(DELETE)) {
            popView = View.inflate(this, R.layout.pop_delete, null);
            TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
            TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data = obj;

                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("id", obj.getId());
                    getDataFromInternet(UrlFactory.del_collection, params, 1);

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
        mMyColoctionListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        mMyColoctionListView.refresh();

        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(MyColocationInfoActivity.id, mDatalists.get(position).getId());
                bundle.putInt(MyColocationInfoActivity.typeKind, 0);
                jumpToActivity(MyColocationInfoActivity.class, bundle, false);
            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transferee.dismiss();
    }
}
