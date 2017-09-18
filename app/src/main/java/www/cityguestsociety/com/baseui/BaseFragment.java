package www.cityguestsociety.com.baseui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import butterknife.ButterKnife;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.bindhouse.SelectHouseInfoActivity;
import www.cityguestsociety.com.login.LoginActivity;
import www.cityguestsociety.com.utils.Constans;


/**
 * fragment基类
 * Created by Administrator on 2017/6/3.
 */
public abstract class BaseFragment extends Fragment {
    protected String TAG = this.getClass().getSimpleName();

    protected FragmentManager mFragmentManager;
    public View rootView;
    protected Activity mContext;
    private Object fragment;
    private Dialog mLoadingDialog;
    private SparseArray<View> mViews;

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //设置点击事件
    protected abstract void setListener();

    //设置布局
    protected abstract int setLayoutResID();


    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare;

    /**
     * 双击间隔大于1秒
     */
    public void noDoubleClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        mViews = new SparseArray<>();
        rootView = inflater.inflate(setLayoutResID(), container, false);
        mContext = getActivity();
        ButterKnife.bind(rootView);


        initView();

        initData();
        setListener();
        return rootView;
    }


    /**
     * 通用findViewById,减少重复的类型转换
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {
            if (rootView != null) {
                E view = (E) mViews.get(id);
                if (view == null) {
                    view = (E) rootView.findViewById(id);
                    mViews.put(id, view);
                }
                return view;
            }
            return null;

        } catch (ClassCastException ex) {
            LogUtils.e(ex);
            throw ex;
        }
    }

    /**
     * Tost消息提醒
     */
    public void ShowToast(String text) {

        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void ShowToastLong(String text) {

        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public boolean isNetworkAvailable() {

        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***********************************************************************************************************/
    public void showLoadingDialog(String text) {
        LayoutInflater inflater = LayoutInflater.from(MyApplication.getContext());
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);// 加载布局
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_quan);
        Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.startAnimation(operatingAnim);
        // 创建自定义样式dialog
        mLoadingDialog = new Dialog(getActivity(), R.style.DialogStyle);
        mLoadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        mLoadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = mLoadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        mLoadingDialog.show();

        //        LoadingDialog.showDialog(getActivity());
    }

    public void cancleLoadingDialog() {
        //        LoadingDialog.closeDialog();
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing())
                mLoadingDialog.dismiss();
        }

    }

    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }


    /**
     * 封装跳转方式
     *
     * @param intent 传递参数
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Intent intent, boolean finish) {

        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }


    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param bundle 传递参数
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }

    /**
     * 启动Activity，
     *
     * @param clazz  到指定页面
     * @param bundle 传递参数
     * @param flags  Intent.setFlag参数，设置为小于0，则不设置
     * @param finish
     */
    public void jumpToActivity(Class<? extends Activity> clazz, Bundle bundle, int flags, boolean finish) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        if (flags > 0) {
            intent.setFlags(flags);
        }

        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }

    public void getDataFromInternet(final String url, final RequestParams params, final int what) {
        if (!isNetworkAvailable()) {
            ShowToast(getString(R.string.not_network));
            return;
        }

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();


        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                cancleLoadingDialog();
                LogUtils.e(TAG, url);
                ShowToast("发生错误了 请稍候再试");

            }

            @Override
            public void onStart() {
                LogUtils.e(params.toString());
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JSONObject object = JSON.parseObject(responseString);
                LogUtils.e(responseString);
                if (object.getInteger("code") == 1) {
                    getSuccess(object, what);
                } else if (object.getInteger("code") == 2) {
                    NoData(object, what);
                } else {
                    ShowToast(object.getString("info"));
                    LogUtils.e(url, responseString);
                    cancleLoadingDialog();
                }


            }
        });

    }

    public void getSuccess(JSONObject object, int what) {
        cancleLoadingDialog();
    }

    public void NoData(JSONObject object, int what) {
        cancleLoadingDialog();
    }

    public boolean isBindHouse() {
        if (!Constans.ID.equals("null")) {
            if (Constans.isBindHouse == true) {
                return true;
            } else {
                jumpToActivity(SelectHouseInfoActivity.class, false);
            }
            return false;
        } else {
            jumpToActivity(LoginActivity.class, false);
        }
        return false;
    }

    public boolean isLogined() {
        if (Constans.ID.equals("null")) {
            jumpToActivity(LoginActivity.class, false);
            return false;
        }
        return true;
    }
}

