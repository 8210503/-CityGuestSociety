package www.cityguestsociety.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuoPan on 2017/5/16 15:44.
 */
public class Bean implements Serializable{
    public String text;
    public String Time;
    public int Bitmap;
    public List<String> imgLists;
    public String sellerAddress;
    List<PraiseInfo> mPraiseInfos;
    List<CommentInfo> mCommentInfos;

    public List<CommentInfo> getCommentInfos() {
        return mCommentInfos;
    }

    public void setCommentInfos(List<CommentInfo> commentInfos) {
        mCommentInfos = commentInfos;
    }

    public List<PraiseInfo> getPraiseInfos() {
        return mPraiseInfos;
    }

    public void setPraiseInfos(List<PraiseInfo> praiseInfos) {
        mPraiseInfos = praiseInfos;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "imgLists=" + imgLists +
                '}';
    }
    public Bean(String text, String time, List<String> imgLists, String sellerAddress, List<PraiseInfo> mPraiseInfos, String iamge, List<CommentInfo> commentInfo) {
        this.text = text;
        Time = time;
        this.mPraiseInfos=mPraiseInfos;
        this.imgLists = imgLists;
        this.sellerAddress = sellerAddress;
        this.iamge = iamge;
        this.mCommentInfos=commentInfo;
    }

    public Bean(String text, String time, List<String> imgLists, String sellerAddress, String textInfo, String iamge) {
        this.text = text;
        Time = time;
        this.imgLists = imgLists;
        this.sellerAddress = sellerAddress;
        this.textInfo = textInfo;
        this.iamge = iamge;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerAddress() {

        return sellerAddress;
    }

    public List<String> getImgLists() {

        return imgLists;
    }

    public void setImgLists(List<String> imgLists) {
        this.imgLists = imgLists;
    }

    public android.graphics.Bitmap getBookenImage() {
        return bookenImage;
    }

    public void setBookenImage(android.graphics.Bitmap bookenImage) {
        this.bookenImage = bookenImage;
    }

    public void setBitmap(int bitmap) {
        Bitmap = bitmap;
    }

    public Bean(String text, String time, String textInfo, String iamge, int bitmap) {
        this.text = text;
        Time = time;
        this.textInfo = textInfo;
        this.iamge = iamge;
        Bitmap = bitmap;
    }

    public android.graphics.Bitmap bookenImage;

    public Bean() {
    }

    public Bean(String text, int bitmap) {
        this.text = text;
        Bitmap = bitmap;
    }

    public int getBitmap() {

        return Bitmap;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Bean(String text, String time, String textInfo, String iamge) {
        this.text = text;
        Time = time;
        this.textInfo = textInfo;
        this.iamge = iamge;
    }

    public Bean(String text, String textInfo, String iamge) {
        this.text = text;
        this.textInfo = textInfo;
        this.iamge = iamge;
    }

    public String getText() {
        return text;

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public String getIamge() {
        return iamge;
    }

    public void setIamge(String iamge) {
        this.iamge = iamge;
    }

    public String textInfo;
    public String iamge;
}
