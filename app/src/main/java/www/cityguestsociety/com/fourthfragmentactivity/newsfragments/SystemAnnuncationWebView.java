package www.cityguestsociety.com.fourthfragmentactivity.newsfragments;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.baseui.ProgressWebview;

public class SystemAnnuncationWebView extends BaseToolbarActivity {


    @BindView(R.id.newsTitle)
    TextView mNewsTitle;
    @BindView(R.id.newsTime)
    TextView mNewsTime;
    @BindView(R.id.newsImage)
    ImageView mNewsImage;
    @BindView(R.id.titleRelative)
    RelativeLayout mTitleRelative;
    @BindView(R.id.progressWebview)
    ProgressWebview mProgressWebview;
    public static final String ID = "ID";
    String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_system_annuncation_web_view;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("id", id);
        getDataFromInternet(UrlFactory.system_content, params, 0);
        showLoadingDialog();


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        cancleLoadingDialog();
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
        initToobar("系统公告");
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
    }

    @Override
    public void onClick(View v) {

    }

}
