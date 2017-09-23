package www.cityguestsociety.com.bindhouse;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.utils.PinyinUtils;

/**
 * 城市定位adapter
 * author zaaach on 2016/1/26.
 */
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<City> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private List<NetworkCityBean> networkHotCityBeen;

    public CityListAdapter(Context mContext, List<City> mCities, List<NetworkCityBean> networkHotCityBeen) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        this.networkHotCityBeen = networkHotCityBeen;

        if (mCities == null) {
            mCities = new ArrayList<>();
        }



      /*  mCities.add(0, new City("#", "0"));
        mCities.add(1, new City("热门城市", "1"));*/
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getSortLetters());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getSortLetters()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }


    public void updateListView(List<City> list) {
        this.mCities = list;
        notifyDataSetChanged();
    }

    /**
     * 更新定位状态
     *
     * @param state
     */
    public void updateLocateState(int state, String city) {
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

   /* @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }*/

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
       /* int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:     //定位
                view = inflater.inflate(R.layout.view_locate_city, parent, false);
                view.setVisibility(View.GONE);
                ViewGroup container = (ViewGroup) view.findViewById(R.id.layout_locate);
                final TextView state = (TextView) view.findViewById(R.id.tv_located_city);
                if (MyApplication.thisCityName == null) {
                    state.setText(mContext.getString(R.string.located_failed));
                } else {
                    state.setText(MyApplication.thisCityName);
                }

                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateState == LocateState.FAILED){
                            //重新定位
                            if (onCityClickListener != null){
                                onCityClickListener.onLocateClick();
                            }
                        }else if (locateState == LocateState.SUCCESS){
                            //返回定位城市
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(locatedCity);
                            }
                        }

                        if (state.getText().toString().equals(mContext.getString(R.string.located_failed)) ||
                                state.getText().toString().equals("")) {
                            //重新定位
                            if (onCityClickListener != null) {
                                onCityClickListener.onLocateClick();
                            }
                        } else {
                            //返回定位城市
                            if (onCityClickListener != null) {
                                MyApplication.cityName = MyApplication.thisCityName;
                                onCityClickListener.onCityClick(MyApplication.thisCityName);
                            }
                        }
                    }
                });
                break;
            case 1:     //热门
                view = inflater.inflate(R.layout.view_hot_city, parent, false);
                view.setVisibility(View.GONE);
                WrapHeightGridView gridView = (WrapHeightGridView) view.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext, networkHotCityBeen);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null) {
                            onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position));
                        }
                    }
                });
                break;
            case 2:     //所有*/
        if (view == null) {
            view = inflater.inflate(R.layout.item_city_listview, parent, false);
            holder = new CityViewHolder();
            holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
            holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
            view.setTag(holder);
        } else {
            holder = (CityViewHolder) view.getTag();
        }

        final String city = mCities.get(position).getCity();

        final Object obj = mCities.get(position);
        holder.name.setText(city);
        String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
        String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
        if (!TextUtils.equals(currentLetter, previousLetter)) {
            holder.letter.setVisibility(View.VISIBLE);
            holder.letter.setText(currentLetter);
        } else {
            holder.letter.setVisibility(View.GONE);
        }
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCityClickListener != null) {
                    onCityClickListener.onCityClick(obj);
                }
            }
        });

         /*       break;
        }*/
        return view;
    }

    private static class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(Object obj);

        void onLocateClick();
    }
}
