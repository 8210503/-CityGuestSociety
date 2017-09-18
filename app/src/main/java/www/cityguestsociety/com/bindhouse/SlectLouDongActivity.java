package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
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
import www.cityguestsociety.com.entity.Ban;

public class SlectLouDongActivity extends BaseToolbarActivity {


    @BindView(R.id.LoudongRecyclerview)
    LRecyclerView mLoudongRecyclerview;
    private String Id;
    List<Ban.DataBean> mList = new ArrayList<>();
    private BaseRecyclerAdapter mAdapter;
    private LRecyclerViewAdapter mRecyclerAdapter;
    public static final int RESULTCODE=2;
    public static final String RESULT="result";

    @Override
    protected int getContentView() {
        return R.layout.activity_slect_lou_dong;
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("community_id", Id);
        getDataFromInternet(UrlFactory.ban, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        mList.clear();
        Ban ban = gson.fromJson(object.toString(), Ban.class);
        mList.addAll(ban.getData());
        mLoudongRecyclerview.refreshComplete(10);
        mAdapter.notifyDataSetChanged();
    }

    public void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<Ban.DataBean>(this, mList, R.layout.item_loudong) {

            @Override
            public void convert(BaseRecyclerHolder holder, Ban.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_loudong, item.getBan());
            }
        };

        mRecyclerAdapter = new LRecyclerViewAdapter(mAdapter);
        mLoudongRecyclerview.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void setListener() {
        mLoudongRecyclerview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        mLoudongRecyclerview.refresh();
        mLoudongRecyclerview.setLoadMoreEnabled(false);

        mRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent();
                intent.putExtra(RESULT,mList.get(position));
                setResult(RESULTCODE,intent);
                finish();
            }
        });
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar("选择楼栋");
        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
        mLoudongRecyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View v) {

    }

}
