package www.cityguestsociety.com.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.firstfragmentactivity.OpenDoorActivity;
import www.cityguestsociety.com.firstfragmentactivity.PropertyServicesActivity;
import www.cityguestsociety.com.firstfragmentactivity.VIPActivity;

/**
 * Created by LuoPan on 2017/9/4 9:23.
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {

    private ViewHolder mHolder;
    RelativeLayout openDoorRelative;
    RelativeLayout mServices;
    RelativeLayout vip;


    @Override
    protected void initView() {
        openDoorRelative = getView(R.id.openDoor);
        mServices = getView(R.id.services);
        vip=getView(R.id.VIP);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        openDoorRelative.setOnClickListener(this);
        mServices.setOnClickListener(this);
        vip.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_first;
    }

    @OnClick({R.id.openDoor, R.id.services,R.id.VIP})
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
        }
    }


    static class ViewHolder {
        @BindView(R.id.CarouselImage)
        ConvenientBanner mCarouselImage;
        @BindView(R.id.openDoor)
        RelativeLayout mOpenDoor;
        @BindView(R.id.services)
        RelativeLayout mServices;
        @BindView(R.id.VIP)
        RelativeLayout mVIP;
        @BindView(R.id.guojiangfengcai)
        RelativeLayout mGuojiangfengcai;
        @BindView(R.id.projectShow)
        RelativeLayout mProjectShow;
        @BindView(R.id.tenderShow)
        RelativeLayout mTenderShow;
        @BindView(R.id.webViewText)
        ImageView mWebViewText;
        @BindView(R.id.webViewImage)
        ImageView mWebViewImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
