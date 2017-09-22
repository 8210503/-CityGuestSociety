package www.cityguestsociety.com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;
import www.cityguestsociety.com.ActivityInfoActivity;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.Banner;
import www.cityguestsociety.com.firstfragmentactivity.GuoJiangActivity;
import www.cityguestsociety.com.firstfragmentactivity.OpenDoorActivity;
import www.cityguestsociety.com.firstfragmentactivity.ProjectShowActivity;
import www.cityguestsociety.com.firstfragmentactivity.PropertyServicesActivity;
import www.cityguestsociety.com.firstfragmentactivity.TenderShowActivity;
import www.cityguestsociety.com.firstfragmentactivity.VIPActivity;
import www.cityguestsociety.com.firstfragmentactivity.VIPfragment.JiFenExchangeActivity;
import www.cityguestsociety.com.utils.Constans;
import www.cityguestsociety.com.utils.NetImageLoaderHolder;
import www.cityguestsociety.com.utils.SPUtils;

/**
 * Created by LuoPan on 2017/9/4 9:23.
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {


    RelativeLayout openDoorRelative;
    RelativeLayout mServices;
    RelativeLayout vip;
    RelativeLayout guojiangfengcai;
    RelativeLayout projectShow;
    RelativeLayout tenderShow;
    ImageView mImageView;
    ConvenientBanner mCarouselImage;
    TextView newActivityTime;
    TextView newActivityTextInfo;
    RelativeLayout newsRelative;
    private int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
    private String[] images;
    private String mStatue;
    private int mCan;
    private String mId;

    private static FirstFragment fistFragment;
    private String mBannerId;
    private Banner mBanner;
    private TwinklingRefreshLayout refreshLayout;


    public FirstFragment() {
        super();
        Log.e("TAG+++++", "FirstFragment");
    }


    public static FirstFragment getInstance() {
     /*   if (fistFragment == null) {
            synchronized (FirstFragment.class) {
                if (fistFragment == null) {
                    fistFragment = new FirstFragment();
                }
            }
        }*/
        fistFragment = new FirstFragment();
        return fistFragment;
    }


    @Override
    protected void initView() {
        openDoorRelative = getView(R.id.openDoor);
        mServices = getView(R.id.services);
        refreshLayout = getView(R.id.refreshLayout);
        vip = getView(R.id.VIP);
        guojiangfengcai = getView(R.id.guojiangfengcai);
        projectShow = getView(R.id.projectShow);
        tenderShow = getView(R.id.tenderShow);
        mImageView = getView(R.id.webViewImage);
        mCarouselImage = getView(R.id.CarouselImage);
        newActivityTime = getView(R.id.newActivityTime);
        newActivityTextInfo = getView(R.id.newActivityTextInfo);
        newsRelative = getView(R.id.newsRelative);


        /**自动登录*/
        if (!SPUtils.getCount().isEmpty()) {
            RequestParams params = new RequestParams();
            params.put("username", SPUtils.getCount());
            params.put("password", SPUtils.getPWD());
            getDataFromInternet(UrlFactory.logins, params, 3);
        }


        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.zhuanquan);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.startRefresh();
        refreshLayout.setEnableLoadmore(false);


    }

    @Override
    protected void initData() {
        /**首页banner图*/
        RequestParams params = new RequestParams();
        params.put("number", 5);
        getDataFromInternet(UrlFactory.topbanner, params, 0);
    }

    public void refresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.startRefresh();
            }
        }, 200);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("onResume");
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.startRefresh();
            }
        }, 200);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                /**首页banner图*/
                Gson gson = new Gson();
                mBanner = gson.fromJson(object.toString(), Banner.class);
                images = new String[mBanner.getData().size()];
                for (int i = 0; i < mBanner.getData().size(); i++) {
                    images[i] = UrlFactory.imaPath + mBanner.getData().get(i).getImg();
                }

                setViews();//设置轮播图
                /**中间的广告图片*/
                RequestParams params = new RequestParams();
                getDataFromInternet(UrlFactory.zbanner, params, 1);
                break;
            case 1:
                /**中间的广告图片*/
                String bannerImage = object.getJSONArray("data").getJSONObject(0).getString("img");
                Glide.with(MyApplication.getContext()).load(UrlFactory.imaPath + bannerImage).centerCrop().into(mImageView);
                mBannerId = object.getJSONArray("data").getJSONObject(0).getString("id");


                /**最新活动*/
                params = new RequestParams();
                params.put("member_id", Constans.ID);
                params.put("num", 1);
                getDataFromInternet(UrlFactory.activity, params, 2);
                break;
            case 2:
                /**最新活动*/
                refreshLayout.finishRefreshing();
                String title = object.getJSONArray("data").getJSONObject(0).getString("title");
                String sendTime = object.getJSONArray("data").getJSONObject(0).getString("release_time");
                String endTime = object.getJSONArray("data").getJSONObject(0).getString("end_time");
                String startTime = object.getJSONArray("data").getJSONObject(0).getString("start_time");
                mId = object.getJSONArray("data").getJSONObject(0).getString("id");
                mStatue = object.getJSONArray("data").getJSONObject(0).getString("state");
                mCan = object.getJSONArray("data").getJSONObject(0).getInteger("can");
                newActivityTime.setText(sendTime);
                newActivityTextInfo.setText(startTime + "到" + endTime + "  " + title);
                break;
            case 3:

                /**自动登录成功*/
                MyApplication.UserInfo user = MyApplication.getUser();
                user.setNickname(object.getJSONArray("data").getJSONObject(0).getString("nickname"));
                user.setUsername(object.getJSONArray("data").getJSONObject(0).getString("username"));
                user.setGender(object.getJSONArray("data").getJSONObject(0).getString("gender"));
                user.setIntegral(object.getJSONArray("data").getJSONObject(0).getString("integral"));
                user.setImg(object.getJSONArray("data").getJSONObject(0).getString("img"));
                user.setId(object.getJSONArray("data").getJSONObject(0).getString("id"));

                Constans.ID = object.getJSONArray("data").getJSONObject(0).getString("id");
                if (object.getJSONArray("data").getJSONObject(0).getInteger("house") == 1) {
                    Constans.isBindHouse = true;
                } else {
                    Constans.isBindHouse = false;
                }

                Intent intent = new Intent("autoLogined");
                intent.putExtra("name", "Logined");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


                /**获取用户的个人资料*/

                if (!Constans.ID.equals("null")) {
                    params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    getDataFromInternet(UrlFactory.my_data, params, 4);
                }

                break;
            case 4:
                /**设置用户资料*/
                Constans.username = object.getJSONArray("data").getJSONObject(0).getString("username");
                Constans.nickName = object.getJSONArray("data").getJSONObject(0).getString("nickname");
                Constans.integral = object.getJSONArray("data").getJSONObject(0).getString("integral");
                Constans.gender = object.getJSONArray("data").getJSONObject(0).getString("gender");
                Constans.img = object.getJSONArray("data").getJSONObject(0).getString("img");
                Constans.birthday = object.getJSONArray("data").getJSONObject(0).getString("birthday");

                /** owner[city] 城市     owner[community]社区     owner[ban] 楼栋号*/
                if (object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("city").isEmpty()) {
                    return;
                } else {
                    Constans.city = object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("city");
                    Constans.community = object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("community");
                    Constans.ban = object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("ban");
                }
                break;


        }
    }

    private void setViews() {
        List<String> imagsList = Arrays.asList(images);//将图片放进这个集合
        mCarouselImage.setPointViewVisible(true);//设置小圆点可见
        mCarouselImage.setPageIndicator(dots);//设置小圆点
        mCarouselImage.setManualPageable(true);//手动滑动
        mCarouselImage.startTurning(3000);//自动轮滑
        mCarouselImage.setPages(new CBViewHolderCreator<NetImageLoaderHolder>() {
            @Override
            public NetImageLoaderHolder createHolder() {
                return new NetImageLoaderHolder();
            }
        }, imagsList);


    }


    @Override
    protected void setListener() {
        openDoorRelative.setOnClickListener(this);
        mServices.setOnClickListener(this);
        vip.setOnClickListener(this);
        guojiangfengcai.setOnClickListener(this);
        projectShow.setOnClickListener(this);
        tenderShow.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        newsRelative.setOnClickListener(this);

        mCarouselImage.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInfoActivity.TITLE, "活动详情");
                bundle.putInt(ActivityInfoActivity.STATUE, Integer.parseInt(mStatue));
                bundle.putInt(ActivityInfoActivity.CAN, mCan);
                bundle.putString(ActivityInfoActivity.URL, UrlFactory.topbanner_content + "/id/" + mBanner.getData().get(position).getId());
                bundle.putBoolean(ActivityInfoActivity.isShowing, false);
                jumpToActivity(ActivityInfoActivity.class, bundle, false);

            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                /**第一个请求*/
                initData();
            }

            @Override
            public void onFinishRefresh() {
            }
        });
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_first;
    }

    @OnClick({R.id.openDoor, R.id.services, R.id.VIP, R.id.guojiangfengcai})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openDoor:
                jumpToActivity(OpenDoorActivity.class, false);
                break;
            case R.id.services:
                jumpToActivity(PropertyServicesActivity.class, false);
                break;
            case R.id.VIP:
                jumpToActivity(VIPActivity.class, false);
                break;
            case R.id.guojiangfengcai:
                jumpToActivity(GuoJiangActivity.class, false);
                break;
            case R.id.projectShow:
                jumpToActivity(ProjectShowActivity.class, false);
                break;
            case R.id.tenderShow:
                jumpToActivity(TenderShowActivity.class, false);
                break;
            case R.id.webViewImage:
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInfoActivity.TITLE, "活动详情");
                bundle.putInt(ActivityInfoActivity.STATUE, Integer.parseInt(mStatue));
                bundle.putInt(ActivityInfoActivity.CAN, mCan);
                bundle.putString(ActivityInfoActivity.URL, UrlFactory.zbanner_content + "/id/" + mBannerId);
                bundle.putBoolean(ActivityInfoActivity.isShowing, false);
                jumpToActivity(ActivityInfoActivity.class, bundle, false);
                break;
            case R.id.newsRelative:
                bundle = new Bundle();
                bundle.putString(JiFenExchangeActivity.TITLE, "最新活动");
                jumpToActivity(JiFenExchangeActivity.class, bundle, false);
                break;
        }
    }

}
