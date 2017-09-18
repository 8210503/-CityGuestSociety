package www.cityguestsociety.com.fourthfragmentactivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.utils.Constans;

public class AdvicesFeedBackActivity extends BaseToolbarActivity {


    @BindView(R.id.feedbackContent)
    EditText mFeedbackContent;
    @BindView(R.id.send)
    Button mSend;

    @Override
    protected int getContentView() {
        return R.layout.activity_advices_feed_back;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mFeedbackContent.getText().toString().trim().isEmpty()) {
                    RequestParams params = new RequestParams();
                    params.put("member_id", Constans.ID);
                    params.put("content", mFeedbackContent.getText().toString().trim());
                    getDataFromInternet(UrlFactory.opinion, params, 0);
                    showLoadingDialog();
                } else {
                    ShowToast("请输入反馈内容");
                }

            }
        });
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        ShowToast(object.getString("info"));
        finish();

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("意见反馈");
    }

    @OnClick(R.id.send)
    public void onClick() {
    }
}
