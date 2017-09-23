package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.ui.SideLetterBar;
import www.cityguestsociety.com.utils.PinyinUtils;

public class SelectContryActivity extends BaseToolbarActivity {


    @BindView(R.id.listview_all_city)
    ListView mListviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView mTvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar mSideLetterBar;
    @BindView(R.id.listview_search_result)
    ListView mListviewSearchResult;
    @BindView(R.id.searchBox)
    EditText mSearchBox;
    public static final int RESULTCODE = 2;
    public static final String RESULT = "result";

    private List<NetworkCityBean> networkCityBeen = new ArrayList<>();
    private List<NetworkCityBean> networkHotCityBeen = new ArrayList<>();
    private DBManager dbManager;
    private ArrayList<City> mAllCities = new ArrayList<>();
    private CityListAdapter mCityAdapter;
    private CityListAdapter mAdapter;
    private ArrayList<City> MSortList;

    @Override
    protected int getContentView() {
        return R.layout.activity_city_local;
    }


    @Override
    protected void initData() {

        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.city, params, 0);


    }


    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        mAllCities.addAll(filledData(object.getJSONArray("data").toJavaList(City.class)));
        LogUtils.e(mAllCities.toString());
        mCityAdapter = new CityListAdapter(this, mAllCities, networkHotCityBeen);

        initLocal();
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(Object obj) {
                City city = (City) obj;
                Intent intent = new Intent();
                intent.putExtra(RESULT, city);
                setResult(RESULTCODE, intent);
                finish();
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                //                mLocationClient.startLocation();
            }
        });

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        MSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            MSortList = mAllCities;
        } else {
            MSortList.clear();
            for (City sortModel : mAllCities) {
                String name = sortModel.getCity();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    MSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(MSortList, new PinyinComparator());
        mCityAdapter.updateListView(MSortList);
    }

    private List<City> filledData(List<City> allCity) {
        List<City> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < allCity.size(); i++) {

            City sortModel = new City();
            sortModel.setId(allCity.get(i).getId());
            sortModel.setCity(allCity.get(i).getCity());
            if (!allCity.get(i).getCity().equals("")) {
                String pinyin = PinyinUtils.getPingYin(allCity.get(i).getCity());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    sortModel.setSortLetters(sortString.toUpperCase());
                    if (!indexString.contains(sortString)) {
                        indexString.add(sortString);
                    }
                }
                mSortList.add(sortModel);
            }
        }
        Collections.sort(indexString);
        mSideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListviewAllCity.setSelection(position);
            }
        });
        Collections.sort(mSortList, new PinyinComparator());
        return mSortList;
    }

    private void initLocal() {

        mListviewAllCity.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mSideLetterBar.setOverlay(overlay);
        mSideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListviewAllCity.setSelection(position);
            }
        });
    }


    @Override
    protected void setListener() {
        mSearchBox.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("选择城市");
    }

    @Override
    public void onClick(View v) {

    }


}

