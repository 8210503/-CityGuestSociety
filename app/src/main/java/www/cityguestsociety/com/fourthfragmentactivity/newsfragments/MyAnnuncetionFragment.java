package www.cityguestsociety.com.fourthfragmentactivity.newsfragments;

import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
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
import www.cityguestsociety.com.entity.Annuncetion;

/**
 * Created by LuoPan on 2017/9/5 15:08.
 */

public class MyAnnuncetionFragment extends BaseFragment {
    LRecyclerView notifyListView;
    private List<Annuncetion.DataBean> mLists = new ArrayList<>();
    private BaseRecyclerAdapter mAdapter;
    private LRecyclerViewAdapter mAdapter1;

    @Override
    protected void initView() {
        notifyListView = getView(R.id.notifyListView);
        notifyListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {

    }

    public void getData() {
        setAdapter();
        RequestParams params = new RequestParams();
        params.put("num", 20);
        getDataFromInternet(UrlFactory.system, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        mLists.clear();
        super.getSuccess(object, what);
        Gson gson = new Gson();
        Annuncetion annuncetion = gson.fromJson(object.toString(), Annuncetion.class);
        mLists.addAll(annuncetion.getData());
        notifyListView.refreshComplete(20);

        LogUtils.e(mLists.toString());
        mAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<Annuncetion.DataBean>(getActivity(), mLists, R.layout.item_announcement) {
            @Override
            public void convert(BaseRecyclerHolder holder, Annuncetion.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.notifyContent, item.getTitle());
                holder.setText(R.id.time, item.getCreation_time());
            }
        };

        mAdapter1 = new LRecyclerViewAdapter(mAdapter);
        notifyListView.setAdapter(mAdapter1);
    }

    @Override
    protected void setListener() {
        notifyListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
              getData();
            }
        });
        notifyListView.refresh();

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mynotify;
    }
}
