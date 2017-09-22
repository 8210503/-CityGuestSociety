package www.cityguestsociety.com.bindhouse;

import android.view.View;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class CheckSuccessActivity extends BaseToolbarActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_check_success;
    }

    @Override
    protected void initData() {

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
        initToobar("验证成功");
    }

    @Override
    public void onClick(View v) {

    }
}
