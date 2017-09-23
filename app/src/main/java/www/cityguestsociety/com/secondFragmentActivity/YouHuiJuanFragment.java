package www.cityguestsociety.com.secondFragmentActivity;

import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.MyCoupon;
import www.cityguestsociety.com.utils.Constans;

/**
 * Created by LuoPan on 2017/9/5 11:19.
 */

public class YouHuiJuanFragment extends BaseFragment {
    LRecyclerView youhuijuanListView;
    public List<MyCoupon.DataBean> mlists = new ArrayList<>();
    public boolean isrefresh = false;
    private LRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void initView() {
        youhuijuanListView = getView(R.id.youhuijuanListView);
        youhuijuanListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        mlists = new ArrayList<>();
        setAdapter();

    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.coupon, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        if (isrefresh) {
            mlists.clear();
        }

        youhuijuanListView.refreshComplete(20);

        MyCoupon myCoupon = gson.fromJson(object.toString(), MyCoupon.class);
        mlists.addAll(myCoupon.getData());
        mRecyclerViewAdapter.notifyDataSetChanged();

    }

    private void setAdapter() {

        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<MyCoupon.DataBean>(getActivity(), mlists, R.layout.item_youhuijuan) {

            @Override
            public void convert(BaseRecyclerHolder holder, MyCoupon.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.sellerName,item.getTitle());
                holder.setText(R.id.sellerAddress,item.getAddress());
                holder.setText(R.id.sellerCount,item.getCoupon()+"折");
            }
        };

        mRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        youhuijuanListView.setAdapter(mRecyclerViewAdapter);

    }

    @Override
    protected void setListener() {
        youhuijuanListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                isrefresh = true;
                getData();
            }
        });
        youhuijuanListView.refresh();
        youhuijuanListView.setLoadMoreEnabled(false);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.youhijianfragment;
    }
}
