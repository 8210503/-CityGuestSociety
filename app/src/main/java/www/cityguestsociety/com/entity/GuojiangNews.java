package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/7 8:49.
 */

public class GuojiangNews {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 14
     * data : [{"id":"29","title":"dasas","img":"","creation_time":"0"},{"id":"28","title":"dasasda","img":"","creation_time":"0"},{"id":"27","title":"dasa","img":"","creation_time":"0"},{"id":"26","title":"das","img":"","creation_time":"0"},{"id":"25","title":"dasdas","img":"","creation_time":"0"},{"id":"24","title":"dasdasad","img":"","creation_time":"0"},{"id":"23","title":"dasdasad","img":"","creation_time":"0"},{"id":"22","title":"dsadsadaas","img":"","creation_time":"0"},{"id":"21","title":"asdsadasds","img":"","creation_time":"0"},{"id":"20","title":"dasdasdasd","img":"","creation_time":"0"}]
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    ", creation_time='" + creation_time + '\'' +
                    '}';
        }

        /**
         * id : 29
         * title : dasas
         * img :
         * creation_time : 0
         */


        private String id;
        private String title;
        private String img;
        private String creation_time;

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
    }
}
