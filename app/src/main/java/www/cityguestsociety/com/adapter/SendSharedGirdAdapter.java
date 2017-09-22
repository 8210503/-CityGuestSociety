package www.cityguestsociety.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.List;

import www.cityguestsociety.com.R;

/**
 * Created by LuoPan on 2017/5/31 17:05.
 */
public class SendSharedGirdAdapter extends BaseAdapter {
    public Context mContext;
    public List<ImageItem> mLists;
    //用来判断是否是刚刚进入，刚进入只显示添加按钮，也就是上面java代码中只传this的时候

    public SendSharedGirdAdapter(Context context, List<ImageItem> bankInfoList) {
        this.mContext = context;
        this.mLists = bankInfoList;

    }

    @Override
    public int getCount() {
        //这里判断数据如果有9张就size等于9,否则就+1，+1是为按钮留的位置
            return mLists.size() < 9 ? mLists.size() + 1 : 9;

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
            Glide.with(mContext).load(mLists.get(position).path).into(holder.mImageView);
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
