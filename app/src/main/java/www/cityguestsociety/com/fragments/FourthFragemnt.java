package www.cityguestsociety.com.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.bindhouse.HouseManagerActivity;
import www.cityguestsociety.com.fourthfragmentactivity.AboutUsActivity;
import www.cityguestsociety.com.fourthfragmentactivity.AdvicesFeedBackActivity;
import www.cityguestsociety.com.fourthfragmentactivity.MineDataActivity;
import www.cityguestsociety.com.fourthfragmentactivity.MyColoctionActivity;
import www.cityguestsociety.com.fourthfragmentactivity.MyJoinActivity;
import www.cityguestsociety.com.fourthfragmentactivity.MyNewsActivity;
import www.cityguestsociety.com.fourthfragmentactivity.MyPostActivity;
import www.cityguestsociety.com.fourthfragmentactivity.MyQRCodeSctivity;
import www.cityguestsociety.com.fourthfragmentactivity.SettingActivity;
import www.cityguestsociety.com.utils.Constans;

import static www.cityguestsociety.com.R.id.setting_iv;

/**
 * Created by LuoPan on 2017/9/4 9:37.
 */

public class FourthFragemnt extends BaseFragment implements View.OnClickListener {
    private ImageView mUserPhoto;
    private RelativeLayout mMyColocation;
    private RelativeLayout mMyQRCode;
    private RelativeLayout mMyNewsCenter;
    private RelativeLayout mMyPost;
    private RelativeLayout mMyJoinActivity;
    private RelativeLayout mMyHouseManager;
    private RelativeLayout mMyAdvices;
    private RelativeLayout mAboutApp;
    private TextView mUserStatue;
    private TextView mUserAddress;
    private ImageView setting;
    private TextView textView7;


    public FourthFragemnt() {
        super();
        LogUtils.e("FourthFragemnt");
    }


    public FourthFragemnt getInstance() {
        FourthFragemnt fourthFragment = new FourthFragemnt();
        return fourthFragment;
    }


    @Override
    protected void initView() {
        mUserPhoto = getView(R.id.userPhoto);
        mUserStatue = getView(R.id.userStatue);
        mUserAddress = getView(R.id.userAddress);
        setting = getView(setting_iv);

        mMyColocation = getView(R.id.myCollection);
        mMyQRCode = getView(R.id.MyQRCode);
        mMyNewsCenter = getView(R.id.newsCenter);
        mMyPost = getView(R.id.myPost);
        mMyJoinActivity = getView(R.id.MyjoinActivity);
        mMyHouseManager = getView(R.id.myHouse);
        mMyAdvices = getView(R.id.MyAddress);
        mAboutApp = getView(R.id.aboutApp);

        textView7 = getView(R.id.textView7);
    }

    @Override
    public void initData() {

        /**获取用户的个人资料*/
/*
        if (!Constans.ID.equals("null")) {
            RequestParams params = new RequestParams();
            params.put("member_id", Constans.ID);
            getDataFromInternet(UrlFactory.my_data, params, 0);
        }*/

        mUserPhoto.postDelayed(new Runnable() {
            @Override
            public void run() {
                onResume();
            }
        }, 2000);


    }


    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Constans.username = object.getJSONArray("data").getJSONObject(0).getString("username");
        Constans.nickName = object.getJSONArray("data").getJSONObject(0).getString("nickname");
        Constans.integral = object.getJSONArray("data").getJSONObject(0).getString("integral");
        Constans.gender = object.getJSONArray("data").getJSONObject(0).getString("gender");
        Constans.img = object.getJSONArray("data").getJSONObject(0).getString("img");
        Constans.birthday = object.getJSONArray("data").getJSONObject(0).getString("birthday");

        /** owner[city] 城市     owner[community]社区     owner[ban] 楼栋号*/
        if (object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").isEmpty()) {
            return;
        } else {
            Constans.city = object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("city");
            Constans.community = object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("community");
            Constans.ban = object.getJSONArray("data").getJSONObject(0).getJSONObject("owner").getString("ban");
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("onResume");
        if (!Constans.ID.equals("null")) {
            Glide.with(MyApplication.getContext()).load(Constans.img).asBitmap().error(R.mipmap.icon_head_portrait).placeholder(R.mipmap.icon_head_portrait).into(mUserPhoto);
        } else {
            Glide.with(MyApplication.getContext()).load(R.mipmap.icon_head_portrait).asBitmap().error(R.mipmap.icon_head_portrait).placeholder(R.mipmap.icon_head_portrait).into(mUserPhoto);

        }
        if (Constans.nickName.isEmpty()) {
            mUserStatue.setText("用户昵称");
        } else {
            mUserStatue.setText(Constans.nickName);
        }

        if (Constans.ID.equals("null")) {
            mUserAddress.setText("点击登录");
        } else if (Constans.city.isEmpty()) {
            mUserAddress.setText("请绑定房屋");
        } else {
            mUserAddress.setText(Constans.city + Constans.community + Constans.ban);
        }

        if (Constans.isBindHouse) {
            textView7.setText("已认证");
        }

    }

    @Override
    protected void setListener() {
        mUserPhoto.setOnClickListener(this);
        mMyColocation.setOnClickListener(this);
        mMyQRCode.setOnClickListener(this);
        mMyNewsCenter.setOnClickListener(this);
        mMyPost.setOnClickListener(this);
        mMyJoinActivity.setOnClickListener(this);
        mMyHouseManager.setOnClickListener(this);
        mMyAdvices.setOnClickListener(this);
        mAboutApp.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_iv:
                jumpToActivity(SettingActivity.class, false);
                break;
            case R.id.userPhoto:
                if (isLogined())
                    jumpToActivity(MineDataActivity.class, false);

                break;
            case R.id.myCollection:
                if (isLogined())
                    jumpToActivity(MyColoctionActivity.class, false);
                break;
            case R.id.MyQRCode:
                if (isBindHouse())
                    jumpToActivity(MyQRCodeSctivity.class, false);
                break;
            case R.id.newsCenter:
                jumpToActivity(MyNewsActivity.class, false);
                break;
            case R.id.myPost:
                jumpToActivity(MyPostActivity.class, false);
                break;
            case R.id.MyjoinActivity:
                jumpToActivity(MyJoinActivity.class, false);
                break;
            case R.id.myHouse:
                if (isLogined())
                    jumpToActivity(HouseManagerActivity.class, false);
                break;
            case R.id.MyAddress:
                //意见反馈
                if (isLogined())
                    jumpToActivity(AdvicesFeedBackActivity.class, false);
                break;
            case R.id.aboutApp:
                jumpToActivity(AboutUsActivity.class, false);

                break;
        }
    }
}
