package www.cityguestsociety.com.firstfragmentactivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.baseui.ProgressWebview;

public class ProjectShowWebViewActivity extends BaseToolbarActivity {

    public static final String ID = "ID";
    String id;
    @BindView(R.id.newsTitle)
    TextView mNewsTitle;
    @BindView(R.id.newsTime)
    TextView mNewsTime;
    @BindView(R.id.newsImage)
    ImageView mNewsImage;
    @BindView(R.id.newsDescripation)
    TextView mNewsDescripation;
    @BindView(R.id.progressWebview)
    ProgressWebview mProgressWebview;

    @Override
    protected int getContentView() {
        return R.layout.activity_project_show_web_view;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("pid", id);
        getDataFromInternet(UrlFactory.project_content, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        String title = object.getJSONArray("data").getJSONObject(0).getString("title");
        String time = object.getJSONArray("data").getJSONObject(0).getString("creation_time");
        String img = object.getJSONArray("data").getJSONObject(0).getString("img");
        String content = object.getJSONArray("data").getJSONObject(0).getString("content");
        String address=object.getJSONArray("data").getJSONObject(0).getString("address");

        mNewsTitle.setText(title);
        mNewsTime.setText("北京城建·" + time);
        Glide.with(this).load(UrlFactory.imaPath+img).asBitmap().into(mNewsImage);
        mProgressWebview.loadUrl(content);
        mNewsDescripation.setText(address);
    }


    //mWebView.loadData(fmtString(notice1), "text/html", "utf-8");

    private String fmtString(String str) {
        String notice = "";
        try {
            notice = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException ex) {

        }
        return notice;
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
        initToobar("项目详情");
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
    }

    @Override
    public void onClick(View v) {

    }


}
