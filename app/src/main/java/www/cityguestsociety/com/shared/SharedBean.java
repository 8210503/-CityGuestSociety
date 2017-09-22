package www.cityguestsociety.com.shared;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/19 11:20.
 */

public class SharedBean {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 4
     * data : [{"id":"6","title":"分享","member_id":"1","wo":0,"img":[{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8a19d630f1.jpg"}],"pub":{"nickname":"","img":""},"give":[{"reply_id":"26","nickname":"i被拒绝"}],"information":[{"reply_id":"26","content":"你好","cover_reply_id":"6","type":"2","reply_name":"i被拒绝","cover_reply_name":"的撒大声的撒"},{"reply_id":"26","content":"你好","cover_reply_id":"6","type":"3","reply_name":"i被拒绝","cover_reply_name":"的撒大声的撒"}]}]
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
         * id : 6
         * title : 分享
         * member_id : 1
         * wo : 0
         * img : [{"img":"/chengkehui/Uploads/Activity/2017-09-13/59b8a19d630f1.jpg"}]
         * pub : {"nickname":"","img":""}
         * give : [{"reply_id":"26","nickname":"i被拒绝"}]
         * information : [{"reply_id":"26","content":"你好","cover_reply_id":"6","type":"2","reply_name":"i被拒绝","cover_reply_name":"的撒大声的撒"},{"reply_id":"26","content":"你好","cover_reply_id":"6","type":"3","reply_name":"i被拒绝","cover_reply_name":"的撒大声的撒"}]
         */

        private String id;
        private String title;
        private String member_id;
        private int wo;
        private PubBean pub;
        private List<ImgBean> img;
        private List<GiveBean> give;
        private List<InformationBean> information;
        private String release_time;
        private int givemi;

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public int getGivemi() {
            return givemi;
        }

        public void setGivemi(int givemi) {
            this.givemi = givemi;
        }

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
             * nickname :
             * img :
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
             * img : /chengkehui/Uploads/Activity/2017-09-13/59b8a19d630f1.jpg
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
             * reply_id : 26
             * nickname : i被拒绝
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
            /**
             * reply_id : 26
             * content : 你好
             * cover_reply_id : 6
             * type : 2
             * reply_name : i被拒绝
             * cover_reply_name : 的撒大声的撒
             */

            private String reply_id;
            private String content;
            private String cover_reply_id;
            private String type;
            private String reply_name;
            private String cover_reply_name;

            public InformationBean(String reply_id, String content, String cover_reply_id, String type, String reply_name, String cover_reply_name) {
                this.reply_id = reply_id;
                this.content = content;
                this.cover_reply_id = cover_reply_id;
                this.type = type;
                this.reply_name = reply_name;
                this.cover_reply_name = cover_reply_name;
            }

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
