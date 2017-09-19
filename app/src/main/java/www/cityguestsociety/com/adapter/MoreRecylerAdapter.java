package www.cityguestsociety.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.cityguestsociety.com.R;

/**
 * Created by LuoPan on 2017/9/18 15:21.
 */

public abstract  class MoreRecylerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    private LayoutInflater inflater;//布局器
    private int itemLayoutId;//布局id
    private Context context;//上下文
    private List<T> list;//数据源
    private int position = 0;
    private boolean isScrolling;//是否在滚动

    public MoreRecylerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(itemLayoutId, parent, false);
        return BaseRecyclerHolder.getRecyclerHolder(context, view);
    }

    @Override

    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        convert(holder, list.get(position), position, isScrolling);
    }

    public void setSelectItem(int position) {
        this.position = position;
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public abstract void convert(BaseRecyclerHolder holder, T item, int position, boolean isScrolling);
}
