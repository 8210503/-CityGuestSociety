package www.cityguestsociety.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.entity.Unite;

public class HouseMainAdapter extends BaseAdapter {

	private Context context;
	private List<Unite.DataBean> list;
	private int position = 0;
	private boolean islodingimg = true;
	Holder hold;

	public HouseMainAdapter(Context context, List<Unite.DataBean> list) {
		this.context = context;
		this.list = list;
	}

	public HouseMainAdapter(Context context, List<Unite.DataBean> list,
							boolean islodingimg) {
		this.context = context;
		this.list = list;
		this.islodingimg = islodingimg;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int arg0, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = View.inflate(context, R.layout.item_select_house_main, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		hold.txt.setText(list.get(arg0).getLabel());
		hold.layout.setBackgroundColor(0xFFEBEBEB);
		if (arg0 == position) {
			hold.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
			hold.txt.setTextColor(context.getResources().getColor(R.color.orange));
		}else {
			hold.layout.setBackgroundColor(context.getResources().getColor(R.color.black_gray));
			hold.txt.setTextColor(Color.parseColor("#767676"));
		}
		return view;
	}

	public void setSelectItem(int position) {
		this.position = position;
	}

	public int getSelectItem() {
		return position;
	}

	private static class Holder {
		RelativeLayout layout;
		TextView txt;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.tv_house);
			layout = (RelativeLayout) view.findViewById(R.id.relative);
		}
	}
}
