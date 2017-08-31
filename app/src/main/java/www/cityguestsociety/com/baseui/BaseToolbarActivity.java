package www.cityguestsociety.com.baseui;

/**
 * Created by LuoPan on 2017/7/12.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.utils.ClickEventUtils;
import www.cityguestsociety.com.utils.KeyBoardUtils;


public abstract class BaseToolbarActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView tvTitle;
    private int menuResId;
    private String menuStr;
    protected Context mContext;
    protected Activity mActivity;
    public static Context context;
    /**
     * 左边点击事件
     */
    View.OnClickListener onClickListenerTopLeft;

    /**
     * 右边点击事件
     */
    View.OnClickListener onClickListenerTopRight;

    /**
     * 记录是点击时屏幕的Y轴起点
     */
    private float startY;
    /**
     * 页面直接引用的LOG标识
     */
    protected String TAG = this.getClass().getSimpleName();

    /**
     * 是否全屏
     */
    protected boolean isFullScreen = false;

    private LinearLayout mDecorView = null;//根布局

    private FrameLayout mContentView = null;//activity内容布局

    /**
     * 是否添加ToolBar
     */
    protected boolean isShowToolBar = true;
    protected boolean isshowActionbar = true;
    private ImageView mRightImage;
    private Dialog mLoadingDialog;
    private TextView mRightText;

    public boolean PORTRAIT = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        mContext = this;
        mActivity = this;
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        initBase();
        if (mDecorView == null) {
            initDecorView();//初始化跟布局（添加toolbar，添加mContentview给子布局留空间）
        }
        //如果已经创建就先把内容清空，再添加
        if (mContentView != null) {
            mContentView.removeAllViews();//mContentview清空里面的view
        }
        setContentView(getContentView());    //添加布局
       /* sizeUtils = new SizeUtils(mActivity);
        spUtils = new SPUtils(mActivity);*/
        ButterKnife.bind(this);
//        apiService = HttpsRequest.provideClientApi();
        initView();
        initData();
        setListener();

    }


    public void showLoadingDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);// 加载布局
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_quan);
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.startAnimation(operatingAnim);

        // 创建自定义样式dialog
        mLoadingDialog = new Dialog(this, R.style.MyDialogStyle);
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
        //        LoadingDialog.showDialog(this);

    }

    public void cancleLoadingDialog() {
        //        LoadingDialog.closeDialog();
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }

        }
    }

    /**
     * 根布局添加title布局
     */
    private void initDecorView() {
        //根容器
        mDecorView = new LinearLayout(this);
        mDecorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDecorView.setOrientation(LinearLayout.VERTICAL);
        if (isshowActionbar) {
            //Title
            addToolBar();
        }
        //内容
        mContentView = new FrameLayout(this);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDecorView.addView(mContentView);

    }


    /**
     * 重写布局设置，加入title布局
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        getLayoutInflater().inflate(layoutResID, mContentView);
        mDecorView.setFitsSystemWindows(true);//保证沉浸式窗体，状态栏和标题栏不重叠
        super.setContentView(mDecorView);
    }


    /**
     * 实例化Toolbar，设置基本参数
     */
    protected void addToolBar() {
        View view = getLayoutInflater().inflate(R.layout.activity_base_toolbar, mDecorView);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mRightImage = (ImageView) view.findViewById(R.id.imageRight);
        mRightText = (TextView) view.findViewById(R.id.textRight);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        //        //初始化设置 Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * @param title 标题
     */
    public void initToobar(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            setTopLeftButton(R.mipmap.fanhui);
            mRightImage.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * @param title 标题
     * @param icon  左边图标
     */
    public void initToobar(String title, int icon) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (icon == 0)
            setTopLeftButton(R.mipmap.fanhui);
        else
            setTopLeftButton(icon);
    }

    /**
     * @param title     标题
     * @param menuResId 菜单ID
     * @param icon      左边图标
     */
    public void initToobar(String title, int menuResId, int icon) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        this.menuResId = menuResId;
        if (icon == 0)
            setTopLeftButton(R.mipmap.fanhui);
        else
            setTopLeftButton(icon);
    }

    /**
     * 模仿微信  更改右边菜单图片并且显示菜单
     *
     * @param title      标题
     * @param menuResId  菜单ID
     * @param icon       左边图标
     * @param rightImage 右边图片
     */
    public void initToobar(String title, int menuResId, int icon, int rightImage) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }

        this.menuResId = menuResId;

        if (rightImage != 0) {
            toolbar.setOverflowIcon(getResources().getDrawable(rightImage));
        }
        if (icon == 0)
            setTopLeftButton(R.mipmap.fanhui);
        else
            setTopLeftButton(icon);
    }

    /**
     * 模仿微信 朋友圈页面 点击右边图片跳转页面
     *
     * @param title      标题
     * @param leftImage  左边返回图片
     * @param rightImage 右边图片
     */
    public void initToobar(int leftImage, String title, int rightImage) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }


        if (rightImage != -1) {
            mRightImage.setBackgroundDrawable(getResources().getDrawable(rightImage));
            mRightImage.setVisibility(View.VISIBLE);
        }
        if (rightImage == -1) {
            mRightImage.setVisibility(View.INVISIBLE);
        }
        if (leftImage == 0)
            setTopLeftButton(R.mipmap.fanhui);
        else if (leftImage == -1) {

        } else
            setTopLeftButton(leftImage);

    }


    /**
     * 左边图片 中间标题 右边文字
     */
    public void initToobar(int leftImage, String title, String rightText) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (rightText != null) {
            mRightText.setText(rightText);
        }

        if (leftImage == 0)
            setTopLeftButton(R.mipmap.fanhui);
        else
            setTopLeftButton(leftImage);
    }

    /**
     * @param title   标题
     * @param menuStr 右边标题
     * @param icon    左边图标
     */
    public void initToobar(String title, String menuStr, int icon) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        this.menuStr = menuStr;
        if (icon == 0)
            setTopLeftButton(R.mipmap.fanhui);
        else
            setTopLeftButton(icon);
    }


    /**
     * 初始化UI所需参数
     */
    protected void initView() {

    }


    /**
     * 设置布局
     *
     * @return 布局文件ID
     */
    protected abstract int getContentView();


    /**
     * 初始化  基本上不使用到地图该方法不用被重写
     *
     * @param savedInstanceState
     */
    public void init(Bundle savedInstanceState) {
        if (PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }
        //是否全屏
        if (isFullScreen) {
            isShowToolBar = false;

            //全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
//            StatusBar.setBackgroundResource(this, stateColor);
        }
    }


    /**
     * @param iconResId 左边按钮图片
     */
    protected void setTopLeftButton(int iconResId) {
        toolbar.setNavigationIcon(iconResId);
        /** f返回键的监听*/
        onClickListenerTopLeft = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TAG", "返回键");
                LeftOnClick();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuResId != 0 || !TextUtils.isEmpty(menuStr)) {
//            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (menuResId != 0) {
//            menu.findItem(R.id.menu_1).setIcon(menuResId);
//        }
//        if (!TextUtils.isEmpty(menuStr)) {
//            menu.findItem(R.id.menu_1).setTitle(menuStr);
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            LeftOnClick();
        } /*else if (item.getItemId() == R.id.menu_1) {
            RightOnClick();
        }*/
        return true; // true 告诉系统我们自己处理了点击事件
    }

    /**
     * 让菜单显示图标加文字
     */

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    /**
     * 左边按钮的点击事件
     */
    public void LeftOnClick() {
        Log.e("--------", "点击左边");
        finish();

    }


    /**
     * 右边按钮的点击事件
     */
    public void RightOnClick() {
        Log.e("--------", "点击右边按钮");
    }


    /**
     * 初始化数据--构造数据--网络数据等
     */
    protected abstract void initData();

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 封装跳转方式
     *
     * @param clazz  跳转到指定页面
     * @param finish 是否关闭当前页面
     */
    public void jumpToActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (finish) {
            finish();
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
            finish();
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
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        if (finish) {
            finish();
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
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        if (flags > 0) {
            intent.setFlags(flags);
        }

        startActivity(intent);
        if (finish) {
            finish();
        }
    }


    /**
     * 初始化基类参数--复写该方法处理是否可预先
     * 设置是否全屏
     */
    protected abstract void initBase();
        /* mContext = this;
        mActivity = this;
        //是否全屏
       if (isFullScreen) {
            //全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            // 仅api_19以上支持,5.0以后由样式设置沉浸式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // 透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            }
        }*/

    /**
     * 针对Android系统页面存在EditTextView是，判断是点击非EditText是否消失软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isTrue = super.dispatchTouchEvent(ev);//让系统的东西先执行,如果有

        //事件组合一直持续，为保证判定一次，故在MotionEvent.ACTION_UP下执行键盘事件
        View view = this.getCurrentFocus();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startY = ev.getY();
        }
        if (view != null && ev.getAction() == MotionEvent.ACTION_UP) {//屏幕上有光标，并且点击区域为EditText
            if (ClickEventUtils.isClickEditText(view, ev) || Math.abs(ev.getY() - startY) > 10) {//点击的是EditText或者手指滑动一段距离都不隐藏
                Log.e(TAG, "屏幕上有光标-点击的是输入框--不隐藏");
            } else {
                Log.e(TAG, "屏幕上有光标-点击的不是输入框--隐藏输入框");
                KeyBoardUtils.hideSoftInput(this);
            }
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return isTrue;
    }

    /**
     * Tost消息提醒
     */
    public void ShowToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void ShowToastLong(String text) {

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }


    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

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

    public void getDataFromInternet(String url, final RequestParams params, final int what) {
        if (!isNetworkAvailable(this)) {
            ShowToast(getString(R.string.not_network));
            cancleLoadingDialog();
            return;
        }

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        // TODO: 2017/8/17    asyncHttpClient.addHeader("content_type","  ");

        asyncHttpClient.setConnectTimeout(5000);
        asyncHttpClient.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                cancleLoadingDialog();
                ShowToast("网络连接错误 请稍候再试");

            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JSONObject jsonObject = JSON.parseObject(responseString);
                getSuccess(jsonObject, what);
            }

        });

    }

    public void getSuccess(com.alibaba.fastjson.JSONObject object, int what) {
        cancleLoadingDialog();
    }

    public Boolean isCorrentPasswrold(EditText text) {
        return ((text.getText().toString().trim().length() < 6) || (text.getText().toString().trim().length() > 18));
    }



}

