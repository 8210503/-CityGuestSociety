package www.cityguestsociety.com.bindhouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class CheckFailedActivity extends BaseToolbarActivity {



    @Override
    protected int getContentView() {
        return R.layout.activity_check_failed;
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
        initToobar("验证失败");
    }

    @Override
    public void onClick(View v) {

    }
}
