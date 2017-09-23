package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.HouseMainAdapter;
import www.cityguestsociety.com.adapter.HouseMoreAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Room;
import www.cityguestsociety.com.entity.Unite;
import www.cityguestsociety.com.utils.PinyinUtils;

public class HouseIDActivity extends BaseToolbarActivity {

    public static final int RESULTCODE = 2;
    public static final String RESULT = "result";
    @BindView(R.id.imageView4)
    ImageView mImageView4;
    @BindView(R.id.etCity)
    EditText mEtCity;
    @BindView(R.id.SerchRelative)
    RelativeLayout mSerchRelative;
    @BindView(R.id.mainListView)
    ListView mMainListView;
    @BindView(R.id.moreListVie)
    ListView mMoreListVie;
    private String Id;
    List<Unite.DataBean> uniteLists = new ArrayList<>();
    List<Room.DataBean> roomLists = new ArrayList<>();
    List<Room.DataBean> MSortList;
    private HouseMainAdapter mHouseMainAdapter;
    private HouseMoreAdapter mMoreAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_house_id;
    }

    @Override
    protected void initData() {
        setMoreAdapter();

        RequestParams params = new RequestParams();
        params.put("id", Id);
        getDataFromInternet(UrlFactory.unit, params, 0);

        params = new RequestParams();
        params.put("ban_id", Id);
        getDataFromInternet(UrlFactory.Room, params, 1);
        showLoadingDialog();


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        switch (what) {
            case 0:
                Gson gson = new Gson();
                Unite unite = gson.fromJson(object.toString(), Unite.class);
                uniteLists.addAll(unite.getData());
                uniteLists.add(0, new Unite.DataBean("0", "全部"));
                setAdapter();
                break;
            case 1:
                cancleLoadingDialog();
                roomLists.clear();
                gson = new Gson();
                Room room = gson.fromJson(object.toString(), Room.class);
                roomLists.addAll(filledData(room.getData()));
                mMoreAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void noData(JSONObject jsonObject, int what) {

        roomLists.clear();
        roomLists.add(new Room.DataBean("0", "暂无相关数据"));
        mMoreAdapter.notifyDataSetChanged();
        cancleLoadingDialog();
    }

    private void setMoreAdapter() {
        mMoreAdapter = new HouseMoreAdapter(this, roomLists);
        mMoreListVie.setAdapter(mMoreAdapter);
    }


    public void setAdapter() {
        mHouseMainAdapter = new HouseMainAdapter(this, uniteLists);
        mMainListView.setAdapter(mHouseMainAdapter);
    }

    @Override
    protected void setListener() {
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_house = (TextView) view.findViewById(R.id.tv_house);
                mHouseMainAdapter.setSelectItem(position);
                mHouseMainAdapter.notifyDataSetChanged();
                RequestParams params = new RequestParams();
                params.put("ban_id", Id);
                params.put("unit_id", uniteLists.get(position).getId());
                getDataFromInternet(UrlFactory.Room, params, 1);
                showLoadingDialog();
            }
        });

        mMoreListVie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (roomLists.get(position).getRoom().equals("暂无相关数据")) {
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(RESULT, roomLists.get(position));
                    setResult(RESULTCODE, intent);
                    finish();
                }
            }
        });
        mEtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
            MSortList = roomLists;
        } else {
            MSortList.clear();
            for (Room.DataBean sortModel : roomLists) {
                String name = sortModel.getRoom();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    MSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        try {
            Collections.sort(MSortList, new RoomComparator());
        } catch (Exception e) {

        }
        mMoreAdapter.updateListView(MSortList);
    }

    private List<Room.DataBean> filledData(List<Room.DataBean> allCity) {
        List<Room.DataBean> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < allCity.size(); i++) {

            Room.DataBean sortModel = new Room.DataBean();
            sortModel.setId(allCity.get(i).getId());
            sortModel.setRoom(allCity.get(i).getRoom());
            if (!allCity.get(i).getRoom().isEmpty()) {

                String pinyin = PinyinUtils.getPingYin(allCity.get(i).getRoom());
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
        try {
            Collections.sort(mSortList, new RoomComparator());
        } catch (Exception e) {

        }
        return mSortList;
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("选择单元房号");
        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
    }

    @Override
    public void onClick(View v) {

    }


}
