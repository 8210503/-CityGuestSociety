package www.cityguestsociety.com.fourthfragmentactivity;

import android.view.View;
import android.widget.GridView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.utils.Constans;

public class BlackListsActivity extends BaseToolbarActivity {


    @BindView(R.id.blackListGridView)
    GridView mBlackListGridView;

    @Override
    protected int getContentView() {
        return R.layout.activity_black_lists;
    }

    @Override
    protected void initData() {
        RequestParams params=new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.blacklist,params,0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }


}
