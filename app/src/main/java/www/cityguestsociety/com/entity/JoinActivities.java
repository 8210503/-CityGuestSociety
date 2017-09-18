package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/14 14:42.
 */

public class JoinActivities {

    /**
     * code : 1
     * info : 获取成功
     * pagecount : 3
     * data : [{"id":"51","title":"adsasd dasd","start_time":"1472918400","end_time":"1600876800","img":"/chengkehui/Uploads/Activity/2017-09-13/59b8e9f3aed8a.png","state":"1","max_num":"1231","sign":"1"}]
     */

    private int code;
    private String info;
    private String pagecount;
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

    public String getPagecount() {
        return pagecount;
    }

    public void setPagecount(String pagecount) {
        this.pagecount = pagecount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 51
         * title : adsasd dasd
         * start_time : 1472918400
         * end_time : 1600876800
         * img : /chengkehui/Uploads/Activity/2017-09-13/59b8e9f3aed8a.png
         * state : 1
         * max_num : 1231
         * sign : 1
         */

        private String id;
        private String title;
        private String start_time;
        private String end_time;
        private String img;
        private String state;
        private String max_num;
        private String sign;

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

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMax_num() {
            return max_num;
        }

        public void setMax_num(String max_num) {
            this.max_num = max_num;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
