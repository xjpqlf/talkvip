package dao.cn.com.talkvip.bean;



/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/7/17 14:49
 * @change uway
 * @chang 2017/7/17 14:49
 * @class describe
 */

public class ImageSave {

    /**
     * result : success
     * data : {"headurl":"/public/uploads/headimg/2017/0717/1500274077.jpg"}
     * code : 1031
     * msg : 上传头像成功！
     */

    private String result;
    private DataEntity data;
    private String code;
    private String msg;

    public void setResult(String result) {
        this.result = result;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public DataEntity getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * headurl : /public/uploads/headimg/2017/0717/1500274077.jpg
         */

        private String headurl;

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getHeadurl() {
            return headurl;
        }
    }
}
