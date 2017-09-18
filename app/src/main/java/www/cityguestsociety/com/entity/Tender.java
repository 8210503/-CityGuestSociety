package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/7 16:37.
 */

public class Tender {

    /**
     * code : 0
     * info : 获取成功
     * pagecount : 8
     * data : [{"id":"8","title":"das","brief":"","img":"","creation_time":"0","state":"1"},{"id":"7","title":"das","brief":"","img":"","creation_time":"0","state":"1"},{"id":"6","title":"ad","brief":"","img":"","creation_time":"0","state":"1"},{"id":"5","title":"d","brief":"","img":"","creation_time":"0","state":"1"},{"id":"4","title":"d","brief":"","img":"","creation_time":"0","state":"1"},{"id":"3","title":"as","brief":"","img":"","creation_time":"0","state":"1"},{"id":"2","title":"as","brief":"","img":"","creation_time":"0","state":"1"},{"id":"1","title":"das","brief":"","img":"","creation_time":"0","state":"1"}]
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
         * id : 8
         * title : das
         * brief :
         * img :
         * creation_time : 0
         * state : 1
         */

        private String id;
        private String title;
        private String brief;
        private String img;
        private String creation_time;
        private String state;

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

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCreation_time() {
            return creation_time;
        }

        public void setCreation_time(String creation_time) {
            this.creation_time = creation_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
