package www.cityguestsociety.com.fourthfragmentactivity;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
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
import www.cityguestsociety.com.entity.Black;
import www.cityguestsociety.com.utils.Constans;

public class BlackListsActivity extends BaseToolbarActivity {


    @BindView(R.id.blackListGridView)
    LRecyclerView mBlackListGridView;
    List<Black.DataBean> mDataLists = new ArrayList<>();
    private LRecyclerViewAdapter mLrAdapter;
    public int pos = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_black_lists;
    }

    @Override
    protected void initData() {
        setAdapter();
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.blacklist, params, 0);
    }

    private void setAdapter() {
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<Black.DataBean>(this, mDataLists, R.layout.item_blacklists) {

            @Override
            public void convert(BaseRecyclerHolder holder, Black.DataBean item, final int position, boolean isScrolling) {
                holder.setText(R.id.userNickName, item.getMember().getNickname());
                holder.setImageByUrl(R.id.userPhoto, item.getMember().getImg());

                holder.getView(R.id.shanchu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // TODO: 2017/9/25

                        RequestParams params = new RequestParams();
                        params.put("id", mDataLists.get(position).getId());
                        getDataFromInternet(UrlFactory.delblacklist, params, 1);
                        pos = position;
                        showLoadingDialog();
                    }
                });

            }
        };
        mLrAdapter = new LRecyclerViewAdapter(adapter);
        mBlackListGridView.setAdapter(mLrAdapter);

        mBlackListGridView.setPullRefreshEnabled(false);
        mBlackListGridView.setLoadMoreEnabled(false);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                Black black = gson.fromJson(object.toString(), Black.class);
                mDataLists.addAll(black.getData());
                mLrAdapter.notifyDataSetChanged();
                break;
            case 1:
                ShowToast(object.getString("info"));
                //                if(pos>=0&&pos<mDataLists.size()){
                //                }
                mDataLists.remove(pos);
                mLrAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView() {
        initToobar("黑名单");
        mBlackListGridView.setLayoutManager(new GridLayoutManager(this, 4));

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
