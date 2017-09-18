package www.cityguestsociety.com.fourthfragmentactivity;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.QRCode;
import www.cityguestsociety.com.utils.Constans;

public class MyQRCodeSctivity extends BaseToolbarActivity {


    @BindView(R.id.userCorn)
    ImageView mUserCorn;
    @BindView(R.id.userNickName)
    TextView mUserNickName;
    @BindView(R.id.userAddress)
    TextView mUserAddress;
    @BindView(R.id.userQRCode)
    ImageView mUserQRCode;
    @BindView(R.id.userID)
    TextView mUserID;
    PopupWindow mPopWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_qrcode_sctivity;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("member_id", Constans.ID);
        getDataFromInternet(UrlFactory.erweima, params, 0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        QRCode qrCode = gson.fromJson(object.toString(), QRCode.class);
        String erweima = qrCode.getData().get(0).getErweima();
        String nickName = qrCode.getData().get(0).getNickname();
        String id = qrCode.getData().get(0).getIds();
        String img = qrCode.getData().get(0).getImg();
        String city = qrCode.getData().get(0).getOwner().getCity();
        String community = qrCode.getData().get(0).getOwner().getCommunity();


        Glide.with(this).load(img).asBitmap().error(R.drawable.icon_head_portrait).placeholder(R.drawable.icon_head_portrait).into(mUserCorn);
        mUserNickName.setText(nickName);

        Glide.with(this).load(UrlFactory.imaPath + erweima).asBitmap().into(mUserQRCode);

        mUserID.setText(id);

        mUserAddress.setText(city + community);
    }

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "我的二维码", "分享");
        mUserQRCode.bringToFront();
    }

    @Override
    public void RightOnClick() {
        showPopWindows();
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
    public void onClick(View v) {

    }

    private void showPopWindows() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;

        popView = View.inflate(this, R.layout.pop_window_shared, null);


        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopWindow.dismiss();
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
}
