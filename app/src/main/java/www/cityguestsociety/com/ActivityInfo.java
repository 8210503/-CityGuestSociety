package www.cityguestsociety.com;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;

/**
 * X5页面 跳转过来需要使用intent来传递一个headTitle
 * 字段为 TITLE
 */

public class ActivityInfo extends BaseToolbarActivity {


    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String STATUE = "statue";
    public static final String isShowing = "isShow";
    public static final String CAN = "can";
    public static final String isCheck = "isHaveCheck";
    @BindView(R.id.bt_statue)
    Button mBtStatue;
    @BindView(R.id.webView)
    WebView mForumContext;
    @BindView(R.id.projectShowImage)
    ImageView mProjectShowImage;
    @BindView(R.id.projectName)
    TextView mProjectName;
    @BindView(R.id.timeImage)
    TextView mTimeImage;
    @BindView(R.id.projectAddress)
    TextView mProjectAddress;
    @BindView(R.id.baomingrenshu)
    TextView mBaomingrenshu;
    @BindView(R.id.baomingrenshuTextview)
    TextView mBaomingrenshuTextview;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.CheckStatue)
    TextView mCheckStatue;
    @BindView(R.id.linear)
    LinearLayout mLinear;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    private String mTitle;
    private String mPath;
    private boolean isShow;
    private int mStatue;
    private int can;
    private String mId;
    private boolean isHaveCheck;
    private CaptureActivity mCaptureContext;
    String checkInUrl="";


    @Override
    protected int getContentView() {
        return R.layout.activity_info;
    }

    @Override
    protected void initData() {

        LogUtils.e(mPath);
        getDataFromGet(mPath, 0);


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        switch (what) {
            case 0:
                String end_time = object.getJSONArray("data").getJSONObject(0).getString("end_time");
                String max_num = object.getJSONArray("data").getJSONObject(0).getString("max_num");
                String title = object.getJSONArray("data").getJSONObject(0).getString("title");
                String start_time = object.getJSONArray("data").getJSONObject(0).getString("start_time");
                can = object.getJSONArray("data").getJSONObject(0).getInteger("can");
                mStatue = Integer.parseInt(object.getJSONArray("data").getJSONObject(0).getString("state"));
                String img = object.getJSONArray("data").getJSONObject(0).getString("img");
                String content = object.getJSONArray("data").getJSONObject(0).getString("content");
                String sign = object.getJSONArray("data").getJSONObject(0).getString("sign");
                mId = object.getJSONArray("data").getJSONObject(0).getString("id");

                init();


                mForumContext.loadUrl(content);
                mProjectAddress.setText(start_time);
                mBaomingrenshuTextview.setText(end_time);
                mCount.setText("报名人数:" + sign + "/" + max_num);
                mProjectName.setText(title);
                Glide.with(this).load(UrlFactory.imaPath + img).asBitmap().into(mProjectShowImage);
                break;
            case 1:
                /**报名成功*/
                ShowToast(object.getString("info"));
                break;
            case 2:
                ShowToast(object.getString("info"));
                break;
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE);

        LogUtils.e(mPath);
        mStatue = intent.getIntExtra(STATUE, 3);
        LogUtils.e(mStatue);
        isShow = intent.getBooleanExtra(isShowing, false);
        can = intent.getIntExtra(CAN, 3);
        isHaveCheck = intent.getBooleanExtra(isCheck, false);


        mPath = intent.getStringExtra(URL);


    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    private void init() {
        // TODO Auto-generated method stub
        mBtStatue = (Button) findViewById(R.id.bt_statue);
        if (!isShow) {
            mBtStatue.setVisibility(View.GONE);
        } else {
            mBtStatue.setVisibility(View.VISIBLE);
        }
        if (mStatue == 0) {

            mBtStatue.setBackgroundColor(getResources().getColor(R.color.gray));
            mBtStatue.setText("已结束");
            mBtStatue.setEnabled(false);
            initToobar(mTitle);

        } else if (mStatue == 1) {
            if (isHaveCheck)
                initToobar(R.mipmap.fanhui, mTitle, "签到");
            else
                initToobar(mTitle);

            if (can == 1) {

                mBtStatue.setText("已参加");
                mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
                mBtStatue.setEnabled(false);
            } else if (can == 0) {
                mBtStatue.setText("活动进行中");
                mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
                mBtStatue.setEnabled(true);
            }

        } else if (mStatue == 2) {
            mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
            mBtStatue.setText("未开始");
            initToobar(mTitle);
            if (can == 1) {
                mBtStatue.setText("已参加");
                mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
                mBtStatue.setEnabled(false);
            } else if (can == 0) {
                mBtStatue.setText("我要报名");
                mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
                mBtStatue.setEnabled(true);
            }
        }


        mBtStatue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constans.ID.equals("null")) {
                    jumpToActivity(LoginActivity.class, false);
                } else {
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("id", mId);
                    getDataFromInternet(UrlFactory.par_activicty, params, 1);
                }
            }
        });

        WebSettings webSettings = mForumContext.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mForumContext.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }


    @OnClick({R.id.CheckStatue, R.id.bt_statue})
    public void onClick(View v) {

    }

    @Override
    public void RightOnClick() {
        QrScan.getInstance().launchScan(this, new IScanModuleCallBack() {
            @Override
            public void OnReceiveDecodeResult(final Context context, String result) {
                mCaptureContext = (CaptureActivity) context;
                if (result != null && !result.isEmpty()) {
                    checkInUrl=result;
                    LogUtils.e(checkInUrl);
                    QrScan.getInstance().finishScan(mCaptureContext);
                    getDataFromGet(checkInUrl+"/member_id/"+Constans.ID,2);
                }


            }
        });
    }
}
