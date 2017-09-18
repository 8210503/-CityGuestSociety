package www.cityguestsociety.com.bindhouse;

import java.util.Comparator;

import www.cityguestsociety.com.entity.Community;

/**
 * Created by LuoPan on 2017/5/22 15:52.
 */
public class CommuntiyComparator implements Comparator<Community> {

    public int compare(Community o1, Community o2) {
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
