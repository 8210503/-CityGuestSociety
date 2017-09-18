package www.cityguestsociety.com.bindhouse;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import www.cityguestsociety.com.R;
import www.cityguestsociety.com.baseui.BaseToolbarActivity;
import www.cityguestsociety.com.entity.Ban;
import www.cityguestsociety.com.entity.Community;

public class SelectHouseInfoActivity extends BaseToolbarActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.countryRelative)
    RelativeLayout mCountryRelative;
    @BindView(R.id.CommunityTextView)
    TextView mCommunityTextView;
    @BindView(R.id.CommunityRelatve)
    RelativeLayout mCommunityRelatve;
    @BindView(R.id.loudongTextView)
    TextView mLoudongTextView;
    @BindView(R.id.loudongRelative)
    RelativeLayout mLoudongRelative;
    @BindView(R.id.houseIDTextview)
    TextView mHouseIDTextview;
    @BindView(R.id.houseIDRelative)
    RelativeLayout mHouseIDRelative;
    @BindView(R.id.houserAddressLinear)
    LinearLayout mHouserAddressLinear;
    @BindView(R.id.houserInfoRelative)
    RelativeLayout mHouserInfoRelative;
    @BindView(R.id.userNumber_7)
    TextView mUserNumber7;
    @BindView(R.id.textViewLine)
    TextView mTextViewLine;
    @BindView(R.id.sellerPhoneLast4)
    EditText mSellerPhoneLast4;
    @BindView(R.id.imageView3)
    ImageView mImageView3;
    @BindView(R.id.userinfoRelative)
    RelativeLayout mUserinfoRelative;
    @BindView(R.id.beginCheck)
    Button mBeginCheck;
    public final int CITY = 1;
    public final int COMMUNITY = 2;
    public final int LOUDONG = 3;
    public final int HOUSE = 4;
    private City mCity = new City();
    private String mId = "";
    private Community mCommunity;
    private Ban.DataBean mBan;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_house_info;
    }

    @Override
    protected void initData() {

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
        initToobar("选择房屋信息");
    }


    @OnClick({R.id.countryRelative, R.id.CommunityRelatve, R.id.loudongRelative, R.id.houseIDRelative, R.id.houserInfoRelative, R.id.beginCheck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.countryRelative:
                /**城市*/
                Intent intent = new Intent(this, SelectContryActivity.class);
                startActivityForResult(intent, CITY);
                break;
            case R.id.CommunityRelatve:
                /**社区*/
                if (mId.isEmpty()) {
                    ShowToast("请先选择城市");
                } else {
                    intent = new Intent(this, SelectCommunityActivity.class);
                    LogUtils.e(mId);
                    intent.putExtra("id", mId);
                    startActivityForResult(intent, COMMUNITY);
                }
                break;
            case R.id.loudongRelative:
                /**楼栋*/
                if (mId.isEmpty()) {
                    ShowToast("请先选择城市");
                } else if (mCommunity == null) {
                    ShowToast("请先选择社区");
                } else {
                    intent = new Intent(this, SlectLouDongActivity.class);
                    intent.putExtra("id", mCommunity.getId());
                    startActivityForResult(intent, LOUDONG);
                }
                break;
            case R.id.houseIDRelative:
                /**单元房号*/
                if (mId.isEmpty()) {
                    ShowToast("请先选择城市");
                } else if (mCommunity == null) {
                    ShowToast("请先选择社区");
                } else if (mBan == null) {
                    ShowToast("请先选择楼栋号");
                } else {
                    intent = new Intent(this, HouseIDActivity.class);
                    intent.putExtra("id", mBan.getId());
                    startActivityForResult(intent, HOUSE);
                }
                break;

            case R.id.beginCheck:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SelectContryActivity.RESULTCODE) {
            switch (requestCode) {
                case CITY:
                    mCity = (City) data.getSerializableExtra(SelectContryActivity.RESULT);
                    mId = mCity.getId();
                    LogUtils.e(mId);
                    mTvCity.setText(mCity.getCity());
                    break;
                case COMMUNITY:
                    mCommunity = (Community) data.getSerializableExtra(SelectCommunityActivity.RESULT);
                    mCommunityTextView.setText(mCommunity.getCommunity());
                    break;
                case LOUDONG:
                    mBan = (Ban.DataBean) data.getSerializableExtra(SlectLouDongActivity.RESULT);
                    mLoudongTextView.setText(mBan.getBan());
                    break;
                case HOUSE:
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
