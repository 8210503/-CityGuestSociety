package www.cityguestsociety.com.firstfragmentactivity.servicesactivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;

public class PropertyJFActivity extends BaseToolbarActivity {


    @BindView(R.id.houserAddressLinearRelative)
    RelativeLayout mHouserAddressLinearRelative;
    @BindView(R.id.radioButton1)
    RadioButton mRadioButton1;
    @BindView(R.id.radioButton2)
    RadioButton mRadioButton2;
    private FragmentManager mManager;
    private WaiteForJFFragment mWaiteForJFFragment;
    private AlreadyJFFragment mAlreadyJFFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_property_jf;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("物业缴费");
        mManager = getSupportFragmentManager();

        {
            FragmentTransaction transaction = mManager.beginTransaction();
            if (mWaiteForJFFragment == null) {
                mWaiteForJFFragment = new WaiteForJFFragment();
                transaction.add(R.id.JFContentRelaative, mWaiteForJFFragment);
            } else {
                transaction.show(mWaiteForJFFragment);
            }
            transaction.commit();
        }

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

    @OnClick({R.id.radioButton1, R.id.radioButton2})
    public void onClick(View v) {
        FragmentTransaction transaction = mManager.beginTransaction();
        hideFragments(transaction);
        switch (v.getId()) {
            case R.id.radioButton1:
                if (mWaiteForJFFragment == null) {
                    mWaiteForJFFragment = new WaiteForJFFragment();
                    transaction.add(R.id.JFContentRelaative, mWaiteForJFFragment);
                } else {
                    transaction.show(mWaiteForJFFragment);
                }

                transaction.commit();
                break;
            case R.id.radioButton2:
                if (mAlreadyJFFragment == null) {
                    mAlreadyJFFragment = new AlreadyJFFragment();
                    transaction.add(R.id.JFContentRelaative, mAlreadyJFFragment);
                } else {
                    transaction.show(mAlreadyJFFragment);
                }
                transaction.commit();

                break;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mWaiteForJFFragment != null) {
            transaction.hide(mWaiteForJFFragment);
        }
        if (mAlreadyJFFragment != null) {
            transaction.hide(mAlreadyJFFragment);
        }
    }

}
