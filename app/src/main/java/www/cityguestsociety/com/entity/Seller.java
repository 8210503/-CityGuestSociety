package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/18 15:07.
 */

public class Seller {

    /**
     * code : 1
     * info : 获取成功
     * pagecount : 0
     * data : [{"id":"9","title":"dasdasdad"},{"id":"8","title":"das "},{"id":"7","title":"大神"},{"id":"6","title":"大"},{"id":"5","title":"大神"},{"id":"4","title":"dasdasdas"},{"id":"3","title":"das "},{"id":"2","title":"大大"},{"id":"1","title":"大神"}]
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

        public DataBean(String id, String title) {
            this.id = id;
            this.title = title;
        }

        /**
         * id : 9
         * title : dasdasdad
         */


        private String id;
        private String title;

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
    }
}
