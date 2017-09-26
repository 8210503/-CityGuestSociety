package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/14 10:47.
 */

public class Annuncetion {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 0
     * data : [{"id":"3","title":"系统公告三","creation_time":"2017.09.26  09:43","state":0},{"id":"1","title":"系统公告一","creation_time":"2017.09.23  10:14","state":0}]
     */

    private int code;
    private String info;
    private int pagecount;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", creation_time='" + creation_time + '\'' +
                    ", state=" + state +
                    '}';
        }

        /**
         * id : 3
         * title : 系统公告三
         * creation_time : 2017.09.26  09:43
         * state : 0
         */


        private String id;
        private String title;
        private String creation_time;
        private int state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreation_time() {
            return creation_time;
        }

        public void setCreation_time(String creation_time) {
            this.creation_time = creation_time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

}
