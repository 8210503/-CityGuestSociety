package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class HouseIDActivity extends BaseToolbarActivity {

    public static final int RESULTCODE = 2;
    public static final String RESULT = "result";
    @BindView(R.id.imageView4)
    ImageView mImageView4;
    @BindView(R.id.etCity)
    EditText mEtCity;
    @BindView(R.id.SerchRelative)
    RelativeLayout mSerchRelative;
    @BindView(R.id.mainListView)
    ListView mMainListView;
    @BindView(R.id.moreListVie)
    ListView mMoreListVie;
    private String Id;

    @Override
    protected int getContentView() {
        return R.layout.activity_house_id;
    }

    @Override
    protected void initData() {
        RequestParams params=new RequestParams();
        params.put("ban_id",Id);
        getDataFromInternet(UrlFactory.Room,params,0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {


    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("选择单元房号");
        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
    }

    @Override
    public void onClick(View v) {

    }


}
