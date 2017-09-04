package www.cityguestsociety.com.firstfragmentactivity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.ui.RoundImageViewr;
import www.cityguestsociety.com.ui.WhewView;
import www.cityguestsociety.com.utils.L;

public class OpenDoorActivity extends BaseToolbarActivity {


    @BindView(R.id.openDoorImage)
    WhewView mOpenDoorImage;
    @BindView(R.id.roundImageViewr)
    RoundImageViewr mRoundImageViewr;
    @BindView(R.id.ImageViewRelative)
    RelativeLayout mImageViewRelative;
    private PopupWindow mPopWindow;


    @Override
    protected int getContentView() {
        return R.layout.activity_open_door;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "一键开门", "授权");
    }

    @Override
    public void RightOnClick() {
        L.e(TAG, "RightOnClick");
        showPopWindows(this);
    }

    @OnClick({R.id.roundImageViewr})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.roundImageViewr:
                if (mOpenDoorImage.isStarting()) {
                    //如果动画正在运行就停止，否则就继续执行
                    mOpenDoorImage.stop();
                    //结束进程
                } else {
                    // 执行动画
                    mOpenDoorImage.start();
                }
                break;

        }
    }

    public void showPopWindows(final Activity activity) {
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(activity, R.layout.pop_window_opendoor, null);
        Button shared = (Button) popView.findViewById(R.id.bt_check);
        // TODO: 2017/9/4 分享到微信或者QQ

        ImageView iv_opendoorCode = (ImageView) popView.findViewById(R.id.openDoorQRCode);
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        Glide.with(activity).load("http://pic94.huitu.com/res/20170328/874924_20170328095633963014_1.jpg").asBitmap().into(iv_opendoorCode);


        mPopWindow = new PopupWindow(popView, width, height);
        mPopWindow.setAnimationStyle(R.style.AnimBottom);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        mPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        ColorDrawable dw = new ColorDrawable(0x30000000);
        mPopWindow.setBackgroundDrawable(dw);
        mPopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
