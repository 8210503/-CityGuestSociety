package www.cityguestsociety.com.secondFragmentActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.baseui.ProgressWebview;

public class SellerInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.sellerPhoto)
    ImageView mSellerPhoto;
    @BindView(R.id.sellerName)
    TextView mSellerName;
    @BindView(R.id.sellerTitle)
    RelativeLayout mSellerTitle;
    @BindView(R.id.imageViewDZ)
    ImageView mImageViewDZ;
    @BindView(R.id.sellerAddress)
    TextView mSellerAddress;
    @BindView(R.id.selleAddressRelative)
    RelativeLayout mSelleAddressRelative;
    @BindView(R.id.sellerYHJRelative)
    RelativeLayout mSellerYHJRelative;
    @BindView(R.id.sellerPhone)
    TextView mSellerPhone;
    @BindView(R.id.sellerNumber)
    TextView mSellerNumber;
    @BindView(R.id.sellerPhoneRelative)
    RelativeLayout mSellerPhoneRelative;
    @BindView(R.id.webView)
    ProgressWebview mWebView;
    private String mId;

    @Override
    protected int getContentView() {
        return R.layout.activity_seller_info;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("id", mId);
        getDataFromInternet(UrlFactory.business_content, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Glide.with(this).load(UrlFactory.imaPath + object.getJSONArray("data").getJSONObject(0).getString("img")).into(mSellerPhoto);
        mSellerNumber.setText(object.getJSONArray("data").getJSONObject(0).getString("telephone"));
        mSellerName.setText(object.getJSONArray("data").getJSONObject(0).getString("title"));
        mSellerAddress.setText(object.getJSONArray("data").getJSONObject(0).getString("address"));
        mWebView.loadUrl(object.getJSONArray("data").getJSONObject(0).getString("content"));
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
        initToobar("商家详情");
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
    }

    @Override
    public void onClick(View v) {

    }


}
