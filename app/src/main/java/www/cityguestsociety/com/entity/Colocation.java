package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/14 15:48.
 */

public class Colocation {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 6
     * data : [{"id":"2","share_id":"1","time":"1970.01.01 08:00","share":{"id":"1","title":"分享","member_id":"26","collection_num":"213","comment":"123"},"share_img":[{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"}],"member":{"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}},{"id":"7","share_id":"3","time":"1970.01.01 08:00","share":{"id":"3","title":"分享","member_id":"26","collection_num":"132","comment":"12"},"share_img":[{"img":"大13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"大神"}],"member":{"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}},{"id":"8","share_id":"1","time":"1970.01.01 08:00","share":{"id":"1","title":"分享","member_id":"26","collection_num":"213","comment":"123"},"share_img":[{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"}],"member":{"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}},{"id":"9","share_id":"1","time":"1970.01.01 08:00","share":{"id":"1","title":"分享","member_id":"26","collection_num":"213","comment":"123"},"share_img":[{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"}],"member":{"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}},{"id":"10","share_id":"1","time":"1970.01.01 08:00","share":{"id":"1","title":"分享","member_id":"26","collection_num":"213","comment":"123"},"share_img":[{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"}],"member":{"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}},{"id":"11","share_id":"1","time":"1970.01.01 08:00","share":{"id":"1","title":"分享","member_id":"26","collection_num":"213","comment":"123"},"share_img":[{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"}],"member":{"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}}]
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
         * id : 2
         * share_id : 1
         * time : 1970.01.01 08:00
         * share : {"id":"1","title":"分享","member_id":"26","collection_num":"213","comment":"123"}
         * share_img : [{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"},{"img":"13/59b8a15a3fdc1.jpg"}]
         * member : {"nickname":"i被拒绝","id":"26","img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png"}
         */

        private String id;
        private String share_id;
        private String time;
        private ShareBean share;
        private MemberBean member;
        private List<ShareImgBean> share_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public List<ShareImgBean> getShare_img() {
            return share_img;
        }

        public void setShare_img(List<ShareImgBean> share_img) {
            this.share_img = share_img;
        }

        public static class ShareBean {
            /**
             * id : 1
             * title : 分享
             * member_id : 26
             * collection_num : 213
             * comment : 123
             */

            private String id;
            private String title;
            private String member_id;
            private String collection_num;
            private String comment;

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

            public String getCollection_num() {
                return collection_num;
            }

            public void setCollection_num(String collection_num) {
                this.collection_num = collection_num;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }
        }

        public static class MemberBean {
            /**
             * nickname : i被拒绝
             * id : 26
             * img : http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-14/59ba20edd710b.png
             */

            private String nickname;
            private String id;
            private String img;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

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
        }

        public static class ShareImgBean {
            /**
             * img : 13/59b8a15a3fdc1.jpg
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
