package www.cityguestsociety.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.entity.Bean;

/**
 * Created by LuoPan on 2017/5/17 16:52.
 */
    public class BaoXiuGridViewAdapter extends BaseAdapter {
        public Context mContext;
        public List<Bean> mLists;
        private static final int TYPE_ITEM = 1;

        public BaoXiuGridViewAdapter(Context context, List<Bean> bankInfoList) {
            this.mContext = context;
            this.mLists = bankInfoList;

        }

        @Override
        public int getCount() {
            return mLists.size()<3?mLists.size() + 1:3;
        }

        @Override
        public Object getItem(int position) {
            return mLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_add_goods_adapter, null);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

                if (position <= mLists.size() - 1) {
                    Glide.with(mContext).load(mLists.get(position).getIamge()).into(holder.mImageView);
                    holder.iv_delete.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(mContext).load(R.mipmap.icon_add_pic).into(holder.mImageView);
                    holder.iv_delete.setVisibility(View.INVISIBLE);

                }

                return convertView;

        }

        class ViewHolder {
            ImageView mImageView;
            ImageView iv_delete;
        }
    }

