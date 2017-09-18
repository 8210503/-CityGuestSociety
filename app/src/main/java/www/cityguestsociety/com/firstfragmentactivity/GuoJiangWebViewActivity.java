package www.cityguestsociety.com.firstfragmentactivity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.baseui.ProgressWebview;

public class GuoJiangWebViewActivity extends BaseToolbarActivity {


    @BindView(R.id.newsTitle)
    TextView mNewsTitle;
    @BindView(R.id.newsTime)
    TextView mNewsTime;
    @BindView(R.id.titleRelative)
    RelativeLayout mTitleRelative;
    @BindView(R.id.progressWebview)
    ProgressWebview mProgressWebview;

    public static final String ID = "ID";
    String id;

    // TODO: 2017/9/13  缺少参数 图片 内容
    @Override
    protected int getContentView() {
        return R.layout.activity_guo_jiang_web_view;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("pid", id);
        getDataFromInternet(UrlFactory.carpenter_content, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        String title = object.getJSONArray("data").getJSONObject(0).getString("title");
        String time = object.getJSONArray("data").getJSONObject(0).getString("creation_time");
        String content = object.getJSONArray("data").getJSONObject(0).getString("content");
        mNewsTitle.setText(title);
        mNewsTime.setText(time);
        mProgressWebview.loadUrl(content);
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
        initToobar("新闻详情");
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
    }

    @Override
    public void onClick(View v) {

    }

}
