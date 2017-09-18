package www.cityguestsociety.com.firstfragmentactivity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.UrlFactory;
import www.cityguestsociety.com.adapter.PagerAdapter;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.GuoJiangTitle;

public class GuoJiangActivity extends BaseToolbarActivity {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    ArrayList<Fragment> fragments = new ArrayList<>();
    private List<String> mLists = new ArrayList<>();
    List<String> titleLists = new ArrayList<>();
    private PagerAdapter mPagerAdapter;

    @Override
    protected void initView() {
        initToobar("国匠风采");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_guo_jiang;
    }

    @Override
    protected void initData() {

        RequestParams params=new RequestParams();
        getDataFromInternet(UrlFactory.carpenter,params,0);
        showLoadingDialog();

        mLists.add("http://www.cnblogs.com/Coderwei2016/p/6149195.html");
        mLists.add("http://blog.csdn.net/changlei_shennan/article/details/51225229");
        mLists.add("http://blog.csdn.net/huweigoodboy/article/details/39878063");
        mLists.add("http://blog.csdn.net/huweigoodboy/article/details/39878063");
        mLists.add("http://www.jcodecraeer.com/a/opensource/2016/1023/6685.html");
        mLists.add("http://www.jcodecraeer.com/a/opensource/2016/1023/6685.html");
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what){
            case 0:
                Gson gson=new Gson();
                GuoJiangTitle guoJiangTitle = gson.fromJson(object.toString(), GuoJiangTitle.class);
                List<GuoJiangTitle.DataBean> dataLists = guoJiangTitle.getData();
                setAdapter(dataLists);
                break;
        }
    }

    private void setAdapter(List<GuoJiangTitle.DataBean> dataLists) {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments, dataLists);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected void setListener() {
        mTablayout.setupWithViewPager(mViewPager, true);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        mTablayout.setTabTextColors(getResources().getColor(R.color.balck), getResources().getColor(R.color.orange));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isShowBackImage = true;
    }

    @Override
    public void onClick(View v) {

    }


}
