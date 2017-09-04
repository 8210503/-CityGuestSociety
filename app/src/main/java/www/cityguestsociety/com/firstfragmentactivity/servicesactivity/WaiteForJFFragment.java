package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.adapter.CommonAdaper;
import www.cityguestsociety.com.baseui.BaseFragment;
import www.cityguestsociety.com.entity.Bean;

/**
 * Created by LuoPan on 2017/9/4 15:00.
 */

public class WaiteForJFFragment extends BaseFragment {
    Button mLijiJFButton;
    ListView mWaiteForJFListVIew;

    private List<Bean> mLists;
    PopupWindow mPopWindow;




    @Override
    protected void initView() {
        mLijiJFButton=getView(R.id.lijiJFButton);
        mWaiteForJFListVIew=getView(R.id.waiteForJFListVIew);
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        mLists.add(new Bean("2017年1月", "189.55", "2"));
        mLists.add(new Bean("2017年2月", "189.55", "2"));
        mLists.add(new Bean("2017年3月", "189.55", "2"));
        initButton();
        setAdapter();

    }
    public void initButton() {
        if (mLists.size() != 0) {
            mLijiJFButton.setBackgroundColor(getActivity().getResources().getColor(R.color.orange));
            mLijiJFButton.setEnabled(true);
        } else {
            mLijiJFButton.setEnabled(false);
            mLijiJFButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }
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
        mLijiJFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindows(getActivity());
                initButton();

            }
        });

    }

    void showPopWindows(Activity activity) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(getActivity(), R.layout.pop_selsect_jf_method, null);
        final RadioButton zhifubao= (RadioButton) popView.findViewById(R.id.zhifubao);
        final RadioButton weixin= (RadioButton) popView.findViewById(R.id.weixin);
        TextView tv_cancle= (TextView) popView.findViewById(R.id.cancle);
        TextView tv_confirm= (TextView) popView.findViewById(R.id.confirm);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zhifubao.isChecked()){
                    ShowToast("支付宝支付");
                }
                if(weixin.isChecked()){
                    ShowToast("微信支付");
                }
            }
        });
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        mPopWindow = new PopupWindow(popView, width, height);
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_jf;
    }


}
