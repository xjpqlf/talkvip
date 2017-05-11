package dao.cn.com.talkvip.bean;

import java.util.List;

/**
 * @name dao.cn.com.talkvip.bean
 * @class name：TalkVip
 * @class describe
 * @anthor uway QQ:343251588
 * @time 2017/5/5 14:28
 * @change uway
 * @chang 2017/5/5 14:28
 * @class describe
 */

public class RemarkDetail {

    private String result;
    private List<Remarks> data;
    private String code;
    private String msg;

    public RemarkDetail() {
    }

    public RemarkDetail(String result, List<Remarks> data, String code, String msg) {
        this.result = result;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Remarks> getData() {
        return data;
    }

    public void setData(List<Remarks> data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "RemarkDetail{" +
                "result='" + result + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
