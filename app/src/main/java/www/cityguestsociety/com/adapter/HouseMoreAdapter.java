package www.cityguestsociety.com.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.entity.Community;
import www.cityguestsociety.com.entity.Room;

public class HouseMoreAdapter extends BaseAdapter {

    private Context context;
    private List<Room.DataBean> text_list;
    private int position = 0;
    Holder hold;

    public HouseMoreAdapter(Context context, List<Room.DataBean> text_list) {
        this.context = context;
        this.text_list = text_list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<Room.DataBean> list) {
        this.text_list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return text_list.size();
    }

    public Object getItem(int position) {
        return text_list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int arg0, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_select_house_more, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        hold.txt.setText(text_list.get(arg0).getRoom());
        /*hold.txt.setTextColor(0xFF666666);
		if (arg0 == position) {
			hold.txt.setTextColor(0xFFFF8C00);
		}*/
        return view;
    }

    public void setSelectItem(int position) {
        this.position = position;
    }




    private static class Holder {
        TextView txt;

        public Holder(View view) {
            txt = (TextView) view.findViewById(R.id.tv_house);

        }
    }
}
