package www.cityguestsociety.com.firstfragmentactivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.baseui.ProgressWebview;

public class TenderShowWebViewActivity extends BaseToolbarActivity {

    public static final String ID = "ID";
    String id;
    @BindView(R.id.newsTitle)
    TextView mNewsTitle;
    @BindView(R.id.newsTime)
    TextView mNewsTime;
    @BindView(R.id.newsImage)
    ImageView mNewsImage;
    @BindView(R.id.progressWebview)
    ProgressWebview mProgressWebview;

    @Override
    protected int getContentView() {
        return R.layout.activity_tender_show_web_view;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("pid", id);
        getDataFromInternet(UrlFactory.attract_content, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        String title = object.getJSONArray("data").getJSONObject(0).getString("title");
        String time = object.getJSONArray("data").getJSONObject(0).getString("creation_time");
        String img = object.getJSONArray("data").getJSONObject(0).getString("img");
        String content = object.getJSONArray("data").getJSONObject(0).getString("content");


        mNewsTitle.setText(title);
        mNewsTime.setText("北京城建·" + time);
        Glide.with(this).load(UrlFactory.imaPath + img).asBitmap().into(mNewsImage);


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
        initToobar("招商详情");
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
    }

    @Override
    public void onClick(View v) {

    }

}
