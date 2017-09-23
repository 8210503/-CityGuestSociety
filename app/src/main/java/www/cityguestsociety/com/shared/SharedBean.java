package www.cityguestsociety.com.shared;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/19 11:20.
 */

public class SharedBean {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 18
     * data : [{"id":"22","title":"冯滚滚滚滚滚","member_id":"4","release_time":"2017.09.23 16:56","wo":0,"img":[{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a083ae1.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0855ec.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0863b5.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a092917.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a092eab.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a09f6ac.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0ad162.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0b8a21.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0ba95f.jpg"}],"pub":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg"},"give":[{"reply_id":"2","nickname":"ifind"}],"collection":0,"givemi":1,"information":[{"reply_id":"2","content":"11111","cover_reply_id":"4","type":"2","reply_name":"ifind","cover_reply_name":"二点多发发发"}]},{"id":"21","title":"dffefe丹参滴丸我单位·","member_id":"4","release_time":"2017.09.23 16:30","wo":0,"img":[{"img":"/chengkehui/Uploads/Share/2017-09-23/59c61bc31b874.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c61bc31e291.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c61bc31e65e.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c61bc328fb3.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c61bc334873.jpg"}],"pub":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg"},"give":[{"reply_id":"2","nickname":"ifind"}],"collection":0,"givemi":1,"information":[]},{"id":"20","title":"dffefe丹参滴丸我单位·","member_id":"4","release_time":"2017.09.23 16:26","wo":0,"img":[{"img":"/chengkehui/Uploads/Share/2017-09-23/59c61abcd707a.jpg"}],"pub":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg"},"give":[{"reply_id":"2","nickname":"ifind"}],"collection":0,"givemi":1,"information":[]},{"id":"19","title":"dffefe丹参滴丸我单位·","member_id":"4","release_time":"2017.09.23 16:25","wo":0,"img":[],"pub":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg"},"give":[],"collection":0,"givemi":0,"information":[]},{"id":"18","title":"dffefe丹参滴丸我单位·","member_id":"4","release_time":"2017.09.23 16:25","wo":0,"img":[],"pub":{"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg"},"give":[],"collection":0,"givemi":0,"information":[]}]
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
         * id : 22
         * title : 冯滚滚滚滚滚
         * member_id : 4
         * release_time : 2017.09.23 16:56
         * wo : 0
         * img : [{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a083ae1.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0855ec.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0863b5.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a092917.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a092eab.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a09f6ac.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0ad162.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0b8a21.jpg"},{"img":"/chengkehui/Uploads/Share/2017-09-23/59c621a0ba95f.jpg"}]
         * pub : {"nickname":"二点多发发发","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg"}
         * give : [{"reply_id":"2","nickname":"ifind"}]
         * collection : 0
         * givemi : 1
         * information : [{"reply_id":"2","content":"11111","cover_reply_id":"4","type":"2","reply_name":"ifind","cover_reply_name":"二点多发发发"}]
         */

        private String id;
        private String title;
        private String member_id;
        private String release_time;
        private int wo;
        private PubBean pub;
        private int collection;
        private int givemi;
        private List<ImgBean> img;
        private List<GiveBean> give;
        private List<InformationBean> information;

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

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public int getWo() {
            return wo;
        }

        public void setWo(int wo) {
            this.wo = wo;
        }

        public PubBean getPub() {
            return pub;
        }

        public void setPub(PubBean pub) {
            this.pub = pub;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public int getGivemi() {
            return givemi;
        }

        public void setGivemi(int givemi) {
            this.givemi = givemi;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<GiveBean> getGive() {
            return give;
        }

        public void setGive(List<GiveBean> give) {
            this.give = give;
        }

        public List<InformationBean> getInformation() {
            return information;
        }

        public void setInformation(List<InformationBean> information) {
            this.information = information;
        }

        public static class PubBean {
            /**
             * nickname : 二点多发发发
             * img : http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-23/59c610ea3f798.jpg
             */

            private String nickname;
            private String img;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class ImgBean {
            /**
             * img : /chengkehui/Uploads/Share/2017-09-23/59c621a083ae1.jpg
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class GiveBean {

            public GiveBean(String reply_id, String nickname) {
                this.reply_id = reply_id;
                this.nickname = nickname;
            }

            /**
             * reply_id : 2
             * nickname : ifind
             */

            private String reply_id;
            private String nickname;

            public String getReply_id() {
                return reply_id;
            }

            public void setReply_id(String reply_id) {
                this.reply_id = reply_id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public static class InformationBean {
            public InformationBean(String reply_id, String content, String cover_reply_id, String type, String reply_name, String cover_reply_name) {
                this.reply_id = reply_id;
                this.content = content;
                this.cover_reply_id = cover_reply_id;
                this.type = type;
                this.reply_name = reply_name;
                this.cover_reply_name = cover_reply_name;
            }

            /**
             * reply_id : 2
             * content : 11111
             * cover_reply_id : 4
             * type : 2
             * reply_name : ifind
             * cover_reply_name : 二点多发发发
             */



            private String reply_id;
            private String content;
            private String cover_reply_id;
            private String type;
            private String reply_name;
            private String cover_reply_name;

            public String getReply_id() {
                return reply_id;
            }

            public void setReply_id(String reply_id) {
                this.reply_id = reply_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCover_reply_id() {
                return cover_reply_id;
            }

            public void setCover_reply_id(String cover_reply_id) {
                this.cover_reply_id = cover_reply_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getReply_name() {
                return reply_name;
            }

            public void setReply_name(String reply_name) {
                this.reply_name = reply_name;
            }

            public String getCover_reply_name() {
                return cover_reply_name;
            }

            public void setCover_reply_name(String cover_reply_name) {
                this.cover_reply_name = cover_reply_name;
            }
        }
    }
}
