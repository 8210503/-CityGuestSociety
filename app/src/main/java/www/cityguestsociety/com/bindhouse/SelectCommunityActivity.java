package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Community;
import www.cityguestsociety.com.ui.SideLetterBar;
import www.cityguestsociety.com.utils.PinyinUtils;

public class SelectCommunityActivity extends BaseToolbarActivity {


    @BindView(R.id.imageView4)
    ImageView mImageView4;
    @BindView(R.id.etCity)
    EditText mEtCity;
    @BindView(R.id.SerchRelative)
    RelativeLayout mSerchRelative;
    @BindView(R.id.listview_all_city)
    ListView mListviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView mTvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar mSideLetterBar;
    @BindView(R.id.listview_search_result)
    ListView mListviewSearchResult;
    private String mId;
    List<Community> mAllCities=new ArrayList<>();
    private CommunityAdapter mCityAdapter;
    private List<NetworkCityBean> networkHotCityBeen = new ArrayList<>();
    private List<Community> MSortList;

    public static final int RESULTCODE = 2;
    public static final String RESULT = "result";


    @Override
    protected int getContentView() {
        return R.layout.activity_select_community;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("city_id", mId);
        getDataFromInternet(UrlFactory.community, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        mAllCities.addAll(filledData(object.getJSONArray("data").toJavaList(Community.class)));

        mCityAdapter = new CommunityAdapter(this, mAllCities, networkHotCityBeen);
        initLocal();

        mCityAdapter.setOnCityClickListener(new CommunityAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(Object obj) {
                Community city = (Community) obj;
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

    private List<Community> filledData(List<Community> allCity) {
        List<Community> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < allCity.size(); i++) {

            Community sortModel = new Community();
            sortModel.setId(allCity.get(i).getId());
            sortModel.setCommunity(allCity.get(i).getCommunity());
            String pinyin = PinyinUtils.getPingYin(allCity.get(i).getCommunity());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        mSideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListviewAllCity.setSelection(position);
            }
        });
        Collections.sort(mSortList, new CommuntiyComparator());
        return mSortList;
    }

    @Override
    protected void setListener() {
        mEtCity.addTextChangedListener(new TextWatcher() {


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
            for (Community sortModel : mAllCities) {
                String name = sortModel.getCommunity();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    MSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(MSortList, new CommuntiyComparator());
        mCityAdapter.updateListView(MSortList);
    }

    @Override
    protected void initView() {
        initToobar("选择社区");
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    public void onClick(View v) {

    }


}
