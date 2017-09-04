package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.Bean;

/**
 * Created by LuoPan on 2017/9/4 15:08.
 */

public class AlreadyJFFragment extends BaseFragment {
    private List<Bean> mLists;
    ListView mWaiteForJFListVIew;
    Button mLijiJFButton;

    @Override
    protected void initView() {
        mWaiteForJFListVIew = getView(R.id.waiteForJFListVIew);
        mLijiJFButton = getView(R.id.lijiJFButton);

    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        mLists.add(new Bean("2017年1月", "189.55", "1"));
        mLists.add(new Bean("2017年2月", "189.55", "1"));
        mLists.add(new Bean("2017年3月", "189.55", "1"));
        initButton();
        setAdapter();
    }

    public void initButton() {
        mLijiJFButton.setVisibility(View.GONE);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_jf;
    }

    private void setAdapter() {
        final CommonAdaper adapter = new CommonAdaper<Bean>(getActivity(), mLists, R.layout.item_jf) {

            @Override
            public void convert(www.cityguestsociety.com.adapter.ViewHolder holder, Bean item, int position) {
                holder.setText(R.id.jfTime, item.getText());
                holder.setText(R.id.JFMoney, "本期应缴纳金额合计" + item.getTextInfo());
                if (item.getIamge().equals("1")) {
                    holder.setText(R.id.JFStatue, "已结清");
                } else if (item.getIamge().equals("2")) {
                    holder.setText(R.id.JFStatue, "未交费");
                    TextView tv_JFState = holder.getView(R.id.JFStatue);
                    tv_JFState.setTextColor(Color.parseColor("#F96060"));
                }
            }
        };
        mWaiteForJFListVIew.setAdapter(adapter);

    }

}
