package www.cityguestsociety.com.shared;

/**
 * Created by LuoPan on 2017/5/29 20:51.
 */
public  class PraiseInfo {
    private int id;
    private String nickname;
    private String logo;

    @Override
    public String toString () {
        return "PraiseInfo{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }

    public int getId () {
        return id;
    }

    public PraiseInfo setId (final int mId) {
        id = mId;
        return this;
    }

    public String getNickname () {
        return nickname;
    }

    public PraiseInfo setNickname (final String mNickname) {
        nickname = mNickname;
        return this;
    }

    public String getLogo () {
        return logo;
    }

    public PraiseInfo setLogo (final String mLogo) {
        logo = mLogo;
        return this;
    }
}
