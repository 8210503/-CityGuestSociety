package www.cityguestsociety.com.secondFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.BaseRecyclerAdapter;
import www.cityguestsociety.com.adapter.BaseRecyclerHolder;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.baseui.ProgressWebview;
import www.cityguestsociety.com.entity.Bean;
import www.cityguestsociety.com.utils.Constans;

public class SellerInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.sellerPhoto)
    ImageView mSellerPhoto;
    @BindView(R.id.sellerName)
    TextView mSellerName;
    @BindView(R.id.sellerTitle)
    RelativeLayout mSellerTitle;
    @BindView(R.id.imageViewDZ)
    ImageView mImageViewDZ;
    @BindView(R.id.sellerAddress)
    TextView mSellerAddress;
    @BindView(R.id.selleAddressRelative)
    RelativeLayout mSelleAddressRelative;
    @BindView(R.id.sellerYHJRelative)
    RelativeLayout mSellerYHJRelative;
    @BindView(R.id.sellerPhone)
    TextView mSellerPhone;
    @BindView(R.id.sellerNumber)
    TextView mSellerNumber;
    @BindView(R.id.sellerPhoneRelative)
    RelativeLayout mSellerPhoneRelative;
    @BindView(R.id.webView)
    ProgressWebview mWebView;
    private String mId;
    PopupWindow mPopWindow;
    public String countdes;
    String descriptation;

    public boolean isHavaYHJ = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_seller_info;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("id", mId);
        LogUtils.e(params.toString());
        getDataFromInternet(UrlFactory.business_content, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {

        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Glide.with(this).load(UrlFactory.imaPath + object.getJSONArray("data").getJSONObject(0).getString("img")).into(mSellerPhoto);
                mSellerNumber.setText(object.getJSONArray("data").getJSONObject(0).getString("telephone"));
                mSellerName.setText(object.getJSONArray("data").getJSONObject(0).getString("title"));
                mSellerAddress.setText(object.getJSONArray("data").getJSONObject(0).getString("address"));
                if (!object.getJSONArray("data").getJSONObject(0).getString("coupon").isEmpty() && (Double.parseDouble(object.getJSONArray("data").getJSONObject(0).getString("coupon")) != 0)) {
                    isHavaYHJ = true;
                } else {
                    isHavaYHJ = false;
                }
                countdes = object.getJSONArray("data").getJSONObject(0).getString("coupon");
                descriptation = object.getJSONArray("data").getJSONObject(0).getString("coupontitle");

                mWebView.loadUrl(object.getJSONArray("data").getJSONObject(0).getString("content"));
                break;
            case 1:
                ShowToast("已经领取了" + countdes + "折的优惠卷");
                mPopWindow.dismiss();
                break;
        }


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
        initToobar("商家详情");
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
    }

    @OnClick({R.id.selleAddressRelative, R.id.sellerYHJRelative})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selleAddressRelative:
                showMapPopWindow();

                break;
            case R.id.sellerYHJRelative:
                if (isHavaYHJ) {
                    showPopWindows();

                } else {
                    ShowToast("本店现在没有优惠卷");
                }
                break;
        }
    }


    private void showPopWindows() {

        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.item_youhuijuan_pop, null);
        RecyclerView listView = (RecyclerView) popView.findViewById(R.id.YHJlistView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listView.setLayoutManager(manager);
        TextView tv_confirm = (TextView) popView.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        List<Bean> YHJlists = new ArrayList<>();
        YHJlists.add(new Bean(countdes + "折", descriptation, ""));

        listView.setAdapter(new BaseRecyclerAdapter<Bean>(this, YHJlists, R.layout.item_yhj_listview) {
            private RelativeLayout mTv_receive;

            @Override
            public void convert(BaseRecyclerHolder holder, final Bean item, int position, boolean isScrolling) {
                holder.setText(R.id.Count, item.getText());
                holder.setText(R.id.useKind, item.getTextInfo());
                mTv_receive = holder.getView(R.id.reciveRelative);
                mTv_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.put("member_id", Constans.ID);
                        params.put("id", mId);
                        getDataFromInternet(UrlFactory.addCoupon, params, 1);
                        showLoadingDialog();

                    }
                });
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


    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public void showMapPopWindow() {
        View parent = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        View popView = null;
        popView = View.inflate(this, R.layout.pop_window_map, null);

        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvilible(context, "com.autonavi.minimap")) {
                    try {
                        Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname" + "&sname=" + "&dname=" + mSellerAddress.getText().toString().trim() + "&dev=0&m=0&t=1");
                        context.startActivity(intent);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                 /*   Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);*/
                    ShowToast("尚未安装高德地图");
                }

            }
        });


        /**百度地图*/
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvilible(context, "com.baidu.BaiduMap")) {
                    Intent i1 = new Intent();
                    i1.setData(Uri.parse("baidumap://map/direction?destination=目的地:" + mSellerAddress.getText().toString().trim()));
                    //                i1.setData(Uri.parse("baidumap://map/geocoder?address=" + mSellerAddress.getText().toString().trim()));
                    startActivity(i1);
                } else {
                    ShowToast("尚未安装百度地图");
                }


            }
        });
        /**高德地图*/
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
