package www.cityguestsociety.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.entity.Seller;

public class ClassifyMainAdapter extends BaseAdapter {

	private Context context;
	private List<Seller.DataBean> list;
	private int position = 0;
	private boolean islodingimg = true;
	Holder hold;

	public ClassifyMainAdapter(Context context, List<Seller.DataBean> list) {
		this.context = context;
		this.list = list;
	}

	public ClassifyMainAdapter(Context context, List<Seller.DataBean> list, boolean islodingimg) {
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
			view = View.inflate(context, R.layout.item_classify_mainlist, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		if (islodingimg == true) {

		}
		hold.txt.setText(list.get(arg0).getTitle());
		hold.layout.setBackgroundColor(0xFFEBEBEB);
		if (arg0 == position) {
			hold.layout.setBackgroundColor(context.getResources().getColor(R.color.orange));
			hold.txt.setTextColor(context.getResources().getColor(R.color.white));
		}else {
			hold.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
			hold.txt.setTextColor(context.getResources().getColor(R.color.balck));
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
		LinearLayout layout;
		ImageView img;
		TextView txt;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.mainitem_txt);
			img = (ImageView) view.findViewById(R.id.mainitem_img);
			layout = (LinearLayout) view.findViewById(R.id.mainitem_layout);
		}
	}
}
