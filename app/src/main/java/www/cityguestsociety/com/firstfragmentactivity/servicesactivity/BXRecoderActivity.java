package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.BaoXiuRecoderGridViewAdapter;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.adapter.ViewHolder;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;

public class BXRecoderActivity extends BaseToolbarActivity {

    @BindView(R.id.BXRecoderListView)
    ListView mBXRecoderListView;
    List<Bean> lists = new ArrayList<>();
    @Override
    protected void initView() {
        initToobar("报修记录");
        mBXRecoderListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bxrecoder;
    }

    @Override
    protected void initData() {
        setAdapter();
    }

    @Override
    protected void setListener() {
        mBXRecoderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG",position+"");
                Intent intent=new Intent(BXRecoderActivity.this, BXInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        List<String> imageLists=new ArrayList<>();
        List<String> imageLists1=new ArrayList<>();
        imageLists.add("http://pic95.huitu.com/res/20170424/1258172_20170424165057597050_1.jpg");
        imageLists.add("http://pic95.huitu.com/res/20170424/1258172_20170424165057597050_1.jpg");
        lists.add(new Bean("厕所堵住了  赶紧来通一下","2017.5.17",imageLists,"","已解决",""));
        lists.add(new Bean("厕所堵住了  赶紧来通一下","2017.5.17",imageLists1,"","已解决",""));
        mBXRecoderListView.setAdapter(new CommonAdaper<Bean>(this, lists, R.layout.item_bxrecoder) {
            @Override
            public void convert(ViewHolder holder, Bean item, int p) {
                holder.setText(R.id.BXDescription, item.getText());
                holder.setText(R.id.BXrecoderTime,item.getTime());
                holder.setText(R.id.BXRecoderStatue,item.getTextInfo());
                GridView gridView = holder.getView(R.id.BXRecoderGridView);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                gridView.setClickable(false);
                gridView.setPressed(false);
                gridView.setEnabled(false);
                //// TODO: 2017/5/17  传递的集合应该是这个报修时上传的图片的集合
                gridView.setAdapter(new BaoXiuRecoderGridViewAdapter(BXRecoderActivity.this,item.getImgLists()));

            }
        });
    }


    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {

    }


}
