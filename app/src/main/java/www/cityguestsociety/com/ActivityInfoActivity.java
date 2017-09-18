package www.cityguestsociety.com;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

/**
 * X5页面 跳转过来需要使用intent来传递一个headTitle
 * 字段为 TITLE
 */

public class ActivityInfoActivity extends BaseToolbarActivity {


    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String STATUE = "statue";
    public static final String isShowing = "isShow";
    public static final String CAN = "can";
    @BindView(R.id.bt_statue)
    Button mBtStatue;
    @BindView(R.id.forum_context)
    WebView mForumContext;
    private String mTitle;
    private String mPath;
    private boolean isShow;
    private int mStatue;
    private int can;


    @Override
    protected int getContentView() {
        return R.layout.activity_activity_info;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE);
        mPath = intent.getStringExtra(URL);
        LogUtils.e(mPath);
        mStatue = intent.getIntExtra(STATUE, 0);
        isShow = intent.getBooleanExtra(isShowing, false);
        try {
            can = intent.getIntExtra(CAN, 0);
        } catch (Exception e) {
            // TODO: 2017/9/8
        }
        initToobar(mTitle);
        init();
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
        } else if (mStatue == 1) {
            mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
            if (can == 1) {
                mBtStatue.setText("已参加");
            } else if (can == 0) {
                mBtStatue.setText("未参加");
            }

        } else if (mStatue == 2) {
            mBtStatue.setBackgroundColor(getResources().getColor(R.color.orange));
            mBtStatue.setText("未开始");
        }

        mForumContext.loadUrl(mPath);
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


    @Override
    public void onClick(View v) {

    }


}
