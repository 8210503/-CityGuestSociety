package www.cityguestsociety.com.bindhouse;

import java.util.Comparator;

/**
 * Created by LuoPan on 2017/5/22 15:52.
 */
public class PinyinComparator implements Comparator<City> {

    public int compare(City o1, City o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
