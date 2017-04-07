package dao.cn.com.talkvip.bean;

/**
 * @name dao.cn.com.talkvip
 * @class name：TalkVIp
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/3/28 11:06
 * @change uway
 * @chang 2017/3/28 11:06
 * @class describe
 */

public class Message {

private  String result;
private  String code;
private  String msg;
    private Data mData;

    public Message(String result, String code, String msg, Data data) {
        this.result = result;
        this.code = code;
        this.msg = msg;
        mData = data;
    }

    public Message() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }
}
