package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/12 11:45.
 */

public class QRCode {

    /**
     * pagecount : 0
     * info : 获取成功
     * data : [{"erweima":"http://120.26.141.238/Uploads/erweima/dd78e016613179ae8c189268746613b7.jpg","username":"15680905350","nickname":"21312313","ids":"2017092867","owner":{"community":"asdsad","city":"12312"},"img":"3231321312312312"}]
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
        /**
         * erweima : http://120.26.141.238/Uploads/erweima/dd78e016613179ae8c189268746613b7.jpg
         * username : 15680905350
         * nickname : 21312313
         * ids : 2017092867
         * owner : {"community":"asdsad","city":"12312"}
         * img : 3231321312312312
         */

        private String erweima;
        private String username;
        private String nickname;
        private String ids;
        private OwnerBean owner;
        private String img;

        public String getErweima() {
            return erweima;
        }

        public void setErweima(String erweima) {
            this.erweima = erweima;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIds() {
            return ids;
        }

        public void setIds(String ids) {
            this.ids = ids;
        }

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public static class OwnerBean {
            /**
             * community : asdsad
             * city : 12312
             */

            private String community;
            private String city;

            public String getCommunity() {
                return community;
            }

            public void setCommunity(String community) {
                this.community = community;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }
    }
}
