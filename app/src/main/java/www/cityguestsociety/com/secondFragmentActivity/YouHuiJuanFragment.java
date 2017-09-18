package www.cityguestsociety.com.secondFragmentActivity;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.adapter.ViewHolder;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.Bean;

/**
 * Created by LuoPan on 2017/9/5 11:19.
 */

public class YouHuiJuanFragment extends BaseFragment {
    ListView youhuijuanListView;
    public List<Bean> mlists;
    @Override
    protected void initView() {
        youhuijuanListView=getView(R.id.youhuijuanListView);
    }

    @Override
    protected void initData() {
        mlists=new ArrayList<>();
        for(int i=0;i<5;i++){
            mlists.add(new Bean("洛可可蛋糕店","成都市科华北路165号","9.7"));
        }
    }
    private void setAdapter() {

        youhuijuanListView.setAdapter(new CommonAdaper<Bean>(getActivity(),mlists,R.layout.item_youhuijuan) {
            @Override
            public void convert(ViewHolder holder, Bean item, int posion) {
                holder.setText(R.id.sellerName,item.getText());
                holder.setText(R.id.sellerAddress,item.getTextInfo());
                holder.setText(R.id.sellerCount,item.getIamge()+"折");
            }
        });
    }
    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.youhijianfragment;
    }
}
