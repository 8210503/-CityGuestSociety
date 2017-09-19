package www.cityguestsociety.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuoPan on 2017/9/18 14:19.
 */

public class Room implements Serializable {

    /**
     * pagecount : 0
     * info : 获取成功
     * data : [{"id":"13","room":"佛尔沙特"},{"id":"14","room":"大厦"}]
     * code : 1
     */

    private int pagecount;
    private String info;
    private int code;
    private List<DataBean> data;

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        public DataBean(String id, String room) {
            this.id = id;
            this.room = room;
        }

        public DataBean() {
        }

        /**
         * id : 13
         * room : 佛尔沙特
         */


        private String id;
        private String room;
        private String sortLetters;


        public String getSortLetters() {

            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }
}
