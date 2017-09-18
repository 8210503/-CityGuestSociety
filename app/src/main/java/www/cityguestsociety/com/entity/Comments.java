package www.cityguestsociety.com.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/9/14 13:42.
 */

public class Comments {


    /**
     * code : 1
     * info : 获取成功
     * pagecount : 15
     * data : [{"type":"1","share_id":"1","content":"你好","reply_id":"26","member":{"img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-13/59b9050339214.png","id":"26","nickname":"i被拒绝"},"share":{"title":"分享"}},{"type":"2","share_id":"1","content":"你好","reply_id":"26","member":{"img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-13/59b9050339214.png","id":"26","nickname":"i被拒绝"},"share":{"title":"分享"}},{"type":"1","share_id":"1","content":"你好","reply_id":"26","member":{"img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-13/59b9050339214.png","id":"26","nickname":"i被拒绝"},"share":{"title":"分享"}},{"type":"2","share_id":"1","content":"你好","reply_id":"26","member":{"img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-13/59b9050339214.png","id":"26","nickname":"i被拒绝"},"share":{"title":"分享"}}]
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
         * type : 1
         * share_id : 1
         * content : 你好
         * reply_id : 26
         * member : {"img":"http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-13/59b9050339214.png","id":"26","nickname":"i被拒绝"}
         * share : {"title":"分享"}
         */

        private String type;
        private String share_id;
        private String content;
        private String reply_id;
        private MemberBean member;
        private ShareBean share;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShare_id() {
            return share_id;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public static class MemberBean {
            /**
             * img : http://120.26.141.238/chengkehui/Uploads/touxiang/2017-09-13/59b9050339214.png
             * id : 26
             * nickname : i被拒绝
             */

            private String img;
            private String id;
            private String nickname;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public static class ShareBean {
            /**
             * title : 分享
             */

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
