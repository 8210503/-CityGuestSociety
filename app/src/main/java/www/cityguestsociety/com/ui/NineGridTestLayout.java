package www.cityguestsociety.com.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import www.cityguestsociety.com.R;
import www.cityguestsociety.com.application.MyApplication;
import www.cityguestsociety.com.utils.ImageLoaderUtil;


/**
 * 时间：2016/5/12
 */
public class NineGridTestLayout extends NineGridLayout {
    private OnItemClickListener listener;//点击事件监听器
    protected static final int MAX_W_H_RATIO = 3;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NineGridTestLayout(Context context) {
        super(context);
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, final String url, final int parentWidth) {


        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int newW;
            int newH;
            if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                newW = parentWidth / 2;
                newH = newW * 5 / 3;
            } else if (h < w) {//h:w = 2:3
                newW = parentWidth * 2 / 3;
                newH = newW * 2 / 3;
            } else {//newH:h = newW :w
                newW = parentWidth / 2;
                newH = h * newW / w;
            }
            setOneImageLayoutParams(imageView, newW, newH,url);

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        //        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
        Glide.with(MyApplication.getContext()).load(url).error(R.drawable.wrong_image).placeholder(R.drawable.wrong_image).into(imageView);
    }

    @Override
    protected void onClickImage(View view, int i, String url, List<String> urlList) {
        //        Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();

        listener.onItemClick(view, i);
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
