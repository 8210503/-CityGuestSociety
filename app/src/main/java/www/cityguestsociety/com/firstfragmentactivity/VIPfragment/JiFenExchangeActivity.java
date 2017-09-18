package www.cityguestsociety.com.firstfragmentactivity.VIPfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.ActivityInfo;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.adapter.ViewHolder;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.NewActivity;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;

public class JiFenExchangeActivity extends BaseToolbarActivity {


    @BindView(R.id.jifenExchengeListView)
    ListView mJifenExchengeListView;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    public static final String TITLE = "title";
    private String mTitle;
    private List<NewActivity.DataBean> mList = new ArrayList<>();
    private CommonAdaper<NewActivity.DataBean> mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_ji_fen_exchange;
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    protected void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.activity, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                mList.clear();
                refreshLayout.finishRefreshing();
                Gson gson = new Gson();
                NewActivity newActivity = gson.fromJson(object.toString(), NewActivity.class);
                mList.addAll(newActivity.getData());
                LogUtils.e(mList.toString());
                mAdapter.notifyDataSetChanged();
                break;
            case 1:
                /**报名成功*/
                ShowToast(object.getString("info"));
                break;
        }

    }

    private void setAdapter() {
        mAdapter = new CommonAdaper<NewActivity.DataBean>(this, mList, R.layout.item_jfexchange) {
            @Override
            public void convert(ViewHolder holder, final NewActivity.DataBean item, int p) {
                holder.setImageByUrl(R.id.projectShowImage, UrlFactory.imaPath + item.getImg());
                holder.setText(R.id.projectName, item.getTitle());
                holder.setText(R.id.projectAddress, item.getStart_time());
                holder.setText(R.id.baomingrenshuTextview, item.getEnd_time());
                holder.setText(R.id.count, "报名人数:" + item.getSign() + "/" + item.getMax_num());
                TextView bt_statue = holder.getView(R.id.CheckStatue);

                if (item.getState().equals("0")) {
                    bt_statue.setText("已结束");
                    bt_statue.setEnabled(false);
                    bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_gray));
                } else if (item.getState().equals("1")) {
                    if (item.getCan() == 0) {
                        bt_statue.setText("我要报名");
                        bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_orange));
                        bt_statue.setEnabled(true);
                    } else if (item.getCan() == 1) {
                        bt_statue.setText("已报名");
                        bt_statue.setEnabled(false);
                        bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_orange));
                    }

                } else if (item.getState().equals("2")) {
                    if (item.getCan() == 0) {
                        bt_statue.setText("我要报名");
                        bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_orange));
                        bt_statue.setEnabled(true);
                    } else if (item.getCan() == 1) {
                        bt_statue.setText("已报名");
                        bt_statue.setEnabled(false);
                        bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_orange));
                    }
                }

                bt_statue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constans.ID.equals("null")) {
                            jumpToActivity(LoginActivity.class, false);
                        } else {
                            RequestParams params = new RequestParams();
                            params.put("member_id", Constans.ID);
                            params.put("id", item.getId());
                            getDataFromInternet(UrlFactory.par_activicty, params, 1);
                        }
                    }
                });

            }
        };
        mJifenExchengeListView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mJifenExchengeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInfo.TITLE, "活动详情");
                bundle.putInt(ActivityInfo.STATUE, Integer.parseInt(mList.get(position).getState()));
                bundle.putInt(ActivityInfo.CAN, mList.get(position).getCan());
                bundle.putString(ActivityInfo.URL, UrlFactory.miactivity_cont + "/member_id/" + Constans.ID + "/id/" + mList.get(position).getId());
                bundle.putBoolean(ActivityInfo.isShowing, true);
                jumpToActivity(ActivityInfo.class, bundle, false);
            }
        });

        refreshLayout.startRefresh();
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getData();
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
            }
        });
        refreshLayout.setEnableLoadmore(false);
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        mTitle = getIntent().getStringExtra(TITLE);
        initToobar(mTitle);
    }

    @Override
    public void onClick(View v) {

    }


}
