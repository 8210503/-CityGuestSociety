package www.cityguestsociety.com.firstfragmentactivity.VIPfragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.adapter.ViewHolder;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Bean;

public class JiFenExchangeActivity extends BaseToolbarActivity {


    @BindView(R.id.jifenExchengeListView)
    ListView mJifenExchengeListView;
    public static final String TITLE = "title";
    private String mTitle;
    private List<Bean> mList;

    @Override
    protected int getContentView() {
        return R.layout.activity_ji_fen_exchange;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        mList.add(new Bean("兑换日来临，高手召集令！", "2016.12.05", "22人报名", "http://pic125.nipic.com/file/20170320/20053289_215331035000_2.jpg", 1));
        mList.add(new Bean("兑换日来临，高手召集令！", "2016.12.05", "22人报名", "http://pic125.nipic.com/file/20170320/20053289_215331035000_2.jpg", 2));
        mList.add(new Bean("兑换日来临，高手召集令！", "2016.12.05", "22人报名", "http://pic125.nipic.com/file/20170320/20053289_215331035000_2.jpg", 3));
        setAdapter();
    }

    private void setAdapter() {
        mJifenExchengeListView.setAdapter(new CommonAdaper<Bean>(this, mList, R.layout.item_jfexchange) {
            @Override
            public void convert(ViewHolder holder, Bean item, int p) {
                holder.setImageByUrl(R.id.projectShowImage, item.getIamge());
                holder.setText(R.id.projectName, item.getText());
                holder.setText(R.id.projectAddress, item.getTime());
                holder.setText(R.id.baomingrenshuTextview, item.getTextInfo());
                TextView bt_statue = holder.getView(R.id.CheckStatue);
                if (item.getBitmap() == 1) {
                    bt_statue.setText("我要报名");
                } else if (item.getBitmap() == 2) {
                    bt_statue.setText("我已报名");
                } else if (item.getBitmap() == 3) {
                    bt_statue.setBackgroundDrawable(getResources().getDrawable(R.drawable.all_corners_gray));
                    bt_statue.setEnabled(false);
                    bt_statue.setText("活动进行中");
                }

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        mTitle = getIntent().getStringExtra(TITLE);
        initToobar(mTitle);
    }

    @Override
    public void onClick(View v) {

    }


}
