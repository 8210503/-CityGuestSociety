package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class KFActivity extends BaseToolbarActivity {


    @BindView(R.id.KFListView)
    ListView mKFListView;

    @Override
    protected int getContentView() {
        return R.layout.activity_kf;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
        initToobar("客服");
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;

    }

    @Override
    public void onClick(View v) {

    }


}
