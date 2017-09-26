package www.cityguestsociety.com.fragments;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
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

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.shared.CommentListTextView;
import www.cityguestsociety.com.shared.PraiseTextView;
import www.cityguestsociety.com.shared.SharedBean;
import www.cityguestsociety.com.thirdfragemntActivity.SendSharedActivity;
import www.cityguestsociety.com.ui.MyYAnimation;
import www.cityguestsociety.com.ui.NineGridLayout;
import www.cityguestsociety.com.ui.NineGridTestLayout;
import www.cityguestsociety.com.utils.Constans;
import www.cityguestsociety.com.utils.SoftKeyBoardListener;

/**
 * Created by LuoPan on 2017/9/4 9:29.
 */

public class ThridFragment extends BaseFragment {
    ImageView sendShared;
    LRecyclerView sharedRecylerView;
    protected Transferee transferee;
    List<SharedBean.DataBean> mdataLists = new ArrayList<>();
    List<SharedBean.DataBean> mdataLists1 = new ArrayList<>();
    SharedBean.DataBean data;
    public static final String mAction = "com.cityGuestsociety.com.refresh";

    public static boolean isRefresh = false;
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
    /**
     * item的高度
     */
    private int viewHeight;
    PopupWindow mPopWindow;
    private Button mBt_send;
    private EditText mEt_commments;
    private int softKeyBoredHeight;
    PraiseTextView mPraiseTextView;
    CommentListTextView commentListTextView;
    int pos;
    private BaseRecyclerAdapter mAdapter;
    private MyYAnimation mAnimation;
    private RelativeLayout mIv_love;
    private ImageView mIvLove;
    private String mReplayId;
    private String mReplayName;
    public final String DELETE = "delete";
    public static boolean isShouldRefresh = false;
    private ImageView mColocation;
    private RelativeLayout mColocationRelative;


    public ThridFragment() {

    }

    @Override
    protected void initView() {
        transferee = Transferee.getDefault(getActivity());
        sendShared = getView(R.id.sendShared);
        sharedRecylerView = getView(R.id.sharedListView);
        sharedRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置底部加载颜色
        sharedRecylerView.setFooterViewColor(R.color.colorAccent, R.color.white, android.R.color.white);
        //设置底部加载文字提示
        sharedRecylerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(mAction);
        BroadcastReceiver br = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtils.e("onReceive");
                sharedRecylerView.refresh();
            }

        };
        localBroadcastManager.registerReceiver(br, intentFilter);

    }

    @Override
    protected void initData() {
        setAdapter();
    }


    private void setAdapter() {

        // TODO: 2017/9/20  发送评论或者回复
        mAdapter = new BaseRecyclerAdapter<SharedBean.DataBean>(getActivity(), mdataLists1, R.layout.item_sharedfragment) {


            @Override
            public void convert(BaseRecyclerHolder holder, final SharedBean.DataBean item, final int position, boolean isScrolling) {
                if (item.getPub() != null) {
                    holder.setImageByUrl(R.id.userImage, item.getPub().getImg());
                    holder.setText(R.id.userNickName, item.getPub().getNickname());
                } else {
                    Glide.with(getActivity()).load(R.mipmap.icon_head_portrait).asBitmap().into((ImageView) holder.getView(R.id.userImage));
                    holder.setText(R.id.userNickName, "用户昵称");
                }

                holder.setText(R.id.sharedTime, item.getRelease_time());
                //                holder.setText(R.id.sharedTime, item.getTime());

                mColocationRelative = holder.getView(R.id.colocationRelative);
                mColocation = holder.getView(R.id.colocation);

                ImageView imageView = holder.getView(R.id.userImage);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (item.getWo() == 1) {
                            return;
                        } else {

                            // TODO: 2017/9/23  弹出对话框
                            new AlertDialog.Builder(getActivity()).setTitle("提示")//设置对话框标题
                                    .setMessage("是否不看此人动态?")//设置显示的内容
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            dialog.dismiss();
                                            pos = position;
                                            RequestParams params = new RequestParams();
                                            params.put("member_id", Constans.ID);
                                            params.put("black_member_id", item.getMember_id());
                                            getDataFromInternet(UrlFactory.addblacklist, params, 6);

                                        }

                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();//在按键响应事件中显示此对话框、

                        }
                    }
                });


                if (item.getCollection() == 0) {
                    Glide.with(getActivity()).load(R.mipmap.btn_compassion_bule).into(mColocation);
                    mColocationRelative.setEnabled(true);
                } else if (item.getCollection() == 1) {
                    Glide.with(getActivity()).load(R.mipmap.btn_compassion_pre).into(mColocation);
                    mColocationRelative.setEnabled(false);
                }

                mColocationRelative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pos = position;
                        RequestParams params = new RequestParams();
                        params.put("member_id", Constans.ID);
                        params.put("share_id", mdataLists1.get(position).getId());
                        getDataFromInternet(UrlFactory.subshare, params, 5);
                    }
                });


                /**点赞的人为空 就隐藏掉这个图片*/
                ImageView img = holder.getView(R.id.imageView8);
                if (mdataLists1.get(position).getGive().size() == 0) {
                    img.setVisibility(View.GONE);
                } else {
                    img.setVisibility(View.VISIBLE);
                }


                /**删除文字*/
                TextView delete = holder.getView(R.id.textView4);
                /**是否是本人发布  0不是自己*/
                if (item.getWo() == 0) {
                    delete.setVisibility(View.INVISIBLE);
                } else {
                    delete.setVisibility(View.VISIBLE);
                }
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopWindows(getActivity(), DELETE, mdataLists1.get(position));
                    }
                });

                mIv_love = holder.getView(R.id.loveRelative);
                mIvLove = holder.getView(R.id.compassion);

                /**未点赞*/
                if (item.getGivemi() == 0) {
                    mIv_love.setEnabled(true);
                    Glide.with(getActivity()).load(R.mipmap.zanwuse).into(mIvLove);
                } else {
                    mIv_love.setEnabled(false);
                    Glide.with(getActivity()).load(R.mipmap.youse).into(mIvLove);
                }

                mIv_love.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.put("member_id", Constans.ID);
                        params.put("cover_reply_id", mdataLists1.get(position).getMember_id());
                        params.put("share_id", mdataLists1.get(position).getId());
                        //                        params.put("content","");
                        params.put("type", 1);
                        getDataFromInternet(UrlFactory.onShare, params, 1);
                        mIvLove.startAnimation(mAnimation);
                        pos = position;
                    }
                });


                RelativeLayout comments = holder.getView(R.id.commentsRelative);
                comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopWindows();
                        popupInputMethodWindow();
                        mEt_commments.setHint("评论" + mdataLists1.get(position).getPub().getNickname());
                        mBt_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEt_commments.getText().toString().trim().isEmpty()) {
                                    mPopWindow.dismiss();
                                } else {

                                    // TODO: 2017/9/20  发送评论或者回复
                                    RequestParams params = new RequestParams();
                                    params.put("member_id", Constans.ID);
                                    params.put("cover_reply_id", mdataLists1.get(position).getMember_id());
                                    params.put("share_id", mdataLists1.get(position).getId());
                                    params.put("content", mEt_commments.getText().toString().trim());
                                    params.put("type", 2);
                                    getDataFromInternet(UrlFactory.onShare, params, 2);
                                    pos = position;


                                }
                            }
                        });
                    }

                });
                mPraiseTextView = holder.getView(R.id.LovePeople);
                mPraiseTextView.setData(item.getGive());
                mPraiseTextView.setNameTextColor(Color.parseColor("#7E8DAE"));
                mPraiseTextView.setMiddleStr("，");//设置分割文本
                mPraiseTextView.setTextColor(Color.parseColor("#7E8DAE"));
                holder.setText(R.id.sharedContent, item.getTitle());


                final List<String> imageList = new ArrayList<>();
                imageList.clear();
                for (int i = 0; i < item.getImg().size(); i++) {
                    imageList.add(UrlFactory.imaPath + item.getImg().get(i).getImg());
                }
                final NineGridTestLayout gridView = holder.getView(R.id.userPhotoGridView);
                gridView.setUrlList(imageList);
                gridView.setIsShowAll(true);
                gridView.setOnItemClickListener(new NineGridTestLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TransferConfig config = TransferConfig.build()
                                .setNowThumbnailIndex(position)
                                .setSourceImageList(imageList)
                                .setMissPlaceHolder(R.mipmap.ic_launcher)
                                .setOriginImageList(wrapOriginImageViewList(gridView))
                                .setProgressIndicator(new ProgressPieIndicator())
                                .setImageLoader(GlideImageLoader.with(MyApplication.getContext()))
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

           /*     *//**点赞的人的昵称*//*
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
*/

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
                    public void onNickNameClick(int i, SharedBean.DataBean.InformationBean mInfo) {


                    }

                    @Override
                    public void onToNickNameClick(int i, SharedBean.DataBean.InformationBean mInfo) {

                    }

                    @Override
                    public void onCommentItemClick(final int i, final SharedBean.DataBean.InformationBean mInfo) {
                        showPopWindows();
                        popupInputMethodWindow();
                        mEt_commments.setHint("回复" + mInfo.getReply_name());
                        mBt_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mEt_commments.getText().toString().trim().isEmpty()) {
                                    mPopWindow.dismiss();
                                } else {
                                    // TODO: 2017/9/20  发送回复
                                    mReplayId = mInfo.getReply_id();
                                    mReplayName = mInfo.getReply_name();

                                    RequestParams params = new RequestParams();
                                    params.put("member_id", Constans.ID);
                                    params.put("cover_reply_id", mReplayId);
                                    params.put("share_id", mdataLists1.get(position).getId());
                                    params.put("content", mEt_commments.getText().toString().trim());
                                    params.put("type", 3);
                                    getDataFromInternet(UrlFactory.onShare, params, 3);
                                    pos = position;

                                }
                            }
                        });
                    }

                    @Override
                    public void onOtherClick() {

                    }
                });


            }
        };
        mRecylerAdapter = new LRecyclerViewAdapter(mAdapter);
        sharedRecylerView.setAdapter(mRecylerAdapter);
    }


    void showPopWindows() {
        View parent = ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
        viewHeight = parent.getHeight();
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.comments_pop_menu, null);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = 200;
        mPopWindow = new PopupWindow(popView, width, height);
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.white));
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getSoftKeyBoredHeight());
        mEt_commments = (EditText) popView.findViewById(R.id.edit_comment);
        mBt_send = (Button) popView.findViewById(R.id.button);
        //        et_commments.setHint("回复" + mInfo.getNickname());


    }

    private void showPopWindows(Activity activity, String message, final SharedBean.DataBean obj) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        if (message.equals(DELETE)) {
            popView = View.inflate(getActivity(), R.layout.pop_delete, null);
            TextView tv_commit = (TextView) popView.findViewById(R.id.YES);
            TextView tv_cancle = (TextView) popView.findViewById(R.id.cancle);
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data = obj;
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("id", obj.getId());
                    getDataFromInternet(UrlFactory.del_share, params, 4);

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

    private void popupInputMethodWindow() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);
    }

    public int getSoftKeyBoredHeight() {
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                softKeyBoredHeight = height;

            }

            @Override
            public void keyBoardHide(int height) {
                mPopWindow.dismiss();

            }
        });
        return softKeyBoredHeight;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        transferee.dismiss();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("next", mCurrentPage);
        params.put("page", REQUEST_COUNT);
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.share, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                mdataLists.clear();
                if (isRefresh) {
                    LogUtils.e("isRefresh");
                    mdataLists1.clear();
                }
                SharedBean sharedBean = gson.fromJson(object.toString(), SharedBean.class);
                mdataLists.addAll(sharedBean.getData());
                mdataLists1.addAll(mdataLists);


                TOTAL_COUNTER = Integer.parseInt(sharedBean.getPagecount());
                mCurrentPage++;
                mCurrentCounter += mdataLists.size();

                LogUtils.e(mCurrentCounter + "----" + TOTAL_COUNTER);

                mRecylerAdapter.notifyDataSetChanged();
                sharedRecylerView.refreshComplete(REQUEST_COUNT);
                break;
            case 1:
                /**点赞*/
                mIvLove.clearAnimation();

                mdataLists1.get(pos).setGivemi(1);

                mdataLists1.get(pos).getGive().add(new SharedBean.DataBean.GiveBean(Constans.ID, Constans.nickName));
                mRecylerAdapter.notifyDataSetChanged();
                break;
            case 2:
                /**评论*/
                mPopWindow.dismiss();
                mdataLists1.get(pos).getInformation().add(new SharedBean.DataBean.InformationBean(Constans.ID, mEt_commments.getText().toString().trim()
                        , mdataLists1.get(pos).getId(), "2", Constans.nickName, mdataLists1.get(pos).getPub().getNickname()));
                mRecylerAdapter.notifyDataSetChanged();
                break;
            case 3:
                /**回复别人*/
                mPopWindow.dismiss();
                mdataLists1.get(pos).getInformation().add(new SharedBean.DataBean.InformationBean(Constans.ID, mEt_commments.getText().toString().trim()
                        , mReplayId, "3", Constans.nickName, mReplayName));
                mRecylerAdapter.notifyDataSetChanged();

                break;
            case 4:
                mPopWindow.dismiss();
                ShowToast(object.getString("info"));
                mdataLists1.remove(data);
                mRecylerAdapter.notifyDataSetChanged();
                break;
            case 5:
                ShowToast(object.getString("info"));
                mColocationRelative.setEnabled(false);
                mdataLists1.get(pos).setCollection(1);
                mRecylerAdapter.notifyDataSetChanged();
                break;
            case 6:
                ShowToast(object.getString("info"));
                mdataLists1.remove(pos);
                mRecylerAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    protected void setListener() {
        sharedRecylerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mCurrentCounter = 0;
                mCurrentPage = 1;
                getData();
            }
        });
        sharedRecylerView.refresh();
        sharedRecylerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;

                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    sharedRecylerView.setNoMore(true);
                }
            }
        });

        mAnimation = new MyYAnimation();

        mAnimation.setRepeatCount(Animation.INFINITE);

        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIv_love.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIv_love.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        sendShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(SendSharedActivity.class, false);
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
    protected List<ImageView> wrapOriginImageViewList(NineGridLayout nineGridTestLayout) {
        List<ImageView> originImgList;
        originImgList = nineGridTestLayout.getImageViewList();
        LogUtils.e(originImgList.size());
        return originImgList;
    }
}
