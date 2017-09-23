package www.cityguestsociety.com.entity;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.Serializable;

/**
 * Created by LuoPan on 2017/9/15 15:35.
 */

public class Community implements Serializable {



        /*private String id;
    private String city;
    public String pinyin;
    private String sortLetters;//显示数据拼音的首字母*/


    public Community() {

    }

    /**
     * community : 大厦
     * id : 9
     */
    public String pinyin;
    private String community;
    private String id;
    private String sortLetters;

    public String getPinyin() {
        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

            format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
            /*format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            format.setVCharType(HanyuPinyinVCharType.WITH_V);*/
            return PinyinHelper.toHanyuPinyinString(community, format, pinyin);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {

            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return "#";
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getSortLetters() {

        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Community{" +
                "pinyin='" + pinyin + '\'' +
                ", community='" + community + '\'' +
                ", id='" + id + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }
}
