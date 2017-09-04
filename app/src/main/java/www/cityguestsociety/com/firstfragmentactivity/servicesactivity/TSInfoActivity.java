package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.BaoXiuRecoderGridViewAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;
import www.cityguestsociety.com.ui.MyGridView;

public class TSInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.noReplyRelative)
    RelativeLayout mNoReplyRelative;
    @BindView(R.id.wuyeReply)
    TextView mWuyeReply;
    @BindView(R.id.hasReplayRelative)
    RelativeLayout mHasReplayRelative;
    @BindView(R.id.imageview)
    ImageView mImageview;
    @BindView(R.id.textviewContact)
    TextView mTextviewContact;
    @BindView(R.id.imageview2)
    ImageView mImageview2;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.BXGirdView)
    MyGridView mBXGirdView;
    @BindView(R.id.BXtime)
    TextView mBXtime;
    @BindView(R.id.contactLinear)
    LinearLayout mContactLinear;
    private int mPosition;

    @Override
    protected int getContentView() {
        return R.layout.activity_tsinfo;
    }

    @Override
    protected void initView() {
        initToobar("建议详情");
        mPosition = getIntent().getIntExtra("position", 0);
        mBXGirdView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        if (mPosition == 0) {
            mHasReplayRelative.setVisibility(View.VISIBLE);
            mNoReplyRelative.setVisibility(View.INVISIBLE);
        } else {
            mHasReplayRelative.setVisibility(View.INVISIBLE);
            mNoReplyRelative.setVisibility(View.VISIBLE);
        }
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
        isShowBackImage = true;
        isShowToolBar = true;
    }

    private void setAdapter() {
        List<Bean> lists = new ArrayList<>();
        List<String> imageLists = new ArrayList<>();
        imageLists.add("http://pic95.huitu.com/res/20170424/1258172_20170424165057597050_1.jpg");
        imageLists.add("http://pic95.huitu.com/res/20170424/1258172_20170424165057597050_1.jpg");
        lists.add(new Bean("厕所堵住了  赶紧来通一下", "2017.5.17", imageLists, "", "已解决", ""));
        mBXGirdView.setAdapter(new BaoXiuRecoderGridViewAdapter(TSInfoActivity.this, imageLists));
    }

    @Override
    public void onClick(View v) {

    }
}

