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

/**
 * Created by LuoPan on 2017/5/17 16:52.
 */
public class BaoXiuRecoderGridViewAdapter extends BaseAdapter {
    public Context mContext;
    public List<String> mLists;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_END_ITEM = 1 + TYPE_ITEM;

    public BaoXiuRecoderGridViewAdapter(Context context, List<String> bankInfoList) {
        this.mContext = context;
        this.mLists = bankInfoList;

    }

    @Override
    public int getCount() {
        return mLists == null ? 0 : mLists.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bxrecoder_adapter, null);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(mLists.get(position).toString().trim()).asBitmap().into(holder.mImageView);


        return convertView;

    }

    class ViewHolder {
        ImageView mImageView;
    }
}

