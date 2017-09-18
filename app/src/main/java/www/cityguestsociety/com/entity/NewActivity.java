package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/8 16:33.
 */

public class NewActivity {

    /**
     * pagecount : 0
     * info : 获取成功
     * data : [{"can":0,"community":"","end_time":"1757383810","release_time":"1757383810","sign":"0","max_num":"0","title":"","id":"3","start_time":"1757383810","state":"2","img":""},{"can":0,"community":"","end_time":"1757383810","release_time":"1441764610","sign":"0","max_num":"0","title":"213","id":"2","start_time":"1441764610","state":"1","img":""},{"can":0,"community":"12","end_time":"1441764610","release_time":"1441764610","sign":"10","max_num":"10","title":"das","id":"1","start_time":"1441764610","state":"0","img":"123"}]
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

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "can=" + can +
                    ", community='" + community + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", release_time='" + release_time + '\'' +
                    ", sign='" + sign + '\'' +
                    ", max_num='" + max_num + '\'' +
                    ", title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", state='" + state + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }

        /**
         * can : 0
         * community :
         * end_time : 1757383810
         * release_time : 1757383810
         * sign : 0
         * max_num : 0
         * title :
         * id : 3
         * start_time : 1757383810
         * state : 2
         * img :
         */


        private int can;
        private String community;
        private String end_time;
        private String release_time;
        private String sign;
        private String max_num;
        private String title;
        private String id;
        private String start_time;
        private String state;
        private String img;

        public int getCan() {
            return can;
        }

        public void setCan(int can) {
            this.can = can;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getMax_num() {
            return max_num;
        }

        public void setMax_num(String max_num) {
            this.max_num = max_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
