package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.BaoXiuRecoderGridViewAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;
import www.cityguestsociety.com.ui.MyGridView;

public class BXInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.BXGirdView)
    MyGridView mBXGirdView;


    @Override
    protected void initView() {
        initToobar("报修记录");
        mBXGirdView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    private void setAdapter() {
        List<Bean> lists = new ArrayList<>();
        List<String> imageLists = new ArrayList<>();
        imageLists.add("http://pic95.huitu.com/res/20170424/1258172_20170424165057597050_1.jpg");
        imageLists.add("http://pic95.huitu.com/res/20170424/1258172_20170424165057597050_1.jpg");
        lists.add(new Bean("厕所堵住了  赶紧来通一下", "2017.5.17", imageLists, "", "已解决", ""));
        mBXGirdView.setAdapter(new BaoXiuRecoderGridViewAdapter(BXInfoActivity.this, imageLists));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bxinfo;
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    @Override
    protected void setListener() {

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
