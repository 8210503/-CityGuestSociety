package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/18 13:48.
 */

public class Unite {

    /**
     * pagecount : 0
     * info : 获取成功
     * data : [{"id":"1","label":"1单元"},{"id":"2","label":"2单元"},{"id":"3","label":"3单元"},{"id":"4","label":"打生打死"},{"id":"5","label":"四单元"},{"id":"6","label":"三单元"},{"id":"7","label":"但萨达萨达"},{"id":"8","label":"das"}]
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

        public DataBean(String id, String label) {
            this.id = id;
            this.label = label;
        }

        /**
         * id : 1
         * label : 1单元
         */



        private String id;
        private String label;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
