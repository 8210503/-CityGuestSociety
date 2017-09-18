package www.cityguestsociety.com.bindhouse;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.Serializable;

/**
 * city bean
 * Created by Administrator on 2017/3/22.
 */

public class City implements Serializable {
    private String id;
    private String city;
    public String pinyin;
    private String sortLetters;//显示数据拼音的首字母

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public City() {
    }

    public City(String city, String pinyin) {
        this.city = city;
        this.pinyin = pinyin;
    }

    public String getPinyin() {

        try {
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

            format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
            /*format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            format.setVCharType(HanyuPinyinVCharType.WITH_V);*/
            return PinyinHelper.toHanyuPinyinString(city, format, pinyin);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {

            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return "#";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }
}







