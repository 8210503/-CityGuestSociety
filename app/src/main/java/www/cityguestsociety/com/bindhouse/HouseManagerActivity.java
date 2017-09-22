package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.House;
import www.cityguestsociety.com.utils.Constans;

public class HouseManagerActivity extends BaseToolbarActivity {

    @BindView(R.id.addHouseBottom)
    Button mAddHouseBottom;
    @BindView(R.id.presentHouseListView)
    LRecyclerView mPresentHouseListView;
    @BindView(R.id.PresentHouseRelative)
    RelativeLayout mPresentHouseRelative;
    private List<House.DataBean> mLists = new ArrayList<>();
    private LRecyclerViewAdapter mAdapter;
    private BaseRecyclerAdapter mAdapter1;
    @BindView(R.id.noHouseRelative)
    RelativeLayout noHouseRelative;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected int getContentView() {
        return R.layout.activity_house_manager;
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.house, params, 0);
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        setAdapter();

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                mLists.clear();
                Gson gson = new Gson();
                House house = gson.fromJson(object.toString(), House.class);
                mLists.addAll(house.getData());
                mPresentHouseListView.refreshComplete(50);

                if (mLists.size() == 0) {
                    LogUtils.e("mLists.size()==0");
                    mPresentHouseRelative.setVisibility(View.INVISIBLE);
                    noHouseRelative.setVisibility(View.VISIBLE);
                } else {
                    LogUtils.e("mLists.size()!=0");
                    mPresentHouseRelative.setVisibility(View.VISIBLE);
                    noHouseRelative.setVisibility(View.INVISIBLE);
                }


                LogUtils.e(mLists.toString());
                mAdapter.notifyDataSetChanged();
                break;
            case 1:
                ShowToast(object.getString("info"));
                mPresentHouseListView.refresh();
                break;
        }


    }

    @Override
    protected void noData(JSONObject jsonObject, int what) {
        super.noData(jsonObject, what);
        mPresentHouseRelative.setVisibility(View.INVISIBLE);
        noHouseRelative.setVisibility(View.VISIBLE);
        mPresentHouseListView.refreshComplete(50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SelectHouseInfoActivity.isCheckSuccess) {
            mPresentHouseListView.refresh();
        }
        SelectHouseInfoActivity.isCheckSuccess = false;
    }


    private void setAdapter() {

        mAdapter1 = new BaseRecyclerAdapter<House.DataBean>(this, mLists, R.layout.item_housemanager_list) {

            @Override
            public void convert(BaseRecyclerHolder holder, House.DataBean item, final int position, boolean isScrolling) {
                holder.setText(R.id.houseAddress, item.getCity() + item.getCommunity() + item.getBan() + item.getRoom());
                holder.setText(R.id.houseID, item.getBan() + item.getRoom());
                      /* if (item.getTextInfo().equals("100")) {
                            holder.getView(R.id.completInfo).setVisibility(View.GONE);
                            holder.getView(R.id.areadyCompleteInfo).setVisibility(View.GONE);
                            holder.getView(R.id.progressBar).setVisibility(View.GONE);
                        } else {
                            ProgressBar progressBar = holder.getView(R.id.progressBar);
                            progressBar.setMax(100);
                            progressBar.setProgress(Integer.valueOf(item.getTextInfo()));
                        }*/
                holder.getView(R.id.rightImageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.put("member_id", Constans.ID);
                        params.put("id", mLists.get(position).getId());
                        getDataFromInternet(UrlFactory.delHouse, params, 1);
                        showLoadingDialog();
                    }
                });

            }
        };


        mAdapter = new LRecyclerViewAdapter(mAdapter1);
        mPresentHouseListView.setAdapter(mAdapter);


    }

    @Override
    protected void setListener() {

        mPresentHouseListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        mPresentHouseListView.refresh();
        mPresentHouseListView.setLoadMoreEnabled(false);
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar("房屋管理");
        mPresentHouseListView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.addHouseBottom, R.id.addHouse})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addHouse:
                Intent intent = new Intent(this, SelectHouseInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.addHouseBottom:
                intent = new Intent(this, SelectHouseInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
