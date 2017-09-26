package www.cityguestsociety.com.secondFragmentActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.adapter.ClassifyMainAdapter;
import www.cityguestsociety.com.adapter.MoreRecylerAdapter;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.Seller;
import www.cityguestsociety.com.entity.SellerInfo;

/**
 * Created by LuoPan on 2017/9/5 11:11.
 */

public class SellerFragment extends BaseFragment {


    ListView mClassifyMainlist;
    LRecyclerView mClassifyMorelist;
    List<Seller.DataBean> mSellseLists = new ArrayList<>();
    private ClassifyMainAdapter mMainAdapter;
    public LocationClient mLocationClient = null;
    List<SellerInfo.DataBean> dataInfo = new ArrayList<>();
    List<SellerInfo.DataBean> dataInfolists = new ArrayList<>();


    /**
     * 服务器端一共多少条数据
     */
    private static int TOTAL_COUNTER = 0;

    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = MyApplication.getCount;

    /**
     * 已经获取到多少条数据了
     */
    private int mCurrentCounter = 0;

    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    RequestParams params = new RequestParams();
    public boolean isRefresh = false;
    private String mLabel = "0";
    public boolean  isClick=false;

    @Override
    protected void initView() {

        mClassifyMainlist = getView(R.id.classify_mainlist);
        mClassifyMorelist = getView(R.id.classify_morelist);
        mClassifyMorelist.setLayoutManager(new LinearLayoutManager(getActivity()));



        mClassifyMorelist.setFooterViewColor(R.color.colorAccent, R.color.white, android.R.color.white);
        //设置底部加载文字提示
        mClassifyMorelist.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        mLocationClient = new LocationClient(MyApplication.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LogUtils.e(bdLocation.getLatitude());
                LogUtils.e(bdLocation.getLongitude());
            }
        });
        //注册监听函数
        initLocation();

    }


    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死


        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }


    @Override
    protected void initData() {
        setMoreAdapter();
        mLocationClient.start();
        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.merchant_label, params, 0);
        getData();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                Seller seller = gson.fromJson(object.toString(), Seller.class);
                mSellseLists.addAll(seller.getData());
                mSellseLists.add(0, new Seller.DataBean("0", "全部"));
                setAdapter();
                break;
            case 1:
                gson = new Gson();
                dataInfo.clear();
                if (isRefresh) {
                    dataInfolists.clear();
                }
                if (isClick){
                    dataInfolists.clear();

                }

                SellerInfo info = gson.fromJson(object.toString(), SellerInfo.class);
                dataInfo.addAll(info.getData());
                dataInfolists.addAll(dataInfo);

                TOTAL_COUNTER = Integer.parseInt(info.getPagecount());
                mCurrentPage++;
                mCurrentCounter += dataInfo.size();
                mRecyclerViewAdapter.notifyDataSetChanged();
                mClassifyMorelist.refreshComplete(REQUEST_COUNT);

                break;
        }

    }

    public void getData() {
        params.put("point_lat", "");
        params.put("point_lng", "");
        params.put("next", mCurrentPage);
        params.put("label", mLabel);
        getDataFromInternet(UrlFactory.business, params, 1);
    }


    private void setAdapter() {

        mMainAdapter = new ClassifyMainAdapter(getActivity(), mSellseLists);
        mClassifyMainlist.setAdapter(mMainAdapter);
    }

    public void setMoreAdapter() {
        MoreRecylerAdapter adapter = new MoreRecylerAdapter<SellerInfo.DataBean>(getActivity(), dataInfolists, R.layout.item_classify_morelist) {

            @Override
            public void convert(BaseRecyclerHolder holder, SellerInfo.DataBean item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.sellerImage, UrlFactory.imaPath + item.getImg());
                holder.setText(R.id.moreitem_txt, item.getTitle());
                holder.setText(R.id.sellerAddress, item.getAddress());

            }
        };
        mRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mClassifyMorelist.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    protected void setListener() {
        mClassifyMainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainAdapter.setSelectItem(position);
                mMainAdapter.notifyDataSetChanged();
                isClick=true;
                mLabel = mSellseLists.get(position).getId();

                mCurrentPage = 1;
                RequestParams params = new RequestParams();
                params.put("point_lat", "");
                params.put("point_lng", "");
                params.put("next", mCurrentPage);
                params.put("label", mLabel);
                getDataFromInternet(UrlFactory.business, params, 1);
                showLoadingDialog("");


            }
        });
        mClassifyMainlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        mClassifyMorelist.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentCounter = 0;
                isRefresh = true;
                mCurrentPage = 1;
                getData();
            }
        });
        mClassifyMorelist.refresh();
        mClassifyMorelist.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isRefresh = false;
                if (mCurrentCounter < TOTAL_COUNTER) {
                    // loading more
                    getData();
                } else {
                    //the end
                    mClassifyMorelist.setNoMore(true);
                }
            }
        });

        mRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", dataInfolists.get(position).getId());
                jumpToActivity(SellerInfoActivity.class, bundle, false);
            }
        });

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.selleractitvityinfo;
    }


}
