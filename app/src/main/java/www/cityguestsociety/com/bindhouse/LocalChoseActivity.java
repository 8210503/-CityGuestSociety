/*
package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.ui.SideLetterBar;

*
 * 城市定位详情页
 * Created by Administrator on 2017/3/22.


public class LocalChoseActivity extends BaseToolbarActivity {
    @Override
    protected int getContentView() {
        return 0;
    }


    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }
    private View allReturn;
    private TextView allTitle;
    private DBManager dbManager;
    private List<City> mAllCities = new ArrayList<>();
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    public static String KEY_PICKED_CITY = "chose_city";
    private ListView mListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ViewGroup emptyView;
    private ListView mResultListView;
    private List<NetworkCityBean> networkCityBeen = new ArrayList<>();
    private List<NetworkCityBean> networkHotCityBeen = new ArrayList<>();

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_city_local;
    }

    @Override
    protected void initView() {
        initData1();

        allReturn = getView(R.id.all_return);
        allTitle = getView(R.id.all_title);
        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        searchBox = (EditText) findViewById(R.id.et_search);
    }

    private void initData1() {

        mCityAdapter = new CityListAdapter(this, mAllCities,networkHotCityBeen);

        initInternet();

        dbManager = new DBManager(this);

//        dbManager.copyDBFile();
//        mAllCities = dbManager.getAllCities();
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                Log.e("onLocateClick", "重新定位...");
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
//                mLocationClient.startLocation();
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);
    }

    @Override
    protected void initData() {
        allTitle.setText(getString(R.string.located_city));
        initLocal();
    }

    @Override
    protected void setListener() {
        //clear
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBox.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                mResultListView.setVisibility(View.GONE);
            }
        });

        //return finish
        allReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initLocal() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        dbManager.copyDBFile();
        dbManager.getAllCities();
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);

                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());
            }
        });

    }

    private void back(String city) {
        if (judgeCity(city)) { //true 当前城市开通 否则没有开通
//            ShowToast("点击的城市：" + city);
            Intent data = new Intent();
            data.putExtra(KEY_PICKED_CITY, city);
            setResult(RESULT_OK, data);
            MyApplication.isNeedRefreshList = true;

            //save city
            MyApplication.cityName = city;
            AppManager.saveLoginInfo(getApplicationContext());

            //选择了城市标志
            MyApplication.isCityChanged = true;
            finish();
        }  else {
            ShowToast(getString(R.string.now_city_cannot_open));
        }
    }


    private boolean judgeCity(String city) {
        for (int i = 0; i < networkCityBeen.size(); i++) {
            if (networkCityBeen.get(i).getArea_name().trim().equals(city)) { //开通后就更新刷新数据
                MyApplication.allAreaId = networkCityBeen.get(i).getArea_id();
                //选择城市后需要刷新商家列表
                MyApplication.isNeedRefreshList = true;
                //定位城市点击后，开始将商家刷新列表标志位变为true
                MyApplication.isLocalRefresh = true;

                return true;
            }
        }
        return false;
    }


    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }


    private void initInternet() {
        if (isNetworkAvailable(LocalChoseActivity.this)) {

            RequestParams params = new RequestParams();
            Get(mContext, HttpContentUtils.localCity, params, 0);
        } else {
            ShowToastLong(getString(R.string.network_error_please_check_network_and_try_again));
        }
    }

    @Override
    public void GetSuccess(JSONObject jsonObject, int what) {
        super.GetSuccess(jsonObject, what);
        if (jsonObject.getString("code").equals("200")) {
            //open_city_list
            networkCityBeen.addAll(JSONObject.parseArray(jsonObject.getJSONObject("data").getJSONArray("open_city_list").toString(), NetworkCityBean.class));
            networkHotCityBeen.addAll(JSONObject.parseArray(jsonObject.getJSONObject("data").getJSONArray("hot_city_list").toString(), NetworkCityBean.class));
            City city;
            if (networkCityBeen.size() != 0) {
                for (int i = 0; i < networkCityBeen.size(); i++) {
                    String name = networkCityBeen.get(i).getArea_name();
                    //get pinyin
                    String pinyin = ChineseChangePinYin.getPinYin(networkCityBeen.get(i).getArea_name());

                    city = new City(name, pinyin);
                    mAllCities.add(city);
                }

                Collections.sort(mAllCities, new CityComparator());

                //notify adapter
                mCityAdapter.notifyDataSetChanged();
            }
        } else {
            ShowToast(jsonObject.getString("msg"));
        }
    }
}
*/
