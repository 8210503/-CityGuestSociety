package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class JFYYActivity extends BaseToolbarActivity {


    @BindView(R.id.houserAddressLinearRelative)
    RelativeLayout mHouserAddressLinearRelative;
    @BindView(R.id.Time)
    TextView mTime;

    @Override
    protected int getContentView() {
        return R.layout.activity_jfyy;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToobar("交房预约");
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
    public void onClick(View v) {

    }

}
