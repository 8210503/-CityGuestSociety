package www.cityguestsociety.com.fourthfragmentactivity;

import android.view.View;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class AboutUsActivity extends BaseToolbarActivity {



    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage=true;
        isShowToolBar=true;
    }

    @Override
    protected void initView() {
        initToobar("关于我们");
    }

    @Override
    public void onClick(View v) {

    }
}
