package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/7 17:14.
 */

public class Banner {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 0
     * data : [{"id":"16","img":"http://localhost/chengkehui/Uploads/Topbanner/2017-09-12/59b76e2d1b42e.jpg","creation_time":"1505193493"},{"id":"15","img":"http://localhost/chengkehui/Uploads/Topbanner/2017-09-12/59b76e07bba0e.png","creation_time":"1505193479"},{"id":"11","img":"http://localhost/chengkehui/Uploads/Topbanner/2017-09-12/59b73f5403d50.jpg","creation_time":"1505181524"},{"id":"10","img":"http://localhost/chengkehui/Uploads/Topbanner/2017-09-11/59b6ad08a279f.jpg","creation_time":"1505144072"},{"id":"9","img":"http://localhost/chengkehui/Uploads/Topbanner/2017-09-11/59b6acafebf1e.jpg","creation_time":"0"}]
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
        /**
         * id : 16
         * img : http://localhost/chengkehui/Uploads/Topbanner/2017-09-12/59b76e2d1b42e.jpg
         * creation_time : 1505193493
         */

        private String id;
        private String img;
        private String creation_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
