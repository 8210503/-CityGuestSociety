package www.cityguestsociety.com.entity;

/**
 * Created by LuoPan on 2017/5/31 10:32.
 */
public class CommentInfo {
    /**
     * 评论ID
     */
    private int ID;
    /**
     * 评论人名称
     */
    private String nickname;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 被评论人名称
     */
    private String tonickname;

    @Override
    public String toString () {
        return "CommentInfo{" +
                "ID=" + ID +
                ", nickname='" + nickname + '\'' +
                ", comment='" + comment + '\'' +
                ", tonickname='" + tonickname + '\'' +
                '}';
    }

    /**
     * 下面可以继续写自定义需要的属性，需要传什么写什么
     */


    public int getID () {
        return ID;
    }

    public CommentInfo setID (final int mID) {
        ID = mID;
        return this;
    }

    public String getNickname () {
        return nickname;
    }

    public CommentInfo setNickname (final String mNickname) {
        nickname = mNickname;
        return this;
    }

    public String getComment () {
        return comment;
    }

    public CommentInfo setComment (final String mComment) {
        comment = mComment;
        return this;
    }

    public String getTonickname () {
        return tonickname;
    }

    public CommentInfo setTonickname (final String mTonickname) {
        tonickname = mTonickname;
        return this;
    }
}
