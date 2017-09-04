package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.ActivityInfoActivity;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.adapter.ViewHolder;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;

public class AnnunciateActivity extends BaseToolbarActivity {


    @BindView(R.id.annunciateListView)
    ListView mAnnunciateListView;
    List<Bean> lists;

    @Override
    protected void initView() {
        initToobar("小区公告");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_annunciate;
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            lists.add(new Bean("集团中标东城区望坛棚户区改造项目",
                    "2016.12.29  14:19",
                    "http://pic125.nipic.com/file/20170321/4562496_155319998000_2.jpg"));
        }
        setAdapter();
    }

    @Override
    protected void setListener() {
        mAnnunciateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInfoActivity.TITLE, "公告详情");
                bundle.putInt(ActivityInfoActivity.STATUE, 0);//不需要显示按钮 则写0
                bundle.putString(ActivityInfoActivity.URL, "http://x5.tencent.com/tb/guide/sdkInit.html");
                bundle.putBoolean(ActivityInfoActivity.isShowing, false);
                jumpToActivity(ActivityInfoActivity.class, bundle, false);
            }
        });
    }

    private void setAdapter() {
        mAnnunciateListView.setAdapter(new CommonAdaper<Bean>(this, lists, R.layout.item_annuction) {
            @Override
            public void convert(ViewHolder holder, Bean item, int p) {
                holder.setText(R.id.textViewGJ, item.getText());
                holder.setText(R.id.textViewGJInfo, item.getTextInfo());
                holder.setImageByUrl(R.id.imageViewGJ, item.getIamge());
            }
        });
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
