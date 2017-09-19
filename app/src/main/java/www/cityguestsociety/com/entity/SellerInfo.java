package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/18 16:57.
 */

public class SellerInfo {

    /**
     * code : 1
     * info : 获取成功
     * pagecount : 8
     * data : [{"id":"11","title":"gsfsd","img":"/chengkehui/Uploads/Business/2017-09-17/59be3a7c2b942.jpg","address":"青羊区"},{"id":"8","title":"dasdas","img":"/chengkehui/Uploads/Business/2017-09-14/59ba407bcade6.png","address":"达大厦"},{"id":"6","title":"阿萨德","img":"/chengkehui/Uploads/Business/2017-09-14/59ba3d7a2d649.png","address":"成都"},{"id":"5","title":"313","img":"/chengkehui/Uploads/Business/2017-09-14/59ba3ca033235.png","address":"成都"},{"id":"4","title":"21312","img":"/chengkehui/Uploads/Business/2017-09-14/59ba3b6719099.png","address":""},{"id":"3","title":"","img":"/chengkehui/Uploads/Business/2017-09-14/59ba32c2f231b.png","address":""},{"id":"2","title":"asda","img":"","address":""},{"id":"1","title":"ads","img":"","address":""}]
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
         * id : 11
         * title : gsfsd
         * img : /chengkehui/Uploads/Business/2017-09-17/59be3a7c2b942.jpg
         * address : 青羊区
         */

        private String id;
        private String title;
        private String img;
        private String address;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
