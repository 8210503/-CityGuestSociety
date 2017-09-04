package www.cityguestsociety.com.firstfragmentactivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.firstfragmentactivity.servicesactivity.AnnunciateActivity;
import www.cityguestsociety.com.firstfragmentactivity.servicesactivity.BXActivity;
import www.cityguestsociety.com.firstfragmentactivity.servicesactivity.JFActivity;
import www.cityguestsociety.com.firstfragmentactivity.servicesactivity.JFYYActivity;
import www.cityguestsociety.com.firstfragmentactivity.servicesactivity.KFActivity;
import www.cityguestsociety.com.firstfragmentactivity.servicesactivity.TSActivity;

import static www.cityguestsociety.com.R.id.BXRelative;

public class PropertyServicesActivity extends BaseToolbarActivity {


    @BindView(R.id.servicesImageRela)
    RelativeLayout mServicesImageRela;
    @BindView(R.id.KFRelative)
    RelativeLayout mKFRelative;
    @BindView(R.id.JFRelative)
    RelativeLayout mJFRelative;
    @BindView(BXRelative)
    RelativeLayout mBXRelative;
    @BindView(R.id.TSRelative)
    RelativeLayout mTSRelative;
    @BindView(R.id.annunciateRelative)
    RelativeLayout mAnnunciateRelative;
    @BindView(R.id.JFYYRelative)
    RelativeLayout mJFYYRelative;
    @BindView(R.id.DCWJRelative)
    RelativeLayout mDCWJRelative;
    @BindView(R.id.servicesImage)
    ImageView servicesImage;

    @Override
    protected int getContentView() {
        return R.layout.activity_property_services;
    }

    @Override
    protected void initData() {
        Glide.with(this).load(R.mipmap.icon_service_bg).asBitmap().into(servicesImage);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;

    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("物业服务", R.mipmap.fanhui);
    }

    @OnClick({R.id.servicesImageRela, R.id.KFRelative, R.id.JFRelative, R.id.BXRelative, R.id.TSRelative, R.id.annunciateRelative, R.id.JFYYRelative, R.id.DCWJRelative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.servicesImageRela:

                break;
            case R.id.KFRelative:
                jumpToActivity(KFActivity.class, false);
                break;
            case R.id.JFRelative:
                jumpToActivity(JFActivity.class, false);
                break;
            case R.id.BXRelative:
                jumpToActivity(BXActivity.class, false);
                break;
            case R.id.TSRelative:
                jumpToActivity(TSActivity.class, false);
                break;
            case R.id.annunciateRelative:
                jumpToActivity(AnnunciateActivity.class, false);
                break;
            case R.id.JFYYRelative:
                jumpToActivity(JFYYActivity.class, false);
                break;
            case R.id.DCWJRelative:

                break;
        }
    }
}
