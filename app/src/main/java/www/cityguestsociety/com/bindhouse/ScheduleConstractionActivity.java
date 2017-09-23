package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.HouseInfo;

public class ScheduleConstractionActivity extends BaseToolbarActivity {


    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.imageview)
    ImageView mImageview;
    @BindView(R.id.textviewContact)
    TextView mTextviewContact;
    @BindView(R.id.imageview2)
    ImageView mImageview2;
    @BindView(R.id.textviewContact2)
    TextView mTextviewContact2;
    @BindView(R.id.contactLinear)
    LinearLayout mContactLinear;
    @BindView(R.id.textview5)
    TextView mTextview5;
    @BindView(R.id.ScheduleProgressBar)
    ProgressBar mScheduleProgressBar;
    @BindView(R.id.recylerView)
    LRecyclerView mRecylerView;

    @BindView(R.id.completInfo)
    TextView completInfo;
    @BindView(R.id.houseAddress)
    TextView houseAddress;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.contactsNumber)
    TextView number;
    private String mHouseId;
    private int mProgress;
    private List<HouseInfo.DataBean> mList = new ArrayList<>();
    private List<HouseInfo.DataBean.SpeedBean> scheduleList = new ArrayList<>();
    private LRecyclerViewAdapter mRecylerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_schedule_constraction;
    }

    @Override
    protected void initData() {

        RequestParams params = new RequestParams();
        params.put("id", mHouseId);
        getDataFromInternet(UrlFactory.houseinfo, params, 0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        HouseInfo info = gson.fromJson(object.toString(), HouseInfo.class);
        mList.addAll(info.getData());
        scheduleList.addAll(info.getData().get(1).getSpeed());
        LogUtils.e(scheduleList.toString());
        setViews();
        setAdapter();
    }

    private void setAdapter() {

        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<HouseInfo.DataBean.SpeedBean>(this, scheduleList, R.layout.item_cchedulelistview) {

            @Override
            public void convert(BaseRecyclerHolder holder, HouseInfo.DataBean.SpeedBean item, int position, boolean isScrolling) {

                holder.setText(R.id.tv_content, item.getContent());
                holder.setText(R.id.tv_time, item.getTime());

                if (position == scheduleList.size() - 1) {
                    TextView content = holder.getView(R.id.tv_content);
                    TextView time = holder.getView(R.id.tv_time);
                    TextView vitcalLine = holder.getView(R.id.text);
                    ImageView cirlce = holder.getView(R.id.grayCicle);

                    content.setTextColor(mContext.getResources().getColor(R.color.orange));

                    time.setTextColor(mContext.getResources().getColor(R.color.orange));
                    vitcalLine.setVisibility(View.GONE);
                    cirlce.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_circle_logistics_oringe));
                }
            }
        };

        mRecylerAdapter = new LRecyclerViewAdapter(adapter);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        mRecylerView.setAdapter(mRecylerAdapter);

        mRecylerView.setLoadMoreEnabled(false);
        mRecylerView.setPullRefreshEnabled(false);
    }

    private void setViews() {
        name.setText(mList.get(0).getName());
        number.setText(mList.get(0).getPhone());
        houseAddress.setText(mList.get(0).getCity() + mList.get(0).getCommunity() + mList.get(0).getBan() + mList.get(0).getRoom());

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
        initToobar("项目进度");
        Intent intent = getIntent();
        mHouseId = intent.getStringExtra("id");
        mProgress = intent.getIntExtra("progress", 0);

        mScheduleProgressBar.setProgress(mProgress);
        completInfo.setText("已完成" + mProgress + "%");


    }

    @Override
    public void onClick(View v) {

    }

}
