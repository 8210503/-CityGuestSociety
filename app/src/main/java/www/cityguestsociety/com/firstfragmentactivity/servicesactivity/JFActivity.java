package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.animation.ItemAnimatorFactory;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;
import www.cityguestsociety.com.ui.DividerGridItemDecoration;

import static www.cityguestsociety.com.R.id.JFimage;

public class JFActivity extends BaseToolbarActivity {


    @BindView(JFimage)
    ImageView mJFimage;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    List<Bean> mBeanList;
    private BaseRecyclerAdapter<Bean> adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_jf;
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    @Override
    protected void initView() {
        initToobar("物业缴费");
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecylerView.setLayoutManager(manager);
        mRecylerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecylerView.setItemAnimator(new ItemAnimatorFactory().slidein());
    }


    private void getData() {
        Glide.with(JFActivity.this).load("http://pic125.nipic.com/file/20170402/17961491_173312807000_2.jpg").asBitmap().into(mJFimage);
        mBeanList.clear();
        int pos = adapter.getItemCount();
        mBeanList.add(new Bean("物业费", R.mipmap.icon_property_fee));
        mBeanList.add(new Bean("缴水费", R.mipmap.icon_water_rate));
        mBeanList.add(new Bean("缴电费", R.mipmap.icon_electric_charge));
        mBeanList.add(new Bean("燃气费", R.mipmap.icon_gas_bill));
        adapter.notifyItemRangeInserted(pos, mBeanList.size());
    }

    private void setAdapter() {
        mBeanList = new ArrayList<>();
        adapter = new BaseRecyclerAdapter<Bean>(JFActivity.this, mBeanList, R.layout.item_jfactivity) {
            @Override
            public void convert(BaseRecyclerHolder holder, Bean item, int position, boolean isScrolling) {
                holder.setText(R.id.JFText, item.getText());
                holder.setImageResource(R.id.JFimage, item.getBitmap());

            }
        };

        mRecylerView.setAdapter(adapter);
        mRecylerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 500);


    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if (position == 0) {
                    Intent intent = new Intent(JFActivity.this, PropertyJFActivity.class);
                    startActivity(intent);
                } else {
                  ShowToast("正在开发中~");
                }
            }
        });

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
